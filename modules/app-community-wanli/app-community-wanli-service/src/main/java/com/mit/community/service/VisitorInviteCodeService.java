package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.HighModel;
import com.mit.community.entity.VisitorInviteCode;
import com.mit.community.mapper.HighModelMapper;
import com.mit.community.mapper.VisitorInviteCodeMapper;
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import com.mit.community.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitorInviteCodeService {
    @Autowired
    private VisitorInviteCodeMapper visitorInviteCodeMapper;
    @Autowired
    private PersonLabelsMapper personLabelsMapper;
    @Autowired
    private HighModelMapper highModelMapper;
    @Autowired
    private HighModelService highModelService;

    public void update (VisitorInviteCode visitorInviteCode) {
        visitorInviteCodeMapper.updateById(visitorInviteCode);
    }

    public VisitorInviteCode getById(Integer id) {
        EntityWrapper<VisitorInviteCode> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<VisitorInviteCode> list = visitorInviteCodeMapper.selectList(wrapper);
        if (list.isEmpty()){
            return null;
        } else {
            return list.get(0);
        }
    }

    public Integer getByDeviceGroupIdAndPassWord(Integer deviceGroupId, String passWord ) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        EntityWrapper<VisitorInviteCode> wrapper = new EntityWrapper<>();
        wrapper.eq("invite_code", passWord);
        wrapper.ge("gmt_create", getPastDate(30));
        wrapper.eq("device_group_id", deviceGroupId);
        List<VisitorInviteCode> list = visitorInviteCodeMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            VisitorInviteCode visitorInviteCode = list.get(0);
            if (visitorInviteCode.getModelType() == 1) {//简单模式
                if (visitorInviteCode.getTimes() == 0) {
                    return visitorInviteCode.getId();
                } else {
                    if (visitorInviteCode.getUseTimes() ==0) {
                        int capmpareTo = sdf.parse(getTime()).compareTo(visitorInviteCode.getExpiryDate());
                        if (capmpareTo == 1) {//过期了
                            return 0;
                        } else {
                            return visitorInviteCode.getId();
                        }
                    } else {
                        return 0;
                    }
                }
            } else {//复杂模式
                EntityWrapper<HighModel> wrapperHighModel = new EntityWrapper<>();
                wrapperHighModel.eq("visitor_invite_code_id", visitorInviteCode.getId());
                wrapperHighModel.eq("timeDay" , getTime());
                List<HighModel> listHighModel = highModelMapper.selectList(wrapperHighModel);
                if (!listHighModel.isEmpty()) {
                    Calendar now = Calendar.getInstance();
                    String hour = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
                    String min = String.valueOf(now.get(Calendar.MINUTE));
                    int nowTime = Integer.parseInt(hour + min);
                    boolean flag = false;
                    for (int i=0; i<listHighModel.size(); i++){
                        int startTime = Integer.parseInt(listHighModel.get(i).getTimeStart().replace(":", ""));
                        int endTime = Integer.parseInt(listHighModel.get(i).getTimeEnd().replace(":", ""));
                        if (nowTime >= startTime && nowTime <= endTime) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        return visitorInviteCode.getId();
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
    }

    @Transactional
    public String getHighModelInviteCode (String cellphone, String deviceGroupId, String communityCode, String days, String timeQuantum) {
        String message = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date day = null;
        try {
            JSONArray jsonDays = JSON.parseArray(days);
            List<Date> dateList = new ArrayList<>();
            for (int i=0 ; i<jsonDays.size(); i++) {
                dateList.add(sdf.parse(jsonDays.getString(i)));
            }

            JSONArray jsonTimeQuantums = JSON.parseArray(timeQuantum);
            List<String> timeQuantumList = new ArrayList<>();
            for (int i=0 ; i<jsonTimeQuantums.size(); i++) {
                timeQuantumList.add(jsonTimeQuantums.getString(i));
            }

            EntityWrapper<HighModel> wrapper = new EntityWrapper<>();
            wrapper.in("b.time_day", dateList);
            wrapper.eq("a.cellphone",cellphone);
            wrapper.eq("a.community_code",communityCode);
            wrapper.eq("a.device_group_id",deviceGroupId);
            List<HighModel> listExtis = personLabelsMapper.getExtisHighModel(wrapper);
            if (!listExtis.isEmpty()) {
                if (isCanApplyHighModel(listExtis, dateList, timeQuantumList)) {//可以添加
                    String inviteCode = saveHighModel ( cellphone, deviceGroupId, communityCode, 2, dateList, timeQuantumList);
                    message = inviteCode;
                } else {
                    message = "有时间段重合，无法添加";
                }
            } else {//可以添加
                String inviteCode = saveHighModel ( cellphone, deviceGroupId, communityCode, 2, dateList, timeQuantumList);
                message = inviteCode;
            }
        } catch (Exception e){
            return "出现错误";
        }
        return message;
    }

    public String saveHighModel (String cellphone, String deviceGroupId, String communityCode, int modelType, List<Date> dateList, List<String> timeQuantumList) {
        EntityWrapper<VisitorInviteCode> wrapperAvailable = new EntityWrapper<>();//有效的密码
        wrapperAvailable.ge("gmt_create", getPastDate(30));
        //wrapperAvailable.le("gmt_create", getFetureDate(1));
        wrapperAvailable.eq("device_group_id", deviceGroupId);
        List<VisitorInviteCode> listAvailable = visitorInviteCodeMapper.selectList(wrapperAvailable);
        List<String> inviteCodeAvailable = listAvailable.parallelStream().map(VisitorInviteCode::getInviteCode).collect(Collectors.toList());
        String inviteCode = getNoRepeatCode(inviteCodeAvailable);

        VisitorInviteCode visitorInviteCode = new VisitorInviteCode();
        visitorInviteCode.setCellphone(cellphone);
        visitorInviteCode.setTimes(1);
        visitorInviteCode.setDeviceGroupId(deviceGroupId);
        visitorInviteCode.setCommunityCode(communityCode);
        visitorInviteCode.setUseTimes(0);
        visitorInviteCode.setInviteCode(inviteCode);
        visitorInviteCode.setGmtCreate(LocalDateTime.now());
        visitorInviteCode.setGmtModified(LocalDateTime.now());
        visitorInviteCode.setModelType(modelType);
        visitorInviteCodeMapper.insert(visitorInviteCode);

        List<HighModel> list = new ArrayList<>();
        Integer visitorInviteCodeId = visitorInviteCode.getId();
        HighModel highModel = null;
        for (int a=0; a<dateList.size(); a++) {
            for (int b=0; b<timeQuantumList.size(); b++) {
                highModel = new HighModel();
                String[] times = timeQuantumList.get(b).split("-");
                String startTime = times[0];
                String endTime = times[1];
                highModel.setVisitorInviteCodeId(visitorInviteCodeId);
                highModel.setTimeDay(dateList.get(a));
                highModel.setTimeStart(startTime);
                highModel.setTimeEnd(endTime);
                highModel.setGmtCreate(LocalDateTime.now());
                highModel.setGmtModified(LocalDateTime.now());
                list.add(highModel);
            }
        }
        highModelService.insertBatch(list);
        return inviteCode;
    }

    public boolean isCanApplyHighModel(List<HighModel> listExtis, List<Date> dateList, List<String> timeQuantumList) {
        boolean flag = true;
        for (int a=0; a<listExtis.size(); a++ ) {
            for (int b=0; b<dateList.size(); b++) {
                if (listExtis.get(a).getTimeDay() == dateList.get(b)) {//同一
                    for (int c=0; c<timeQuantumList.size(); c++) {
                        String[] times = timeQuantumList.get(c).split("-");
                        int timeStart = Integer.parseInt(times[0].replace(":", ""));
                        int timeEnd = Integer.parseInt(times[1].replace(":", ""));

                        int sqlStart = Integer.parseInt(listExtis.get(a).getTimeStart().replace(":", ""));
                        int sqlEnd = Integer.parseInt(listExtis.get(a).getTimeEnd().replace(":", ""));
                        if ((sqlStart <= timeStart && timeStart <= sqlEnd) || (sqlStart <= timeEnd && timeEnd <= sqlEnd)) {//时间段上有重合
                            flag = false;
                            break;
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        //Log.e(null, result);
        return result;
    }

    @Transactional
    public String getInviteCode(String cellphone, String dateTag, String times, String deviceGroupId, String communityCode) throws Exception{
        String message = null;
        Date expiryDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if ("0".equals(dateTag)) {
            expiryDate = sdf.parse(getTime());
        } else {
            expiryDate = sdf.parse(getFetureDate(1));
        }
        EntityWrapper<VisitorInviteCode> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellphone);
        wrapper.ge("expiry_date", getTime());
        wrapper.le("expiry_date", getFetureDate(1));
        wrapper.eq("device_group_id", deviceGroupId);
        List<VisitorInviteCode> list = visitorInviteCodeMapper.selectList(wrapper);//该电话号码今天和明天在该设备组下申请的所有记录
        if (list.isEmpty()) {//直接添加
            String inviteCode = save( cellphone, dateTag, times, deviceGroupId, communityCode, expiryDate, 1);
            message = inviteCode;
        } else {
            if (isCanApply(list , dateTag)) {
                String inviteCode = save( cellphone, dateTag, times, deviceGroupId, communityCode, expiryDate, 1);
                message = inviteCode;
            } else {
                message = "当前不能申请";
            }
        }
        return message;
    }


    public boolean isCanApply(List<VisitorInviteCode> list, String dateTag) {
        boolean flag = true;
        for (VisitorInviteCode visitorInviteCode : list) {
            if (visitorInviteCode.getTimes() == 0) { // 可以用无数次的
                if (visitorInviteCode.getDataTag() == 1) {//已经有明天才到期的可使用无数次的邀请码
                    flag = false;
                    break;
                } else { //今天内有效的可以用无数次的
                    if (Integer.parseInt(dateTag) == 0) {//用户申请的也是今天的
                        flag = false;
                        break;
                    }
                }
            } else {//只能用一次的
                if (Integer.parseInt(dateTag) == visitorInviteCode.getDataTag() && visitorInviteCode.getUseTimes() ==0) { //申请的类型已经有可用的邀请码
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public String save(String cellphone, String dateTag, String times, String deviceGroupId, String communityCode, Date expiryDate, int modelType) {
        EntityWrapper<VisitorInviteCode> wrapperAvailable = new EntityWrapper<>();//有效的密码
        wrapperAvailable.ge("gmt_create", getPastDate(30));
        wrapperAvailable.eq("device_group_id", deviceGroupId);
        List<VisitorInviteCode> listAvailable = visitorInviteCodeMapper.selectList(wrapperAvailable);
        List<String> inviteCodeAvailable = listAvailable.parallelStream().map(VisitorInviteCode::getInviteCode).collect(Collectors.toList());
        String inviteCode = getNoRepeatCode(inviteCodeAvailable);

        VisitorInviteCode visitorInviteCode = new VisitorInviteCode();
        visitorInviteCode.setCellphone(cellphone);
        if (StringUtils.isNotBlank(dateTag)) {
            visitorInviteCode.setDataTag(Integer.parseInt(dateTag));
        }
        visitorInviteCode.setTimes(Integer.parseInt(times));
        visitorInviteCode.setDeviceGroupId(deviceGroupId);
        visitorInviteCode.setCommunityCode(communityCode);
        if (expiryDate != null) {
            visitorInviteCode.setExpiryDate(expiryDate);
        }
        visitorInviteCode.setUseTimes(0);
        visitorInviteCode.setInviteCode(inviteCode);
        visitorInviteCode.setGmtCreate(LocalDateTime.now());
        visitorInviteCode.setGmtModified(LocalDateTime.now());
        visitorInviteCode.setModelType(modelType);
        visitorInviteCodeMapper.insert(visitorInviteCode);
        return inviteCode;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        //Log.e(null, result);
        return result;
    }

    public String getTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        System.out.println("格式化后的日期：" + dateNowStr);
        return dateNowStr;
    }


    public String getNoRepeatCode(List<String> exitsCode) {//获取不重复的邀请码
        String code = String.valueOf(Math.round(Math.random() * 10000));
        if (exitsCode.contains(code)) {
            getNoRepeatCode(exitsCode);
        } else {
            return code;
        }
        return code;
    }

}

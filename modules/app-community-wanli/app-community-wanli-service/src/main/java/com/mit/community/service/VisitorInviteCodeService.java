package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.VisitorInviteCode;
import com.mit.community.mapper.VisitorInviteCodeMapper;
import com.mit.community.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Integer getByDeviceGroupIdAndPassWord(Integer deviceGroupId, String passWord ) {
        EntityWrapper<VisitorInviteCode> wrapper = new EntityWrapper<>();
        wrapper.eq("inviteCode", passWord);
        wrapper.le("expiry_date", getTime());
        wrapper.le("expiry_date", getFetureDate(1));
        wrapper.eq("device_group_id", deviceGroupId);
        List<VisitorInviteCode> list = visitorInviteCodeMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            VisitorInviteCode visitorInviteCode = list.get(0);
            if (visitorInviteCode.getTimes() == 0) {
                return visitorInviteCode.getId();
            } else {
                if (visitorInviteCode.getUseTimes() ==0) {
                    return visitorInviteCode.getId();
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
    }

    @Transactional
    public String getInviteCode(String cellphone, String dateTag, String times, String deviceGroupId, String communityCode) throws Exception{
        String message = "";
        Date expiryDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if ("0".equals(dateTag)) {
            expiryDate = sdf.parse(getTime());
        } else {
            expiryDate = sdf.parse(getFetureDate(1));
        }
        EntityWrapper<VisitorInviteCode> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellphone);
        wrapper.le("expiry_date", getTime());
        wrapper.le("expiry_date", getFetureDate(1));
        wrapper.eq("device_group_id", deviceGroupId);
        List<VisitorInviteCode> list = visitorInviteCodeMapper.selectList(wrapper);//该电话号码今天和明天在该设备组下申请的所有记录
        if (list.isEmpty()) {//直接添加
            EntityWrapper<VisitorInviteCode> wrapperAvailable = new EntityWrapper<>();//有效的密码
            wrapperAvailable.ge("expiry_date", getTime());
            wrapperAvailable.le("expiry_date", getFetureDate(1));
            wrapperAvailable.eq("device_group_id", deviceGroupId);
            List<VisitorInviteCode> listAvailable = visitorInviteCodeMapper.selectList(wrapper);
            List<String> inviteCodeAvailable = listAvailable.parallelStream().map(VisitorInviteCode::getInviteCode).collect(Collectors.toList());
            String inviteCode = getNoRepeatCode(inviteCodeAvailable);
            save( cellphone, dateTag, times, deviceGroupId, communityCode, expiryDate, inviteCode);
            message = inviteCode;
        } else {
            if (isCanApply(list , dateTag)) {
                EntityWrapper<VisitorInviteCode> wrapperAvailable = new EntityWrapper<>();//有效的密码
                wrapperAvailable.ge("expiry_date", getTime());
                wrapperAvailable.le("expiry_date", getFetureDate(1));
                wrapperAvailable.eq("device_group_id", deviceGroupId);
                List<VisitorInviteCode> listAvailable = visitorInviteCodeMapper.selectList(wrapper);
                List<String> inviteCodeAvailable = listAvailable.parallelStream().map(VisitorInviteCode::getInviteCode).collect(Collectors.toList());
                String inviteCode = getNoRepeatCode(inviteCodeAvailable);
                save( cellphone, dateTag, times, deviceGroupId, communityCode, expiryDate, inviteCode);
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

    public void save(String cellphone, String dateTag, String times, String deviceGroupId, String communityCode, Date expiryDate, String inviteCode) {
        VisitorInviteCode visitorInviteCode = new VisitorInviteCode();
        visitorInviteCode.setCellphone(cellphone);
        visitorInviteCode.setDataTag(Integer.parseInt(dateTag));
        visitorInviteCode.setTimes(Integer.parseInt(times));
        visitorInviteCode.setDeviceGroupId(deviceGroupId);
        visitorInviteCode.setCommunityCode(communityCode);
        visitorInviteCode.setExpiryDate(expiryDate);
        visitorInviteCode.setUseTimes(0);
        visitorInviteCode.setInviteCode(inviteCode);
        visitorInviteCode.setGmtCreate(LocalDateTime.now());
        visitorInviteCode.setGmtModified(LocalDateTime.now());
        visitorInviteCodeMapper.insert(visitorInviteCode);
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

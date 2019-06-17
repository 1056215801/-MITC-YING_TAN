package com.mit.community.service.data.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.mapper.data.dao.SmokeDetectorStatusMapper;
import com.mit.community.model.SmokeDetectorStatus;
import com.mit.community.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 烟感感知
 *
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@Service
public class SmokeDetectorStatusService extends ServiceImpl<SmokeDetectorStatusMapper, SmokeDetectorStatus> {

    @Autowired
    private SmokeDetectorStatusMapper smokeDetectorStatusMapper;

    /**
     * 分页查询
     *
     * @param deviceNum
     * @param gmtWarnStart
     * @param pageNum
     * @param pageSize
     * @return com.baomidou.mybatisplus.plugins.Page<com.mit.community.rest.modular.makedata.model.WellLid>
     * @throws
     * @author shuyy
     * @date 2019-01-04 14:50
     * @company mitesofor
     */
    public Page<SmokeDetectorStatus> listPage(String communityCode, String deviceNum, String devicePlace, Short status,
                                              Short deviceStatus, LocalDate gmtWarnStart,
                                              LocalDate gmtUploadEnd, Integer pageNum,
                                              Integer pageSize) {

        EntityWrapper<SmokeDetectorStatus> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(deviceNum)) {
            wrapper.eq("device_num", deviceNum);
        }
        if (StringUtils.isNotBlank(devicePlace)) {
            wrapper.eq("device_place", devicePlace);
        }
        if (null != status) {
            wrapper.eq("status", status);
        }
        if (null != deviceStatus) {
            wrapper.eq("device_status", deviceStatus);
        }
        if (null != gmtWarnStart) {
            wrapper.ge("gmt_upload", gmtWarnStart);
        }
        if (null != gmtUploadEnd) {
            wrapper.le("gmt_upload", gmtUploadEnd);
        }
        wrapper.orderBy("gmt_upload", false);
        Page<SmokeDetectorStatus> page = new Page<>(pageNum, pageSize);
        List<SmokeDetectorStatus> list = smokeDetectorStatusMapper.selectPage(page, wrapper);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (SmokeDetectorStatus smokeDetectorStatus : list) {
            //获取时间
            LocalDateTime time = smokeDetectorStatus.getGmtUpload();
            String localTime = df.format(time);
            //2019-01-28 14:00:00
            if (localTime.substring(11, 13).equals("14")) {
                localTime = localTime.replace("14", "13");
            }
            if (localTime.substring(0, 10).equals("2019-01-28")) {
                Date date = new Date();
                String newTime = sdf.format(date) + localTime.substring(10, localTime.length());
                //转为LocalDateTime
                LocalDateTime ldt = LocalDateTime.parse(newTime, df);
                smokeDetectorStatus.setGmtUpload(ldt);
            } else if (localTime.substring(0, 10).equals("2019-01-27")) {
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                date = calendar.getTime();
                String newTime = sdf.format(date) + localTime.substring(10, localTime.length());
                //转为LocalDateTime
                LocalDateTime ldt = LocalDateTime.parse(newTime, df);
                smokeDetectorStatus.setGmtUpload(ldt);
            } else if (localTime.substring(0, 10).equals("2019-01-26")) {
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, -2);
                date = calendar.getTime();
                String newTime = sdf.format(date) + localTime.substring(10, localTime.length());
                //转为LocalDateTime
                LocalDateTime ldt = LocalDateTime.parse(newTime, df);
                smokeDetectorStatus.setGmtUpload(ldt);
            }
        }
        page.setRecords(list);
        return page;
    }

    public void insertDataKXWT() {
        String communityCode = "0125caffaae1472b996390e869129cc7";
        String[] deviceNameArray = {"1栋-1单元-1层", "1栋-1单元-2层", "1栋-1单元-3层", "1栋-1单元-4层", "1栋-1单元-5层", "1栋-1单元-6层", "1栋-1单元-7层", "1栋-1单元-8层", "1栋-1单元-9层", "1栋-1单元-10层", "1栋-1单元-11层", "1栋-1单元-12层", "1栋-1单元-13层", "1栋-1单元-14层", "1栋-1单元-15层", "1栋-1单元-16层", "1栋-1单元-17层", "1栋-2单元-1层", "1栋-2单元-2层", "1栋-2单元-3层", "1栋-2单元-4层", "1栋-2单元-5层", "1栋-2单元-6层", "1栋-2单元-7层", "1栋-2单元-8层", "1栋-2单元-9层", "1栋-2单元-10层", "1栋-2单元-11层", "1栋-2单元-12层", "1栋-2单元-13层", "1栋-2单元-14层", "1栋-2单元-15层", "1栋-2单元-16层", "1栋-2单元-17层", "2栋-1单元-1层", "2栋-1单元-2层", "2栋-1单元-3层", "2栋-1单元-4层", "2栋-1单元-5层", "2栋-1单元-6层", "2栋-1单元-7层", "2栋-1单元-8层", "2栋-1单元-9层", "2栋-1单元-10层", "2栋-1单元-11层", "2栋-1单元-12层", "2栋-1单元-13层", "2栋-1单元-14层", "2栋-1单元-15层", "2栋-1单元-16层", "2栋-1单元-17层", "2栋-1单元-18层", "2栋-1单元-19层", "2栋-1单元-20层", "2栋-1单元-21层", "2栋-1单元-22层", "2栋-1单元-23层", "2栋-1单元-24层", "2栋-2单元-1层", "2栋-2单元-2层", "2栋-2单元-3层", "2栋-2单元-4层", "2栋-2单元-5层", "2栋-2单元-6层", "2栋-2单元-7层", "2栋-2单元-8层", "2栋-2单元-9层", "2栋-2单元-10层", "2栋-2单元-11层", "2栋-2单元-12层", "2栋-2单元-13层", "2栋-2单元-14层", "2栋-2单元-15层", "2栋-2单元-16层", "2栋-2单元-17层", "2栋-2单元-18层", "2栋-2单元-19层", "2栋-2单元-20层", "2栋-2单元-21层", "2栋-2单元-22层", "2栋-2单元-23层", "2栋-2单元-24层", "3栋-1单元-1层", "3栋-1单元-2层", "3栋-1单元-3层", "3栋-1单元-4层", "3栋-1单元-5层", "3栋-1单元-6层", "3栋-1单元-7层", "3栋-1单元-8层", "3栋-1单元-9层", "3栋-1单元-10层", "3栋-1单元-11层", "3栋-1单元-12层", "3栋-1单元-13层", "3栋-1单元-14层", "3栋-1单元-15层", "3栋-1单元-16层", "3栋-1单元-17层", "3栋-1单元-18层", "3栋-1单元-19层", "3栋-1单元-20层", "3栋-1单元-21层", "3栋-1单元-22层", "3栋-1单元-23层", "4栋-1单元-1层", "4栋-1单元-2层", "4栋-1单元-3层", "4栋-1单元-4层", "4栋-1单元-5层", "4栋-1单元-6层", "4栋-1单元-7层", "4栋-1单元-8层", "4栋-1单元-9层", "4栋-1单元-10层", "4栋-1单元-11层", "4栋-1单元-12层", "4栋-1单元-13层", "4栋-1单元-14层", "4栋-1单元-15层", "4栋-1单元-16层", "4栋-1单元-17层", "4栋-2单元-1层", "4栋-2单元-2层", "4栋-2单元-3层", "4栋-2单元-4层", "4栋-2单元-5层", "4栋-2单元-6层", "4栋-2单元-7层", "4栋-2单元-8层", "4栋-2单元-9层", "4栋-2单元-10层", "4栋-2单元-11层", "4栋-2单元-12层", "4栋-2单元-13层", "4栋-2单元-14层", "4栋-2单元-15层", "4栋-2单元-16层", "4栋-2单元-17层", "5栋-1单元-1层", "5栋-1单元-2层", "5栋-1单元-3层", "5栋-1单元-4层", "5栋-1单元-5层", "5栋-1单元-6层", "5栋-1单元-7层", "5栋-1单元-8层", "5栋-1单元-9层", "5栋-1单元-10层", "5栋-1单元-11层", "5栋-1单元-12层", "5栋-1单元-13层", "5栋-1单元-14层", "5栋-1单元-15层", "5栋-1单元-16层", "5栋-1单元-17层", "6栋-1单元-1层", "6栋-1单元-2层", "6栋-1单元-3层", "6栋-1单元-4层", "6栋-1单元-5层", "6栋-1单元-6层", "6栋-1单元-7层", "6栋-1单元-8层", "6栋-1单元-9层", "6栋-1单元-10层", "6栋-1单元-11层", "6栋-1单元-12层", "6栋-1单元-13层", "6栋-1单元-14层", "6栋-1单元-15层", "6栋-1单元-16层", "6栋-1单元-17层", "6栋-2单元-1层", "6栋-2单元-2层", "6栋-2单元-3层", "6栋-2单元-4层", "6栋-2单元-5层", "6栋-2单元-6层", "6栋-2单元-7层", "6栋-2单元-8层", "6栋-2单元-9层", "6栋-2单元-10层", "6栋-2单元-11层", "6栋-2单元-12层", "6栋-2单元-13层", "6栋-2单元-14层", "6栋-2单元-15层", "6栋-2单元-16层", "6栋-2单元-17层", "7栋-1单元-1层", "7栋-1单元-2层", "7栋-1单元-3层", "7栋-1单元-4层", "7栋-1单元-5层", "7栋-1单元-6层", "7栋-1单元-7层", "7栋-1单元-8层", "7栋-1单元-9层", "7栋-1单元-10层", "7栋-1单元-11层", "7栋-1单元-12层", "7栋-1单元-13层", "7栋-1单元-14层", "7栋-1单元-15层", "7栋-1单元-16层", "7栋-2单元-1层", "7栋-2单元-2层", "7栋-2单元-3层", "7栋-2单元-4层", "7栋-2单元-5层", "7栋-2单元-6层", "7栋-2单元-7层", "7栋-2单元-8层", "7栋-2单元-9层", "7栋-2单元-10层", "7栋-2单元-11层", "7栋-2单元-12层", "7栋-2单元-13层", "7栋-2单元-14层", "7栋-2单元-15层", "7栋-2单元-16层", "8栋-1单元-1层", "8栋-1单元-2层", "8栋-1单元-3层", "8栋-1单元-4层", "8栋-1单元-5层", "8栋-1单元-6层", "8栋-1单元-7层", "8栋-1单元-8层", "8栋-1单元-9层", "8栋-1单元-10层", "8栋-1单元-11层", "8栋-1单元-12层", "8栋-1单元-13层", "8栋-1单元-14层", "8栋-1单元-15层", "8栋-1单元-16层", "8栋-1单元-17层", "8栋-2单元-1层", "8栋-2单元-2层", "8栋-2单元-3层", "8栋-2单元-4层", "8栋-2单元-5层", "8栋-2单元-6层", "8栋-2单元-7层", "8栋-2单元-8层", "8栋-2单元-9层", "8栋-2单元-10层", "8栋-2单元-11层", "8栋-2单元-12层", "8栋-2单元-13层", "8栋-2单元-14层", "8栋-2单元-15层", "8栋-2单元-16层", "8栋-2单元-17层", "9栋-1单元-1层", "9栋-1单元-2层", "9栋-1单元-3层", "9栋-1单元-4层", "9栋-1单元-5层", "9栋-1单元-6层", "9栋-1单元-7层", "9栋-1单元-8层", "9栋-1单元-9层", "9栋-1单元-10层", "9栋-1单元-11层", "9栋-1单元-12层", "9栋-1单元-13层", "9栋-1单元-14层", "9栋-1单元-15层", "9栋-1单元-16层", "9栋-1单元-17层", "9栋-2单元-1层", "9栋-2单元-2层", "9栋-2单元-3层", "9栋-2单元-4层", "9栋-2单元-5层", "9栋-2单元-6层", "9栋-2单元-7层", "9栋-2单元-8层", "9栋-2单元-9层", "9栋-2单元-10层", "9栋-2单元-11层", "9栋-2单元-12层", "9栋-2单元-13层", "9栋-2单元-14层", "9栋-2单元-15层", "9栋-2单元-16层", "9栋-2单元-17层", "9栋-3单元-1层", "9栋-3单元-2层", "9栋-3单元-3层", "9栋-3单元-4层", "9栋-3单元-5层", "9栋-3单元-6层", "9栋-3单元-7层", "9栋-3单元-8层", "9栋-3单元-9层", "9栋-3单元-10层", "9栋-3单元-11层", "9栋-3单元-12层", "9栋-3单元-13层", "9栋-3单元-14层", "9栋-3单元-15层", "9栋-3单元-16层", "9栋-3单元-17层", "10栋-1单元-1层", "10栋-1单元-2层", "10栋-1单元-3层", "10栋-1单元-4层", "10栋-1单元-5层", "10栋-1单元-6层", "10栋-1单元-7层", "10栋-1单元-8层", "10栋-1单元-9层", "10栋-1单元-10层", "10栋-1单元-11层", "10栋-1单元-12层", "10栋-1单元-13层", "10栋-1单元-14层", "10栋-1单元-15层", "10栋-1单元-16层", "10栋-1单元-17层", "11栋-1单元-1层", "11栋-1单元-2层", "11栋-1单元-3层", "11栋-1单元-4层", "11栋-1单元-5层", "11栋-1单元-6层", "11栋-1单元-7层", "11栋-1单元-8层", "11栋-1单元-9层", "11栋-1单元-10层", "11栋-1单元-11层", "11栋-1单元-12层", "11栋-1单元-13层", "11栋-1单元-14层", "11栋-1单元-15层", "11栋-1单元-16层", "11栋-1单元-17层", "11栋-2单元-1层", "11栋-2单元-2层", "11栋-2单元-3层", "11栋-2单元-4层", "11栋-2单元-5层", "11栋-2单元-6层", "11栋-2单元-7层", "11栋-2单元-8层", "11栋-2单元-9层", "11栋-2单元-10层", "11栋-2单元-11层", "11栋-2单元-12层", "11栋-2单元-13层", "11栋-2单元-14层", "11栋-2单元-15层", "11栋-2单元-16层", "11栋-2单元-17层"};
        EntityWrapper<SmokeDetectorStatus> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", "0125caffaae1472b996390e869129cc7");
        wrapper.orderBy("gmt_upload", false);
        List<SmokeDetectorStatus> s = smokeDetectorStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (s.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);
        } else {
            localDateTime = s.get(0).getGmtUpload().plusHours(1);
        }
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 出现大量烟雾，环境正常
        Short[] statusArray = {1, 2};
        String deviceName = "烟雾感知设备";
        String deviceType = "NB-IoT";
        List<SmokeDetectorStatus> smokeDetectorStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytkxwt-yg-00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytkxwt-yg-0" + (i + 1);
                } else {
                    deviceNum = "ytkxwt-yg-" + (i + 1);
                }
                Short deviceStatus = 1;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 5) {
                        deviceStatus = 2;
                    } else if (r < 100) {
                        deviceStatus = 1;
                    }
                }
                SmokeDetectorStatus smokeDetectorStatus = new SmokeDetectorStatus(communityCode, deviceNum,
                        deviceName, "凯翔外滩小区" + devicePlace, deviceType,
                        statusArray[1], deviceStatus, localDateTime);
                smokeDetectorStatus.setGmtCreate(LocalDateTime.now());
                smokeDetectorStatus.setGmtModified(LocalDateTime.now());
                smokeDetectorStatuses.add(smokeDetectorStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!smokeDetectorStatuses.isEmpty()) {
            this.insertBatch(smokeDetectorStatuses);
        }
    }

    public void insertDataXJB() {
        String communityCode = "b41bf2c76a3a4d5aaece65c15cfc350b";
        String[] deviceNameArray = {"1栋-1单元-1层", "1栋-1单元-2层", "1栋-1单元-3层", "1栋-1单元-4层", "1栋-1单元-5层", "1栋-1单元-6层", "1栋-1单元-7层", "1栋-1单元-8层", "1栋-1单元-9层", "1栋-1单元-10层", "1栋-1单元-11层", "2栋-1单元-1层", "2栋-1单元-2层", "2栋-1单元-3层", "2栋-1单元-4层", "2栋-1单元-5层", "2栋-1单元-6层", "2栋-1单元-7层", "2栋-1单元-8层", "2栋-1单元-9层", "2栋-1单元-10层", "2栋-1单元-11层", "2栋-2单元-1层", "2栋-2单元-2层", "2栋-2单元-3层", "2栋-2单元-4层", "2栋-2单元-5层", "2栋-2单元-6层", "2栋-2单元-7层", "2栋-2单元-8层", "2栋-2单元-9层", "2栋-2单元-10层", "2栋-2单元-11层", "2栋-3单元-1层", "2栋-3单元-2层", "2栋-3单元-3层", "2栋-3单元-4层", "2栋-3单元-5层", "2栋-3单元-6层", "2栋-3单元-7层", "2栋-3单元-8层", "2栋-3单元-9层", "2栋-3单元-10层", "2栋-3单元-11层", "3栋-1单元-1层", "3栋-1单元-2层", "3栋-1单元-3层", "3栋-1单元-4层", "3栋-1单元-5层", "3栋-1单元-6层", "3栋-1单元-7层", "3栋-1单元-8层", "3栋-1单元-9层", "3栋-1单元-10层", "3栋-1单元-11层", "3栋-2单元-1层", "3栋-2单元-2层", "3栋-2单元-3层", "3栋-2单元-4层", "3栋-2单元-5层", "3栋-2单元-6层", "3栋-2单元-7层", "3栋-2单元-8层", "3栋-2单元-9层", "3栋-2单元-10层", "3栋-2单元-11层", "3栋-3单元-1层", "3栋-3单元-2层", "3栋-3单元-3层", "3栋-3单元-4层", "3栋-3单元-5层", "3栋-3单元-6层", "3栋-3单元-7层", "3栋-3单元-8层", "3栋-3单元-9层", "3栋-3单元-10层", "3栋-3单元-11层", "4栋-1单元-1层", "4栋-1单元-2层", "4栋-1单元-3层", "4栋-1单元-4层", "4栋-1单元-5层", "4栋-1单元-6层", "4栋-1单元-7层", "4栋-1单元-8层", "4栋-1单元-9层", "4栋-1单元-10层", "4栋-1单元-11层", "4栋-2单元-1层", "4栋-2单元-2层", "4栋-2单元-3层", "4栋-2单元-4层", "4栋-2单元-5层", "4栋-2单元-6层", "4栋-2单元-7层", "4栋-2单元-8层", "4栋-2单元-9层", "4栋-2单元-10层", "4栋-2单元-11层", "5栋-1单元-1层", "5栋-1单元-2层", "5栋-1单元-3层", "5栋-1单元-4层", "5栋-1单元-5层", "5栋-1单元-6层", "5栋-1单元-7层", "5栋-1单元-8层", "5栋-1单元-9层", "5栋-1单元-10层", "5栋-1单元-11层", "5栋-2单元-1层", "5栋-2单元-2层", "5栋-2单元-3层", "5栋-2单元-4层", "5栋-2单元-5层", "5栋-2单元-6层", "5栋-2单元-7层", "5栋-2单元-8层", "5栋-2单元-9层", "5栋-2单元-10层", "5栋-2单元-11层", "5栋-3单元-1层", "5栋-3单元-2层", "5栋-3单元-3层", "5栋-3单元-4层", "5栋-3单元-5层", "5栋-3单元-6层", "5栋-3单元-7层", "5栋-3单元-8层", "5栋-3单元-9层", "5栋-3单元-10层", "5栋-3单元-11层", "6栋-1单元-1层", "6栋-1单元-2层", "6栋-1单元-3层", "6栋-1单元-4层", "6栋-1单元-5层", "6栋-1单元-6层", "6栋-1单元-7层", "6栋-1单元-8层", "6栋-1单元-9层", "6栋-1单元-10层", "6栋-1单元-11层", "6栋-2单元-1层", "6栋-2单元-2层", "6栋-2单元-3层", "6栋-2单元-4层", "6栋-2单元-5层", "6栋-2单元-6层", "6栋-2单元-7层", "6栋-2单元-8层", "6栋-2单元-9层", "6栋-2单元-10层", "6栋-2单元-11层", "6栋-3单元-1层", "6栋-3单元-2层", "6栋-3单元-3层", "6栋-3单元-4层", "6栋-3单元-5层", "6栋-3单元-6层", "6栋-3单元-7层", "6栋-3单元-8层", "6栋-3单元-9层", "6栋-3单元-10层", "6栋-3单元-11层", "7栋-1单元-1层", "7栋-1单元-2层", "7栋-1单元-3层", "7栋-1单元-4层", "7栋-1单元-5层", "7栋-1单元-6层", "7栋-1单元-7层", "7栋-1单元-8层", "7栋-1单元-9层", "7栋-1单元-10层", "7栋-1单元-11层", "7栋-1单元-12层", "7栋-1单元-13层", "7栋-1单元-14层", "7栋-1单元-15层", "7栋-1单元-16层", "7栋-1单元-17层", "7栋-1单元-18层", "7栋-2单元-1层", "7栋-2单元-2层", "7栋-2单元-3层", "7栋-2单元-4层", "7栋-2单元-5层", "7栋-2单元-6层", "7栋-2单元-7层", "7栋-2单元-8层", "7栋-2单元-9层", "7栋-2单元-10层", "7栋-2单元-11层", "7栋-2单元-12层", "7栋-2单元-13层", "7栋-2单元-14层", "7栋-2单元-15层", "7栋-2单元-16层", "7栋-2单元-17层", "7栋-2单元-18层", "7栋-3单元-1层", "7栋-3单元-2层", "7栋-3单元-3层", "7栋-3单元-4层", "7栋-3单元-5层", "7栋-3单元-6层", "7栋-3单元-7层", "7栋-3单元-8层", "7栋-3单元-9层", "7栋-3单元-10层", "7栋-3单元-11层", "7栋-3单元-12层", "7栋-3单元-13层", "7栋-3单元-14层", "7栋-3单元-15层", "7栋-3单元-16层", "7栋-3单元-17层", "7栋-3单元-18层", "8栋-1单元-1层", "8栋-1单元-2层", "8栋-1单元-3层", "8栋-1单元-4层", "8栋-1单元-5层", "8栋-1单元-6层", "8栋-1单元-7层", "8栋-1单元-8层", "8栋-1单元-9层", "8栋-1单元-10层", "8栋-1单元-11层", "8栋-2单元-1层", "8栋-2单元-2层", "8栋-2单元-3层", "8栋-2单元-4层", "8栋-2单元-5层", "8栋-2单元-6层", "8栋-2单元-7层", "8栋-2单元-8层", "8栋-2单元-9层", "8栋-2单元-10层", "8栋-2单元-11层", "9栋-1单元-1层", "9栋-1单元-2层", "9栋-1单元-3层", "9栋-1单元-4层", "9栋-1单元-5层", "9栋-1单元-6层", "9栋-1单元-7层", "9栋-1单元-8层", "9栋-1单元-9层", "9栋-1单元-10层", "9栋-1单元-11层", "9栋-2单元-1层", "9栋-2单元-2层", "9栋-2单元-3层", "9栋-2单元-4层", "9栋-2单元-5层", "9栋-2单元-6层", "9栋-2单元-7层", "9栋-2单元-8层", "9栋-2单元-9层", "9栋-2单元-10层", "9栋-2单元-11层", "10栋-1单元-1层", "10栋-1单元-2层", "10栋-1单元-3层", "10栋-1单元-4层", "10栋-1单元-5层", "10栋-1单元-6层", "10栋-1单元-7层", "10栋-1单元-8层", "10栋-1单元-9层", "10栋-1单元-10层", "10栋-1单元-11层", "10栋-2单元-1层", "10栋-2单元-2层", "10栋-2单元-3层", "10栋-2单元-4层", "10栋-2单元-5层", "10栋-2单元-6层", "10栋-2单元-7层", "10栋-2单元-8层", "10栋-2单元-9层", "10栋-2单元-10层", "10栋-2单元-11层", "10栋-3单元-1层", "10栋-3单元-2层", "10栋-3单元-3层", "10栋-3单元-4层", "10栋-3单元-5层", "10栋-3单元-6层", "10栋-3单元-7层", "10栋-3单元-8层", "10栋-3单元-9层", "10栋-3单元-10层", "10栋-3单元-11层", "10栋-4单元-1层", "10栋-4单元-2层", "10栋-4单元-3层", "10栋-4单元-4层", "10栋-4单元-5层", "10栋-4单元-6层", "10栋-4单元-7层", "10栋-4单元-8层", "10栋-4单元-9层", "10栋-4单元-10层", "10栋-4单元-11层", "11栋-1单元-1层", "11栋-1单元-2层", "11栋-1单元-3层", "11栋-1单元-4层", "11栋-1单元-5层", "11栋-1单元-6层", "11栋-1单元-7层", "11栋-1单元-8层", "11栋-1单元-9层", "11栋-1单元-10层", "11栋-1单元-11层", "11栋-2单元-1层", "11栋-2单元-2层", "11栋-2单元-3层", "11栋-2单元-4层", "11栋-2单元-5层", "11栋-2单元-6层", "11栋-2单元-7层", "11栋-2单元-8层", "11栋-2单元-9层", "11栋-2单元-10层", "11栋-2单元-11层", "11栋-3单元-1层", "11栋-3单元-2层", "11栋-3单元-3层", "11栋-3单元-4层", "11栋-3单元-5层", "11栋-3单元-6层", "11栋-3单元-7层", "11栋-3单元-8层", "11栋-3单元-9层", "11栋-3单元-10层", "11栋-3单元-11层", "11栋-4单元-1层", "11栋-4单元-2层", "11栋-4单元-3层", "11栋-4单元-4层", "11栋-4单元-5层", "11栋-4单元-6层", "11栋-4单元-7层", "11栋-4单元-8层", "11栋-4单元-9层", "11栋-4单元-10层", "11栋-4单元-11层", "12栋-1单元-1层", "12栋-1单元-2层", "12栋-1单元-3层", "12栋-1单元-4层", "12栋-1单元-5层", "12栋-1单元-6层", "12栋-1单元-7层", "12栋-1单元-8层", "12栋-1单元-9层", "12栋-1单元-10层", "12栋-1单元-11层", "12栋-1单元-12层", "12栋-1单元-13层", "12栋-1单元-14层", "12栋-1单元-15层", "12栋-1单元-16层", "12栋-1单元-17层", "12栋-1单元-18层", "13栋-1单元-1层", "13栋-1单元-2层", "13栋-1单元-3层", "13栋-1单元-4层", "13栋-1单元-5层", "13栋-1单元-6层", "13栋-1单元-7层", "13栋-1单元-8层", "13栋-1单元-9层", "13栋-1单元-10层", "13栋-1单元-11层", "13栋-1单元-12层", "13栋-1单元-13层", "13栋-1单元-14层", "13栋-1单元-15层", "13栋-1单元-16层", "13栋-1单元-17层", "13栋-1单元-18层", "14栋-1单元-1层", "14栋-1单元-2层", "14栋-1单元-3层", "14栋-1单元-4层", "14栋-1单元-5层", "14栋-1单元-6层", "14栋-1单元-7层", "14栋-1单元-8层", "14栋-1单元-9层", "14栋-1单元-10层", "14栋-1单元-11层", "14栋-1单元-12层", "14栋-1单元-13层", "14栋-1单元-14层", "14栋-1单元-15层", "14栋-1单元-16层", "14栋-1单元-17层", "14栋-1单元-18层", "15栋-1单元-1层", "15栋-1单元-2层", "15栋-1单元-3层", "15栋-1单元-4层", "15栋-1单元-5层", "15栋-1单元-6层", "15栋-1单元-7层", "15栋-1单元-8层", "15栋-1单元-9层", "15栋-1单元-10层", "15栋-1单元-11层", "15栋-1单元-12层", "15栋-1单元-13层", "15栋-1单元-14层", "15栋-1单元-15层", "15栋-1单元-16层", "15栋-1单元-17层", "15栋-1单元-18层", "15栋-2单元-1层", "15栋-2单元-2层", "15栋-2单元-3层", "15栋-2单元-4层", "15栋-2单元-5层", "15栋-2单元-6层", "15栋-2单元-7层", "15栋-2单元-8层", "15栋-2单元-9层", "15栋-2单元-10层", "15栋-2单元-11层", "15栋-2单元-12层", "15栋-2单元-13层", "15栋-2单元-14层", "15栋-2单元-15层", "15栋-2单元-16层", "15栋-2单元-17层", "15栋-2单元-18层", "15栋-3单元-1层", "15栋-3单元-2层", "15栋-3单元-3层", "15栋-3单元-4层", "15栋-3单元-5层", "15栋-3单元-6层", "15栋-3单元-7层", "15栋-3单元-8层", "15栋-3单元-9层", "15栋-3单元-10层", "15栋-3单元-11层", "15栋-3单元-12层", "15栋-3单元-13层", "15栋-3单元-14层", "15栋-3单元-15层", "15栋-3单元-16层", "15栋-3单元-17层", "15栋-3单元-18层", "16栋-1单元-1层", "16栋-1单元-2层", "16栋-1单元-3层", "16栋-1单元-4层", "16栋-1单元-5层", "16栋-1单元-6层", "16栋-1单元-7层", "16栋-1单元-8层", "16栋-1单元-9层", "16栋-1单元-10层", "16栋-1单元-11层", "16栋-2单元-1层", "16栋-2单元-2层", "16栋-2单元-3层", "16栋-2单元-4层", "16栋-2单元-5层", "16栋-2单元-6层", "16栋-2单元-7层", "16栋-2单元-8层", "16栋-2单元-9层", "16栋-2单元-10层", "16栋-2单元-11层", "17栋-1单元-1层", "17栋-1单元-2层", "17栋-1单元-3层", "17栋-1单元-4层", "17栋-1单元-5层", "17栋-1单元-6层", "17栋-1单元-7层", "17栋-1单元-8层", "17栋-1单元-9层", "17栋-1单元-10层", "17栋-1单元-11层", "17栋-2单元-1层", "17栋-2单元-2层", "17栋-2单元-3层", "17栋-2单元-4层", "17栋-2单元-5层", "17栋-2单元-6层", "17栋-2单元-7层", "17栋-2单元-8层", "17栋-2单元-9层", "17栋-2单元-10层", "17栋-2单元-11层"};
        EntityWrapper<SmokeDetectorStatus> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", "b41bf2c76a3a4d5aaece65c15cfc350b");
        wrapper.orderBy("gmt_upload", false);
        List<SmokeDetectorStatus> s = smokeDetectorStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (s.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);
        } else {
            localDateTime = s.get(0).getGmtUpload().plusHours(1);
        }
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 出现大量烟雾，环境正常
        Short[] statusArray = {1, 2};
        String deviceName = "烟雾感知设备";
        String deviceType = "NB-IoT";
        List<SmokeDetectorStatus> smokeDetectorStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytxjb-yg-00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytxjb-yg-0" + (i + 1);
                } else {
                    deviceNum = "ytxjb-yg-" + (i + 1);
                }
                Short deviceStatus = 1;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 5) {
                        deviceStatus = 2;
                    } else if (r < 100) {
                        deviceStatus = 1;
                    }
                }
                SmokeDetectorStatus smokeDetectorStatus = new SmokeDetectorStatus(communityCode, deviceNum,
                        deviceName, "心家泊小区" + devicePlace, deviceType,
                        statusArray[1], deviceStatus, localDateTime);
                smokeDetectorStatus.setGmtCreate(LocalDateTime.now());
                smokeDetectorStatus.setGmtModified(LocalDateTime.now());
                smokeDetectorStatuses.add(smokeDetectorStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!smokeDetectorStatuses.isEmpty()) {
            this.insertBatch(smokeDetectorStatuses);
        }
    }

    public void insertDataYWHDHY() {
        String communityCode = "2c58fbed7bce49778da3b1717241df25";
        String[] deviceNameArray = {"33号1栋-1单元-1层", "33号1栋-1单元-2层", "33号1栋-1单元-3层", "33号1栋-1单元-4层", "33号1栋-1单元-5层", "33号1栋-1单元-6层", "33号1栋-1单元-7层", "33号1栋-2单元-1层", "33号1栋-2单元-2层", "33号1栋-2单元-3层", "33号1栋-2单元-4层", "33号1栋-2单元-5层", "33号1栋-2单元-6层", "33号1栋-2单元-7层", "33号2栋-1单元-1层", "33号2栋-1单元-2层", "33号2栋-1单元-3层", "33号2栋-1单元-4层", "33号2栋-1单元-5层", "33号2栋-1单元-6层", "33号2栋-2单元-1层", "33号2栋-2单元-2层", "33号2栋-2单元-3层", "33号2栋-2单元-4层", "33号2栋-2单元-5层", "33号2栋-2单元-6层", "33号2栋-3单元-1层", "33号2栋-3单元-2层", "33号2栋-3单元-3层", "33号2栋-3单元-4层", "33号2栋-3单元-5层", "33号2栋-3单元-6层", "33号3栋-1单元-1层", "33号3栋-1单元-2层", "33号3栋-1单元-3层", "33号3栋-1单元-4层", "33号3栋-1单元-5层", "33号3栋-1单元-6层", "33号3栋-2单元-1层", "33号3栋-2单元-2层", "33号3栋-2单元-3层", "33号3栋-2单元-4层", "33号3栋-2单元-5层", "33号3栋-2单元-6层", "33号3栋-3单元-1层", "33号3栋-3单元-2层", "33号3栋-3单元-3层", "33号3栋-3单元-4层", "33号3栋-3单元-5层", "33号3栋-3单元-6层", "33号4栋-1单元-1层", "33号4栋-1单元-2层", "33号4栋-1单元-3层", "33号4栋-1单元-4层", "33号4栋-1单元-5层", "33号4栋-2单元-1层", "33号4栋-2单元-2层", "33号4栋-2单元-3层", "33号4栋-2单元-4层", "33号4栋-2单元-5层", "33号4栋-3单元-1层", "33号4栋-3单元-2层", "33号4栋-3单元-3层", "33号4栋-3单元-4层", "33号4栋-3单元-5层", "35号1栋-1单元-1层", "35号1栋-1单元-2层", "35号1栋-1单元-3层", "35号1栋-1单元-4层", "35号1栋-1单元-5层", "35号1栋-1单元-6层", "35号1栋-1单元-7层", "35号1栋-2单元-1层", "35号1栋-2单元-2层", "35号1栋-2单元-3层", "35号1栋-2单元-4层", "35号1栋-2单元-5层", "35号1栋-2单元-6层", "35号1栋-2单元-7层", "35号1栋-3单元-1层", "35号1栋-3单元-2层", "35号1栋-3单元-3层", "35号1栋-3单元-4层", "35号1栋-3单元-5层", "35号1栋-3单元-6层", "35号1栋-3单元-7层", "35号2栋-1单元-1层", "35号2栋-1单元-2层", "35号2栋-1单元-3层", "35号2栋-1单元-4层", "35号2栋-1单元-5层", "35号2栋-1单元-6层", "35号2栋-2单元-1层", "35号2栋-2单元-2层", "35号2栋-2单元-3层", "35号2栋-2单元-4层", "35号2栋-2单元-5层", "35号2栋-2单元-6层", "35号2栋-3单元-1层", "35号2栋-3单元-2层", "35号2栋-3单元-3层", "35号2栋-3单元-4层", "35号2栋-3单元-5层", "35号2栋-3单元-6层", "35号3栋-1单元-1层", "35号3栋-1单元-2层", "35号3栋-1单元-3层", "35号3栋-1单元-4层", "35号3栋-1单元-5层", "35号3栋-1单元-6层", "35号3栋-2单元-1层", "35号3栋-2单元-2层", "35号3栋-2单元-3层", "35号3栋-2单元-4层", "35号3栋-2单元-5层", "35号3栋-2单元-6层", "35号3栋-3单元-1层", "35号3栋-3单元-2层", "35号3栋-3单元-3层", "35号3栋-3单元-4层", "35号3栋-3单元-5层", "35号3栋-3单元-6层", "35号4栋-1单元-1层", "35号4栋-1单元-2层", "35号4栋-1单元-3层", "35号4栋-1单元-4层", "35号4栋-1单元-5层", "35号4栋-1单元-6层", "35号4栋-2单元-1层", "35号4栋-2单元-2层", "35号4栋-2单元-3层", "35号4栋-2单元-4层", "35号4栋-2单元-5层", "35号4栋-2单元-6层"};
        EntityWrapper<SmokeDetectorStatus> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", "2c58fbed7bce49778da3b1717241df25");
        wrapper.orderBy("gmt_upload", false);
        List<SmokeDetectorStatus> s = smokeDetectorStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (s.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);
        } else {
            localDateTime = s.get(0).getGmtUpload().plusHours(1);
        }
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 出现大量烟雾，环境正常
        Short[] statusArray = {1, 2};
        String deviceName = "烟雾感知设备";
        String deviceType = "NB-IoT";
        List<SmokeDetectorStatus> smokeDetectorStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytywhdhy-yg-00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytywhdhy-yg-0" + (i + 1);
                } else {
                    deviceNum = "ytywhdhy-yg-" + (i + 1);
                }
                Short deviceStatus = 1;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 5) {
                        deviceStatus = 2;
                    } else if (r < 100) {
                        deviceStatus = 1;
                    }
                }
                SmokeDetectorStatus smokeDetectorStatus = new SmokeDetectorStatus(communityCode, deviceNum,
                        deviceName, "鹰王环东花苑小区" + devicePlace, deviceType,
                        statusArray[1], deviceStatus, localDateTime);
                smokeDetectorStatus.setGmtCreate(LocalDateTime.now());
                smokeDetectorStatus.setGmtModified(LocalDateTime.now());
                smokeDetectorStatuses.add(smokeDetectorStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!smokeDetectorStatuses.isEmpty()) {
            this.insertBatch(smokeDetectorStatuses);
        }
    }

    public void insertDataNY() {
        String communityCode = "047cd4ab796a419a80a4d362b9da1c8f";
        String[] deviceNameArray = {"1栋-1单元-1层", "1栋-1单元-2层", "1栋-1单元-3层", "1栋-1单元-4层", "1栋-1单元-5层", "1栋-1单元-6层", "1栋-2单元-1层", "1栋-2单元-2层", "1栋-2单元-3层", "1栋-2单元-4层", "1栋-2单元-5层", "1栋-2单元-6层", "1栋-3单元-1层", "1栋-3单元-2层", "1栋-3单元-3层", "1栋-3单元-4层", "1栋-3单元-5层", "1栋-3单元-6层", "3栋-1单元-1层", "3栋-1单元-2层", "3栋-1单元-3层", "3栋-1单元-4层", "3栋-1单元-5层", "3栋-1单元-6层", "3栋-1单元-7层", "3栋-1单元-8层", "3栋-1单元-9层", "3栋-1单元-10层", "3栋-1单元-11层", "3栋-1单元-12层", "3栋-1单元-13层", "3栋-1单元-14层", "3栋-1单元-15层", "3栋-1单元-16层", "3栋-1单元-17层", "3栋-1单元-18层", "4栋-1单元-1层", "4栋-1单元-2层", "4栋-1单元-3层", "4栋-1单元-4层", "4栋-1单元-5层", "4栋-1单元-6层", "4栋-1单元-7层", "4栋-1单元-8层", "4栋-1单元-9层", "4栋-1单元-10层", "4栋-1单元-11层", "4栋-1单元-12层", "4栋-1单元-13层", "4栋-1单元-14层", "4栋-1单元-15层", "4栋-1单元-16层", "4栋-1单元-17层", "4栋-1单元-18层", "4栋-1单元-19层", "4栋-1单元-20层", "4栋-1单元-21层", "4栋-1单元-22层", "4栋-1单元-23层", "4栋-1单元-24层", "4栋-1单元-25层", "4栋-1单元-26层", "4栋-1单元-27层", "4栋-2单元-1层", "4栋-2单元-2层", "4栋-2单元-3层", "4栋-2单元-4层", "4栋-2单元-5层", "4栋-2单元-6层", "4栋-2单元-7层", "4栋-2单元-8层", "4栋-2单元-9层", "4栋-2单元-10层", "4栋-2单元-11层", "4栋-2单元-12层", "4栋-2单元-13层", "4栋-2单元-14层", "4栋-2单元-15层", "4栋-2单元-16层", "4栋-2单元-17层", "4栋-2单元-18层", "4栋-2单元-19层", "4栋-2单元-20层", "4栋-2单元-21层", "4栋-2单元-22层", "4栋-2单元-23层", "4栋-2单元-24层", "4栋-2单元-25层", "4栋-2单元-26层", "4栋-2单元-27层", "5栋-1单元-1层", "5栋-1单元-2层", "5栋-1单元-3层", "5栋-1单元-4层", "5栋-1单元-5层", "5栋-1单元-6层", "5栋-1单元-7层", "5栋-1单元-8层", "5栋-1单元-9层", "5栋-1单元-10层", "5栋-1单元-11层", "5栋-1单元-12层", "5栋-1单元-13层", "5栋-1单元-14层", "5栋-1单元-15层", "5栋-1单元-16层", "5栋-1单元-17层", "5栋-1单元-18层", "5栋-1单元-19层", "5栋-1单元-20层", "5栋-1单元-21层", "5栋-1单元-22层", "5栋-1单元-23层", "5栋-2单元-1层", "5栋-2单元-2层", "5栋-2单元-3层", "5栋-2单元-4层", "5栋-2单元-5层", "5栋-2单元-6层", "5栋-2单元-7层", "5栋-2单元-8层", "5栋-2单元-9层", "5栋-2单元-10层", "5栋-2单元-11层", "5栋-2单元-12层", "5栋-2单元-13层", "5栋-2单元-14层", "5栋-2单元-15层", "5栋-2单元-16层", "5栋-2单元-17层", "5栋-2单元-18层", "5栋-2单元-19层", "5栋-2单元-20层", "5栋-2单元-21层", "5栋-2单元-22层", "5栋-2单元-23层", "6栋-1单元-1层", "6栋-1单元-2层", "6栋-1单元-3层", "6栋-1单元-4层", "6栋-1单元-5层", "6栋-1单元-6层", "6栋-1单元-7层", "6栋-1单元-8层", "6栋-1单元-9层", "6栋-1单元-10层", "6栋-1单元-11层", "6栋-1单元-12层", "6栋-1单元-13层", "6栋-1单元-14层", "6栋-1单元-15层", "6栋-1单元-16层", "6栋-1单元-17层", "6栋-1单元-18层", "6栋-1单元-19层", "6栋-1单元-20层", "6栋-1单元-21层", "6栋-1单元-22层", "6栋-1单元-23层", "6栋-1单元-24层"};
        EntityWrapper<SmokeDetectorStatus> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", "047cd4ab796a419a80a4d362b9da1c8f");
        wrapper.orderBy("gmt_upload", false);
        List<SmokeDetectorStatus> s = smokeDetectorStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (s.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);
        } else {
            localDateTime = s.get(0).getGmtUpload().plusHours(1);
        }
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 出现大量烟雾，环境正常
        Short[] statusArray = {1, 2};
        String deviceName = "烟雾感知设备";
        String deviceType = "NB-IoT";
        List<SmokeDetectorStatus> smokeDetectorStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytny-yg-00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytny-yg-0" + (i + 1);
                } else {
                    deviceNum = "ytny-yg-" + (i + 1);
                }
                Short deviceStatus = 1;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 5) {
                        deviceStatus = 2;
                    } else if (r < 100) {
                        deviceStatus = 1;
                    }
                }
                SmokeDetectorStatus smokeDetectorStatus = new SmokeDetectorStatus(communityCode, deviceNum,
                        deviceName, "南苑小区" + devicePlace, deviceType,
                        statusArray[1], deviceStatus, localDateTime);
                smokeDetectorStatus.setGmtCreate(LocalDateTime.now());
                smokeDetectorStatus.setGmtModified(LocalDateTime.now());
                smokeDetectorStatuses.add(smokeDetectorStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!smokeDetectorStatuses.isEmpty()) {
            this.insertBatch(smokeDetectorStatuses);
        }
    }

}

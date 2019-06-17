package com.mit.community.service.data.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.mapper.data.dao.WellLidStatusMapper;
import com.mit.community.model.WellLidStatus;
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
 * 井盖感知
 *
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@Service
public class WellLidStatusService extends ServiceImpl<WellLidStatusMapper, WellLidStatus> {
    @Autowired
    private WellLidStatusMapper wellLidStatusMapper;

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
    public Page<WellLidStatus> listPage(String communityCode, String deviceNum, String devicePlace, Short status,
                                        Short deviceStatus, LocalDate gmtWarnStart,
                                        LocalDate gmtUploadEnd, Integer pageNum,
                                        Integer pageSize) {

        EntityWrapper<WellLidStatus> wrapper = new EntityWrapper<>();
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
        Page<WellLidStatus> page = new Page<>(pageNum, pageSize);
        List<WellLidStatus> wellLids = wellLidStatusMapper.selectPage(page, wrapper);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (WellLidStatus wellLid : wellLids) {
            //获取时间
            LocalDateTime time = wellLid.getGmtUpload();
            String localTime = df.format(time);
            //2019-01-28 14:00:00
            if (localTime.substring(11, 13).equals("14")) {
                localTime = localTime.replace("14", "13");
            }
            if (localTime.substring(0, 10).equals("2019-01-28")) {
                Date date = new Date();
                //Calendar calendar = Calendar.getInstance();
                //calendar.setTime(date);
                //calendar.add(Calendar.DAY_OF_MONTH, -1);
                //date = calendar.getTime();
                String newTime = sdf.format(date) + localTime.substring(10, localTime.length());
                //转为LocalDateTime
                LocalDateTime ldt = LocalDateTime.parse(newTime, df);
                wellLid.setGmtUpload(ldt);
            } else if (localTime.substring(0, 10).equals("2019-01-27")) {
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                date = calendar.getTime();
                String newTime = sdf.format(date) + localTime.substring(10, localTime.length());
                //转为LocalDateTime
                LocalDateTime ldt = LocalDateTime.parse(newTime, df);
                wellLid.setGmtUpload(ldt);
            } else if (localTime.substring(0, 10).equals("2019-01-26")) {
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, -2);
                date = calendar.getTime();
                String newTime = sdf.format(date) + localTime.substring(10, localTime.length());
                //转为LocalDateTime
                LocalDateTime ldt = LocalDateTime.parse(newTime, df);
                wellLid.setGmtUpload(ldt);
            }
        }
        page.setRecords(wellLids);
        return page;
    }

    public void insertDataXJB() {
        String communityCode = "b41bf2c76a3a4d5aaece65c15cfc350b";
        String[] deviceNameArray = {"1栋1单元门口",
                "2栋1单元门口",
                "2栋2单元门口",
                "2栋3单元门口",
                "3栋1单元门口",
                "3栋2单元门口",
                "3栋3单元门口",
                "4栋1单元门口",
                "4栋2单元门口",
                "5栋1单元门口",
                "5栋2单元门口",
                "5栋3单元门口",
                "6栋1单元门口",
                "6栋2单元门口",
                "6栋3单元门口",
                "7栋1单元门口",
                "7栋2单元门口",
                "7栋3单元门口",
                "8栋1单元门口",
                "8栋2单元门口",
                "9栋1单元门口",
                "9栋2单元门口",
                "10栋1单元门口",
                "10栋2单元门口",
                "10栋3单元门口",
                "10栋4单元门口",
                "11栋1单元门口",
                "11栋2单元门口",
                "11栋3单元门口",
                "11栋4单元门口",
                "12栋1单元门口",
                "13栋1单元门口",
                "14栋1单元门口",
                "15栋1单元门口",
                "15栋2单元门口",
                "15栋3单元门口",
                "16栋1单元门口",
                "16栋2单元门口",
                "17栋1单元门口",
                "17栋2单元门口",
                "小区大门-1",
                "小区大门-2",
                "小区大门-3",
                "小区大门-4",
                "小区后门-1",
                "小区后门-2",
                "小区后门-3",
                "小区后门-4",
                "主干道-1",
                "主干道-2",
                "主干道-3",
                "主干道-4",
                "主干道-5",
                "主干道-6",
                "主干道-7",
                "主干道-8",
                "主干道-9",
                "主干道-10",
                "1栋旁边-1",
                "1栋旁边-2",
                "2栋旁边-1",
                "2栋旁边-2",
                "2栋旁边-3",
                "2栋旁边-4",
                "2栋旁边-5",
                "2栋旁边-6",
                "3栋旁边-1",
                "3栋旁边-2",
                "3栋旁边-3",
                "3栋旁边-4",
                "3栋旁边-5",
                "3栋旁边-6",
                "4栋旁边-1",
                "4栋旁边-2",
                "4栋旁边-3",
                "4栋旁边-4",
                "5栋旁边-1",
                "5栋旁边-2",
                "5栋旁边-3",
                "5栋旁边-4",
                "5栋旁边-5",
                "5栋旁边-6",
                "6栋旁边-1",
                "6栋旁边-2",
                "6栋旁边-3",
                "6栋旁边-4",
                "6栋旁边-5",
                "6栋旁边-6",
                "7栋旁边-1",
                "7栋旁边-2",
                "7栋旁边-3",
                "7栋旁边-4",
                "7栋旁边-5",
                "7栋旁边-6",
                "8栋旁边-1",
                "8栋旁边-2",
                "8栋旁边-3",
                "8栋旁边-4",
                "9栋旁边-1",
                "9栋旁边-2",
                "9栋旁边-3",
                "9栋旁边-4",
                "10栋旁边-1",
                "10栋旁边-2",
                "10栋旁边-3",
                "10栋旁边-4",
                "10栋旁边-5",
                "10栋旁边-6",
                "10栋旁边-7",
                "10栋旁边-8",
                "11栋旁边-1",
                "11栋旁边-2",
                "11栋旁边-3",
                "11栋旁边-4",
                "11栋旁边-5",
                "11栋旁边-6",
                "11栋旁边-7",
                "11栋旁边-8",
                "12栋旁边-1",
                "12栋旁边-2",
                "13栋旁边-1",
                "13栋旁边-2",
                "14栋旁边-1",
                "14栋旁边-2",
                "15栋旁边-1",
                "15栋旁边-2",
                "15栋旁边-3",
                "15栋旁边-4",
                "15栋旁边-5",
                "15栋旁边-6",
                "16栋旁边-1",
                "16栋旁边-2",
                "16栋旁边-3",
                "16栋旁边-4",
                "17栋旁边-1",
                "17栋旁边-2",
                "17栋旁边-3",
                "17栋旁边-4"};
        EntityWrapper<WellLidStatus> wrapper = new EntityWrapper<>();
        wrapper.orderBy("gmt_upload", false);
        wrapper.eq("community_code", "b41bf2c76a3a4d5aaece65c15cfc350b");
        List<WellLidStatus> wellLidStatuses1 = wellLidStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (wellLidStatuses1.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);

        } else {
            localDateTime = wellLidStatuses1.get(0).getGmtUpload().plusHours(1);
        }
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // "移位","缺失","异常开启",复位
        Short[] statusArray = {1, 2, 3, 4};
        String deviceName = "井盖感知设备";
        String deviceType = "NB-IoT";
        List<WellLidStatus> wellLidStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytxjb-jg-00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytxjb-jg-0" + (i + 1);
                } else {
                    deviceNum = "ytxjb-jg-" + (i + 1);
                }
                Short deviceStatus = 1;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 10) {
                        deviceStatus = 2;
                    } else if (r < 90) {
                        deviceStatus = 1;
                    } else {
                        deviceStatus = 3;
                    }
                }
                WellLidStatus wellLidStatus = new WellLidStatus(communityCode, deviceNum,
                        deviceName, "心家泊小区" + devicePlace, deviceType,
                        statusArray[3], deviceStatus, localDateTime);
                wellLidStatus.setGmtCreate(LocalDateTime.now());
                wellLidStatus.setGmtModified(LocalDateTime.now());
                wellLidStatuses.add(wellLidStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!wellLidStatuses.isEmpty()) {
            this.insertBatch(wellLidStatuses);
        }
    }

    public void insertDataKXWT() {
        String communityCode = "0125caffaae1472b996390e869129cc7";
        EntityWrapper<WellLidStatus> wrapper = new EntityWrapper<>();
        wrapper.orderBy("gmt_upload", false);
        wrapper.eq("community_code", "0125caffaae1472b996390e869129cc7");
        List<WellLidStatus> wellLidStatuses1 = wellLidStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (wellLidStatuses1.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);

        } else {
            localDateTime = wellLidStatuses1.get(0).getGmtUpload().plusHours(1);
        }
        String[] deviceNameArray = {"1栋1单元门口",
                "1栋2单元门口",
                "2栋1单元门口",
                "2栋2单元门口",
                "3栋1单元门口",
                "4栋1单元门口",
                "4栋2单元门口",
                "5栋1单元门口",
                "6栋1单元门口",
                "6栋2单元门口",
                "7栋1单元门口",
                "7栋2单元门口",
                "8栋1单元门口",
                "8栋2单元门口",
                "9栋1单元门口",
                "9栋2单元门口",
                "9栋3单元门口",
                "10栋1单元门口",
                "11栋1单元门口",
                "11栋2单元门口",
                "12栋别墅门口",
                "13栋别墅门口",
                "14栋别墅门口",
                "15栋别墅门口",
                "16栋别墅门口",
                "17栋别墅门口",
                "18栋别墅门口",
                "19栋别墅门口",
                "20栋别墅门口",
                "小区前岗出-1",
                "小区前岗出-2",
                "小区后岗出-1",
                "小区后岗出-2",
                "前岗花圃假山-1",
                "前岗花圃假山-2",
                "前岗花圃假山-3",
                "后岗喷泉旁-1",
                "后岗喷泉旁-2",
                "后岗喷泉旁-3",
                "主干道-1",
                "主干道-2",
                "主干道-3",
                "辅干道-1",
                "辅干道-2",
                "辅干道-3"};
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // "移位","缺失","异常开启",复位
        Short[] statusArray = {1, 2, 3, 4};
        Short warnType = 0;
        String deviceName = "井盖感知设备";
        String deviceType = "NB-IoT";
        List<WellLidStatus> wellLidStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytkxwt-jg-00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytkxwt-jg-0" + (i + 1);
                } else {
                    deviceNum = "ytkxwt-jg-" + (i + 1);
                }
                Short deviceStatus = 1;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 10) {
                        deviceStatus = 2;
                    } else if (r < 90) {
                        deviceStatus = 1;
                    } else {
                        deviceStatus = 3;
                    }
                }
                WellLidStatus wellLidStatus = new WellLidStatus(communityCode, deviceNum,
                        deviceName, "凯翔外滩小区" + devicePlace, deviceType,
                        statusArray[3], deviceStatus, localDateTime);
                wellLidStatus.setGmtCreate(LocalDateTime.now());
                wellLidStatus.setGmtModified(LocalDateTime.now());
                wellLidStatuses.add(wellLidStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!wellLidStatuses.isEmpty()) {
            this.insertBatch(wellLidStatuses);
        }
    }

    public void insertDataYWHDHY() {
        String communityCode = "2c58fbed7bce49778da3b1717241df25";
        String[] deviceNameArray = {"33号1栋1单元门口",
                "33号1栋2单元门口",
                "33号2栋1单元门口",
                "33号2栋2单元门口",
                "33号2栋3单元门口",
                "33号3栋1单元门口",
                "33号3栋2单元门口",
                "33号3栋3单元门口",
                "33号4栋1单元门口",
                "33号4栋2单元门口",
                "33号4栋3单元门口",
                "35号1栋1单元门口",
                "35号1栋2单元门口",
                "35号1栋3单元门口",
                "35号2栋1单元门口",
                "35号2栋2单元门口",
                "35号2栋3单元门口",
                "35号3栋1单元门口",
                "35号3栋2单元门口",
                "35号3栋3单元门口",
                "35号4栋1单元门口",
                "35号4栋2单元门口",
                "1栋别墅门口",
                "2栋别墅门口",
                "3栋别墅门口",
                "4栋别墅门口",
                "5栋别墅门口",
                "6栋别墅门口",
                "主干道-1",
                "主干道-2",
                "主干道-3",
                "主干道-4"};
        EntityWrapper<WellLidStatus> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", "2c58fbed7bce49778da3b1717241df25");
        wrapper.orderBy("gmt_upload", false);
        List<WellLidStatus> wellLidStatuses1 = wellLidStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (wellLidStatuses1.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);
        } else {
            localDateTime = wellLidStatuses1.get(0).getGmtUpload().plusHours(1);
        }
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // "移位","缺失","异常开启",复位
        Short[] statusArray = {1, 2, 3, 4};
        String deviceName = "井盖感知设备";
        String deviceType = "NB-IoT";
        List<WellLidStatus> wellLidStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytywhdhy-jg-00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytywhdhy-jg-0" + (i + 1);
                } else {
                    deviceNum = "ytywhdhy-jg-" + (i + 1);
                }
                Short deviceStatus = 1;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 10) {
                        deviceStatus = 2;
                    } else if (r < 90) {
                        deviceStatus = 1;
                    } else {
                        deviceStatus = 3;
                    }
                }
                WellLidStatus wellLidStatus = new WellLidStatus(communityCode, deviceNum,
                        deviceName, "鹰王环东花苑小区" + devicePlace, deviceType,
                        statusArray[3], deviceStatus, localDateTime);
                wellLidStatus.setGmtCreate(LocalDateTime.now());
                wellLidStatus.setGmtModified(LocalDateTime.now());
                wellLidStatuses.add(wellLidStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!wellLidStatuses.isEmpty()) {
            this.insertBatch(wellLidStatuses);
        }
    }

    public void insertDataNY() {
        String communityCode = "047cd4ab796a419a80a4d362b9da1c8f";
        String[] deviceNameArray = {"1栋1单元门口",
                "1栋2单元门口",
                "1栋3单元门口",
                "3栋1单元门口",
                "4栋1单元门口",
                "4栋2单元门口",
                "5栋1单元门口",
                "5栋2单元门口",
                "6栋1单元门口",
                "主干道-1",
                "主干道-2",
                "主干道-3",
                "主干道-4",
                "主干道-5",
                "主干道-6",
                "主干道-7",
                "主干道-8",
                "主干道-9",
                "主干道-10",
                "1栋旁边-1",
                "1栋旁边-2",
                "1栋旁边-3",
                "1栋旁边-4",
                "1栋旁边-5",
                "1栋旁边-6",
                "1栋旁边-7",
                "1栋旁边-8",
                "1栋旁边-9",
                "2栋（酒店）旁边-1",
                "2栋（酒店）旁边-2",
                "2栋（酒店）旁边-3",
                "2栋（酒店）旁边-4",
                "2栋（酒店）旁边-5",
                "2栋（酒店）旁边-6",
                "3栋旁边-1",
                "3栋旁边-2",
                "3栋旁边-3",
                "4栋旁边-1",
                "4栋旁边-2",
                "4栋旁边-3",
                "4栋旁边-4",
                "4栋旁边-5",
                "4栋旁边-6",
                "5栋旁边-1",
                "5栋旁边-2",
                "5栋旁边-3",
                "5栋旁边-4",
                "5栋旁边-5",
                "5栋旁边-6",
                "6栋旁边-1",
                "6栋旁边-2",
                "6栋旁边-3"};
        EntityWrapper<WellLidStatus> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", "047cd4ab796a419a80a4d362b9da1c8f");
        wrapper.orderBy("gmt_upload", false);
        List<WellLidStatus> wellLidStatuses1 = wellLidStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (wellLidStatuses1.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);

        } else {
            localDateTime = wellLidStatuses1.get(0).getGmtUpload().plusHours(1);
        }
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // "移位","缺失","异常开启",复位
        Short[] statusArray = {1, 2, 3, 4};
        String deviceName = "井盖感知设备";
        String deviceType = "NB-IoT";
        List<WellLidStatus> wellLidStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytny-jg-00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytny-jg-0" + (i + 1);
                } else {
                    deviceNum = "ytny-jg-" + (i + 1);
                }
                Short deviceStatus = 1;
                if (localDateTime.getYear() == now.getYear() && localDateTime.getMonth() == now.getMonth() && localDateTime.getDayOfMonth() == now.getDayOfMonth()) {
                    int r = RandomUtil.random(1, 100);
                    if (r < 10) {
                        deviceStatus = 2;
                    } else if (r < 90) {
                        deviceStatus = 1;
                    } else {
                        deviceStatus = 3;
                    }
                }
                WellLidStatus wellLidStatus = new WellLidStatus(communityCode, deviceNum,
                        deviceName, "南苑小区" + devicePlace, deviceType,
                        statusArray[3], deviceStatus, localDateTime);
                wellLidStatus.setGmtCreate(LocalDateTime.now());
                wellLidStatus.setGmtModified(LocalDateTime.now());
                wellLidStatuses.add(wellLidStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!wellLidStatuses.isEmpty()) {
            this.insertBatch(wellLidStatuses);
        }
    }

}

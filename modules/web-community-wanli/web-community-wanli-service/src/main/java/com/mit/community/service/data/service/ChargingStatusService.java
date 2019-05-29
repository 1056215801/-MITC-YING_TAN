package com.mit.community.service.data.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.mapper.data.dao.ChargingStatusMapper;
import com.mit.community.model.ChargingStatus;
import com.mit.community.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 充电桩感知
 *
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@Service
public class ChargingStatusService extends ServiceImpl<ChargingStatusMapper, ChargingStatus> {
    @Autowired
    private ChargingStatusMapper chargingStatusMapper;

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
    public Page<ChargingStatus> listPage(String communityCode, String deviceNum, String devicePlace, Short status,
                                         Short deviceStatus, LocalDate gmtWarnStart,
                                         LocalDate gmtUploadEnd, Integer pageNum,
                                         Integer pageSize) {

        EntityWrapper<ChargingStatus> wrapper = new EntityWrapper<>();
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
        Page<ChargingStatus> page = new Page<>(pageNum, pageSize);
        List<ChargingStatus> list = chargingStatusMapper.selectPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public void insertDataKXWT() {
        String communityCode = "0125caffaae1472b996390e869129cc7";
        String[] deviceNameArray = {"5栋1单元门口-1", "5栋1单元门口-2", "5栋1单元门口-3", "5栋1单元门口-4", "5栋1单元门口-5", "5栋1单元门口-6", "5栋1单元门口-7", "5栋1单元门口-8", "5栋1单元门口-9", "5栋1单元门口-10", "5栋1单元门口-11", "5栋1单元门口-12", "5栋1单元门口-13", "5栋1单元门口-14", "5栋1单元门口-15", "5栋1单元门口-16", "5栋1单元门口-17", "5栋1单元门口-18", "5栋1单元门口-19", "5栋1单元门口-20"};
        EntityWrapper<ChargingStatus> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", "0125caffaae1472b996390e869129cc7");
        wrapper.orderBy("gmt_upload", false);
        List<ChargingStatus> s = chargingStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (s.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);
        } else {
            localDateTime = s.get(0).getGmtUpload().plusHours(1);
        }
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 占用情况（充电中，空闲）
        Short[] statusArray = {1, 2};
        String deviceName = "充电桩状态感知设备";
        String deviceType = "NB-IoT";
        List<ChargingStatus> chargingStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytkxwt-cdz -00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytkxwt-cdz -0" + (i + 1);
                } else {
                    deviceNum = "ytkxwt-cdz -" + (i + 1);
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
                int statusIndx = RandomUtil.random(1, 50);
                if (statusIndx < 25) {
                    statusIndx = 1;
                } else {
                    statusIndx = 0;
                }
                ChargingStatus chargingStatus = new ChargingStatus(communityCode, deviceNum,
                        deviceName, "凯翔外滩小区" + devicePlace, deviceType,
                        statusArray[statusIndx], deviceStatus, localDateTime);
                chargingStatus.setGmtCreate(LocalDateTime.now());
                chargingStatus.setGmtModified(LocalDateTime.now());
                chargingStatuses.add(chargingStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!chargingStatuses.isEmpty()) {
            this.insertBatch(chargingStatuses);
        }
    }


    public void insertDataXJB() {
        String communityCode = "b41bf2c76a3a4d5aaece65c15cfc350b";
        String[] deviceNameArray = {"9栋2单元门口-1", "9栋2单元门口-2", "9栋2单元门口-3", "9栋2单元门口-4", "9栋2单元门口-5", "9栋2单元门口-6", "9栋2单元门口-7", "9栋2单元门口-8", "9栋2单元门口-9", "9栋2单元门口-10", "9栋2单元门口-11", "9栋2单元门口-12"};
        EntityWrapper<ChargingStatus> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", "b41bf2c76a3a4d5aaece65c15cfc350b");
        wrapper.orderBy("gmt_upload", false);
        List<ChargingStatus> s = chargingStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (s.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);
        } else {
            localDateTime = s.get(0).getGmtUpload().plusHours(1);
        }
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 占用情况（充电中，空闲）
        Short[] statusArray = {1, 2};
        String deviceName = "充电桩状态感知设备";
        String deviceType = "NB-IoT";
        List<ChargingStatus> chargingStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytxjb-cdz -00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytxjb-cdz -0" + (i + 1);
                } else {
                    deviceNum = "ytxjb-cdz -" + (i + 1);
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
                int statusIndx = RandomUtil.random(1, 50);
                if (statusIndx < 25) {
                    statusIndx = 1;
                } else {
                    statusIndx = 0;
                }
                ChargingStatus chargingStatus = new ChargingStatus(communityCode, deviceNum,
                        deviceName, "心家泊小区" + devicePlace, deviceType,
                        statusArray[statusIndx], deviceStatus, localDateTime);
                chargingStatus.setGmtCreate(LocalDateTime.now());
                chargingStatus.setGmtModified(LocalDateTime.now());
                chargingStatuses.add(chargingStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!chargingStatuses.isEmpty()) {
            this.insertBatch(chargingStatuses);
        }
    }

    public void insertDataNY() {
        String communityCode = "047cd4ab796a419a80a4d362b9da1c8f";
        String[] deviceNameArray = {"3栋1单元门口-1", "3栋1单元门口-2", "3栋1单元门口-3", "3栋1单元门口-4", "3栋1单元门口-5", "3栋1单元门口-6", "3栋1单元门口-7", "3栋1单元门口-8", "3栋1单元门口-9", "3栋1单元门口-10", "3栋1单元门口-11", "3栋1单元门口-12", "3栋1单元门口-13", "3栋1单元门口-14", "3栋1单元门口-15", "3栋1单元门口-16", "3栋1单元门口-17", "3栋1单元门口-18", "3栋1单元门口-19", "3栋1单元门口-20", "6栋地下室-1", "6栋地下室-2", "6栋地下室-3", "6栋地下室-4", "6栋地下室-5", "6栋地下室-6", "6栋地下室-7", "6栋地下室-8", "6栋地下室-9", "6栋地下室-10", "6栋地下室-11", "6栋地下室-12", "6栋地下室-13", "6栋地下室-14", "6栋地下室-15", "6栋地下室-16", "6栋地下室-17", "6栋地下室-18", "6栋地下室-19", "6栋地下室-20"};
        EntityWrapper<ChargingStatus> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", "047cd4ab796a419a80a4d362b9da1c8f");
        wrapper.orderBy("gmt_upload", false);
        List<ChargingStatus> s = chargingStatusMapper.selectPage(new RowBounds(1, 1), wrapper);
        LocalDateTime localDateTime = null;
        if (s.isEmpty()) {
            localDateTime = LocalDateTime.of(2018, 11, 15,
                    16, 59, 9);
        } else {
            localDateTime = s.get(0).getGmtUpload().plusHours(1);
        }
        LocalDateTime now = LocalDateTime.now();
//        now = now.plusDays(1);
        // 占用情况（充电中，空闲）
        Short[] statusArray = {1, 2};
        String deviceName = "充电桩状态感知设备";
        String deviceType = "NB-IoT";
        List<ChargingStatus> chargingStatuses = Lists.newArrayListWithExpectedSize(1000);
        while ((localDateTime.isBefore(now))) {
            for (int i = 0; i < deviceNameArray.length; i++) {
                localDateTime = localDateTime.plusSeconds(RandomUtil.random(1, 100));
                String devicePlace = deviceNameArray[i];
                String deviceNum;
                if (i < 10) {
                    deviceNum = "ytny-cdz -00" + (i + 1);
                } else if (i < 99) {
                    deviceNum = "ytny-cdz -0" + (i + 1);
                } else {
                    deviceNum = "ytny-cdz -" + (i + 1);
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
                int statusIndx = RandomUtil.random(1, 50);
                if (statusIndx < 25) {
                    statusIndx = 1;
                } else {
                    statusIndx = 0;
                }
                ChargingStatus chargingStatus = new ChargingStatus(communityCode, deviceNum,
                        deviceName, "南苑小区" + devicePlace, deviceType,
                        statusArray[statusIndx], deviceStatus, localDateTime);
                chargingStatus.setGmtCreate(LocalDateTime.now());
                chargingStatus.setGmtModified(LocalDateTime.now());
                chargingStatuses.add(chargingStatus);
            }
            localDateTime = localDateTime.plusMinutes(30);
        }
        if (!chargingStatuses.isEmpty()) {
            this.insertBatch(chargingStatuses);
        }
    }

}

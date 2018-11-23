package com.mit.community.module.bigdata.schedule;

import com.google.common.collect.Lists;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Device;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  设备定时同步
 * @author shuyy
 * @date 2018/11/22
 * @company mitesofor
 */
//@Component
public class DeviceSchedule {

    private final ClusterCommunityService clusterCommunityService;

    private final DeviceService deviceService;

    @Autowired
    public DeviceSchedule(ClusterCommunityService clusterCommunityService, DeviceService deviceService) {
        this.clusterCommunityService = clusterCommunityService;
        this.deviceService = deviceService;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport(){
        List<String> clusterCommunityNameList = new ArrayList<>(4);
        clusterCommunityNameList.add("凯翔外滩小区");
        clusterCommunityNameList.add("心家泊小区");
        clusterCommunityNameList.add("南苑小区");
        clusterCommunityNameList.add("鹰王环东花苑小区");
        // 下面的是测试的
        clusterCommunityNameList.add("珉轩工业园");
        List<ClusterCommunity> clusterCommunitiesList = clusterCommunityService.listByNames(clusterCommunityNameList);
        List<String> list = clusterCommunitiesList.stream().map(ClusterCommunity::getCommunityCode).collect(Collectors.toList());
        List<Device> deviceList = Lists.newArrayListWithCapacity(100);
        list.forEach(item -> {
            List<Device> devices = deviceService.listFromDnakeByCommunityCode(item);
            deviceList.addAll(devices);
        });
        deviceService.remove();
        deviceService.insertBatch(deviceList);
    }
}

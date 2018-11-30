package com.mit.community.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Device;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.DeviceService;

/**
 *  设备定时同步
 * @author shuyy
 * @date 2018/11/22
 * @company mitesofor
 */
@Component
public class DeviceSchedule {

    private final ClusterCommunityService clusterCommunityService;

    private final DeviceService deviceService;

    @Autowired
    public DeviceSchedule(ClusterCommunityService clusterCommunityService, DeviceService deviceService) {
        this.clusterCommunityService = clusterCommunityService;
        this.deviceService = deviceService;
    }

    @Scheduled(cron = "0 25 1 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport(){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        List<Device> deviceList = Lists.newArrayListWithCapacity(100);
        communityCodeList.forEach(item -> {
            List<Device> devices = deviceService.listFromDnakeByCommunityCode(item);
            deviceList.addAll(devices);
        });
        deviceService.remove();
        deviceService.insertBatch(deviceList);
    }
}

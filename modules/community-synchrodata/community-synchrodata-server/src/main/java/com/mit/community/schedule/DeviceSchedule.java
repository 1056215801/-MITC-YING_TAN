package com.mit.community.schedule;

import com.google.common.collect.Lists;
import com.mit.community.entity.Device;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Scheduled(cron = "0 */10 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport(){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        communityCodeList.addAll(clusterCommunityService.listCommunityCodeListByCityName("南昌市"));
        List<Device> deviceList = Lists.newArrayListWithCapacity(100);
        communityCodeList.forEach(item -> {
            List<Device> devices = deviceService.listFromDnakeByCommunityCode(item);
            deviceList.addAll(devices);
        });
        if(!deviceList.isEmpty()){
            deviceService.remove();
            deviceService.insertBatch(deviceList);
        }
    }
}

package com.mit.community.schedule;

import com.google.common.collect.Lists;
import com.mit.community.entity.DeviceDeviceGroup;
import com.mit.community.entity.DeviceGroup;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.DeviceDeviceGroupService;
import com.mit.community.service.DeviceGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备组同步
 *
 * @author shuyy
 * @date 2018/12/10
 * @company mitesofor
 */
@Component
public class DeviceGroupSchedule {

    private final DeviceGroupService deviceGroupService;

    private final ClusterCommunityService clusterCommunityService;

    private final DeviceDeviceGroupService deviceDeviceGroupService;

    @Autowired
    public DeviceGroupSchedule(DeviceGroupService deviceGroupService, ClusterCommunityService clusterCommunityService, DeviceDeviceGroupService deviceDeviceGroupService) {
        this.deviceGroupService = deviceGroupService;
        this.clusterCommunityService = clusterCommunityService;
        this.deviceDeviceGroupService = deviceDeviceGroupService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 */5 * * * ?")
//    @Scheduled(cron = "*/5 * * * * ?")
    public void removeAndImport(){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        communityCodeList.addAll(clusterCommunityService.listCommunityCodeListByCityName("南昌市"));
        List<DeviceGroup> deviceGroups = deviceGroupService.listFromDnakeByCommunityCodeList(communityCodeList);
        if(!deviceGroups.isEmpty()){
            deviceGroupService.remove();
            deviceGroupService.insertBatch(deviceGroups);
        }
        List<DeviceDeviceGroup> list = Lists.newArrayListWithCapacity(200);
        deviceGroups.forEach(deviceGroup -> {
            List<DeviceDeviceGroup> deviceDeviceGroups = deviceGroup.getDeviceDeviceGroups();
            if(deviceDeviceGroups != null){
                list.addAll(deviceDeviceGroups);
            }
        });
        if(!list.isEmpty()){
            deviceDeviceGroupService.remove();
            deviceDeviceGroupService.insertBatch(list);
        }
    }
}

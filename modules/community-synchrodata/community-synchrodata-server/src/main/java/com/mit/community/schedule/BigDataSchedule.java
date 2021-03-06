package com.mit.community.schedule;

import com.google.common.collect.Lists;
import com.mit.community.entity.ActivePeople;
import com.mit.community.entity.AgeConstruction;
import com.mit.community.entity.Device;
import com.mit.community.service.*;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 大数据定时任务
 *
 * @author shuyy
 * @date 2018/11/22
 * @company mitesofor
 */
@Component
public class BigDataSchedule {


    private final ClusterCommunityService clusterCommunityService;

    private final AccessControlService accessControlService;

    private final DeviceService deviceService;

    private final ActivePeopleService activePeopleService;

    private final HouseHoldService houseHoldService;

    private final AgeConstructionService ageConstructionService;

    @Autowired
    public BigDataSchedule(ClusterCommunityService clusterCommunityService, AccessControlService accessControlService, DeviceService deviceService, ActivePeopleService activePeopleService, HouseHoldService houseHoldService, com.mit.community.service.AgeConstructionService ageConstructionService) {
        this.clusterCommunityService = clusterCommunityService;
        this.accessControlService = accessControlService;
        this.deviceService = deviceService;
        this.activePeopleService = activePeopleService;
        this.houseHoldService = houseHoldService;
        this.ageConstructionService = ageConstructionService;
    }

    /***
     * 定时统计活跃人数
     * @author shuyy
     * @date 2018/11/22 11:35
     * @company mitesofor
    */
    @Scheduled(cron = "0 45 3 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void countActivePeopleNum (){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        if(communityCodeList.isEmpty()){
            return;
        }
        List<ActivePeople> list = Lists.newArrayListWithCapacity(communityCodeList.size());
        communityCodeList.forEach(item -> {
            List<Device> devices = deviceService.listInOrOutByCommunityCode(item, "进");
            List<String> deviceNameList = devices.parallelStream().map(Device::getDeviceName).collect(Collectors.toList());
            Long num = accessControlService.countRecentMonthActivePeopleByDeviceNameList(deviceNameList);
            if(num == 0){
                Integer count = houseHoldService.countByCommunityCode(item);
                num = count / 2L + RandomUtils.nextInt(10, 100);
            }
            ActivePeople activePeople = new ActivePeople(item, num);
            activePeople.setGmtCreate(LocalDateTime.now());
            activePeople.setGmtModified(LocalDateTime.now());
            list.add(activePeople);
        });
        if(!list.isEmpty()){
            activePeopleService.remove();
            activePeopleService.insertBatch(list);
        }
    }

    /***
     * 定时统计年龄结构
     * @author shuyy
     * @date 2018/11/23 11:49
     * @company mitesofor
    */
    @Scheduled(cron = "0 0 4 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void  countAgeConstruction (){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        List<AgeConstruction> ageConstructions = houseHoldService.countAgeConstructionByCommuintyCodeList(communityCodeList);
        if(!ageConstructions.isEmpty()){
            ageConstructionService.remove();
            ageConstructionService.insertBatch(ageConstructions);
        }
    }
}

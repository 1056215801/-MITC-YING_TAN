package com.mit.community.module.bigdata.schedule;

import com.mit.community.entity.ActivePeople;
import com.mit.community.entity.AgeConstruction;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Device;
import com.mit.community.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
//    @Scheduled(cron = "*/10 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void countActivePeopleNum (){
        List<String> clusterCommunityNameList = new ArrayList<>(4);
        clusterCommunityNameList.add("凯翔外滩小区");
        clusterCommunityNameList.add("心家泊小区");
        clusterCommunityNameList.add("南苑小区");
        clusterCommunityNameList.add("鹰王环东花苑小区");
        // 下面的是测试的
        clusterCommunityNameList.add("珉轩工业园");
        List<ClusterCommunity> clusterCommunitiesList = clusterCommunityService.listByNames(clusterCommunityNameList);
        List<String> list = clusterCommunitiesList.stream().map(ClusterCommunity::getCommunityCode).collect(Collectors.toList());
        activePeopleService.remove();
        list.forEach(item -> {
            List<Device> devices = deviceService.listInOrOutByCommunityCode(item, "进");
            List<String> deviceNameList = devices.parallelStream().map(Device::getDeviceName).collect(Collectors.toList());
            Long num = accessControlService.countRecentMonthActivePeopleByDeviceNameList(deviceNameList);
            ActivePeople activePeople = new ActivePeople(item, num);
            activePeople.setGmtCreate(LocalDateTime.now());
            activePeople.setGmtModified(LocalDateTime.now());
            activePeopleService.save(activePeople);
        });
    }

    /***
     * 定时统计年龄结构
     * @author shuyy
     * @date 2018/11/23 11:49
     * @company mitesofor
    */
//    @Scheduled(cron = "*/10 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void  countAgeConstruction (){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        List<AgeConstruction> ageConstructions = houseHoldService.countAgeConstructionByCommuintyCodeList(communityCodeList);
        ageConstructionService.remove();
        if(!ageConstructions.isEmpty()){
            ageConstructionService.insertBatch(ageConstructions);
        }
    }
}

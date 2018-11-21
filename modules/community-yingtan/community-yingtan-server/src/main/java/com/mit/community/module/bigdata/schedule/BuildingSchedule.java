package com.mit.community.module.bigdata.schedule;

import com.mit.community.entity.Building;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Zone;
import com.mit.community.service.BuildingService;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.ZoneService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 楼栋定时
 *
 * @author shuyy
 * @date 2018/11/20
 * @company mitesofor
 */
//@Component
public class BuildingSchedule {

    private final BuildingService buildingService;

    private final ClusterCommunityService clusterCommunityService;

    private final ZoneService zoneService;

    public BuildingSchedule(BuildingService buildingService, ClusterCommunityService clusterCommunityService, ZoneService zoneService) {
        this.buildingService = buildingService;
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
    }

    @Scheduled(cron = "* * */12 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void remoteAndImport(){
        List<String> clusterCommunityNameList = new ArrayList<>(4);
        clusterCommunityNameList.add("凯翔外滩小区");
        clusterCommunityNameList.add("心家泊小区");
        clusterCommunityNameList.add("南苑小区");
        clusterCommunityNameList.add("鹰王环东花苑小区");
        // 下面的是测试的
        clusterCommunityNameList.add("珉轩工业园");
        List<ClusterCommunity> clusterCommunitiesList = clusterCommunityService.listByNames(clusterCommunityNameList);
        List<String> list = clusterCommunitiesList.stream().map(ClusterCommunity::getCommunityCode).collect(Collectors.toList());
        // 先删除，在插入
        buildingService.remove();
        list.forEach(item -> {
            // 查询分区
            List<Zone> zones = zoneService.listByCommunityCode(item);
            zones.forEach(zone -> {
                List<Building> buildings = buildingService.listFormDnakeByCommunityCodeAndZoneId(item, zone.getZoneId());
                if(!buildings.isEmpty()){
                    buildingService.insertBatch(buildings);
                }
            });
        });
    }
}

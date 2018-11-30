package com.mit.community.schedule;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mit.community.entity.Building;
import com.mit.community.entity.Zone;
import com.mit.community.service.BuildingService;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.ZoneService;

/**
 * 楼栋定时
 *
 * @author shuyy
 * @date 2018/11/20
 * @company mitesofor
 */
@Component
public class BuildingSchedule {

    private final BuildingService buildingService;

    private final ClusterCommunityService clusterCommunityService;

    private final ZoneService zoneService;

    public BuildingSchedule(BuildingService buildingService, ClusterCommunityService clusterCommunityService, ZoneService zoneService) {
        this.buildingService = buildingService;
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
    }

    @Scheduled(cron = "0 15 1 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void remoteAndImport(){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        // 先删除，在插入
        buildingService.remove();
        communityCodeList.forEach(item -> {
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

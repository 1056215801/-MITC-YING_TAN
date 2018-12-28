package com.mit.community.schedule;

import com.ace.cache.annotation.CacheClear;
import com.google.common.collect.Lists;
import com.mit.community.entity.Building;
import com.mit.community.entity.Zone;
import com.mit.community.service.BuildingService;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.ZoneService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @CacheClear(pre="building")
    @Scheduled(cron = "0 10 23 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void remoteAndImport(){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        communityCodeList.addAll(clusterCommunityService.listCommunityCodeListByCityName("南昌市"));
        List<Building> buildingsList = Lists.newArrayListWithCapacity(100);
        communityCodeList.forEach(item -> {
            // 查询分区
            List<Zone> zones = zoneService.listByCommunityCode(item);
            zones.forEach(zone -> {
                List<Building> buildings = buildingService.listFormDnakeByCommunityCodeAndZoneId(item, zone.getZoneId());
                buildingsList.addAll(buildings);
            });
        });
        if(!buildingsList.isEmpty()){
            // 先删除，在插入
            buildingService.remove();
            buildingService.insertBatch(buildingsList);
        }
    }
}

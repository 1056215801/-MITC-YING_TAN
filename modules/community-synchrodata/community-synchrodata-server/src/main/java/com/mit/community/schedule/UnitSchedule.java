package com.mit.community.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mit.community.entity.Building;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Unit;
import com.mit.community.entity.Zone;
import com.mit.community.service.BuildingService;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.UnitService;
import com.mit.community.service.ZoneService;

/**
 * 单元定时同步数据
 *
 * @author shuyy
 * @date 2018/11/21
 * @company mitesofor
 */
//@Component
public class UnitSchedule {

    private final BuildingService buildingService;

    private final ClusterCommunityService clusterCommunityService;

    private final ZoneService zoneService;

    private final UnitService unitService;

    @Autowired
    public UnitSchedule(BuildingService buildingService,
                        ClusterCommunityService clusterCommunityService,
                        ZoneService zoneService, UnitService unitService) {
        this.buildingService = buildingService;
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
        this.unitService = unitService;
    }

    /***
     * 先删除，再导入单元数据
     * @author shuyy
     * @date 2018/11/21 9:21
     * @company mitesofor
    */
    @Scheduled(cron = "0 0 0 */5 * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport(){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        // 先删除，在插入
        unitService.remove();
        communityCodeList.forEach(item -> {
            // 查询分区
            List<Zone> zones = zoneService.listByCommunityCode(item);
            zones.forEach(zone -> {
                // 查询楼栋
                List<Building> buildingList = buildingService.listByZoneId(zone.getZoneId());
                buildingList.forEach(b -> {
                    List<Unit> units = unitService.listFromDnakeByCommunityCodeAndBuildingId(item, zone.getZoneId(),
                            b.getBuildingId());
                    if(!units.isEmpty()){
                        unitService.insertBatch(units);
                    }
                });
            });
        });
    }

}

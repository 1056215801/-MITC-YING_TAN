package com.mit.community.module.bigdata.schedule;

import com.mit.community.entity.Building;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Unit;
import com.mit.community.entity.Zone;
import com.mit.community.service.BuildingService;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.UnitService;
import com.mit.community.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Scheduled(cron = "* * */5 * * ?")
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
        // 先删除，在插入
        unitService.remove();
        list.forEach(item -> {
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

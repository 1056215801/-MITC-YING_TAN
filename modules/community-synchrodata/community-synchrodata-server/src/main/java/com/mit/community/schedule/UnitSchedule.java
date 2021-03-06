package com.mit.community.schedule;

import com.ace.cache.annotation.CacheClear;
import com.google.common.collect.Lists;
import com.mit.community.entity.Building;
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

import java.util.List;

/**
 * 单元定时同步数据
 *
 * @author shuyy
 * @date 2018/11/21
 * @company mitesofor
 */
@Component
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
    @CacheClear(pre = "unit")
    //@Scheduled(cron = "0 20 23 * * ?")
    @Scheduled(cron = "0 */10 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport() {
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        communityCodeList.addAll(clusterCommunityService.listCommunityCodeListByCityName("南昌市"));
        // 先删除，在插入
        List<Unit> list = Lists.newArrayListWithCapacity(100);
        communityCodeList.forEach(item -> {
            // 查询分区
            List<Zone> zones = zoneService.listByCommunityCode(item);
            zones.forEach(zone -> {
                // 查询楼栋
                List<Building> buildingList = buildingService.listByZoneId(zone.getZoneId());
                buildingList.forEach(b -> {
                    List<Unit> units = unitService.listFromDnakeByCommunityCodeAndBuildingId(item, zone.getZoneId(),
                            b.getBuildingId());
                    list.addAll(units);
                });
            });
        });
        if (!list.isEmpty()) {
            unitService.remove();
            unitService.insertBatch(list);
        }
    }

}

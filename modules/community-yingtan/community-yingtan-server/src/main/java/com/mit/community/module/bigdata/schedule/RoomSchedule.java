package com.mit.community.module.bigdata.schedule;

import com.google.common.collect.Lists;
import com.mit.community.entity.*;
import com.mit.community.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 房间定时同步
 *
 * @author shuyy
 * @date 2018/11/22
 * @company mitesofor
 */
//@Component
public class RoomSchedule {

    private final RoomService roomService;

    private final ClusterCommunityService clusterCommunityService;

    private final ZoneService zoneService;

    private final BuildingService buildingService;

    private final UnitService unitService;
    @Autowired
    public RoomSchedule(RoomService roomService, ClusterCommunityService clusterCommunityService, ZoneService zoneService, BuildingService buildingService, UnitService unitService) {
        this.roomService = roomService;
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
        this.buildingService = buildingService;
        this.unitService = unitService;
    }

    @Scheduled(cron = "*/5 * * * * ?")
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
        roomService.remove();
        List<Zone> zones = zoneService.listByCommunityCodeList(list);
        List<Integer> zoneIdList = zones.parallelStream().map(Zone::getZoneId).collect(Collectors.toList());
        List<Building> buildings = buildingService.listByZoneIdList(zoneIdList);
        List<Integer> buildingIdList = buildings.parallelStream().map(Building::getBuildingId).collect(Collectors.toList());
        List<Unit> units = unitService.listFromBuildingIdList(buildingIdList);
        List<Room> roomList = Lists.newArrayListWithCapacity(10000);
        units.forEach(item -> {
            List<Room> rooms = roomService.listFromDnakeByUnitId(item.getCommunityCode(), item.getZoneId(), item.getBuildingId(), item.getUnitId());
            roomList.addAll(rooms);
        });
        roomService.insertBatch(roomList);

    }
}

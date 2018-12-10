package com.mit.community.schedule;

import com.ace.cache.annotation.CacheClear;
import com.google.common.collect.Lists;
import com.mit.community.entity.Building;
import com.mit.community.entity.Room;
import com.mit.community.entity.Unit;
import com.mit.community.entity.Zone;
import com.mit.community.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 房间定时同步
 *
 * @author shuyy
 * @date 2018/11/22
 * @company mitesofor
 */
@Component
public class RoomSchedule {

    private final RoomService roomService;

    private final ClusterCommunityService clusterCommunityService;

    private final ZoneService zoneService;

    private final BuildingService buildingService;

    private final UnitService unitService;
    @Autowired
    public RoomSchedule(RoomService roomService, ClusterCommunityService clusterCommunityService,
                        ZoneService zoneService, BuildingService buildingService, UnitService unitService) {
        this.roomService = roomService;
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
        this.buildingService = buildingService;
        this.unitService = unitService;
    }

    @CacheClear(pre="room")
    @Scheduled(cron = "0 59 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport(){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        communityCodeList.addAll(clusterCommunityService.listCommunityCodeListByCityName("南昌市"));
        roomService.remove();
        List<Zone> zones = zoneService.listByCommunityCodeList(communityCodeList);
        List<Integer> zoneIdList = zones.parallelStream().map(Zone::getZoneId).collect(Collectors.toList());
        List<Building> buildings = buildingService.listByZoneIdList(zoneIdList);
        List<Integer> buildingIdList = buildings.parallelStream().map(Building::getBuildingId).collect(Collectors.toList());
        List<Unit> units = unitService.listFromBuildingIdList(buildingIdList);
        List<Room> roomList = Lists.newArrayListWithCapacity(10000);
        units.forEach(item -> {
            List<Room> rooms = roomService.listFromDnakeByUnitId(item.getCommunityCode(), item.getZoneId(),
                    item.getBuildingId(), item.getUnitId());
            roomList.addAll(rooms);
        });
        if(!roomList.isEmpty()) {
        	roomService.insertBatch(roomList);
        }

    }
}

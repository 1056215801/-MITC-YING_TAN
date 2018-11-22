package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeConstants;
import com.google.common.collect.Lists;
import com.mit.community.entity.Building;
import com.mit.community.entity.Zone;
import com.mit.community.entity.modelTest.BuildingTest;
import com.mit.community.mapper.BuildingMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 楼栋业务层
 *
 * @author Mr.Deng
 * @date 2018/11/14 16:36
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class BuildingService extends ServiceImpl<BuildingMapper, Building> {
    private final BuildingMapper buildingMapper;

    @Autowired
    public BuildingService(BuildingMapper buildingMapper) {
        this.buildingMapper = buildingMapper;
    }

    /**
     * 添加楼栋信息
     *
     * @param building 楼栋信息
     * @author Mr.Deng
     * @date 16:38 2018/11/14
     */
    public void save(Building building) {
        buildingMapper.insert(building);
    }

    /**
     * 获取所有的楼栋信息
     *
     * @return 楼栋信息列表
     * @author Mr.Deng
     * @date 16:55 2018/11/14
     */
    public List<Building> list() {
        return buildingMapper.selectList(null);
    }

    /***
     * 获取楼栋通过楼栋名和分区id
     * @param buildName 楼栋名
     * @param zoneId 分区id
     * @return com.mit.community.entity.Building
     * @author shuyy
     * @date 2018/11/19 16:30
     * @company mitesofor
     */
    public Building getByNameAndZoneId(String buildName, Integer zoneId) {
        EntityWrapper<Building> wrapper = new EntityWrapper<>();
        wrapper.eq("zone_id", zoneId);
        wrapper.eq("building_name", buildName);
        List<Building> buildings = buildingMapper.selectList(wrapper);
        if (buildings.isEmpty()) {
            return null;
        } else {
            return buildings.get(0);
        }
    }

    /***
     * 查询楼栋，通过分区id
     * @param zoneId 分区id
     * @return java.util.List<com.mit.community.entity.Building>
     * @author shuyy
     * @date 2018/11/21 9:03
     * @company mitesofor
     */
    public List<Building> listByZoneId(Integer zoneId) {
        EntityWrapper<Building> wrapper = new EntityWrapper<>();
        wrapper.eq("zone_id", zoneId);
        List<Building> buildings = buildingMapper.selectList(wrapper);
        return buildings;
    }

    /***
     * 查询房间，通过分区列表
     * @param zoneIdList 分区列表
     * @return java.util.List<com.mit.community.entity.Building>
     * @author shuyy
     * @date 2018/11/22 8:54
     * @company mitesofor
    */
    public List<Building> listByZoneIdList(List<Integer> zoneIdList) {
        EntityWrapper<Building> wrapper = new EntityWrapper<>();
        wrapper.in("zone_id", zoneIdList);
        List<Building> buildings = buildingMapper.selectList(wrapper);
        return buildings;
    }


    /**
     * 查询楼栋列表，通过社区code和分区id
     *
     * @param communityCode 社区code
     * @param zoneId        分区id
     * @return java.util.List<com.mit.community.entity.Building>
     * @author shuyy
     * @date 2018/11/20 10:12
     * @company mitesofor
     */
    public List<Building> listFormDnakeByCommunityCodeAndZoneId(String communityCode, Integer zoneId) {
        String url = "/v1/building/getBuildingList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", communityCode);
        map.put("zoneId", zoneId);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("buildingList");
        List<Building> buildings = JSON.parseArray(jsonArray.toString(), Building.class);
        buildings.forEach(item -> {
            item.setZoneId(zoneId);
            item.setCommunityCode(communityCode);
            item.setGmtCreate(LocalDateTime.now());
            item.setGmtModified(LocalDateTime.now());
        });
        return buildings;
    }

    /***
     * 从dnake接口获取楼栋，通过社区code列表
     * @param communityCodeList
     * @return java.util.List<com.mit.community.entity.Building>
     * @throws
     * @author shuyy
     * @date 2018/11/20 9:49
     * @company mitesofor
     */
    public List<Building> listFromDnakeByCommunityCodeList(List<String> communityCodeList) {
        List<Building> buildingList = Lists.newArrayListWithCapacity(30);
        communityCodeList.forEach(item -> {
            List<Building> buildings = this.listFromDnakeByCommunityCode(item);
            buildingList.addAll(buildings);
        });
        return buildingList;
    }

    /***
     * 从dnake接口获取楼栋，通过社区code
     * @param communityCode 社区code
     * @return java.util.List<com.mit.community.entity.Building>
     * @author shuyy
     * @date 2018/11/20 9:44
     * @company mitesofor
     */
    public List<Building> listFromDnakeByCommunityCode(String communityCode) {
        String url = "/v1/building/getBuildingList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", communityCode);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("buildingList");
        List<Building> buildings = JSON.parseArray(jsonArray.toString(), Building.class);
        buildings.forEach(item -> {
            item.setCommunityCode(communityCode);
            item.setGmtCreate(LocalDateTime.now());
            item.setGmtModified(LocalDateTime.now());
        });
        return buildings;
    }

    /***
     * 获取楼栋，通过楼栋id
     * @param buildingId
     * @return com.mit.community.entity.Building
     * @throws
     * @author shuyy
     * @date 2018/11/21 9:17
     * @company mitesofor
     */
    public Building getByBuildingId(Integer buildingId) {
        EntityWrapper<Building> wrapper = new EntityWrapper<>();
        wrapper.eq("building_id", buildingId);
        List<Building> buildings = buildingMapper.selectList(wrapper);
        if (buildings.isEmpty()) {
            return null;
        } else {
            return buildings.get(0);
        }
    }

    /***
     * 删除所有楼栋
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/11/20 9:52
     * @company mitesofor
     */
    public void remove() {
        buildingMapper.delete(null);
    }

}

package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.Building;
import com.mit.community.mapper.BuildingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 楼栋业务处理层
 * @author Mr.Deng
 * @date 2018/12/7 18:07
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class BuildingService extends ServiceImpl<BuildingMapper, Building> {

    @Autowired
    private BuildingMapper buildingMapper;

    /**
     * 查询楼栋列表，通过分区id
     * @param zoneId 分区id
     * @return 楼栋列表
     * @author Mr.Deng
     * @date 18:10 2018/12/7
     */
    public List<Building> listByZoneId(Integer zoneId) {
        EntityWrapper<Building> wrapper = new EntityWrapper<>();
        wrapper.eq("zone_id", zoneId);
        wrapper.eq("building_status", 1);
        return buildingMapper.selectList(wrapper);
    }

    /**
     * 查询楼栋信息，通过楼栋code
     * @param buildingCode 楼栋code
     * @return 楼栋信息
     * @author Mr.Deng
     * @date 14:23 2018/12/21
     */
    @Cache(key = "building:buildingCode:communityCode:{1}:{2}")
    public Building getBybuildingCode(String buildingCode, String communityCode) {
        EntityWrapper<Building> wrapper = new EntityWrapper<>();
        wrapper.eq("building_code", buildingCode);
        wrapper.eq("community_code", communityCode);
        wrapper.eq("building_status", 1);
        List<Building> buildings = buildingMapper.selectList(wrapper);
        if (buildings.isEmpty()) {
            return null;
        }
        return buildings.get(0);
    }

    public Building getByBuidingId(Integer buildingId) {
        EntityWrapper<Building> wrapper = new EntityWrapper<>();
        wrapper.eq("building_id", buildingId);
        List<Building> list = buildingMapper.selectList(wrapper);
        if (list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}

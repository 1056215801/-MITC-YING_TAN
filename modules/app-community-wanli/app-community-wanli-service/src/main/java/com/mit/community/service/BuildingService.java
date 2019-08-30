package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.Building;
import com.mit.community.entity.Unit;
import com.mit.community.mapper.BuildingMapper;
import com.mit.community.mapper.UnitMapper;
import com.mit.community.util.Result;
import io.lettuce.core.GeoArgs;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 楼栋业务处理层
 * @author Mr.Deng
 * @date 2018/12/7 18:07
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class BuildingService extends ServiceImpl<BuildingMapper,Building> {

    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private UnitMapper unitMapper;

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



    public void save(Integer zoneId, String buildingName, String buildingCode, Integer sort, String communityCode) {
        EntityWrapper<Building> wrapper=new EntityWrapper<>();
        wrapper.orderBy("building_id",false);
        wrapper.last("limit 1");
        Building buildingMax = baseMapper.selectList(wrapper).get(0);
        Building building=new Building();
        building.setCommunityCode(communityCode);
        building.setZoneId(zoneId);
        building.setBuildingId(buildingMax.getBuildingId()+1);
        building.setBuildingName(buildingName);
        building.setBuildingCode(buildingCode);
        building.setBuildingStatus(1);
        building.setSort(sort);
        building.setGmtCreate(LocalDateTime.now());
        building.setGmtModified(LocalDateTime.now());
        buildingMapper.insert(building);


    }

    public Page<Building> getbuildingList(Building building, Integer pageNum, Integer pageSize, String communityCode) {
        Page<Building> page=new Page<>(pageNum,pageSize);
        EntityWrapper<Building> wrapper=new EntityWrapper<>();
        if (StringUtils.isNotEmpty(building.getBuildingName()))
        {
            wrapper.like("b.building_name",building.getBuildingName());
        }
        if (StringUtils.isNotEmpty(building.getBuildingCode())){
            wrapper.like("b.building_code",building.getBuildingCode());
        }
        if (building.getZoneId()!=null){
            wrapper.eq("b.zone_id",building.getZoneId());
        }
        if (building.getBuildingStatus()!=null){
            wrapper.eq("b.building_status",building.getBuildingStatus());
        }
        if (StringUtils.isNotEmpty(communityCode)){
            wrapper.eq("b.community_code",communityCode);
        }
        wrapper.orderBy("b.sort",true);
//        List<Building> buildingList = baseMapper.selectPage(page, wrapper);
        List<Building> buildingList = buildingMapper.selectBuildingPage(page,wrapper);
        page.setRecords(buildingList);
        return page;

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

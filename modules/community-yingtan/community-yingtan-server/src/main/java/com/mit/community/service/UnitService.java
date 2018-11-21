package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dnake.common.DnakeWebApiUtil;
import com.google.common.collect.Maps;
import com.mit.community.entity.Building;
import com.mit.community.entity.Unit;
import com.mit.community.mapper.UnitMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 单元业务类
 *
 * @author Mr.Deng
 * @date 2018/11/14 17:18
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class UnitService extends ServiceImpl<UnitMapper, Unit> {
    private final UnitMapper unitMapper;

    private final BuildingService buildingService;

    @Autowired
    public UnitService(UnitMapper unitMapper, BuildingService buildingService) {
        this.unitMapper = unitMapper;
        this.buildingService = buildingService;
    }

    /**
     * 保存单元信息
     *
     * @param unit 单元信息
     * @author Mr.Deng
     * @date 17:19 2018/11/14
     */
    public void save(Unit unit) {
        unitMapper.insert(unit);
    }

    /**
     * 获取所有的单元信息
     *
     * @return 单元信息列表
     * @author Mr.Deng
     * @date 17:20 2018/11/14
     */
    public List<Unit> list() {
        return unitMapper.selectList(null);
    }

    /***
     * 获取单元。通过单元名和楼栋id
     * @param unitName 单元名
     * @param buildingId 楼栋id
     * @return com.mit.community.entity.Unit
     * @author shuyy
     * @date 2018/11/19 16:50
     * @company mitesofor
    */
    public Unit getByNameAndBuildingId(String unitName, Integer buildingId){
        EntityWrapper<Unit> wrapper = new EntityWrapper<>();
        wrapper.eq("unit_name", unitName).eq("building_id", buildingId);
        List<Unit> units = unitMapper.selectList(wrapper);
        if(units.isEmpty()){
            return null;
        }else {
            return units.get(0);
        }
    }

    /***
     * 从dnake接口获取单元。通过社区code，楼栋id
     * @param communityCode 社区code
     * @param zoneId  分区id
     * @param buildingId 楼栋id
     * @return java.util.List<com.mit.community.entity.modelTest.UnitTest>
     * @author shuyy
     * @date 2018/11/21 9:09
     * @company mitesofor
    */
    public List<Unit> listFromDnakeByCommunityCodeAndBuildingId(String communityCode, Integer zoneId, Integer buildingId) {
        String url = "/v1/unit/getUnitList";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(5);
        map.put("communityCode", communityCode);
        map.put("zoneId", zoneId);
        map.put("buildingId", buildingId);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("unitList");
        List<Unit> units = JSON.parseArray(jsonArray.toString(), Unit.class);
        units.forEach(item -> {
            item.setGmtCreate(LocalDateTime.now());
            item.setGmtModified(LocalDateTime.now());
            item.setBuildingId(buildingId);
            item.setCommunityCode(communityCode);
            item.setZoneId(zoneId);
        });
        return units;
    }

    /***
     * 删除所有数据
     * @author shuyy
     * @date 2018/11/21 9:00
     * @company mitesofor
    */
    public void remove(){
        this.unitMapper.delete(null);
    }

}

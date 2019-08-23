package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.Room;
import com.mit.community.entity.Unit;
import com.mit.community.mapper.RoomMapper;
import com.mit.community.mapper.UnitMapper;
import com.mit.community.util.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 单元业务处理层
 * @author Mr.Deng
 * @date 2018/12/7 18:18
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class UnitService extends ServiceImpl<UnitMapper,Unit> {
    @Autowired
    private UnitMapper unitMapper;
    @Autowired
    private RoomMapper roomMapper;
    /**
     * 查询单元列表，通过楼栋id
     * @param buildingId 楼栋id
     * @return 单元列表
     * @author Mr.Deng
     * @date 18:21 2018/12/7
     */
    public List<Unit> listByBuildingId(Integer buildingId) {
        EntityWrapper<Unit> wrapper = new EntityWrapper<>();
        wrapper.eq("building_id", buildingId);
        wrapper.eq("unit_status", 1);
        return unitMapper.selectList(wrapper);
    }

    /**
     * 查询单元信息，通过单元code
     * @param unitCode 单元code
     * @return 单元信息
     * @author Mr.Deng
     * @date 14:30 2018/12/21
     */
    @Cache(key = "unit:unitCode:communityCode:{1}:{2}")
    public Unit getByUnitCode(String unitCode, String communityCode) {
        EntityWrapper<Unit> wrapper = new EntityWrapper<>();
        wrapper.eq("unit_code", unitCode);
        wrapper.eq("community_code", communityCode);
        wrapper.eq("unit_status", 1);
        List<Unit> units = unitMapper.selectList(wrapper);
        if (units.isEmpty()) {
            return null;
        }
        return units.get(0);
    }

    public Result deleteByIdList(List<Integer> idList) {
        for (int i = 0; i <idList.size() ; i++) {
            EntityWrapper<Unit> entityWrapper=new EntityWrapper<>();
            entityWrapper.eq("id",idList.get(i));
            Unit unit = baseMapper.selectList(entityWrapper).get(0);
            EntityWrapper<Room> wrapper=new EntityWrapper<>();
            wrapper.eq("unit_id",unit.getUnitId());
            List<Room> roomList = roomMapper.selectList(wrapper);
            if (roomList.size()>0){
                return Result.error("单元已经被房屋关联不能删除");
            }
        }
       baseMapper.deleteBatchIds(idList);

        return Result.success("删除成功");
    }


    public Page<Unit> getUnitList(Unit unit, Integer pageNum, Integer pageSize, String communityCode) {

        Page<Unit> page=new Page<>(pageNum,pageSize);
        EntityWrapper<Unit> wrapper=new EntityWrapper<>();
        if (StringUtils.isNotEmpty(unit.getUnitName())){
            wrapper.like("unit_name",unit.getUnitName());
        }
        if (StringUtils.isNotEmpty(unit.getUnitCode()))
        {
            wrapper.like("unit_code",unit.getUnitCode());
        }
        if (unit.getZoneId()!=null){
            wrapper.eq("zone_id",unit.getZoneId());
        }
        if (unit.getBuildingId()!=null){
            wrapper.eq("building_id",unit.getBuildingId());
        }
        if (unit.getUnitStatus()!=null){
            wrapper.eq("unit_status",unit.getUnitStatus());
        }
        if (StringUtils.isNotEmpty(communityCode))
        {
            wrapper.eq("u.community_code",communityCode);
        }
        List<Unit> unitList=unitMapper.getUnitList(page,wrapper);
        page.setRecords(unitList);
        return page;
    }
}

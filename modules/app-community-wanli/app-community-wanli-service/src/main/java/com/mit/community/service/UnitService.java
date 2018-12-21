package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Unit;
import com.mit.community.mapper.UnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 单元业务处理层
 * @author Mr.Deng
 * @date 2018/12/7 18:18
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class UnitService {
    @Autowired
    private UnitMapper unitMapper;

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

}

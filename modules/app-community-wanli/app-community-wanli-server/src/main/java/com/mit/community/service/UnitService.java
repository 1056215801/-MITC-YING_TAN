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
    
}

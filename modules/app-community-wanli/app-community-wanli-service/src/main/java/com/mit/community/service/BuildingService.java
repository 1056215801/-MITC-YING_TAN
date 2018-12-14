package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
public class BuildingService {
    
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
}

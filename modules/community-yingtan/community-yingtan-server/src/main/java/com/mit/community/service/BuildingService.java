package com.mit.community.service;

import com.mit.community.entity.Building;
import com.mit.community.mapper.BuildingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 楼栋业务层
 *
 * @author Mr.Deng
 * @date 2018/11/14 16:36
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class BuildingService {
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
    public List<Building> getBuildingList() {
        return buildingMapper.selectList(null);
    }
    
}

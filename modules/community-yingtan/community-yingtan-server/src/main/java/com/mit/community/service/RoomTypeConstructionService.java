package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.RoomTypeConstruction;
import com.mit.community.mapper.RoomTypeConstructionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author LW
 * @creatTime 2018-11-23 11:29
 */
@Service
public class RoomTypeConstructionService {
    private final RoomTypeConstructionMapper roomTypeConstructionMapper;

    @Autowired
    public RoomTypeConstructionService(RoomTypeConstructionMapper roomTypeConstructionMapper) {
        this.roomTypeConstructionMapper = roomTypeConstructionMapper;
    }

    /**
     * 添加房屋结构信息
     *
     * @param roomTypeConstruction 房屋结构信息
     * @author lw
     * @date 15:03 2018/11/23
     */
    public void save(RoomTypeConstruction roomTypeConstruction) {
        roomTypeConstructionMapper.insert(roomTypeConstruction);
    }

    /***
     * 删除所有
     * @author lw
     * @author lw
     * @date 15:03 2018/11/23
     */
    public void remove() {
        roomTypeConstructionMapper.delete(null);
    }

    /**
     * 通过小区code获取房屋结构信息
     *
     * @param communityCode 小区code
     * @return 房屋结构信息
     * @author lw
     * @date 15:03 2018/11/23
     */
    public RoomTypeConstruction getByCommunityCode(String communityCode) {
        EntityWrapper<RoomTypeConstruction> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return roomTypeConstructionMapper.selectList(wrapper).get(0);
    }

    /**
     * 获取本市人口和外来人口的自住，闲置，出租，其他的房屋数
     * TODO 可能需要优化
     * @param commmunityCode 社区id
     * @return RoomTypeConstruction对象那个
     * @author lw
     */
    public RoomTypeConstruction countRoomTypeConstructionByCommunityCode(String commmunityCode) {
        RoomTypeConstruction roomTypeConstruction = new RoomTypeConstruction();
        //包含外来房屋自住，出租，其他
        RoomTypeConstruction foreignRoomTypeConstruction = roomTypeConstructionMapper.getForeignRoomTypeConstructionByCommunityCode(commmunityCode);
        //包含本市房屋自住，出租，其他
        RoomTypeConstruction innerRoomTypeConstruction = roomTypeConstructionMapper.getInnerRoomTypeConstructionByCommunityCode(commmunityCode);
        //包含外来房屋和本市房屋人口
        RoomTypeConstruction populationRoomTypeConstrction = roomTypeConstructionMapper.getForeignInnerPopulationRoomTypeConstrctionByCommunityCode(commmunityCode);

        Integer innerPopulation = populationRoomTypeConstrction.getInnerPopulation() == null ? 0 : populationRoomTypeConstrction.getInnerPopulation();
        Integer foreignPopulation = populationRoomTypeConstrction.getForeignPopulation() == null ? 0 : populationRoomTypeConstrction.getForeignPopulation();

        Integer foreignOther = foreignRoomTypeConstruction.getForeignOther() == null ? 0 : foreignRoomTypeConstruction.getForeignOther();
        Integer foreignSelf = foreignRoomTypeConstruction.getForeignSelf() == null ? 0 : foreignRoomTypeConstruction.getForeignSelf();
        Integer foreignRent = foreignRoomTypeConstruction.getForeignRent() == null ? 0 : foreignRoomTypeConstruction.getForeignRent();
        Integer foreignLeisure = foreignPopulation - (foreignOther + foreignSelf + foreignRent);
        foreignLeisure = foreignLeisure < 0 ? 0 : foreignLeisure;


        Integer innerOther = innerRoomTypeConstruction.getInnerOther() == null ? 0 : innerRoomTypeConstruction.getInnerOther();
        Integer innerSelf = innerRoomTypeConstruction.getInnerSelf() == null ? 0 : innerRoomTypeConstruction.getInnerSelf();
        Integer innerRent = innerRoomTypeConstruction.getInnerRent() == null ? 0 : innerRoomTypeConstruction.getInnerRent();
        Integer innerLeisure = innerPopulation - (innerOther + innerSelf + innerRent);
        innerLeisure = innerLeisure < 0 ? 0 : innerLeisure;

        roomTypeConstruction.setCommunityCode(commmunityCode);

        roomTypeConstruction.setForeignLeisure(foreignLeisure);
        roomTypeConstruction.setForeignOther(foreignOther);
        roomTypeConstruction.setForeignSelf(foreignSelf);
        roomTypeConstruction.setInnerRent(foreignRent);
        roomTypeConstruction.setForeignLeisure(foreignLeisure);
        roomTypeConstruction.setForeignPopulation(foreignPopulation);

        roomTypeConstruction.setInnerLeisure(innerLeisure);
        roomTypeConstruction.setInnerOther(innerOther);
        roomTypeConstruction.setInnerPopulation(innerPopulation);
        roomTypeConstruction.setInnerRent(innerRent);
        roomTypeConstruction.setInnerSelf(innerSelf);
        return roomTypeConstruction;
    }


}

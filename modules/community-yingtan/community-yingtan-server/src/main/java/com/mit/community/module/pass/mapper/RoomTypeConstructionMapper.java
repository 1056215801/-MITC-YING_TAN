package com.mit.community.module.pass.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mit.community.entity.RoomTypeConstruction;

/**
 * @author LW
 * @creatTime 2018-11-23 11:31
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public interface RoomTypeConstructionMapper extends BaseMapper<RoomTypeConstruction> {
    /**
     * 获取外来人口的自住，出租，其他的房屋数
     * @param communityCode 社区id
     * @return RoomTypeConstruction对象对应属性有值
     */
    RoomTypeConstruction getForeignRoomTypeConstructionByCommunityCode(String communityCode);

    /**
     * 获取本市人口的自住，出租，其他的房屋数
     * @param communityCode 社区id
     * @return RoomTypeConstruction对象对应属性有值
     */
    RoomTypeConstruction getInnerRoomTypeConstructionByCommunityCode(String communityCode);

    /**
     * 获取本市和外来的房屋数量
     * @param CommunityCode 社区的code
     * @return RoomTypeConstruction对象对应属性有值
     */
    RoomTypeConstruction getForeignInnerPopulationRoomTypeConstrctionByCommunityCode(String CommunityCode);
}

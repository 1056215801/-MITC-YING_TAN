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
public class RoomTypeConstructionService  {
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
}

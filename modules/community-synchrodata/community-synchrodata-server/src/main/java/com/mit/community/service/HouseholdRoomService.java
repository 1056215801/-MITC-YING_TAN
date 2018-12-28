package com.mit.community.service;

import com.ace.cache.annotation.CacheClear;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.HouseholdRoom;
import com.mit.community.mapper.HouseholdRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 住户房屋关联
 *
 * @author shuyy
 * @date 2018/12/11
 * @company mitesofor
 */
@Service
public class HouseholdRoomService extends ServiceImpl<HouseholdRoomMapper, HouseholdRoom> {

    private final HouseholdRoomMapper householdRoomMapper;

    @Autowired
    public HouseholdRoomService(HouseholdRoomMapper householdRoomMapper) {
        this.householdRoomMapper = householdRoomMapper;
    }

    /**
     * 查询房屋，通过住户id
     * @param householdId 住户id
     * @return java.util.List<com.mit.community.entity.HouseholdRoom>
     * @author shuyy
     * @date 2018/12/12 9:04
     * @company mitesofor
     */
    public List<HouseholdRoom> listByHouseholdId(Integer householdId){
        EntityWrapper<HouseholdRoom> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        List<HouseholdRoom> householdRooms = householdRoomMapper.selectList(wrapper);
        return householdRooms;
    }

    /**
     * 查询所有有住户的房间id
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author shuyy
     * @date 2018/12/28 9:01
     * @company mitesofor
    */
    public List<Map<String, Object>> listActiveRoomId(){
        EntityWrapper<HouseholdRoom> wrapper = new EntityWrapper<>();
        wrapper.groupBy("room_id");
        wrapper.setSqlSelect("room_id");
        return householdRoomMapper.selectMaps(wrapper);
    }

    /**
     * 查询所有住户房屋，通过房间id
     * @param roomId 房间id
     * @return java.util.List<com.mit.community.entity.HouseholdRoom>
     * @author shuyy
     * @date 2018/12/28 9:04
     * @company mitesofor
    */
    public List<HouseholdRoom> listByRoomId(Integer roomId){
        EntityWrapper<HouseholdRoom> wrapper = new EntityWrapper<>();
        wrapper.eq("room_id",roomId);
        return householdRoomMapper.selectList(wrapper);
    }

    /***
     * 删除
     * @author shuyy
     * @date 2018/12/11 20:24
     * @company mitesofor
    */
    @CacheClear(pre = "householdRoom")
    public void remove(){
        householdRoomMapper.delete(null);
    }
}

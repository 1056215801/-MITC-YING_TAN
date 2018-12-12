package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.HouseholdRoom;
import com.mit.community.mapper.HouseholdRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /***
     * 删除
     * @author shuyy
     * @date 2018/12/11 20:24
     * @company mitesofor
    */
    public void remove(){
        householdRoomMapper.delete(null);
    }
}

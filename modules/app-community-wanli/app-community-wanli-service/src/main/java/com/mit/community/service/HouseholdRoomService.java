package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.HouseholdRoom;
import com.mit.community.mapper.HouseholdRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 住户房屋关联
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
    @Cache(key = "householdRoom:householdId:{1}")
    public List<HouseholdRoom> listByHouseholdId(Integer householdId) {
        EntityWrapper<HouseholdRoom> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        return householdRoomMapper.selectList(wrapper);
    }

    /**
     * 查询房屋列表，通过住户id列表
     * @param householdIdList 住户id列表
     * @return java.util.List<com.mit.community.entity.HouseholdRoom>
     * @author shuyy
     * @date 2018/12/12 9:04
     * @company mitesofor
     */
    public List<HouseholdRoom> listByHouseholdIdlList(List<Integer> householdIdList) {
        EntityWrapper<HouseholdRoom> wrapper = new EntityWrapper<>();
        wrapper.in("household_id", householdIdList);
        return householdRoomMapper.selectList(wrapper);
    }



    /**
     * 查询房间信息，通过住户id和房号
     * @param householdId 住户id
     * @param roomNum     房号
     * @return 房间信息
     * @author Mr.Deng
     * @date 14:05 2018/12/12
     */
    public HouseholdRoom getByHouseholdIdAndRoomNum(Integer householdId, String roomNum) {
        EntityWrapper<HouseholdRoom> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        wrapper.eq("room_num", roomNum);
        List<HouseholdRoom> householdRooms = householdRoomMapper.selectList(wrapper);
        if (householdRooms.isEmpty()) {
            return null;
        }
        return householdRooms.get(0);
    }


    /***
     * 删除
     * @author shuyy
     * @date 2018/12/11 20:24
     * @company mitesofor
     */
    @CacheClear(pre = "householdRoom")
    public void remove() {
        householdRoomMapper.delete(null);
    }
}

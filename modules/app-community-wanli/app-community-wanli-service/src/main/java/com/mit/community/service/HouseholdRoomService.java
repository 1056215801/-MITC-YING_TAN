package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.HouseholdRoom;
import com.mit.community.mapper.HouseHoldMapper;
import com.mit.community.mapper.HouseholdRoomMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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
    private HouseHoldMapper houseHoldMapper;

    @Autowired
    public HouseholdRoomService(HouseholdRoomMapper householdRoomMapper) {
        this.householdRoomMapper = householdRoomMapper;
    }

    /**
     * 查询房屋，通过住户id
     *
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
        wrapper.orderBy("gmt_create", true);
        return householdRoomMapper.selectList(wrapper);
    }

    /**
     * 查询房屋列表，通过住户id列表
     *
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
     *
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

    /**
     * 查询房间信息，通过住户id和房间id
     *
     * @param householdId 住户id
     * @param     房号
     * @return 房间信息
     * @author Mr.Deng
     * @date 14:05 2018/12/12
     */
    public HouseholdRoom getByHouseholdIdAndRoomId(Integer householdId, Integer roomId) {
        EntityWrapper<HouseholdRoom> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        wrapper.eq("room_id", roomId);
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

    /**
     * 根据住户id删除房屋列表
     */
    public void deleteByHouseholdId(Integer householdId) {
        EntityWrapper<HouseholdRoom> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        householdRoomMapper.delete(wrapper);
    }

    /**
     * 保存
     *
     * @param householdRoom
     * @return void
     * @throws
     * @author shuyy
     * @date 2019-01-24 15:49
     * @company mitesofor
     */
//    @Cache(key = "householdRoom:householdId:{1}")
    @CacheClear(key = "householdRoom:householdId:{1.householdId}")
    public void save(HouseholdRoom householdRoom) {
        householdRoom.setGmtCreate(LocalDateTime.now());
        householdRoom.setGmtModified(LocalDateTime.now());
        householdRoomMapper.insert(householdRoom);
    }

    public HouseholdRoom getByHouseHoldIdAndCommunityCodeAndBuilingIdAndUnitId(Integer householdId,String communityCode, String buildingId, String unitId){
        EntityWrapper<HouseholdRoom> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        wrapper.eq("community_code", communityCode);
        wrapper.eq("building_id", buildingId);
        wrapper.eq("unit_id", unitId);
        List<HouseholdRoom> list = householdRoomMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Page<HouseHold> getInfoList(String householdName, String mobile, Integer zoneId, Integer buildingId, Integer unitId, String roomNum, Short householdType, Integer householdStatus, Date validityTime, Integer authorizeStatus, Integer pageNum, Integer pageSize) {
        Page<HouseHold> page=new Page<>(pageNum,pageSize);
        EntityWrapper<HouseHold> wrapper=new EntityWrapper<>();
        if (StringUtils.isNotEmpty(householdName)){
            wrapper.eq("household_name",householdName);
        }
        if (StringUtils.isNotEmpty(mobile))
        {
            wrapper.eq("mobile",mobile);
        }
        if (validityTime!=null){
            wrapper.eq("validity_time",validityTime);
        }
        if (authorizeStatus != null) {
            wrapper.eq("authorize_status",authorizeStatus);
        }
        if (householdStatus != null) {
            wrapper.eq("household_status",householdStatus);
        }
        List<HouseHold> houseHoldList=houseHoldMapper.getInfoList(page,wrapper,zoneId,buildingId,unitId,roomNum,householdType);
        page.setRecords(houseHoldList);
        return page;
    }
}

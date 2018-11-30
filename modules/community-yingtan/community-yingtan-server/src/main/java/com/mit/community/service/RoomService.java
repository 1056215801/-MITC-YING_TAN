package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeConstants;
import com.mit.community.entity.Room;
import com.mit.community.module.pass.mapper.RoomMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 房间业务表
 * @author Mr.Deng
 * @date 2018/11/14 17:59
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class RoomService extends ServiceImpl<RoomMapper, Room> {

    private final RoomMapper roomMapper;

    @Autowired
    public RoomService(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    /**
     * 添加房间信息
     * @param room 房间信息
     * @author Mr.Deng
     * @date 18:01 2018/11/14
     */
    public void save(Room room) {
        roomMapper.insert(room);
    }

    /**
     * 查询所有房间信息
     * @return 房间信息列表
     * @author Mr.Deng
     * @date 18:37 2018/11/14
     */
    public List<Room> list() {
        return roomMapper.selectList(null);
    }

    /***
     * 从dnake接口查询所有房间， 通过社区code， 分区id， 楼栋id， 单元id
     * @param communityCode 社区code
     * @param zoneId 分区id
     * @param buildingId 楼栋id
     * @param unitId 单元id
     * @return java.util.List<com.mit.community.entity.Room>
     * @author shuyy
     * @date 2018/11/22 9:06
     * @company mitesofor
     */
    public List<Room> listFromDnakeByUnitId(String communityCode, Integer zoneId, Integer buildingId, Integer unitId) {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/room/getRoomList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", communityCode);
        map.put("zoneId", zoneId);
        map.put("buildingId", buildingId);
        map.put("unitId", unitId);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("roomList");
        List<Room> rooms = JSON.parseArray(jsonArray.toString(), Room.class);
        rooms.forEach(room -> {
            room.setCommunityCode(communityCode);
            room.setZoneId(zoneId);
            room.setBuildingId(buildingId);
            room.setUnitId(unitId);
            room.setGmtCreate(LocalDateTime.now());
            room.setGmtModified(LocalDateTime.now());
        });
        return rooms;
    }

    /***
     * 删除所有
     * @author shuyy
     * @date 2018/11/22 8:51
     * @company mitesofor
     */
    public void remove() {
        roomMapper.delete(null);
    }

    /**
     * 统计房间总数，通过小区code
     * @param communityCode 小区code
     * @return 房间信息列表
     * @author Mr.Deng
     * @date 15:03 2018/11/21
     */
    public Integer countByCommunityCode(String communityCode) {
        EntityWrapper<Room> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return roomMapper.selectCount(wrapper);
    }

    /**
     * 统计房间总数，通过一组小区code
     * @param communityCodes 小区code列表
     * @return 房间总数
     * @author Mr.Deng
     * @date 15:06 2018/11/21
     */
    public Integer countByCommunityCodes(List<String> communityCodes) {
        EntityWrapper<Room> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodes);
        return roomMapper.selectCount(wrapper);
    }

    /**
     * 获取房间，通过单元id和房间号
     * @param roomNum 房间号
     * @param unitId  单元id
     * @return 房间信息列表
     * @author Mr.Deng
     * @date 15:06 2018/11/21
     */
    public Room getByUnitIdAndRoomNum(String roomNum, Integer unitId) {
        EntityWrapper<Room> wrapper = new EntityWrapper<>();
        wrapper.eq("unit_id", unitId);
        wrapper.eq("room_num", roomNum);
        List<Room> list = roomMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }

    }
}

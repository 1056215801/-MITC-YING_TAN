package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.Room;
import com.mit.community.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房间业务表
 *
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
     *
     * @param room 房间信息
     * @author Mr.Deng
     * @date 18:01 2018/11/14
     */
    public void save(Room room) {
        roomMapper.insert(room);
    }

    /**
     * 获取所有房间信息
     *
     * @return 房间信息列表
     * @author Mr.Deng
     * @date 18:37 2018/11/14
     */
    public List<Room> list() {
        return roomMapper.selectList(null);
    }

    /**
     * 通过小区code获取房间信息
     *
     * @param communityCode 小区code
     * @return 房间信息列表
     * @author Mr.Deng
     * @date 15:03 2018/11/21
     */
    public List<Room> listByCommunityCode(String communityCode) {
        EntityWrapper<Room> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return roomMapper.selectList(wrapper);
    }

    /**
     * 通过一组小区code获取房间信息
     *
     * @param communityCodes 小区code列表
     * @return 房间信息列表
     * @author Mr.Deng
     * @date 15:06 2018/11/21
     */
    public List<Room> listByCommunityCodes(List<String> communityCodes) {
        EntityWrapper<Room> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodes);
        return roomMapper.selectList(wrapper);
    }
}

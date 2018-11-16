package com.mit.community.service;

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
public class RoomService {

    @Autowired
    private RoomMapper roomMapper;

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
    public List<Room> getRoomList() {
        return roomMapper.selectList(null);
    }
}

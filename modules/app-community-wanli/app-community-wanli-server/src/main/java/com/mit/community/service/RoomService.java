package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Room;
import com.mit.community.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房间业务处理层
 * @author Mr.Deng
 * @date 2018/12/8 9:02
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class RoomService {
    @Autowired
    private RoomMapper roomMapper;

    /**
     * 查询房间信息，通过单元id
     * @param unitId 单元id
     * @return 房间信息列表
     * @author Mr.Deng
     * @date 9:05 2018/12/8
     */
    public List<Room> listByUnitId(Integer unitId) {
        EntityWrapper<Room> wrapper = new EntityWrapper<>();
        wrapper.eq("unit_id", unitId);
        wrapper.eq("room_status", 1);
        return roomMapper.selectList(wrapper);
    }
}

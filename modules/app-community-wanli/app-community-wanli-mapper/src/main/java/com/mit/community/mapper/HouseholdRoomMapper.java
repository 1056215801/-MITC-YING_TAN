package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.HouseholdRoom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 住户房屋关联
 *
 * @author shuyy
 * @date 2018/12/11
 * @company mitesofor
 */
public interface HouseholdRoomMapper extends BaseMapper<HouseholdRoom> {


    List<HouseHold> getInfoList(RowBounds rowBounds, @Param("ew") EntityWrapper<HouseHold> wrapper, @Param("zoneId") Integer zoneId, @Param("buildingId") Integer buildingId, @Param("unitId") Integer unitId, @Param("roomNum") String roomNum, @Param("householdType") short householdType);
}

package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.HouseHold;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 住户
 *
 * @author Mr.Deng
 * @date 2018/11/14 19:32
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public interface HouseHoldMapper extends BaseMapper<HouseHold> {

    //自定义接口:更新居住期限时间
    public void updateObjectById(@Param("residenceTime") LocalDate residenceTime, @Param("household_id") Integer household_id);

    //自定义接口:更新权限有效期时间
    public void updateValidityTime(@Param("validityTime") Date validityTime, @Param("status") Integer authorizeStatus, @Param("household_id") Integer household_id);

    //自定义接口:住户信息修改
    public void updateHouseholdByHouseholdId(HouseHold houseHold);

    List<HouseHold> getInfoList(RowBounds rowBounds, @Param("ew") EntityWrapper<HouseHold> wrapper, @Param("zoneId") Integer zoneId, @Param("buildingId") Integer buildingId, @Param("unitId") Integer unitId, @Param("roomNum") String roomNum, @Param("householdType") Short householdType);
}

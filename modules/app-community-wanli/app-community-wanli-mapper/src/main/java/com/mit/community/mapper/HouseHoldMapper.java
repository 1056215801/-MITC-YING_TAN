package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mit.community.entity.HouseHold;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.Date;

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

}

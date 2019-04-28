package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mit.community.entity.User;
import org.apache.ibatis.annotations.Param;


/**
 * 用户
 *
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
public interface UserMapper extends BaseMapper<User> {

    //自定义接口:根据手机号码更新用户表住户id
    public void updateHouseholdIdByMobile(@Param("householdId") Integer householdId, @Param("mobile") String mobile);

    //自定义接口:根据手机号码更新用户表住户id
    public void updateMobileByHouseholdId(@Param("mobile") String mobile, @Param("householdId") Integer householdId);

    //自定义接口:重置用户id
    public void updateByHouseholdId(@Param("householdId") Integer householdId);
}

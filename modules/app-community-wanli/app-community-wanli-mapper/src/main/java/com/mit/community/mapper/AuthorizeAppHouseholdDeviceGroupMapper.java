package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mit.community.entity.AuthorizeAppHouseholdDeviceGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * app用户授权组设备
 *
 * @author shuyy
 * @date 2018/11/19
 * @company mitesofor
 */
public interface AuthorizeAppHouseholdDeviceGroupMapper extends BaseMapper<AuthorizeAppHouseholdDeviceGroup> {

    //自定义接口
    List<AuthorizeAppHouseholdDeviceGroup> getObjectByIds(@Param("household_id")Integer household_id, @Param("device_group_id") Integer device_group_id);
}

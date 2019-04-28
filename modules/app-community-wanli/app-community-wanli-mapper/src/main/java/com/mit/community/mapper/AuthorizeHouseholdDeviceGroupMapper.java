package com.mit.community.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mit.community.entity.AuthorizeAppHouseholdDeviceGroup;
import com.mit.community.entity.AuthorizeHouseholdDeviceGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 住户设备授权
 *
 * @author shuyy
 * @date 2018/11/19
 * @company mitesofor
 */
public interface AuthorizeHouseholdDeviceGroupMapper extends BaseMapper<AuthorizeHouseholdDeviceGroup> {

    //自定义接口
    List<AuthorizeHouseholdDeviceGroup> getObjectByIds(@Param("household_id")Integer household_id, @Param("device_group_id") Integer device_group_id);

}

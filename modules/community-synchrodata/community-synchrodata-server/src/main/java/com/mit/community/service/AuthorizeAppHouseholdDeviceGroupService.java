package com.mit.community.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.AuthorizeAppHouseholdDeviceGroup;
import com.mit.community.mapper.AuthorizeAppHouseholdDeviceGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * app用户授权设备
 *
 * @author shuyy
 * @date 2018/11/19
 * @company mitesofor
 */
@Service
public class AuthorizeAppHouseholdDeviceGroupService extends ServiceImpl<AuthorizeAppHouseholdDeviceGroupMapper, AuthorizeAppHouseholdDeviceGroup> {

    private final AuthorizeAppHouseholdDeviceGroupMapper authorizeAppHouseholdDeviceMapper;

    @Autowired
    public AuthorizeAppHouseholdDeviceGroupService(AuthorizeAppHouseholdDeviceGroupMapper authorizeAppHouseholdDeviceMapper) {
        this.authorizeAppHouseholdDeviceMapper = authorizeAppHouseholdDeviceMapper;
    }

    /***
     * 删除所有
     * @author shuyy
     * @date 2018/11/21 10:08
     * @company mitesofor
    */
    public void remove(){
        authorizeAppHouseholdDeviceMapper.delete(null);
    }
}

package com.mit.community.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.AuthorizeAppHouseholdDevice;
import com.mit.community.module.pass.mapper.AuthorizeAppHouseholdDeviceMapper;
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
public class AuthorizeAppHouseholdDeviceService extends ServiceImpl<AuthorizeAppHouseholdDeviceMapper, AuthorizeAppHouseholdDevice> {

    private final AuthorizeAppHouseholdDeviceMapper authorizeAppHouseholdDeviceMapper;

    @Autowired
    public AuthorizeAppHouseholdDeviceService(AuthorizeAppHouseholdDeviceMapper authorizeAppHouseholdDeviceMapper) {
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

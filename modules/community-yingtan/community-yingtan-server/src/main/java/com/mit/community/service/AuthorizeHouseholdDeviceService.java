package com.mit.community.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.AccessControl;
import com.mit.community.entity.AuthorizeHouseholdDevice;
import com.mit.community.mapper.AccessControlMapper;
import com.mit.community.mapper.AuthorizeHouseholdDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 住户设备授权
 *
 * @author shuyy
 * @date 2018/11/19
 * @company mitesofor
 */
@Service
public class AuthorizeHouseholdDeviceService extends ServiceImpl<AuthorizeHouseholdDeviceMapper, AuthorizeHouseholdDevice> {

    private final AuthorizeHouseholdDeviceMapper authorizeHouseholdDeviceMapper;

    @Autowired
    public AuthorizeHouseholdDeviceService(AuthorizeHouseholdDeviceMapper authorizeHouseholdDeviceMapper) {
        this.authorizeHouseholdDeviceMapper = authorizeHouseholdDeviceMapper;
    }


    /***
     * 保存
     * @param authorizeHouseholdDevice 住户设备授权
     * @author shuyy
     * @date 2018/11/19 17:11
     * @company mitesofor
    */
    public void save(AuthorizeHouseholdDevice authorizeHouseholdDevice){
        authorizeHouseholdDeviceMapper.insert(authorizeHouseholdDevice);
    }

    /***
     * 删除所有
     * @author shuyy
     * @date 2018/11/21 10:06
     * @company mitesofor
    */
    public void remove(){
        authorizeHouseholdDeviceMapper.delete(null);
    }

}

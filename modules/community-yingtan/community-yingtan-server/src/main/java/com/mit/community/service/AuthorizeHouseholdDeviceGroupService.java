package com.mit.community.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.AuthorizeHouseholdDeviceGroup;
import com.mit.community.module.pass.mapper.AuthorizeHouseholdDeviceGroupMapper;
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
public class AuthorizeHouseholdDeviceGroupService extends ServiceImpl<AuthorizeHouseholdDeviceGroupMapper, AuthorizeHouseholdDeviceGroup> {

    private final AuthorizeHouseholdDeviceGroupMapper authorizeHouseholdDeviceMapper;

    @Autowired
    public AuthorizeHouseholdDeviceGroupService(AuthorizeHouseholdDeviceGroupMapper authorizeHouseholdDeviceMapper) {
        this.authorizeHouseholdDeviceMapper = authorizeHouseholdDeviceMapper;
    }


    /***
     * 保存
     * @param authorizeHouseholdDeviceGroup 住户设备授权
     * @author shuyy
     * @date 2018/11/19 17:11
     * @company mitesofor
    */
    public void save(AuthorizeHouseholdDeviceGroup authorizeHouseholdDeviceGroup){
        authorizeHouseholdDeviceMapper.insert(authorizeHouseholdDeviceGroup);
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

package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.AuthorizeAppHouseholdDevice;
import com.mit.community.module.system.mapper.AuthorizeAppHouseholdDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 住户授权app设备
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@Service
public class AuthorizeAppHouseholdDeviceService {

    @Autowired
    private AuthorizeAppHouseholdDeviceMapper AuthorizeAppHouseholdDeviceMapper;


    /**
     * 查询住户app授权设备，通过住户id列表
     * @param householdIdList 住户id列表
     * @return java.util.List<com.mit.community.entity.AuthorizeAppHouseholdDevice>
     * @author shuyy
     * @date 2018/11/30 10:55
     * @company mitesofor
    */
    public List<AuthorizeAppHouseholdDevice> listByHouseholdIdList(List<Integer> householdIdList){
        EntityWrapper<AuthorizeAppHouseholdDevice> wrapper = new EntityWrapper<>();
        wrapper.in("household_id", householdIdList);
        return AuthorizeAppHouseholdDeviceMapper.selectList(wrapper);
    }
}

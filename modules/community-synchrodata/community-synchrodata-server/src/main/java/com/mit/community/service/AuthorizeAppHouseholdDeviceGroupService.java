package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.AuthorizeAppHouseholdDeviceGroup;
import com.mit.community.mapper.AuthorizeAppHouseholdDeviceGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 查询所有，通过householdId
     * @param householdId
     * @return java.util.List<com.mit.community.entity.AuthorizeAppHouseholdDeviceGroup>
     * @throws
     * @author shuyy
     * @date 2018/12/29 11:34
     * @company mitesofor
    */
    public List<AuthorizeAppHouseholdDeviceGroup> listByHouseholdId(Integer householdId){
        EntityWrapper<AuthorizeAppHouseholdDeviceGroup> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        return authorizeAppHouseholdDeviceMapper.selectList(wrapper);
    }
}

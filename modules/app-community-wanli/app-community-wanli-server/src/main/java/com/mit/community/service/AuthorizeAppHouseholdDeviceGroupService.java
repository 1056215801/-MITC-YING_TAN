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
    public void remove() {
        authorizeAppHouseholdDeviceMapper.delete(null);
    }

    /**
     * 查询住户关联设备组表，通告住户id列表
     * @param householdIdList 住户id列表
     * @return 住户关联设备组
     * @author Mr.Deng
     * @date 16:20 2018/12/13
     */
    public List<AuthorizeAppHouseholdDeviceGroup> listByHouseholdIdList(List<Integer> householdIdList) {
        EntityWrapper<AuthorizeAppHouseholdDeviceGroup> wrapper = new EntityWrapper<>();
        wrapper.in("household_id", householdIdList);
        return authorizeAppHouseholdDeviceMapper.selectList(wrapper);
    }
}

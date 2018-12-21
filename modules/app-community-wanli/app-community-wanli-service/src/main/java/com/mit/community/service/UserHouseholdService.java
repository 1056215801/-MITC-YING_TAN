package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.UserHousehold;
import com.mit.community.mapper.UserHouseholdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户住户关联
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@Service
public class UserHouseholdService {

    private final UserHouseholdMapper userHouseholdMapper;

    @Autowired
    public UserHouseholdService(UserHouseholdMapper userHouseholdMapper) {
        this.userHouseholdMapper = userHouseholdMapper;
    }

    /**
     * 查询用户住户关联，通过用户id
     * @param userId 用户id
     * @return java.util.List<com.mit.community.entity.UserHousehold>
     * @author shuyy
     * @date 2018/11/30 10:37
     * @company mitesofor
    */
    public List<UserHousehold> listByUserId(Integer userId){
        EntityWrapper<UserHousehold> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return userHouseholdMapper.selectList(wrapper);
    }



}

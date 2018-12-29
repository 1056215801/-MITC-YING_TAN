/*
package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.UserHousehold;
import com.mit.community.mapper.UserHouseholdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

*/
/**
 * 用户住户关联
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 *//*

@Service
public class UserHouseholdService {
    @Autowired
    private UserHouseholdMapper userHouseholdMapper;

    */
/**
     * 查询用户住户关联，通过用户id
     * @param userId 用户id
     * @return java.util.List<com.mit.community.entity.UserHousehold>
     * @author shuyy
     * @date 2018/11/30 10:37
     * @company mitesofor
     *//*

    public List<UserHousehold> listByUserId(Integer userId) {
        EntityWrapper<UserHousehold> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return userHouseholdMapper.selectList(wrapper);
    }

    */
/**
     * 查询住户-用户关联信息，通过住户id
     * @param householdId 住户id
     * @return 用户住户关联信息
     * @author Mr.Deng
     * @date 13:46 2018/12/24
     *//*

    public UserHousehold getByHouseholdId(Integer householdId) {
        EntityWrapper<UserHousehold> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        List<UserHousehold> userHouseholds = userHouseholdMapper.selectList(wrapper);
        if (userHouseholds.isEmpty()) {
            return null;
        }
        return userHouseholds.get(0);
    }

}
*/

package com.mit.community.service;

import com.ace.cache.annotation.CacheClear;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.User;
import com.mit.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户
 *
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 更新
     * @param user
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/12/12 11:00
     * @company mitesofor
    */
    public void update(User user){
        userMapper.updateById(user);
    }

    /**
     * 获取用户，通过手机号
     * @param cellphone
     * @return com.mit.community.entity.User
     * @throws
     * @author shuyy
     * @date 2018/12/12 11:02
     * @company mitesofor
    */
    public User getByCellphone(String cellphone){
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellphone);
        List<User> users = userMapper.selectList(wrapper);
        if(users.isEmpty()){
            return null;
        }
        return users.get(0);
    }
    /**
     * 获取用户，通过住户id
     * @param householdId 住户id
     * @return com.mit.community.entity.User
     * @author shuyy
     * @date 2018/12/12 11:02
     * @company mitesofor
     */
    public User getByHouseholdId(String householdId){
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        List<User> users = userMapper.selectList(wrapper);
        if(users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    /**
     * 更新手机号, 通过住户id过滤
     * @param cellphone 手机号
     * @author shuyy
     * @date 2018/12/12 11:04
     * @company mitesofor
    */
    @CacheClear(key = "user:cellphone:{0}")
    public void updateCellphoneByHouseholdId(String cellphone, Integer householdId){
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        User user = new User();
        user.setCellphone(cellphone);
        userMapper.update(user, wrapper);
    }
}

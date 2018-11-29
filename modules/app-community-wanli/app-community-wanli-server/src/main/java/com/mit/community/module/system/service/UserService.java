package com.mit.community.module.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.User;
import com.mit.community.module.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
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

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 保存
     * @param user user
     * @author shuyy
     * @date 2018/11/29 11:25
     * @company mitesofor
    */
    public void save(User user){
        userMapper.insert(user);
    }

    /**
     * 获取User，通过username和密码
     * @param username username
     * @param password 密码
     * @return com.mit.community.entity.User
     * @author shuyy
     * @date 2018/11/29 11:28
     * @company mitesofor
    */
    public User getByUsernameAndPassword(String username, String password){
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        List<User> users = userMapper.selectList(wrapper);
        if(users.isEmpty()){
            return null;
        }else{
            return users.get(0);
        }
    }

    /**
     * 获取User，通过cellphone和密码
     * @param cellphone 手机号
     * @param password  密码
     * @return com.mit.community.entity.User
     * @author shuyy
     * @date 2018/11/29 11:28
     * @company mitesofor
     */
    public User getByCellphoneAndPassword(String cellphone, String password){
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellphone);
        wrapper.eq("password", password);
        List<User> users = userMapper.selectList(wrapper);
        if(users.isEmpty()){
            return null;
        }else{
            return users.get(0);
        }
    }

    /**
     * 获取User，通过cellphone
     * @param cellphone 手机号
     * @return com.mit.community.entity.User
     * @author shuyy
     * @date 2018/11/29 11:28
     * @company mitesofor
     */
    public User getByCellphone(String cellphone){
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellphone);
        List<User> users = userMapper.selectList(wrapper);
        if(users.isEmpty()){
            return null;
        }else{
            return users.get(0);
        }
    }

}

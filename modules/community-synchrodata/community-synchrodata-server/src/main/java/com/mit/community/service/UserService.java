package com.mit.community.service;

import com.ace.cache.annotation.CacheClear;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.common.util.DateUtils;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.User;
import com.mit.community.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @CacheClear(key = "user:cellphone{0}")
    public void updateCellphoneByHouseholdId(String cellphone, Integer householdId){
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        User user = new User();
        user.setCellphone(cellphone);
        userMapper.update(user, wrapper);
    }

    /**
     * 将新增的住户中没有注册app账号的注册原始账号
     * @param list
     */
    public void insert(List<HouseHold> list) {
        User user = null;
        for (int i=0; i<list.size(); i++) {
           User isExits = getByCellphone(list.get(i).getMobile());
            if (isExits == null) {
                short gender = 0;
                if (list.get(i).getGender() == 0){//女
                    gender = 0;
                } else {
                    gender = 1;
                }
                user = new User();
                user.setCellphone(list.get(i).getMobile());
                user.setPassword("123456");
                user.setHouseholdId(list.get(i).getHouseholdId());
                user.setNickname(list.get(i).getHouseholdName());
                user.setGender(gender);
                user.setRole("普通业主");
                user.setIcon_url("http://www.miesofor.tech/1ec47936-e19a-43d2-86c1-481ddfe07a8c.png");
                user.setBirthday(LocalDate.of(1900, 1,
                        1));
                user.setGmtCreate(LocalDateTime.now());
                user.setGmtModified(LocalDateTime.now());
                userMapper.insert(user);
            }
        }
    }

    public void delete(List<String> list) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.in("cellphone", list);
        userMapper.delete(wrapper);
    }

    public void update(List<HouseHold> list) {
        User user = null;
        for (int i=0; i<list.size(); i++) {
            User isExits = getByCellphone(list.get(i).getMobile());
            if (isExits == null) {
                short gender = 0;
                if (list.get(i).getGender() == 0){//女
                    gender = 0;
                } else {
                    gender = 1;
                }
                user = new User();
                user.setCellphone(list.get(i).getMobile());
                user.setPassword("123456");
                user.setHouseholdId(list.get(i).getHouseholdId());
                user.setNickname(list.get(i).getHouseholdName());
                user.setGender(gender);
                user.setRole("普通业主");
                user.setIcon_url("http://www.miesofor.tech/1ec47936-e19a-43d2-86c1-481ddfe07a8c.png");
                System.out.println("狄耐克的性别="+list.get(i).getGender()+",user的性别="+user.getGender());
                user.setBirthday(LocalDate.of(1900, 1,
                        1));
                user.setGmtCreate(LocalDateTime.now());
                user.setGmtModified(LocalDateTime.now());
                userMapper.insert(user);
            }
        }
    }
}

package com.mit.community.module.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.common.util.DateUtils;
import com.mit.common.util.UUIDUtils;
import com.mit.community.entity.User;
import com.mit.community.entity.UserLabel;
import com.mit.community.module.system.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final UserMapper userMapper;

    private final UserLabelService userLabelService;

    @Autowired
    public UserService(UserMapper userMapper, UserLabelService userLabelService) {
        this.userMapper = userMapper;
        this.userLabelService = userLabelService;
    }

    /**
     * 保存
     * @param user user
     * @author shuyy
     * @date 2018/11/29 11:25
     * @company mitesofor
    */
    public void save(User user){
        user.setGmtCreate(LocalDateTime.now());
        user.setGmtModified(LocalDateTime.now());
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

    /**
     * 注册
     * @param cellphone 电话号码
     * @param username 用户名
     * @param password 密码
     * @param labelCodes 标签code
     * @author shuyy
     * @date 2018/11/30 9:29
     * @company mitesofor
    */
    @Transactional(rollbackFor = Exception.class)
    public void register(String cellphone, String username, String password, String[] labelCodes){
        User user = new User(cellphone, password, cellphone, (short) 1, StringUtils.EMPTY, StringUtils.EMPTY, DateUtils.getNull(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        this.save(user);
        for (String labelCode : labelCodes) {
            UserLabel userLabel = new UserLabel(labelCode, user.getId());
            userLabelService.save(userLabel);
        }
    }

    /**
     * 选择标签
     * @param cellphone 电话号码
     * @param labelList label列表
     * @author shuyy
     * @date 2018/11/30 9:41
     * @company mitesofor
    */
    @Transactional(rollbackFor = Exception.class)
    public void chooseLabelList(String cellphone, String[] labelList){
        /*User user = new User(cellphone, UUIDUtils.generateShortUuid(), StringUtils.EMPTY, (short) 1, StringUtils.EMPTY, cellphone,
                StringUtils.EMPTY, DateUtils.getNull(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        this.save(user);
        for (String labelCode : labelList) {
            UserLabel userLabel = new UserLabel(labelCode, user.getId());
            userLabelService.save(userLabel);
        }*/
    }

}

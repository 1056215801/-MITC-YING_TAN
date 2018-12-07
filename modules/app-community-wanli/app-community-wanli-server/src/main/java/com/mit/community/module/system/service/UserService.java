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
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    private final UserMapper userMapper;

    @Autowired
    private UserLabelService userLabelService;

    @Autowired
    private RedisService redisService;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    private UserLabelService userLabelService;

    /**
     * 查询用户信息，通过用户id
     * @param id 用户id
     * @return 用户信息
     * @author Mr.Deng
     * @date 14:28 2018/12/7
     */
    public User getById(Integer id) {
        return userMapper.selectById(id);
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @date 10:56 2018/12/7
     */
    public void update(User user) {
        user.setGmtModified(LocalDateTime.now());
        userMapper.updateById(user);
    }

    /**
     * 保存
     * @param user user
     * @author shuyy
     * @date 2018/11/29 11:25
     * @company mitesofor
     */
    public void save(User user) {
        user.setGmtCreate(LocalDateTime.now());
        user.setGmtModified(LocalDateTime.now());
        userMapper.insert(user);
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
    public User getByCellphoneAndPassword(String cellphone, String password) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellphone);
        wrapper.eq("password", password);
        List<User> users = userMapper.selectList(wrapper);
        if (users.isEmpty()) {
            return null;
        } else {
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
    public User getByCellphone(String cellphone) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellphone);
        List<User> users = userMapper.selectList(wrapper);
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    /**
     * 注册
     * @param cellphone  电话号码
     * @param password   密码
     * @author shuyy
     * @date 2018/12/07 16:52
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void register(String cellphone, String password){
        User user = new User(cellphone, password, cellphone, (short) 0, StringUtils.EMPTY, StringUtils.EMPTY, Constants.NULL_LOCAL_DATE_TIME, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        this.save(user);
//        for (String labelCode : labelCodes) {
//            UserLabel userLabel = new UserLabel(labelCode, user.getId());
//            userLabelService.save(userLabel);
//        }
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
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        for (String labelCode : labelList) {
            UserLabel userLabel = new UserLabel(labelCode, user.getId());
            userLabelService.save(userLabel);
        }
    }

    /**
     * 修改用户信息
     * @param id         用户id
     * @param nickname   昵称
     * @param gender     性别1、男。0、女。
     * @param email      邮件
     * @param cellphone  电话
     * @param icon_url   头像地址
     * @param birthday   生日 yyyy-MM-dd HH:mm:ss
     * @param bloodType  血型
     * @param profession 职业
     * @param signature  我的签名
     * @author Mr.Deng
     * @date 14:35 2018/12/7
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(Integer id, String nickname, Short gender, String email,
                               String cellphone, String iconUrl, String birthday, String bloodType, String profession,
                               String signature) {
        LocalDateTime birthdayTime = DateUtils.parseStringToDateTime(birthday, null);
        User user = this.getById(id);
        if (user != null) {
            user.setNickname(nickname);
            user.setGender(gender);
            user.setEmail(email);
            user.setCellphone(cellphone);
            user.setIcon_url(iconUrl);
            user.setBirthday(birthdayTime);
            user.setBloodType(bloodType);
            user.setProfession(profession);
            user.setSignature(signature);

        }
    }

}

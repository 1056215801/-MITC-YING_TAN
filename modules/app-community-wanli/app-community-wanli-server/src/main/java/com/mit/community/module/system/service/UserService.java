package com.mit.community.module.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.common.util.DateUtils;
import com.mit.community.constants.Constants;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.User;
import com.mit.community.entity.UserLabel;
import com.mit.community.module.system.mapper.UserMapper;
import com.mit.community.service.DnakeAppApiService;
import com.mit.community.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UserLabelService userLabelService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private DnakeAppApiService dnakeAppApiService;

    /**
     * 查询用户信息，通过用户id
     *
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
     *
     * @param user 用户信息
     * @date 10:56 2018/12/7
     */
    public void update(User user) {
        user.setGmtModified(LocalDateTime.now());
        userMapper.updateById(user);
    }

    /**
     * 保存
     *
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
     *
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
     *
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
     *
     * @param cellphone 电话号码
     * @param password  密码
     * @author shuyy
     * @date 2018/12/07 16:52
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer register(String cellphone, String password) {
        int status = 1;
        User user = this.getByCellphone(cellphone);
        if(user != null){
            status = 0;
            return status;
        }
        user = new User(cellphone, password, 0, cellphone, (short) 0, StringUtils.EMPTY, StringUtils.EMPTY, Constants.NULL_LOCAL_DATE, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, "普通业主");
        this.save(user);
        return status;
    }

    /**
     * 选择标签
     *
     * @param cellphone 电话号码
     * @param labelList label列表
     * @author shuyy
     * @date 2018/11/30 9:41
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void chooseLabelList(String cellphone, String[] labelList) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        for (String labelCode : labelList) {
            UserLabel userLabel = new UserLabel(labelCode, user.getId(), null);
            userLabelService.save(userLabel);
        }
    }

    /**
     * 修改用户信息
     *
     * @param userId     用户id
     * @param nickname   昵称
     * @param gender     性别1、男。0、女。
     * @param email      邮件
     * @param cellphone  电话
     * @param iconUrl    头像地址
     * @param birthday   生日 yyyy-MM-dd HH:mm:ss
     * @param bloodType  血型
     * @param profession 职业
     * @param signature  我的签名
     * @author Mr.Deng
     * @date 14:35 2018/12/7
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(Integer userId, String nickname, Short gender, String email, String cellphone,
                               String iconUrl, String birthday, String bloodType, String profession, String signature) {
        LocalDate birthdayTime = DateUtils.parseStringToLocalDate(birthday, null);
        User user = this.getById(userId);
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
            this.update(user);
        }
    }

    /**
     * 修改密码
     *
     * @param cellPhone   电话号码
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     * @return 返回状态码（1，重置成功；0，密码不匹配）
     * @author Mr.Deng
     * @date 14:19 2018/12/8
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer modifyPwd(String cellPhone, String newPassword, String oldPassword) {
        User user = this.getByCellphone(cellPhone);
        int result = 0;
        if (user != null) {
            if (oldPassword.equals(user.getPassword())) {
                //匹配成功修改数据库用户密码
                user.setPassword(newPassword);
                this.update(user);
                //然后调用狄耐克重置密码接口重置狄耐克密码
                dnakeAppApiService.resetPwd(cellPhone, newPassword);
                //重置成功并退出登录
                this.loginOut(cellPhone);
                result = 1;
            }
        }
        return result;
    }

    /**
     * 重置密码
     * @param cellPhone   电话号码
     * @param newPassword 新密码
     * @return 返回状态码（1，重置成功；0，密码不匹配）
     * @author Mr.Deng
     * @date 14:19 2018/12/8
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer resetPwd(String cellPhone, String newPassword) {
        User user = this.getByCellphone(cellPhone);
        int result = 0;
        if (user != null) {
            user.setPassword(newPassword);
            this.update(user);
            //然后调用狄耐克重置密码接口重置狄耐克密码
            dnakeAppApiService.resetPwd(cellPhone, newPassword);
            //重置成功并退出登录
            this.loginOut(cellPhone);
            result = 1;
        }
        return result;
    }

/*    *//**
     * 修改密码
     * @param cellPhone   电话号码
     * @param newPassword 新密码
     * @return 返回状态码（1，重置成功；0，密码不匹配）
     * @author Mr.Deng
     * @date 14:19 2018/12/8
     *//*
    @Transactional(rollbackFor = Exception.class)
    public Integer updateCellphone(String cellPhone, String newCellphone) {
        User user = this.getByCellphone(cellPhone);
        user.setCellphone(newCellphone);
            this.update(user);
            //然后调用狄耐克重置密码接口重置狄耐克密码
            dnakeAppApiService.resetPwd(cellPhone, newPassword);
            //重置成功并退出登录
            this.loginOut(cellPhone);
            result = 1;
        return result;
    }*/

    /**
     * 登出
     *
     * @param cellPhone 手机号码
     * @author Mr.Deng
     * @date 14:47 2018/12/8
     */
    public void loginOut(String cellPhone) {
        redisService.remove(RedisConstant.DNAKE_LOGIN_RESPONSE + cellPhone);
        redisService.remove(RedisConstant.USER + cellPhone);
    }

}

package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.constants.Constants;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.HouseholdRoom;
import com.mit.community.entity.User;
import com.mit.community.mapper.UserMapper;
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
    @Autowired
    private HouseholdRoomService householdRoomService;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private UserService userService;

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
    @CacheClear(key = "user:cellphone{1.cellphone}")
    @Transactional(rollbackFor = Exception.class)
    public void update(User user) {
        user.setGmtModified(LocalDateTime.now());
        userMapper.updateById(user);
        User u = this.getById(user.getId());
        u.setPassword(null);
        redisService.set(RedisConstant.USER + user.getCellphone(), u);
    }

    /**
     * 保存
     *
     * @param user user
     * @author shuyy
     * @date 2018/11/29 11:25
     * @company mitesofor
     */
    @CacheClear(key = "user:cellphone{1.cellphone}")
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
   /*
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
    }*/

    /**
     * 获取User，通过cellphone
     *
     * @param cellphone 手机号
     * @return com.mit.community.entity.User
     * @author shuyy
     * @date 2018/11/29 11:28
     * @company mitesofor
     */
    @CacheClear(key = "user:cellphone{1}")
    @Transactional
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
     * 获取User，通过cellphone，无缓存
     *
     * @param cellphone 手机号
     * @return com.mit.community.entity.User
     * @author shuyy
     * @date 2018/11/29 11:28
     * @company mitesofor
     */
    //@Cache(key = "user:cellphone{1}")
    @Transactional
    public User getByCellphoneNoCache(String cellphone) {
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
    @CacheClear(key = "user:cellphone{1}")
    @Transactional(rollbackFor = Exception.class)
    public Integer register(String cellphone, String password) {
        int status = 1;
        User user = this.getByCellphone(cellphone);
        if (user != null) {
            status = 0;
            return status;
        }
        user = new User(cellphone, password, 0, cellphone, (short) 0, StringUtils.EMPTY, Constants.USER_ICO_DEFULT,
                Constants.NULL_LOCAL_DATE, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                "普通业主", StringUtils.EMPTY, null, null, null,
                null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null);
        this.save(user);
        return status;
    }

    /**
     * 修改用户信息
     *
     * @param cellphone     手机号
     * @param userId        用户id
     * @param nickname      昵称
     * @param gender        性别1、男。0、女。
     * @param birthday      出生日期 yyyy-MM-dd
     * @param bloodType     血型
     * @param profession    职业
     * @param signature     我的签名
     * @param constellation 星座
     * @author Mr.Deng
     * @date 14:35 2018/12/7
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(Integer userId, String nickname, Short gender, LocalDate birthday, String bloodType,
                               String profession, String signature, String constellation, String cellphone) {
        User user = new User(cellphone, null, null, nickname, gender, null, null,
                birthday, bloodType, profession, signature, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null);
        user.setId(userId);
        userService.update(user);
//        User user = this.getById(userId);
        if (StringUtils.isNotBlank(constellation)) {
            List<HouseHold> houseHoldList = houseHoldService.getByCellphone(cellphone);
            if (!houseHoldList.isEmpty()) {
                houseHoldList.forEach(item -> {
                    item.setConstellation(constellation);
                    houseHoldService.update(item);
                });
            }
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
    @CacheClear(key = "user:cellphone{1}")
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
     *
     * @param cellPhone   电话号码
     * @param newPassword 新密码
     * @return 返回状态码（1，重置成功；0，密码不匹配）
     * @author Mr.Deng
     * @date 14:19 2018/12/8
     */
    @CacheClear(key = "user:cellphone{1}")
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

    /**
     * 判断是否已经登录
     *
     * @param cellphone 手机号
     * @author shuyy
     * @date 2018/12/18 10:31
     * @company mitesofor
     */
    public boolean haveLogin(String mac, String cellphone) {
        Object o = redisService.get(RedisConstant.USER + cellphone);
        if (o == null) {
            return false;
        } else {
            String redisMac = (String) redisService.get(RedisConstant.MAC + cellphone);
            if (!mac.equals(redisMac)) {
                return false;
            }
            return true;
        }
    }

    /**
     * 更新用户手机号
     *
     * @param cellPhone    旧手机号
     * @param newCellPhone 新手机号
     * @author shuyy
     * @date 2018/12/13 16:15
     * @company mitesofor
     */
    @CacheClear(key = "user:cellphone{1}")
    @Transactional(rollbackFor = Exception.class)
    public void updateHouseholdCellphone(String cellPhone, String newCellPhone) {
        User user = this.getByCellphone(cellPhone);
        user.setCellphone(newCellPhone);
        this.update(user);
        // 修改dnake手机号
        HouseHold houseHold = houseHoldService.getByHouseholdId(user.getHouseholdId());
        List<HouseholdRoom> householdRooms = householdRoomService.listByHouseholdId(user.getHouseholdId());
        boolean status = dnakeAppApiService.updateHouseholdCellphone(user.getHouseholdId(),
                houseHold.getCommunityCode(), houseHold.getHouseholdName(), householdRooms, newCellPhone);
        if (!status) {
            throw new RuntimeException("更新失败");
        }
    }

    /**
     * 更新用户的住户信息id
     *
     * @param cellphone
     * @param householdId
     */
    public void updateCellphoneByHouseholdId(String cellphone, Integer householdId) {
        userMapper.updateHouseholdIdByMobile(householdId, cellphone);
    }

    /**
     * 通过身份证获取用户
     * @param IDNumber
     * @return
     */
    @Transactional
    public User getByIDNumber(String IDNumber) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("id_card_num", IDNumber);
        List<User> users = userMapper.selectList(wrapper);
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public void addDeviceDugAccount(String name, String cellPhone, String sex, String communityCode, String accountType, String expireTime, String remarks) {
        User user = new User();
        user.setCellphone(cellPhone);
        user.setPassword("123456");
        user.setNickname(name);
        user.setName(name);
=======
    public void addDeviceDugAccount(String cellPhone, String sex, String communityCode, String accountType) {
        User user = new User();
        user.setCellphone(cellPhone);
        user.setPassword("123456");
        user.setNickname("设备调试员");
>>>>>>> remotes/origin/newdev
=======
    public void addDeviceDugAccount(String name, String cellPhone, String sex, String communityCode, String accountType, String expireTime, String remarks) {
        User user = new User();
        user.setCellphone(cellPhone);
        user.setPassword("123456");
        user.setNickname(name);
        user.setName(name);
>>>>>>> 575d0536f7a990502d9678f3d35bb9f1fab83d10
        user.setGender((short)("男".equals(sex)? 1:2));
        user.setRole("设备调试员");
        user.setIcon_url("http://www.miesofor.tech/1ec47936-e19a-43d2-86c1-481ddfe07a8c.png");
        user.setBirthday(LocalDate.of(1900, 1,
                1));
        user.setGmtCreate(LocalDateTime.now());
        user.setGmtModified(LocalDateTime.now());
        user.setIdentity(1);
<<<<<<< HEAD
<<<<<<< HEAD
        user.setSerialnumber(communityCode);
        user.setFaceToken(accountType);
        user.setDoornum(expireTime);//到期时间
        user.setCardnum(remarks);

        User userExits = userService.getByCellphone(cellPhone);
        if (userExits == null) {
            userMapper.insert(user);
        } else {
            EntityWrapper<User> wrapper = new EntityWrapper<>();
            wrapper.eq("id", userExits.getId());
            userMapper.update(user, wrapper);
        }
=======
        user.setSerialnumber(String.valueOf(communityCode));
        user.setFaceToken(accountType);
        userMapper.insert(user);
>>>>>>> remotes/origin/newdev
=======
        user.setSerialnumber(communityCode);
        user.setFaceToken(accountType);
        user.setDoornum(expireTime);//到期时间
        user.setCardnum(remarks);

        User userExits = userService.getByCellphone(cellPhone);
        if (userExits == null) {
            userMapper.insert(user);
        } else {
            EntityWrapper<User> wrapper = new EntityWrapper<>();
            wrapper.eq("id", userExits.getId());
            userMapper.update(user, wrapper);
        }
>>>>>>> 575d0536f7a990502d9678f3d35bb9f1fab83d10
    }

    public void deviceDugPeopleChange(String cellPhone, Integer changeType) {
        User user = new User();
        if (changeType == 1) {
            user.setIdentity(1);
        } else {
<<<<<<< HEAD
<<<<<<< HEAD
            user.setIdentity(2);
=======
            user.setIdentity(0);
>>>>>>> remotes/origin/newdev
=======
            user.setIdentity(2);
>>>>>>> 575d0536f7a990502d9678f3d35bb9f1fab83d10
        }
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellPhone);
        userMapper.update(user, wrapper);
    }
}

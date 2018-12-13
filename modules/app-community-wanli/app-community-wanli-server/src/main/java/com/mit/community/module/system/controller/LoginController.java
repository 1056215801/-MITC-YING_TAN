package com.mit.community.module.system.controller;

import com.mit.common.util.DateUtils;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.module.system.service.UserService;
import com.mit.community.service.*;
import com.mit.community.util.FastDFSClient;
import com.mit.community.util.Result;
import com.mit.community.util.SmsCommunityAppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 注册登陆
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/login")
@Slf4j
@Api(tags = "注册登录")
public class LoginController {

    private final RedisService redisService;
    private final UserService userService;
    private final ClusterCommunityService clusterCommunityService;
    private final DnakeAppApiService dnakeAppApiService;
    private final HouseHoldService houseHoldService;
    private final UserTrackService userTrackService;

    @Autowired
    public LoginController(RedisService redisService, UserService userService, ClusterCommunityService clusterCommunityService, DnakeAppApiService dnakeAppApiService,
                           HouseHoldService houseHoldService, UserTrackService userTrackService) {
        this.redisService = redisService;
        this.userService = userService;
        this.clusterCommunityService = clusterCommunityService;
        this.dnakeAppApiService = dnakeAppApiService;
        this.houseHoldService = houseHoldService;
        this.userTrackService = userTrackService;
    }

    /***
     * 获取手机验证码
     * @param cellphone 手机号
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/29 10:55
     * @company mitesofor
     */
    @GetMapping("/getMobileVerificationCode")
    @ApiOperation(value = "获取手机验证码", notes = "传参：cellphone 手机号、type 类型：1 注册, 2 登陆")
    public Result getMobileVerificationCode(String mac, String cellphone, Integer type) {
        String code = SmsCommunityAppUtil.generatorCode();
        if (SmsCommunityAppUtil.TYPE_REGISTER.equals(type)) {
            // 注册
            SmsCommunityAppUtil.send(cellphone, code, SmsCommunityAppUtil.TYPE_REGISTER);
        } else if (SmsCommunityAppUtil.TYPE_LOGIN_CONFIRM.equals(type)) {
            SmsCommunityAppUtil.send(cellphone, code, SmsCommunityAppUtil.TYPE_LOGIN_CONFIRM);
        }
        redisService.set(RedisConstant.VERIFICATION_CODE + cellphone, code, RedisConstant.VERIFICATION_CODE_EXPIRE_TIME);
        return Result.success("发送成功");
    }

    /**
     * 快捷登录或密码登录
     * @param cellphone        手机号
     * @param verificationCode 手机号验证码
     * @param password         密码
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/29 11:02
     * @company mitesofor
     */
    @PostMapping("/login")
    @ApiOperation(value = "快捷登录或密码登录", notes = "密码登陆则传参verificationCode不传password。密码登录则相反。<br/> " +
            "传参;cellphone 手机号：verificationCode 手机验证码。password：密码<br/> " +
            "返回：1、resultStatus:false, message: 验证码错误。<br/>" +
            "2、resultStatus:false, message: 用户不存在。<br/>" +
            "3、resultStatus: true, message: 没有关联住户, object:user。<br/>" +
            "4、resultStatus: true, message: 没有授权app, object:user。<br/>" +
            "5、resultStatus: true, message: 已授权app, object:user。")
    public Result login(String mac, String cellphone, String verificationCode, String password) {
        User user;
        if (verificationCode != null) {
            // 验证码登陆
            Object o = redisService.get(RedisConstant.VERIFICATION_CODE + cellphone);
            if (o == null || !verificationCode.equals(o.toString())) {
                return Result.error("验证码错误");
            }
            user = userService.getByCellphone(cellphone);
            if (user == null) {
                return Result.error("用户不存在");
            }
        } else {
            // 密码登陆
            user = userService.getByCellphoneAndPassword(cellphone, password);
            if (user == null) {
                return Result.success("用户名或密码错误");
            }
        }
        String psd = user.getPassword();
        user.setPassword(StringUtils.EMPTY);
        // redis中保存用户
        redisService.set(RedisConstant.USER + user.getCellphone(), user);
        redisService.set(RedisConstant.MAC + user.getCellphone(), mac);
        HouseHold houseHold = houseHoldService.getByCellphone(user.getCellphone());
        if (houseHold == null) {
            return Result.success(user, "没有关联住户");
        } else {
            if (user.getHouseholdId() == 0) {
                user.setHouseholdId(houseHold.getHouseholdId());
                user.setPassword(psd);
                userService.update(user);
            }
        }
        Integer authorizeStatus = houseHold.getAuthorizeStatus();
        String s = Integer.toBinaryString(authorizeStatus);
        if (s.charAt(1) != '1') {
            return Result.success(user, "没有授权app");
        } else {
            // 已经授权app
            DnakeLoginResponse dnakeLoginResponse = dnakeAppApiService.login(cellphone, psd);
            redisService.set(RedisConstant.DNAKE_LOGIN_RESPONSE + cellphone,
                    dnakeLoginResponse, RedisConstant.LOGIN_EXPIRE_TIME);
            return Result.success(user, "已授权app");
        }
    }

    /**
     * 登出
     * @param cellphone 用户登录手机号
     * @return result
     * @author Mr.Deng
     * @date 14:49 2018/12/8
     */
    @ApiOperation(value = "登出", notes = "输入参数：cellphone 用户登录手机号")
    @GetMapping("/loginOut")
    public Result loginOut(String mac, String cellphone) {
        if (StringUtils.isNotBlank(cellphone)) {
            userService.loginOut(cellphone);
            //添加足迹
            userTrackService.addUserTrack(cellphone, "退出登录", "退出登录成功");
            return Result.success("退出成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 选择标签
     * @param cellphone 电话号码
     * @param labelList label列表
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/30 9:39
     * @company mitesofor
     */
    @PostMapping("/chooseLabelList")
    @ApiOperation(value = "选择标签", notes = "传参;cellphone 手机号：labelList 多个标签。标签来自于数据字典parentCode为label")
    public Result chooseLabelList(String cellphone, String[] labelList) {
        if (StringUtils.isNotBlank(cellphone)) {
            userService.chooseLabelList(cellphone, labelList);
            return Result.success("成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 选择性别
     * @param cellphone 电话号码
     * @param gender    性别
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/7 18:22
     * @company mitesofor
     */
    @PostMapping("/updateGender")
    @ApiOperation(value = "选择性别", notes = "传参;cellphone 手机号：gender 性别，1、男。2、女")
    public Result updateGender(String cellphone, Short gender) {
        if (StringUtils.isNotBlank(cellphone)) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            user.setGender(gender);
            userService.update(user);
            return Result.success("成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 选择出生日期和昵称
     * @param cellphone 手机号
     * @param birthday  出生日期
     * @param nickName  昵称
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/7 18:22
     * @company mitesofor
     */
    @PostMapping("/updateBirthdayAndNick")
    @ApiOperation(value = "选择出生日期和昵称", notes = "传参;cellphone 手机号、birthday 出生日期、nickName 昵称 ")
    public Result updateBirthdayAndNick(String cellphone, String birthday, String nickName) {
        if (StringUtils.isNotBlank(cellphone)) {
            LocalDate localDate = DateUtils.parseStringToLocalDate(birthday, null);
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            user.setBirthday(localDate);
            user.setNickname(nickName);
            user.setPassword(null);
            userService.update(user);
            return Result.success("成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 选择地区
     * @param cellphone 手机号
     * @param region    地区 （省+市）
     * @return result
     * @author Mr.Deng
     * @date 11:20 2018/12/13
     */
    @PostMapping("/updateRegion")
    @ApiOperation(value = "添加地区", notes = "传参：cellphone 手机号；region 地区 （省+市）")
    public Result updateRegion(String cellphone, String region) {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(region)) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            user.setRegion(region);
            userService.update(user);
            return Result.success("成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 手机验证码验证
     * @param cellphone        手机号
     * @param verificationCode 手机号验证码
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/29 11:06
     * @company mitesofor
     */
    @GetMapping("/cellphoneVerification")
    @ApiOperation(value = "手机验证码验证", notes = "传参;cellphone 手机号：verificationCode 手机验证码")
    public Result cellphoneVerification(String mac, String cellphone, String verificationCode) {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(verificationCode)) {
            Object o = redisService.get(RedisConstant.VERIFICATION_CODE + cellphone);
            if (o == null || !verificationCode.equals(o.toString())) {
                return Result.error("验证码错误");
            }
            redisService.set(RedisConstant.VERIFICATION_SUCCESS + cellphone, cellphone, RedisConstant.VERIFICATION_SUCCESS_EXPIRE_TIME);
            return Result.success("验证成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 注册
     * @param cellphone 手机号
     * @param password  密码
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/07 16:53
     * @company mitesofor
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "传参;cellphone 手机号;password 密码")
    public Result register(String cellphone, String password) {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(password)) {
            Object o = redisService.get(RedisConstant.VERIFICATION_SUCCESS + cellphone);
            if (o == null) {
                return Result.error("请在10分钟内完成注册");
            }
            int status = userService.register(cellphone, password);
            if (status == 0) {
                return Result.success("用户已经存在");
            } else {
                return Result.success("注册成功");
            }
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询用户授权的所有小区
     * @param cellphone 手机号
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/30 11:48
     * @company mitesofor
     */
    @GetMapping("/listClusterCommunityByUserCellphone")
    @ApiOperation(value = "查询用户授权的所有小区", notes = "传参;cellphone 手机号")
    public Result listClusterCommunityByUserCellphone(String cellphone) {
        if (StringUtils.isNotBlank(cellphone)) {
            List<ClusterCommunity> clusterCommunities = clusterCommunityService.listClusterCommunityByUserCellphone(cellphone);
            if (clusterCommunities == null) {
                return Result.success("没有关联小区");
            }
            return Result.success(clusterCommunities);
        }
        return Result.error("参数不能为空");
    }

//    /**
//     * @param communityCode 小区code
//     * @param cellphone     手机号
//     * @return com.mit.community.util.Result
//     * @author shuyy
//     * @date 2018/11/30 11:50
//     * @company mitesofor
//     */
//    @GetMapping("/listDeviceByCommunityCodeAndCellphone")
//    @ApiOperation(value = "全部钥匙", notes = "传参;communityCode 小区code, cellphone 手机号")
//    public Result listDeviceByCommunityCodeAndCellphone(String communityCode, String cellphone) {
//        List<Device> devices = deviceService.listDeviceByCommunityCodeAndCellphone(communityCode,
//                cellphone);
//        if (devices == null) {
//            return Result.success("没有钥匙");
//        }
//        return Result.success(devices);
//    }

    /**
     * 修改用户信息
     * @param cellphone     手机号
     * @param nickname      昵称
     * @param gender        性别1、男。0、女。
     * @param birthday      生日 yyyy-MM-dd
     * @param bloodType     血型
     * @param profession    职业
     * @param signature     我的签名
     * @param constellation 星座
     * @return result
     * @author Mr.Deng
     * @date 12:02 2018/12/8
     */
    @ApiOperation(value = "修改用户信息", notes = "输入信息：cellphone 手机号；nickname 昵称；gender 性别1、男。0、女；" +
            "birthday 生日 yyyy-MM-dd；bloodType 血型；profession 职业；signature 我的签名；constellation 星座")
    @PatchMapping("/updateUserInfo")
    public Result updateUserInfo(String nickname, Short gender, String cellphone, String birthday, String bloodType,
                                 String profession, String signature, String constellation) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        if (user != null) {
            userService.updateUserInfo(user.getId(), nickname, gender, birthday, bloodType, profession, signature, constellation, cellphone);
            //添加足迹
            userTrackService.addUserTrack(cellphone, "修改用户信息", "修改用户信息成功");
            return Result.success("修改成功");
        }
        return Result.error("请登录");
    }

    /**
     * 修改头像
     * @param cellphone 手机号
     * @param image     头像图片
     * @return result
     * @author Mr.Deng
     * @date 11:04 2018/12/13
     */
    @PostMapping(value = "/updateUserIco", produces = {"application/json"})
    @ApiOperation(value = "修改头像", notes = "传参：cellphone 手机号，image 头像图片")
    public Result updateUserIco(String cellphone, MultipartFile image) throws Exception {
        if (StringUtils.isNotBlank(cellphone) && image != null) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                String imageUrl = Objects.requireNonNull(FastDFSClient.getInstance()).uploadFile(image);
                user.setIcon_url(imageUrl);
                userService.update(user);
                //记录足迹
                userTrackService.addUserTrack(cellphone, "修改头像", "修改头像成功");
                return Result.success("成功");
            }
            return Result.error("请登录");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 修改密码
     * @param cellPhone   电话号码
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     * @return result
     * @author Mr.Deng
     * @date 13:54 2018/12/8
     */
    @PatchMapping("/modifyPwd")
    @ApiOperation(value = "修改密码", notes = "输入参数：cellPhone 电话号码；newPassword 新密码；oldPassword 旧密码")
    public Result modifyPwd(String cellPhone, String newPassword, String oldPassword) {
        if (StringUtils.isNotBlank(cellPhone) && StringUtils.isNotBlank(newPassword) && StringUtils.isNotBlank(oldPassword)) {
            Integer status = userService.modifyPwd(cellPhone, newPassword, oldPassword);
            if (status == 0) {
                return Result.error("旧密码不匹配");
            }
            if (status == 1) {
                userTrackService.addUserTrack(cellPhone, "修改密码", "修改密码成功");
                return Result.success("修改密码成功");
            }
        }
        return Result.error("参数不能为空");
    }

    /**
     * 重置密码
     * @param cellphone   手机号
     * @param newPassword 新密码
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/11 9:59
     * @company mitesofor
     */
    @PatchMapping("/resetPwd")
    @ApiOperation(value = "重置密码", notes = "输入参数：cellPhone 电话号码；newPassword 新密码")
    public Result resetPwd(String cellphone, String newPassword) {
        Object o = redisService.get(RedisConstant.VERIFICATION_SUCCESS + cellphone);
        if (o == null) {
            return Result.error("请在10分钟内完成重置密码");
        }
        Integer status = userService.resetPwd(cellphone, newPassword);
        if (status == 0) {
            return Result.success("不存在用户");
        }
        userTrackService.addUserTrack(cellphone, "修改密码", "修改密码成功");
        return Result.success("重置成功");
    }

    /**
     * 查询我的资料，通告手机号和小区code
     * @param cellphone     手机号
     * @param communityCode 小区code
     * @return result
     * @author Mr.Deng
     * @date 13:44 2018/12/13
     */
    @GetMapping("/myProfile")
    @ApiOperation(value = "我的资料", notes = "反回参数：nickName 昵称，gender 性别；bloodType 血型；birthday 出生日期yyyy-mm-dd;" +
            "constellation 星座；region 地区；profession 职业，role 身份；coordinates 小区位置；signature 签名；userLabels 用户标签")
    public Result myProfile(String cellphone, String communityCode) {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(communityCode)) {
            Map<String, Object> map = userService.mapProfile(cellphone, communityCode);
            return Result.success(map);
        }
        return Result.error("参数不能为空");
    }

   @PatchMapping("/updateCellphone")
    @ApiOperation(value = "修改手机号", notes = "输入参数：cellPhone 电话号码；newPassword 新密码")
    public Result updateCellphone(String mac, String cellphone, String newCellphone) {
        Object o = redisService.get(RedisConstant.VERIFICATION_SUCCESS + newCellphone);
        if (o == null) {
            return Result.error("请在10分钟内完成修改手机号");
        }
        try {
            userService.updateHouseholdCellphone(cellphone, newCellphone);
        } catch (Exception e){
            e.printStackTrace();
            return Result.error("修改失败");
        }
        return Result.success("修改成功");
    }

}
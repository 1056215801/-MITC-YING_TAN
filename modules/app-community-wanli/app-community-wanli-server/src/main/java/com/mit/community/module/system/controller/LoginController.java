package com.mit.community.module.system.controller;

import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.module.system.service.UserService;
import com.mit.community.service.*;
import com.mit.community.util.Result;
import com.mit.community.util.SmsCommunityAppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 注册登陆
 *
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

    private final DeviceService deviceService;

    private final ClusterCommunityService clusterCommunityService;

    private final DnakeAppApiService dnakeAppApiService;

    private final HouseHoldService houseHoldService;


    @Autowired
    public LoginController(RedisService redisService, UserService userService, DeviceService deviceService, ClusterCommunityService clusterCommunityService, DnakeAppApiService dnakeAppApiService, HouseHoldService houseHoldService) {
        this.redisService = redisService;
        this.userService = userService;
        this.deviceService = deviceService;
        this.clusterCommunityService = clusterCommunityService;
        this.dnakeAppApiService = dnakeAppApiService;
        this.houseHoldService = houseHoldService;
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
    @ApiOperation(value = "获取手机验证码", notes="传参：cellphone 手机号、type 类型：1 注册, 2 登陆")
    public Result getMobileVerificationCode(String cellphone, Integer type) {
        String code = SmsCommunityAppUtil.generatorCode();
        if (type == SmsCommunityAppUtil.TYPE_REGISTER) {
            // 注册
            SmsCommunityAppUtil.send(cellphone, code, SmsCommunityAppUtil.TYPE_REGISTER);
        }else if(type == SmsCommunityAppUtil.TYPE_LOGIN_CONFIRM){
            SmsCommunityAppUtil.send(cellphone, code, SmsCommunityAppUtil.TYPE_LOGIN_CONFIRM);
        }
        redisService.set(RedisConstant.VERIFICATION_CODE + cellphone, code, RedisConstant.VERIFICATION_CODE_EXPIRE_TIME);
        return Result.success("发送成功");
    }

    /***
     * @param cellphone 手机号
     * @param verificationCode 手机号验证码
     * @param password 密码
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
    public Result login(String cellphone, String verificationCode, String password) {
        User user = null;
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
        redisService.set(RedisConstant.USER + user.getCellphone(), user, RedisConstant.LOGIN_EXPIRE_TIME);
        List<HouseHold> houseHolds = houseHoldService.listByCellphone(user.getCellphone());
        if (houseHolds.isEmpty()) {
            return Result.success(user, "没有关联住户");
        }
        List<HouseHold> filterAppHouseholds = houseHoldService.filterAuthorizedApp(houseHolds);
        if (filterAppHouseholds.isEmpty()) {
            // 没有授权app
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
     * 选择标签
     *
     * @param cellphone 电话号码
     * @param labelList label列表
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/30 9:39
     * @company mitesofor
     */
    @PostMapping("chooseLabelList")
    @ApiOperation(value = "选择标签", notes = "传参;cellphone 手机号：labelList 多个标签")
    public Result chooseLabelList(String cellphone, String[] labelList) {
        userService.chooseLabelList(cellphone, labelList);
        return Result.success("成功");
    }

    /**
     * @param cellphone
     * @param gender
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/7 18:22
     * @company mitesofor
     */
    @PostMapping("updateGender")
    @ApiOperation(value = "选择性别", notes = "传参;cellphone 手机号：gender 性别，1、男。2、女")
    public Result updateGender(String cellphone, Short gender) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        user.setGender(gender);
        userService.update(user);
        return Result.success("成功");
    }

    /***
     * @param cellphone 手机号
     * @param verificationCode 手机号验证码
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/29 11:06
     * @company mitesofor
     */
    @GetMapping("/cellphoneVerification")
    @ApiOperation(value = "手机验证码验证", notes = "传参;cellphone 手机号：verificationCode 手机验证码")
    public Result cellphoneVerification(String cellphone, String verificationCode) {
        Object o = redisService.get(RedisConstant.VERIFICATION_CODE + cellphone);
        if (o == null || !verificationCode.equals(o.toString())) {
            return Result.error("验证码错误");
        }
        redisService.set(RedisConstant.VERIFICATION_SUCCESS + cellphone, cellphone, RedisConstant.VERIFICATION_SUCCESS_EXPIRE_TIME);
        return Result.success("验证成功");
    }

    /**
     * @param cellphone 手机号
     * @param password  密码
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/07 16:53
     * @company mitesofor
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "传参;cellphone 手机号：username用户名，password 密码")
    public Result register(String cellphone, String password) {
        Object o = redisService.get(RedisConstant.VERIFICATION_SUCCESS + cellphone);
        if (o == null) {
            return Result.error("请在2分钟内完成注册");
        }
        userService.register(cellphone, password);
        return Result.success("注册成功");
    }

    /**
     * @param cellphone 手机号
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/30 11:48
     * @company mitesofor
     */
    @GetMapping("/listClusterCommunityByUserCellphone")
    @ApiOperation(value = "查询用户授权的所有小区", notes = "传参;cellphone 手机号")
    public Result listClusterCommunityByUserCellphone(String cellphone) {
        List<ClusterCommunity> clusterCommunities = clusterCommunityService.listClusterCommunityByUserCellphone(cellphone);
        if (clusterCommunities == null) {
            return Result.success("没有关联小区");
        }
        return Result.success(clusterCommunities);
    }

    /**
     * @param communityCode 小区code
     * @param cellphone     手机号
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/30 11:50
     * @company mitesofor
     */
    @GetMapping("/listDeviceByCommunityCodeAndCellphone")
    @ApiOperation(value = "全部钥匙", notes = "传参;communityCode 小区code, cellphone 手机号")
    public Result listDeviceByCommunityCodeAndCellphone(String communityCode, String cellphone) {
        List<Device> devices = deviceService.listDeviceByCommunityCodeAndCellphone(communityCode,
                cellphone);
        if (devices == null) {
            return Result.success("没有钥匙");
        }
        return Result.success(devices);
    }


}
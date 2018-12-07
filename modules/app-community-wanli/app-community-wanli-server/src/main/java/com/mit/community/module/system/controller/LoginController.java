package com.mit.community.module.system.controller;

import com.mit.community.constants.Constants;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Device;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.User;
import com.mit.community.service.DnakeAppApiService;
import com.mit.community.module.system.service.UserService;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.DeviceService;
import com.mit.community.service.HouseHoldService;
import com.mit.community.service.RedisService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
    @ApiOperation(value = "获取手机验证码")
    public Result getMobileVerificationCode(String cellphone) {
        String registerSmsCode = dnakeAppApiService.getRegisterSmsCode(cellphone);
        redisService.set(Constants.VERIFICATION_CODE + cellphone, registerSmsCode, RedisConstant.VERIFICATION_CODE_EXPIRE_TIME);
        return Result.success("发送成功");
    }

    /***
     * @param cellphone 手机号
     * @param verificationCode 手机号验证码
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/29 11:02
     * @company mitesofor
     */
    @PostMapping("/cellphoneLogin")
    @ApiOperation(value = "手机号登陆", notes = "传参;cellphone 手机号：verificationCode 手机验证码")
    public Result cellphoneLogin(String cellphone, String verificationCode) {
        Object o = redisService.get(Constants.VERIFICATION_CODE + cellphone);
        if (o == null || !verificationCode.equals(o.toString())) {
            return Result.error("验证码错误");
        }
        User user = userService.getByCellphone(cellphone);
        if (user == null) {
            redisService.set(Constants.VERIFICATION_SUCCESS + cellphone, cellphone, RedisConstant.VERIFICATION_SUCCESS_EXPIRE_TIME);
            return Result.success("第一次登陆");
        }
        HouseHold houseHold = houseHoldService.getByMobile(user.getCellphone());
        if(houseHold == null){
            return Result.success("登陆成功");
        }
        Integer authorizeStatus = houseHold.getAuthorizeStatus();
        String s = Integer.toBinaryString(authorizeStatus);
        if(s.indexOf(1) == 1){
            // 开启了app
            dnakeAppApiService.login(cellphone, user.getPassword());

        }else{

        }
        return Result.success("登陆成功");
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
        Object o = redisService.get(Constants.VERIFICATION_SUCCESS + cellphone);
        if (o == null) {
            return Result.error("请先登陆");
        }
        userService.chooseLabelList(cellphone, labelList);
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
    @ApiOperation(value = "注册-手机验证码验证", notes = "传参;cellphone 手机号：verificationCode 手机验证码")
    public Result cellphoneVerification(String cellphone, String verificationCode) {
        Object o = redisService.get(Constants.VERIFICATION_CODE + cellphone);
        if (o == null || !verificationCode.equals(o.toString())) {
            return Result.error("验证码错误");
        }
        redisService.set(Constants.VERIFICATION_SUCCESS + cellphone, cellphone, RedisConstant.VERIFICATION_SUCCESS_EXPIRE_TIME);
        return Result.success("验证成功");
    }

    /**
     * @param cellphone 手机号
     * @param username  用户名
     * @param password  密码
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/29 11:24
     * @company mitesofor
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册", notes = "传参;cellphone 手机号：username用户名，password 密码")
    public Result register(String cellphone, String username, String password, String[] labelCodes) {
        Object o = redisService.get(Constants.VERIFICATION_SUCCESS + cellphone);
        if (o == null) {
            return Result.error("请在2分钟内完成注册");
        }
        userService.register(cellphone, username, password, labelCodes);
        return Result.success("注册成功");
    }

    /**
     * 登陆
     *
     * @param username 用户名或手机号
     * @param password 密码
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/29 11:32
     * @company mitesofor
     */
    @PostMapping("/")
    @ApiOperation(value = "登陆", notes = "传参;username 用户名或手机号、password 密码")
    public Result login(String username, String password) {
        User user = userService.getByUsernameAndPassword(username, password);
        if (user == null) {
            user = userService.getByCellphoneAndPassword(username, password);
            if (user == null) {
                return Result.success("用户名或密码错误");
            }
        }
        HouseHold houseHold = houseHoldService.getByMobile(user.getCellphone());
        if(houseHold == null){

        }
        return Result.success("登陆成功");
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
        if(clusterCommunities == null){
            return Result.success("没有关联小区");
        }
        return Result.success(clusterCommunities);
    }

    /**
     * @param communityCode 小区code
     * @param cellphone 手机号
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
        if(devices == null){
            return Result.success("没有钥匙");
        }
        return Result.success(devices);
    }


}
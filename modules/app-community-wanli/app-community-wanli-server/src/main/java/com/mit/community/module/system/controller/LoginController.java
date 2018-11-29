package com.mit.community.module.system.controller;

import com.mit.common.util.UUIDUtils;
import com.mit.community.constants.Constants;
import com.mit.community.entity.User;
import com.mit.community.module.system.service.UserService;
import com.mit.community.service.RedisService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
@Api(value = "注册登录")
public class LoginController {

    private final RedisService redisService;

    private final UserService userService;

    @Autowired
    public LoginController(RedisService redisService, UserService userService) {
        this.redisService = redisService;
        this.userService = userService;
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
        redisService.set(Constants.VERIFICATION_CODE + cellphone, "123456", 120L);
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
    @GetMapping("/cellphoneLogin")
    @ApiOperation(value = "手机号登陆", notes = "传参;cellphone 手机号：verificationCode 手机验证码")
    public Result cellphoneLogin(String cellphone, String verificationCode) {
        Object o = redisService.get(Constants.VERIFICATION_CODE + cellphone);
        if (o == null || !verificationCode.equals(o.toString())) {
            return Result.error("验证码错误");
        }
        User user = userService.getByCellphone(cellphone);
        if (user == null) {
            user = new User(cellphone, UUIDUtils.generateShortUuid(), StringUtils.EMPTY, (short) 1, StringUtils.EMPTY, cellphone, StringUtils.EMPTY, LocalDateTime.MIN, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
            userService.save(user);
        }
        return Result.success("登陆成功");
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
        redisService.set(Constants.VERIFICATION_SUCCESS + cellphone, cellphone, 2L);
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
    @GetMapping("/register")
    @ApiOperation(value = "注册", notes = "传参;cellphone 手机号：username用户名，password 密码")
    public Result register(String cellphone, String username, String password, String[] label) {
        Object o = redisService.get(Constants.VERIFICATION_SUCCESS + cellphone);
        if (o == null) {
            return Result.error("请在2分钟内完成注册");
        }
        User user = new User(username, password, cellphone, (short) 1, StringUtils.EMPTY,
                cellphone, StringUtils.EMPTY, LocalDateTime.MIN, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);

        userService.save(user);
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
    @GetMapping("/")
    @ApiOperation(value = "登陆", notes = "传参;username 用户名或手机号、password 密码")
    public Result login(String username, String password) {
        User user = userService.getByUsernameAndPassword(username, password);
        if (user == null) {
            user = userService.getByCellphoneAndPassword(username, password);
            if (user == null) {
                return Result.success("用户名或密码错误");
            }
        }
        return Result.success("登陆成功");
    }


}

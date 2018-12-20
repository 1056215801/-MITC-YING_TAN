package com.mit.community.module.system.controller;

import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.SysUser;
import com.mit.community.service.RedisService;
import com.mit.community.service.SysUserService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录
 *
 * @author shuyy
 * @date 2018/12/18
 * @company mitesofor
 */
@RequestMapping(value = "/login")
@RestController
@Slf4j
@Api(tags = "登录")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisService redisService;

    /**
     * 初始化cookie
     *
     * @param session session
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 10:55
     * @company mitesofor
     */
    @PostMapping("/initCookie")
    @ApiOperation(value = "初始化cookie")
    public Result initCookie(HttpSession session) {
        return Result.success("初始化cookie成功");
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/18 19:32
     * @company mitesofor
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "传参：username 用户名，password 密码")
    public Result login(String username, String password, HttpSession session, HttpServletRequest request) {
        SysUser sysUser = sysUserService.getByUsername(username);
        if (sysUser == null) {
            return Result.error("用户不存在");
        }
        if (sysUser.getPassword().equals(password)) {
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("JSESSIONID")) {
                    redisService.set(RedisConstant.SESSION_ID + cookie.getValue(),
                            sysUser, RedisConstant.LOGIN_EXPIRE_TIME);
                    break;
                }
            }
            return Result.success(sysUser, "登录成功");
        }
        return Result.error("用户密码错误");
    }
}
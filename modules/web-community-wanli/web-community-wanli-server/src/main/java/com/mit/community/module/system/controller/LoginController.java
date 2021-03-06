package com.mit.community.module.system.controller;

import com.google.code.kaptcha.Producer;
import com.google.common.collect.Maps;
import com.mit.common.util.HttpClientUtil;
import com.mit.community.common.HttpLogin;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.SysUser;
import com.mit.community.service.RedisService;
import com.mit.community.service.SysUserService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Header;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

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
    @Autowired
    private Producer producer;


    /**
     * 生成验证码
     *
     * @param request  httpRequest
     * @param response httpResponse
     * @author shuyy
     */
    @ApiOperation(value = "生成验证码")
    @GetMapping("code")
    public void code(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        HttpSession session1 = request.getSession();
        Object hello = session.getAttribute("hello");
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = producer.createText();
        // store the text in the session
        String sessionId = CookieUtils.getSessionId(request);
        redisService.set(RedisConstant.KAPTCHA + sessionId,
                capText, RedisConstant.LOGIN_EXPIRE_TIME);
        // create the image with the text
        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ImageIO.write(bi, "jpg", Objects.requireNonNull(out));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                Objects.requireNonNull(out).flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                Objects.requireNonNull(out).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    @PostMapping("/")
    @ApiOperation(value = "登录", notes = "传参：username 用户名，password 密码")
    public Result login(String username, String password, String kaptcha, HttpServletRequest request) {

        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        String sessionId = CookieUtils.getSessionId(request);
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            SysUser sysUser = sysUserService.getByUsernameAndPassWord(username, password);
            if (sysUser != null) {
                String menuUser = "小区管理员";
                String menuAdmin = "集群管理员";
                String adminName = sysUser.getAdminName();
                String communityCode = sysUser.getCommunityCode();
                String role = sysUser.getRole();
                String menu = StringUtils.EMPTY;
                map.put("adminName", StringUtils.isBlank(adminName) ? StringUtils.EMPTY : adminName);
                map.put("role", StringUtils.isBlank(role) ? StringUtils.EMPTY : role);
                map.put("communityCode", StringUtils.isBlank(communityCode) ? StringUtils.EMPTY : communityCode);
                //map.put("session", httpLogin.getCookie());
                if (menuUser.equals(role)) {
                    menu = "0";
                    request.getSession().setAttribute("role", 0);
                }
                if (menuAdmin.equals(role)) {
                    request.getSession().setAttribute("role", 1);
                    menu = "1";
                }
                //request.getSession().setAttribute("session", httpLogin.getCookie());
                map.put("isAdmin", menu);
                if ("湾里区".equals(sysUser.getAreaName())) {
                    map.put("isWanli", 1);
                    request.getSession().setAttribute("isWanli", true);
                } else {
                    map.put("isWanli", 0);
                    request.getSession().setAttribute("isWanli", false);
                }
                // redis中保存session用户
                redisService.set(RedisConstant.SESSION_ID + sessionId,
                        sysUser, RedisConstant.LOGIN_EXPIRE_TIME);
                System.out.println("==============登录成功");
                return Result.success(map, "登录成功");
            } else {
                return Result.error("用户名或密码错误");
            }
        } else {
            return Result.error("登录失败,账号或密码为空");
        }

    }
}
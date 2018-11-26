package com.mit.community.module.system.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.common.collect.Maps;
import com.mit.community.entity.SysUser;
import com.mit.community.service.SysUserService;
import com.mit.community.util.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
 * @author shuyy
 * @date 2018/11/14
 * @company mitesofor
 */
@RestController
@RequestMapping("login")
public class LoginController {

    private final Producer producer;

    private final SysUserService sysUserService;

    @Autowired
    public LoginController(SysUserService sysUserService, Producer producer) {
        this.sysUserService = sysUserService;
        this.producer = producer;
    }

    /**
     * 生成验证码
     * @param request  httpRequest
     * @param response httpResponse
     * @author shuyy
     */
    @ApiOperation(value = "生成验证码")
    @GetMapping("code")
    public void code(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
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
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
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

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "数据展示前台项目登录", notes = "数据展示前台项目登录 返回参数：adminName 管理员姓名、role 账号角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kaptcha", value = "验证码", dataType = "string", paramType = "query")})
    public Result login(String username, String password, String kaptcha, HttpServletRequest request) {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isBlank(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
            return Result.error("验证码错误");
        }
        SysUser sysUser = sysUserService.getSysUser(username);
        if (sysUser.getPassword().equals(password)) {
            map.put("adminName", sysUser.getAdminName());
            map.put("role", sysUser.getRole());
            return Result.success(map, "登录成功");
        }
        return Result.error("登录失败");
    }
}

package com.mit.community.module.system.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.common.collect.Maps;
import com.mit.community.common.HttpLogin;
import com.mit.community.entity.SysUser;
import com.mit.community.service.SysUserService;
import com.mit.community.util.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.httpclient.Header;
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
        } else {
            HttpLogin httpLogin = new HttpLogin(username, password);
            //判断是否是集群管理账户访问不同的登录接口
            //是集群账户
            if ("ytyuehu".equals(username)) {
                httpLogin.loginAdmin();
            } else {
                //是小区管理账户
                httpLogin.loginUser();
            }
            boolean loginWhether = false;
            //判断是否登录成功
            for (Header h : httpLogin.getHeaders()) {
                if ("Location".equals(h.getName())) {
                    loginWhether = true;
                }
            }
            boolean updateOrInsert = false;
            if (loginWhether) {
                //登录成功后,判断本地数据库是否有这个用户名
                SysUser sysUser = sysUserService.getSysUser(username);
                if (sysUser != null) {
                    //如果有该用户名，进行用户名密码验证
                    if (!sysUser.getPassword().equals(password)) {
                        sysUser.setPassword(password);
                        Integer update = sysUserService.update(sysUser);
                        if (update > 0) {
                            updateOrInsert = true;
                        }
                    } else {
                        updateOrInsert = true;
                    }
                } else {
                    //如果该用户名在本地数据库中不存在，则将数据添加到本地数据库
                    SysUser sysUser1 = new SysUser();
                    sysUser1.setPassword(password);
                    sysUser1.setUsername(username);
                    sysUser1.setRole("小区管理员");
                    sysUser1.setCityName("鹰潭市");
                    sysUser1.setProvinceName("江西省");
                    sysUser1.setAreaName("月湖区");
                    Integer save = sysUserService.save(sysUser1);
                    if (save > 0) {
                        updateOrInsert = true;
                    }
                }
                //本地数据更新成功后执行显示数据
                if (updateOrInsert) {
                    String menuUser = "小区管理员";
                    String menuAdmin = "集群管理员";
                    String adminName = sysUser.getAdminName();
                    String communityCode = sysUser.getCommunityCode();
                    String role = sysUser.getRole();
                    String menu = StringUtils.EMPTY;
                    map.put("adminName", StringUtils.isBlank(adminName) ? StringUtils.EMPTY : adminName);
                    map.put("role", StringUtils.isBlank(role) ? StringUtils.EMPTY : role);
                    map.put("communityCode", StringUtils.isBlank(communityCode) ? StringUtils.EMPTY : communityCode);
                    if (menuUser.equals(role)) {
                        menu = "menuUser";
                    }
                    if (menuAdmin.equals(role)) {
                        menu = "menuAdmin";
                    }
                    map.put("menuType", menu);
                    return Result.success(map, "登录成功");
                } else {
                    return Result.error("用户名或密码错误");
                }
            }
        }
        return Result.error("登录失败");
    }
}

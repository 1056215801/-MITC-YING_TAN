package com.mit.community.module.system.controller;

import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author shuyy
 * @date 2019-01-22
 * @company mitesofor
 */
@RequestMapping(value = "/")
@RestController
@Slf4j
@Api(tags = "初始化cookie")
public class InitCookieController {

    /**
     * 初始化cookie
     *
     * @param session session
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 10:55
     * @company mitesofor
     */
    @GetMapping("/initCookie")
    @ApiOperation(value = "初始化cookie")
    public Result initCookie(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String id = session.getId();
        Cookie cookie = new Cookie("SESSION", id);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setMaxAge(1800);
        response.addCookie(cookie);
        session.setAttribute("hello", "world");
        return Result.success("初始化cookie成功");
    }
}

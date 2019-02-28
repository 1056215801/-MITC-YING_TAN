package com.mit.community.config.interceptor;

import com.mit.community.common.constant.LoginConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LW
 * @creatTime 2019-02-28 09:52
 */
@Configuration
public class LoginAccessInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return true;
        }
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (LoginConstant.LOGIN_SESSION.equals(cookie.getName())) {
                cookie.setPath("/");
                cookie.setHttpOnly(false);
                cookie.setMaxAge(1800);
                response.addCookie(cookie);
                return true;
            }
        }
        return true;
    }

}

package com.mit.community.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.mit.community.constants.RedisConstant;
import com.mit.community.service.RedisService;
import com.mit.community.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录拦截器
 *
 * @author shuyy
 * @date 2018/12/12
 * @company mitesofor
 */
@Configuration
public class    LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String servletPath = request.getServletPath();
        if (servletPath.contains("swagger") || servletPath.contains(".html") ||
                servletPath.contains(".js") || servletPath.contains(".css")
                || servletPath.contains(".jpg") || servletPath.contains(".png") ||
                servletPath.contains(".gif") || servletPath.contains("/error")
                || servletPath.contains("/login/") || servletPath.contains("/excel")
                || servletPath.contains("/initCookie") || servletPath.contains("/uploadImg")|| servletPath.contains("/labels") || servletPath.contains("/faceController")|| servletPath.contains("/personBaseInfo")) {
            return true;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            writeMessage(response, "请登录");
            return false;
        }
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookie.getName().equals("SESSION")) {
                Object o = redisService.get(RedisConstant.SESSION_ID + cookie.getValue());
                if (o == null) {
                    writeMessage(response, "请登录");
                    return false;
                }
                break;
            }
        }
        return true;
    }

    private void writeMessage(HttpServletResponse response, String message) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            Result result = Result.error(message);
            String s = JSON.toJSONString(result);
            writer.print(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}

package com.mit.community.config;

import com.alibaba.fastjson.JSON;
import com.mit.community.constants.RedisConstant;
import com.mit.community.service.RedisService;
import com.mit.community.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String mac = request.getParameter("mac");
        String cellphone = request.getParameter("cellphone");
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(mac)) {
            String macRedis = (String) redisService.get(RedisConstant.MAC + cellphone);
            if (!mac.equals(macRedis)) {
                PrintWriter writer = null;
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html; charset=utf-8");
                try {
                    writer = response.getWriter();
                    Result result = Result.error("请重新登录");
                    String s = JSON.toJSONString(result);
                    writer.print(s);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null)
                        writer.close();
                }
                return false;
            }
        }
        return true;
    }
}

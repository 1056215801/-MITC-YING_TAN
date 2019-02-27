package com.mit.community.config;

import com.mit.community.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ip拦截器
 *
 * @author shuyy
 * @date 2018/12/12
 * @company mitesofor
 */
@Configuration
public class Ipterceptor extends HandlerInterceptorAdapter {



    @Autowired
    private RedisService redisService;

    public static final String IP_PREFIX = "ip:";

    public static final String BLACK_PREFIX = "blacklist:";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String servletPath = request.getServletPath();
        if (servletPath.contains("swagger") || servletPath.contains(".html") ||
                servletPath.contains(".js") || servletPath.contains(".css")
                || servletPath.contains(".jpg") || servletPath.contains(".png") ||
                servletPath.contains(".gif") || servletPath.contains("/error")) {
            return true;
        }
        String ip = request.getRemoteHost();
        String key = IP_PREFIX + ip;
        Object value = redisService.get(BLACK_PREFIX + ip);
        if(value != null){
            // 在黑名单则不让访问
            return false;
        }
        Object o = redisService.get(key);
        if(o == null){
            redisService.set(key, 1, 2L);
        } else if(o.equals(50)){
            // 2秒超过50个请求就屏蔽掉,放入黑名单,1小时候之后不能访问
            redisService.set(BLACK_PREFIX + ip, 1, 3600L);
        }else{
            redisService.increment(key, 1);
        }
        return true;
    }
}

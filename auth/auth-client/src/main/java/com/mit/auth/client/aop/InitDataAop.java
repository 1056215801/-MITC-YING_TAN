package com.mit.auth.client.aop;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.mit.auth.client.config.UserAuthConfig;
import com.mit.auth.common.util.jwt.IJWTInfo;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mit.auth.client.jwt.UserAuthUtil;
import com.mit.common.context.BaseContextHandler;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 初始化一些数据、操作
 *
 * @author shuyy
 * @date 2018/11/7 16:33
 * @company mitesofor
 */
@Aspect
@Component
@Slf4j
public class InitDataAop {

    private final UserAuthUtil userAuthUtil;

    private final UserAuthConfig userAuthConfig;

    @Autowired
    public InitDataAop(UserAuthUtil userAuthUtil, UserAuthConfig userAuthConfig) {
        this.userAuthUtil = userAuthUtil;
        this.userAuthConfig = userAuthConfig;
    }

    /***
     * 每次请求requestMapper修试的方法，都分析一次token，放入ThreadLocal中，
     * 供方法内使用
     * @param joinPoint 切点
     * @param requestMapping requestMapping注解
     * @return java.lang.Object
     * @author shuyy
     * @date 2018/11/7 16:37
     * @company mitesofor
     */
    @Around("@annotation(requestMapping)")
    public Object around(ProceedingJoinPoint joinPoint, RequestMapping requestMapping) throws Throwable {
        log.info("分析 token数据， 放入ThreadLocal");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        String token = request.getHeader(userAuthConfig.getTokenHeader());
        token = analyzeCookieToToken(request, token, userAuthConfig);
        //登录的时候，token是空的，但是会请求admin模块校验密码
        if (token != null) {
            IJWTInfo infoFromToken = userAuthUtil.getInfoFromToken(token);
            BaseContextHandler.setUsername(infoFromToken.getUniqueName());
            BaseContextHandler.setName(infoFromToken.getName());
            BaseContextHandler.setUserID(infoFromToken.getId());
        }
        Object result;
        result = joinPoint.proceed();
        return result;
    }

    public static String analyzeCookieToToken(HttpServletRequest request, String token, UserAuthConfig userAuthConfig) {
        if (StringUtils.isEmpty(token)) {
            // request的header中没有token信息，则从cookie中取token信息
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(userAuthConfig.getTokenHeader())) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        return token;
    }
}

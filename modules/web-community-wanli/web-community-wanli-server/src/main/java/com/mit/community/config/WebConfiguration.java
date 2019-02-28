package com.mit.community.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.mit.auth.client.interceptor.ServiceAuthRestInterceptor;
import com.mit.auth.client.interceptor.UserAuthRestInterceptor;
import com.mit.common.handler.GlobalExceptionHandler;
import com.mit.community.config.interceptor.LoginAccessInterceptor;
import com.mit.community.config.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

/**
 * @author ace
 * @date 2017/9/8
 */
@Configuration("admimWebConfig")
@Primary
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private LoginAccessInterceptor loginAccessInterceptor;

    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//    	//是否有访问这个服务的权限
        registry.addInterceptor(getServiceAuthRestInterceptor()).
                addPathPatterns(getIncludePathPatterns());
        //这里的拦截，只是拦截是否有登录过，request头部有没有token,并且把token中分析的信息放入threadLocal
        registry.addInterceptor(getUserAuthRestInterceptor()).
                addPathPatterns(getIncludePathPatterns());
        // 拦截只有一台设备可以登录
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginAccessInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(loginInterceptor).excludePathPatterns("/login/**","**/error**",
//                "/**/swagger**/**", "\\.html", "*\\.js",
//                "*\\.css", "*\\.jpg", "*\\.gif", "/**\\.png").addPathPatterns("/**");


    }

    @Bean
    ServiceAuthRestInterceptor getServiceAuthRestInterceptor() {
        return new ServiceAuthRestInterceptor();
    }

    @Bean
    UserAuthRestInterceptor getUserAuthRestInterceptor() {
        return new UserAuthRestInterceptor();
    }

    /**
     * 需要用户和服务认证判断的路径
     *
     * @return
     */
    private ArrayList<String> getIncludePathPatterns() {
        ArrayList<String> list = new ArrayList<>();
        String[] urls = {
                "/api/**"
//                "/element/**",
//                "/gateLog/**",
//                "/group/**",
//                "/groupType/**",
//                "/menu/**",
//                "/user/**",
//                "/api/permissions",
//                "/api/user/un/**"
        };
        Collections.addAll(list, urls);
        return list;
    }

    /**
     * 验证码生成相关
     */
    @Bean
    public DefaultKaptcha kaptcha() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.border.color", "105,179,90");
        properties.put("kaptcha.textproducer.font.color", "blue");
        properties.put("kaptcha.image.width", "125");
        properties.put("kaptcha.image.height", "45");
        properties.put("kaptcha.textproducer.font.size", "45");
        properties.put("kaptcha.session.key", "code");
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}

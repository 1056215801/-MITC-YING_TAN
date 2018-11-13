package com.mit.admin.config;

import com.mit.auth.client.interceptor.ServiceAuthRestInterceptor;
import com.mit.auth.client.interceptor.UserAuthRestInterceptor;
import com.mit.common.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author shuyy
 * @date 2018/11/8 11:20
 * @company mitesofor
*/
@Configuration("admimWebConfig")
@Primary
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	//是否有访问这个服务的权限
        registry.addInterceptor(getServiceAuthRestInterceptor()).
                addPathPatterns(getIncludePathPatterns()).addPathPatterns("/api/user/validate");
        //这里的拦截，只是拦截是否有登录过，request头部有没有token,并且把token中分析的信息放入threadLocal
        registry.addInterceptor(getUserAuthRestInterceptor()).
                addPathPatterns(getIncludePathPatterns());
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
     * @return
     */
    private ArrayList<String> getIncludePathPatterns() {
        ArrayList<String> list = new ArrayList<>();
        String[] urls = {
                "/element/**",
                "/gateLog/**",
                "/group/**",
                "/groupType/**",
                "/menu/**",
                "/user/**",
                "/api/permissions",
                "/api/user/un/**"
        };
        Collections.addAll(list, urls);
        return list;
    }

}

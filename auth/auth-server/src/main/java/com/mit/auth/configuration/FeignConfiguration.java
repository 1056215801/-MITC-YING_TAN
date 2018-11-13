package com.mit.auth.configuration;

import com.mit.auth.interceptor.ClientTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* feigin配置
* @author: shuyy
* @date: 2018/11/6 17:06
* @company mitesofor
*/
@Configuration
public class FeignConfiguration {
    @Bean
    ClientTokenInterceptor getClientTokenInterceptor(){
        return new ClientTokenInterceptor();
    }
}

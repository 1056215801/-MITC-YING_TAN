package com.mit.auth.configuration;

import com.mit.common.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* 异常处理器
* @author: shuyy
* @date: 2018/11/6 17:02
* @company mitesofor
*/
@Configuration
public class AuthConfiguration {
    @Bean
    public GlobalExceptionHandler getGlobalExceptionHandler(){
        return new GlobalExceptionHandler();
    }
}

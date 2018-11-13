package com.mit.auth.client.configuration;

import com.mit.auth.client.config.ServiceAuthConfig;
import com.mit.auth.client.config.UserAuthConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 * @author shuyy
 * @date 2018/11/7 16:49
 * @company mitesofor
*/
@Configuration
@ComponentScan({"com.mit.auth.client"})
public class AutoConfiguration {
    @Bean
    ServiceAuthConfig getServiceAuthConfig(){
        return new ServiceAuthConfig();
    }

    @Bean
    UserAuthConfig getUserAuthConfig(){
        return new UserAuthConfig();
    }

}

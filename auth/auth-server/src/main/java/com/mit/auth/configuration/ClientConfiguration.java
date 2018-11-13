package com.mit.auth.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
* 客户端配置实体类
* @author: shuyy
* @date: 2018/11/6 17:03
* @company mitesofor
*/
@Configuration
@Data
public class ClientConfiguration {
    @Value("${client.id}")
    private String clientId;
    @Value("${client.secret}")
    private String clientSecret;
    @Value("${client.token-header}")
    private String clientTokenHeader;
}

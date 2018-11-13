package com.mit;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
* 启动类
* @author: shuyy
* @date: 2018/11/6 15:55
* @company mitesofor
*/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.mit.auth.mapper")
public class AuthBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(AuthBootstrap.class, args);
    }
}

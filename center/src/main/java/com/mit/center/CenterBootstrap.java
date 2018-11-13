package com.mit.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
* 启动一个服务注册中心提供给其他应用进行对话
* @author: shuyy
* @date: 2018/11/6 14:36
* @company mitesofor
*/
@EnableEurekaServer
@SpringBootApplication
public class CenterBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(CenterBootstrap.class, args);
    }
}

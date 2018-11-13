package com.mit;

import com.mit.auth.client.EnableAceAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/***
 * 启动类
 * @author shuyy
 * @date 2018/11/7 16:22
 * @company mitesofor
*/
@SpringBootApplication
@EnableDiscoveryClient
@EnableAceAuthClient
@EnableFeignClients({"com.mit.auth.client.feign","com.mit.gate.feign"})
public class GatewayServerBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerBootstrap.class, args);
    }
}

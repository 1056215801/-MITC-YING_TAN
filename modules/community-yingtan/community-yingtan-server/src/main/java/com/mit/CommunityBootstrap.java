package com.mit;

import com.ace.cache.EnableAceCache;
import com.mit.auth.client.EnableAceAuthClient;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 *
 * @author shuyy
 * @date 2018年11月9日
 * @company mitesofor
 */
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
/**auth-client模块，会定时发送查询哪些服务可以访问这个服务，所以需要把client的feign路径包含进来*/
@EnableFeignClients({"com.mit.auth.client.feign"})
@EnableScheduling
@EnableAceAuthClient
@EnableAceCache
@EnableTransactionManagement
@EnableSwagger2Doc
public class CommunityBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CommunityBootstrap.class).web(WebApplicationType.SERVLET).run(args);
    }
}

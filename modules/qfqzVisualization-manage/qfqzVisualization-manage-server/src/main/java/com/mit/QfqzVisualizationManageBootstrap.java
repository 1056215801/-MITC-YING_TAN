package com.mit;

import com.ace.cache.EnableAceCache;
import com.alibaba.fastjson.JSONObject;
import com.mit.community.util.ArtemisConfig;
import com.mit.community.util.ArtemisHttpUtil;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

/**
 * 启动类
 * EnableFeignClients({"com.mit.auth.client.feign"})：uth-client模块，会定时发送查询哪些服务可以访问这个服务，所以需要把client的feign路径包含进来
 * @author shuyy
 * @date 2018年11月9日
 * @company mitesofor
 */
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
//@EnableAceAuthClient
//@EnableAceCache
@EnableTransactionManagement
@EnableSwagger2Doc
@ServletComponentScan
//如果web监听器 ServletContextListener没有和启动类下一个包下@ServletComponentScan(value= "com.smp.listener")这样才能扫到对应包下类，如果是多个包就和@ComponentScan一样处理加{}
public class QfqzVisualizationManageBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(QfqzVisualizationManageBootstrap.class).web(WebApplicationType.SERVLET).run(args);
    }
}

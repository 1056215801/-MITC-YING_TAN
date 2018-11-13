package com.mit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ace.cache.EnableAceCache;
import com.mit.auth.client.EnableAceAuthClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/***
 * 启动类
 * @author shuyy
 * @date 2018/11/8 11:18
 * @company mitesofor
*/
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
@EnableFeignClients({ "com.mit.auth.client.feign" })
@EnableScheduling
@EnableAceAuthClient
@EnableAceCache
@EnableTransactionManagement
@MapperScan("com.mit.admin.mapper")
@EnableSwagger2
public class AdminBootstrap {
	public static void main(String[] args) {
		new SpringApplicationBuilder(AdminBootstrap.class).web(WebApplicationType.SERVLET).run(args);
	}

	/*@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.mit.admin.rest"))
				.paths(PathSelectors.any()).build().apiInfo(new ApiInfoBuilder().version("1.0").title("ace-admin")
						.description("Documentation ace-admin v1.0").build());
	}*/
	
	
	
	
}

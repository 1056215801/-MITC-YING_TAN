package com.mit.auth.client.config;

import com.mit.auth.client.interceptor.OkHttpTokenInterceptor;
import feign.Feign;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
/**
 * okHttp配置
 * @author shuyy
 * @date 2018/11/7 16:45
 * @company mitesofor
*/
@AutoConfigureBefore(FeignAutoConfiguration.class)
@Configuration
@ConditionalOnClass(Feign.class)
public class FeignOkHttpConfig {

	private final OkHttpTokenInterceptor okHttpLoggingInterceptor;

	@Autowired
	public FeignOkHttpConfig(OkHttpTokenInterceptor okHttpLoggingInterceptor) {
		this.okHttpLoggingInterceptor = okHttpLoggingInterceptor;
	}

	@Bean
	public okhttp3.OkHttpClient okHttpClient() {
		int feignOkHttpReadTimeout = 60;
		int feignConnectTimeout = 60;
		int feignWriteTimeout = 120;
		return new okhttp3.OkHttpClient.Builder().readTimeout(feignOkHttpReadTimeout, TimeUnit.SECONDS).connectTimeout(feignConnectTimeout, TimeUnit.SECONDS)
				.writeTimeout(feignWriteTimeout, TimeUnit.SECONDS).connectionPool(new ConnectionPool())
				 .addInterceptor(okHttpLoggingInterceptor)
				.build();
	}
}

package com.mit.community.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @Author HuShanLin
 * @Date Created in 19:22 2019/6/20
 * @Company: mitesofor </p>
 * @Description:~文件上传配置类
 */
@Configuration
//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class MultipartResolver {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(1048576);
        return multipartResolver;
    }
}

package com.mit.community.config;

import com.dnake.constant.DnakeConstants;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * dnake api 配置
 *
 * @author shuyy
 * @date 2018/11/16
 * @company mitesofor
 */
@Configuration
public class DnakeApiConfig implements ApplicationRunner {

    /***
     * 服务启动的时候启动。
     * @param args
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/11/16 16:37
     * @company mitesofor
    */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 选择模式。生产模式
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
    }
}

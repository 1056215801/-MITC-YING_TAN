package com.mit.community.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
@Slf4j
public class InitProjectParameter {
    public static String deviceIp;
    public static String baiduAccessToken;
    public static long getAccessTokenTime;
    public static String ak;
    public static String sk;
    public static String snapPhotoPath;

    static{
        log.info("加载value配置文件");
        System.out.println("加载value配置文件");
        Properties prop = new Properties();
        InputStream in = InitProjectParameter.class.getResourceAsStream("/value.properties");
        try{
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("加载value配置文件出现错误:" + e);
        }
        deviceIp = prop.getProperty("deviceIp");
        baiduAccessToken = prop.getProperty("baiduAccessToken");
        getAccessTokenTime = Long.parseLong(prop.getProperty("getAccessTokenTime"));
        ak = prop.getProperty("ak");
        sk = prop.getProperty("sk");
        snapPhotoPath = prop.getProperty("snapPhotoPath");
    }

}

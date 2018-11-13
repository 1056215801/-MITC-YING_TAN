package com.mit.auth.client.config;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * auth配置
 * @author shuyy
 * @date 2018/11/7 16:47
 * @company mitesofor
*/
public class ServiceAuthConfig {
    private byte[] pubKeyByte;
    @Value("${auth.client.id:null}")
    private String clientId;
    @Value("${auth.client.secret}")
    private String clientSecret;
    @Value("${auth.client.token-header}")
    private String tokenHeader;
    @Value("${spring.application.name}")
    private String applicationName;

    public String getTokenHeader() {
        return tokenHeader;
    }



    public String getClientId() {
        return "null".equals(clientId)?applicationName:clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public byte[] getPubKeyByte() {
        return pubKeyByte;
    }

    public void setPubKeyByte(byte[] pubKeyByte) {
        this.pubKeyByte = pubKeyByte;
    }
}

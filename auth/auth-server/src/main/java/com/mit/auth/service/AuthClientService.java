package com.mit.auth.service;


import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
* 校验服务
* @author: shuyy
* @date: 2018/11/6 17:25
* @company mitesofor
*/
public interface AuthClientService {
    /**
     * apply
     * @param clientId
     * @param secret
     * @return java.lang.String
     * @throws Exception
     * @author shuyy
     * @date 2018/11/7 10:38
     * @company mitesofor
    */
    String apply(String clientId, String secret) throws Exception;

    /**
     * 获取所有允许访问的cient
     * @param serviceId
     * @param secret
     * @return java.util.List<java.lang.String>
     * @exception 
     * @author shuyy
     * @date 2018/11/7 10:30
     * @company mitesofor
    */
    List<String> getAllowedClient(String serviceId, String secret);

    /**
     * 获取服务授权的客户端列表
     * @param serviceId
     * @return java.util.List<java.lang.String>
     * @throws
     * @author shuyy
     * @date 2018/11/7 10:50
     * @company mitesofor
    */
    List<String> getAllowedClient(String serviceId);

    /**
     * 校验
     * @param clientId
     * @param secret
     * @return void
     * @throws Exception
     * @author shuyy
     * @date 2018/11/7 10:51
     * @company mitesofor
    */
    void validate(String clientId, String secret) throws Exception;

    /**
     * 注册client
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/11/7 11:17
     * @company mitesofor
    */
    void registryClient();
}

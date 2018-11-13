package com.mit.auth.service;


import com.mit.auth.util.user.JwtAuthenticationRequest;
/**
 * 登入校验
 * @author shuyy
 * @date 2018/11/7 10:51
 * @company mitesofor
*/
public interface AuthService {
    /**
     * 登录
     * @param authenticationRequest
     * @return java.lang.String
     * @throws Exception
     * @author shuyy
     * @date 2018/11/7 10:52
     * @company mitesofor
    */
    String login(JwtAuthenticationRequest authenticationRequest) throws Exception;
    /**
     * 刷新
     * @param oldToken
     * @return java.lang.String
     * @throws Exception
     * @author shuyy
     * @date 2018/11/7 10:52
     * @company mitesofor
    */
    String refresh(String oldToken) throws Exception;

    /**
     * 校验
     * @param token
     * @return void
     * @throws Exception
     * @author shuyy
     * @date 2018/11/7 10:53
     * @company mitesofor
    */
    void validate(String token) throws Exception;
}

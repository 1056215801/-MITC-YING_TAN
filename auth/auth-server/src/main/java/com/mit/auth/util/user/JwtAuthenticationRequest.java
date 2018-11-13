package com.mit.auth.util.user;

import lombok.Data;

import java.io.Serializable;

/**
 * jwt实体类
 * @author shuyy
 * @date 2018/11/7 11:12
 * @company mitesofor
*/
@Data
public class JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;
}

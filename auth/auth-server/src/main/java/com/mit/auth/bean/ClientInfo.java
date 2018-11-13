package com.mit.auth.bean;


import com.mit.auth.common.util.jwt.IJWTInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户实体类
 * @author: shuyy
 * @date: 2018/11/6 15:59
 * @company mitesofor
 */
@Data
@AllArgsConstructor
public class ClientInfo implements IJWTInfo {
    String clientId;
    String name;
    String id;


    @Override
    public String getUniqueName() {
        return clientId;
    }


}

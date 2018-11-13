package com.mit.common.exception.auth;


import com.mit.common.constant.CommonConstants;
import com.mit.common.exception.BaseException;

/**
 * client token exception
 * @author shuyy
 * @date 2018/11/8 8:39
 * @company mitesofor
*/
public class ClientTokenException extends BaseException {
    public ClientTokenException(String message) {
        super(message, CommonConstants.EX_CLIENT_INVALID_CODE);
    }
}

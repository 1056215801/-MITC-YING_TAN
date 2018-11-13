package com.mit.common.exception.auth;


import com.mit.common.constant.CommonConstants;
import com.mit.common.exception.BaseException;

/**
 * client invalid exception
 * @author shuyy
 * @date 2018/11/8 8:38
 * @company mitesofor
*/
public class ClientInvalidException extends BaseException {
    public ClientInvalidException(String message) {
        super(message, CommonConstants.EX_CLIENT_INVALID_CODE);
    }
}

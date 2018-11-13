package com.mit.common.exception.auth;


import com.mit.common.constant.CommonConstants;
import com.mit.common.exception.BaseException;

/**
 * client forbidden exception
 * @author shuyy
 * @date 2018/11/8 8:38
 * @company mitesofor
*/
public class ClientForbiddenException extends BaseException {
    public ClientForbiddenException(String message) {
        super(message, CommonConstants.EX_CLIENT_FORBIDDEN_CODE);
    }

}

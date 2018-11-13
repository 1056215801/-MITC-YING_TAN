package com.mit.common.exception.auth;


import com.mit.common.constant.CommonConstants;
import com.mit.common.exception.BaseException;

/**
 * user token exception
 * @author shuyy
 * @date 2018/11/8 8:39
 * @company mitesofor
*/
public class UserTokenException extends BaseException {
    public UserTokenException(String message) {
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}

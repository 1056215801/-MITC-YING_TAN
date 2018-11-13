package com.mit.common.exception.auth;


import com.mit.common.constant.CommonConstants;
import com.mit.common.exception.BaseException;

/**
 * user invalid exception
 * @author shuyy
 * @date 2018/11/8 8:39
 * @company mitesofor
*/
public class UserInvalidException extends BaseException {
    public UserInvalidException(String message) {
        super(message, CommonConstants.EX_USER_PASS_INVALID_CODE);
    }
}

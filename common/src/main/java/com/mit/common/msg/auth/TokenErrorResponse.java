package com.mit.common.msg.auth;

import com.mit.common.constant.RestCodeConstants;
import com.mit.common.msg.BaseResponse;

/**
 * token error response
 * @author shuyy
 * @date 2018/11/8 8:42
 * @company mitesofor
*/
public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}

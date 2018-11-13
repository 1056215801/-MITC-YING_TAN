package com.mit.common.msg.auth;

import com.mit.common.constant.RestCodeConstants;
import com.mit.common.msg.BaseResponse;

/**
 * token forbidden response
 * @author shuyy
 * @date 2018/11/8 8:42
 * @company mitesofor
*/
public class TokenForbiddenResponse  extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(RestCodeConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}

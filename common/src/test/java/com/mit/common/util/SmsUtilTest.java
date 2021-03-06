package com.mit.common.util;

import com.aliyuncs.exceptions.ClientException;
import com.mit.common.constant.SmsConstants;
import org.assertj.core.util.Maps;

import java.util.Map;

/**
 * 发送短信测试类
 *
 * @author shuyy
 * @date 2018/12/10
 * @company mitesofor
 */
public class SmsUtilTest {

//    @Test
    public void test() throws ClientException {
        Map<String, String> param = Maps.newHashMap("code", "1234");
        SmsUtil.sendSms("13064102937", SmsConstants.SIGN_MXKJ,
                SmsConstants.MODEL_CODE_LOGIN_CONFIRM, param);
    }
}

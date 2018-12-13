package com.mit.community.util;

import com.aliyuncs.exceptions.ClientException;
import com.google.common.collect.Maps;
import com.mit.common.constant.SmsConstants;
import com.mit.common.util.SmsUtil;
import org.apache.commons.lang3.RandomUtils;

import java.util.Map;

/**
 * 智慧社区发送短信工具类
 * @author shuyy
 * @date 2018/12/10
 * @company mitesofor
 */
public class SmsCommunityAppUtil {

    /**
     * 发送注册验证码
     * @param tellphone 手机号
     * @param code      验证码
     * @author shuyy
     * @date 2018/12/10 10:12
     * @company mitesofor
     */
    public static void sendRegister(String tellphone, String code) {
        Map<String, String> param = Maps.newHashMapWithExpectedSize(1);
        param.put("code", code);
        try {
            SmsUtil.sendSms(tellphone, SmsConstants.SIGN_MXZN, SmsConstants.MODEL_CODE_REGISTER,
                    param);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成随机验证码
     * @return java.lang.String
     * @author shuyy
     * @date 2018/12/10 10:16
     * @company mitesofor
     */
    public String generatorCode() {
        int i = RandomUtils.nextInt(1000, 9999);
        return i + "";
    }
}

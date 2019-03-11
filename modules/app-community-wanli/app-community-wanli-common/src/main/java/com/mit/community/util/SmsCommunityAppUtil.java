package com.mit.community.util;

import com.aliyuncs.exceptions.ClientException;
import com.google.common.collect.Maps;
import com.mit.common.constant.SmsConstants;
import com.mit.common.util.SmsUtil;
import org.apache.commons.lang3.RandomUtils;

import java.util.Map;

/**
 * 智慧社区发送短信工具类
 *
 * @author shuyy
 * @date 2018/12/10
 * @company mitesofor
 */
public class SmsCommunityAppUtil {

    /**
     * 注册类型
     */
    public static final Integer TYPE_REGISTER = 1;
    /**
     * 登陆类型
     */
    public static final Integer TYPE_LOGIN_CONFIRM = 2;

    /**
     * 发送注册验证码
     *
     * @param cellphone 手机号
     * @param code      验证码
     * @param type 类型.SmsCommunityAppUtil.TYPE_REGISTER、SmsCommunityAppUtil、TYPE_REGISTER
     * @author shuyy
     * @date 2018/12/10 10:12
     * @company mitesofor
     */
    public static void send(String cellphone, String code, Integer type) {
        Map<String, String> param = Maps.newHashMapWithExpectedSize(1);
        param.put("code", code);
        try {
            if (type.equals(TYPE_REGISTER)) {
                SmsUtil.sendSms(cellphone, SmsConstants.SIGN_MXKJ, SmsConstants.MODEL_CODE_NORMAL_VERIFICATION,
                        param);
            } else if (type.equals(TYPE_LOGIN_CONFIRM)) {
                SmsUtil.sendSms(cellphone, SmsConstants.SIGN_MXKJ, SmsConstants.MODEL_CODE_NORMAL_VERIFICATION,
                        param);
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成随机验证码
     *
     * @return java.lang.String
     * @author shuyy
     * @date 2018/12/10 10:16
     * @company mitesofor
     */
    public static String generatorCode() {
        int i = RandomUtils.nextInt(1000, 9999);
        return i + "";
    }
}

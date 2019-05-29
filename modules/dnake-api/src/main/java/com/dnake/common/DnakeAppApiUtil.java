package com.dnake.common;

import com.dnake.constant.DnakeAppConstants;
import com.dnake.constant.DnakeConstants;
import com.dnake.entity.DnakeAppUser;
import com.dnake.enums.InterfaceParamEnum;
import com.dnake.util.EncodeUtil;
import com.dnake.util.HttpClientUtil;
import com.dnake.util.JsonMapper;
import com.dnake.util.RC4Util;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Map;

/**
 * <p>Description:APP通用方法 <p>
 *
 * @author Mr.Deng
 * @date 2018/11/8 16:32
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public class DnakeAppApiUtil {

    /***
     * app接口调用
     * @param url url
     * @param param 参数
     * @return java.lang.String
     * @author shuyy
     * @date 2018/11/12 15:12
     */
    public static String invoke(String url, Map<String, Object> param, DnakeAppUser dnakeAppUser) {
        return invoke(url, param, dnakeAppUser, 1);
    }

    /**
     * @param url      url
     * @param param    参数
     * @param retryNum 重试次数。
     * @author Mr.Deng
     * @date 10:31 2018/11/12
     */
    private static String invoke(String url, Map<String, Object> param, DnakeAppUser dnakeAppUser, Integer retryNum) {
        //区分是否为基础接口
        String str = "base";
        String timestamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        //基础接口：Base64编码（key + 冒号 + 时间戳）
        StringBuilder authorizationStr = new StringBuilder(DnakeAppConstants.KEY).append(":").append(timestamp);
        if (!url.contains(str)) {
            //非基础接口
            // Base64编码（key + 冒号 + 时间戳 + 冒号 + 用户Id + 冒号 + uuid)
            authorizationStr.append(":").append(dnakeAppUser.getAppUserId()).append(":").append(dnakeAppUser.getUuid());
        }
        String authorization = EncodeUtil.encodeBase64(authorizationStr.toString().getBytes());
        // sign：MD5（key + secret + 时间戳），共32位
        String sign = DnakeAppConstants.KEY + DnakeAppConstants.SECRET + timestamp;
        String signature = StringUtils.EMPTY;
        try {
            signature = EncodeUtil.md5Digest(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder cipherStringBuilder = new StringBuilder();
        param.forEach((key, value) -> {
            cipherStringBuilder.append(key).append("=").append(value);
            cipherStringBuilder.append("&");
        });
        cipherStringBuilder.deleteCharAt(cipherStringBuilder.length() - 1);
        String cipher = RC4Util.encryptToString(cipherStringBuilder.toString(), DnakeAppConstants.SECRET);
        //封装请求参数
        NameValuePair[] nameValuePair = {new NameValuePair(InterfaceParamEnum.Cipher.getParam(), cipher)};
        String result = HttpClientUtil.apiPostMethodRequestResponse(DnakeConstants.serviceUrl.concat(url),
                nameValuePair, authorization, signature);
        Map<String, Object> map = JsonMapper.processResponse(result);
        Object errorCode = map.get("errorCode");
        //如果存在错误提示，然后进行重试，三次后返回错误信息
        if (errorCode != null) {
            if (retryNum >= DnakeAppConstants.RETRY_NUM) {
                return result;
            }
            retryNum++;
            if (errorCode.equals(DnakeAppConstants.ERROR_NOT_LOGIN)) {
                return "请登录";
            }
            result = invoke(url, param, dnakeAppUser, retryNum);
        }
        return result;
    }

}

package com.dnake.common;

import com.dnake.constant.DnakeAppConstants;
import com.dnake.constant.DnakeConstants;
import com.dnake.constant.DnakeWebConstants;
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
 * c
 * 通用方法
 *
 * @author Mr.Deng
 * @date 2018/11/8 11:58
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public class DnakeWebApiUtil {

    /***
     * web api 调用
     * @param url url
     * @param param 参数
     * @return java.lang.String
     * @author shuyy
     * @date 2018/11/12 16:11
     */
    public static String invoke(String url, Map<String, Object> param) {
        return invoke(url, param, 1);
    }

    /***
     * 调用封装
     * @param url 请求url 例如:/v1/community/queryClusterCommunity
     * @param param 请求参数
     * @return java.lang.String
     * @author shuyy
     * @date 2018/11/10 17:15
     */
    private static String invoke(String url, Map<String, Object> param, Integer retryNum) {
        String timestamp = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        // sig= MD5（账户Id + 账户授权令牌 + 时间戳），共32位(注:转成大写)
        String sig = DnakeWebConstants.ACCOUNT_ID + DnakeWebConstants.TOKEN +
                timestamp;
        // 使用Base64编码（账户Id + 冒号 + 时间戳）
        String authorization = DnakeWebConstants.ACCOUNT_ID +
                ":" + timestamp;
        String signature = StringUtils.EMPTY;
        try {
            signature = EncodeUtil.md5Digest(sig);
            authorization = EncodeUtil.encodeBase64(authorization.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder cipherStringBuilder = new StringBuilder();
        param.forEach((key, value) -> {
            cipherStringBuilder.append(key).append("=").append(value);
            cipherStringBuilder.append("&");
        });
        if (cipherStringBuilder.length() > 0) {
            cipherStringBuilder.deleteCharAt(cipherStringBuilder.length() - 1);
        }
        String cipher = RC4Util.encryptToString(cipherStringBuilder.toString(), DnakeWebConstants.TOKEN);
        NameValuePair[] nameValuePair = {new NameValuePair(InterfaceParamEnum.Cipher.getParam(), cipher),
                new NameValuePair(InterfaceParamEnum.Sig.getParam(), signature)};
        String result = HttpClientUtil.postMethodRequestResponse(DnakeConstants.serviceUrl.concat(url),
                nameValuePair, authorization);
        Map<String, Object> map = JsonMapper.processResponse(result);
        Object errorCode = map.get("errorCode");
        if (errorCode != null) {
            if (retryNum >= DnakeAppConstants.RETRY_NUM) {
                return result;
            }
            retryNum++;
            result = invoke(url, param, retryNum);
        }
        return result;
    }

}

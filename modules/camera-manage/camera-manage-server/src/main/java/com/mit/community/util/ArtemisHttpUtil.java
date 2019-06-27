package com.mit.community.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ArtemisHttpUtil {
    private static final List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList();
    private static final String SUCC_PRE = "2";
    private static final String REDIRECT_PRE = "3";

    public ArtemisHttpUtil() {
    }

    public static String doPostStringArtemis(Map<String, String> path, String body, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema != null && !StringUtils.isEmpty(httpSchema)) {
            String responseStr = null;

            try {
                Map<String, String> headers = new HashMap();
                if (StringUtils.isNotBlank(accept)) {
                    headers.put("Accept", accept);
                } else {
                    headers.put("Accept", "*/*");
                }

                if (StringUtils.isNotBlank(contentType)) {
                    headers.put("Content-Type", contentType);
                } else {
                    headers.put("Content-Type", "application/text;charset=UTF-8");
                }

                if (header != null) {
                    headers.putAll(header);
                }

                CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
                Request request = new Request(Method.POST_STRING, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                request.setStringBody(body);
                Response response = Client.execute(request);
                responseStr = getResponseResult(response);
            } catch (Exception var11) {
                //logger.error("the Artemis PostString Request is failed[doPostStringArtemis]", var11);
                System.out.println("the Artemis PostString Request is failed[doPostStringArtemis]" + var11);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static String doPostStringArtemis(Map<String, String> path, String body, Map<String, String> querys, String accept, String contentType) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema != null && !StringUtils.isEmpty(httpSchema)) {
            String responseStr = null;

            try {
                Map<String, String> headers = new HashMap();
                if (StringUtils.isNotBlank(accept)) {
                    headers.put("Accept", accept);
                } else {
                    headers.put("Accept", "*/*");
                }

                if (StringUtils.isNotBlank(contentType)) {
                    headers.put("Content-Type", contentType);
                } else {
                    headers.put("Content-Type", "application/text;charset=UTF-8");
                }

                CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
                Request request = new Request(Method.POST_STRING, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                request.setStringBody(body);
                Response response = Client.execute(request);
                responseStr = getResponseResult(response);
            } catch (Exception var10) {
                System.out.println("the Artemis PostString Request is failed[doPostStringArtemis]" + var10);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    private static String getResponseResult(Response response) {
        String responseStr = null;
        int statusCode = response.getStatusCode();
        if (!String.valueOf(statusCode).startsWith("2") && !String.valueOf(statusCode).startsWith("3")) {
            String msg = response.getErrorMessage();
            responseStr = response.getBody();
            System.out.println("the Artemis Request is Failed,statusCode:" + statusCode + " errorMsg:" + msg);
        } else {
            responseStr = response.getBody();
            System.out.println("the Artemis Request is Success,statusCode:" + statusCode + " SuccessMsg:" + response.getBody());
        }

        return responseStr;
    }
}

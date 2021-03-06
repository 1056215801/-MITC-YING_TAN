package com.mit.community.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtemisHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(ArtemisHttpUtil.class);
    private static final List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList();
    private static final String SUCC_PRE = "2";
    private static final String REDIRECT_PRE = "3";

    public ArtemisHttpUtil() {
    }

    public static String doGetArtemis(Map<String, String> path, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
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

                logger.info((String)path.get(httpSchema));
                CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
                Request request = new Request(Method.GET, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                Response response = Client.execute(request);
                responseStr = response.getBody();
            } catch (Exception var10) {
                logger.error("the Artemis GET Request is failed[doGetArtemis]", var10);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static String doGetArtemis(Map<String, String> path, Map<String, String> querys, String accept, String contentType) {
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

                logger.info((String)path.get(httpSchema));
                CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
                Request request = new Request(Method.GET, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                Response response = Client.execute(request);
                responseStr = response.getBody();
            } catch (Exception var9) {
                logger.error("the Artemis GET Request is failed[doGetArtemis]", var9);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static HttpResponse doGetResponse(Map<String, String> path, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema != null && !StringUtils.isEmpty(httpSchema)) {
            HttpResponse httpResponse = null;

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
                Request request = new Request(Method.GET_RESPONSE, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                Response response = Client.execute(request);
                httpResponse = response.getResponse();
            } catch (Exception var10) {
                logger.error("the Artemis GET Request is failed[doGetArtemis]", var10);
            }

            return httpResponse;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static String doPostFormArtemis(Map<String, String> path, Map<String, String> paramMap, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
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
                    headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                }

                if (header != null) {
                    headers.putAll(header);
                }

                CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
                Request request = new Request(Method.POST_FORM, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                request.setBodys(paramMap);
                Response response = Client.execute(request);
                responseStr = getResponseResult(response);
            } catch (Exception var11) {
                logger.error("the Artemis PostForm Request is failed[doPostFormArtemis]", var11);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static String doPostFormArtemis(Map<String, String> path, Map<String, String> paramMap, Map<String, String> querys, String accept, String contentType) {
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
                    headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                }

                CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
                Request request = new Request(Method.POST_FORM, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                request.setBodys(paramMap);
                Response response = Client.execute(request);
                responseStr = getResponseResult(response);
            } catch (Exception var10) {
                logger.error("the Artemis PostForm Request is failed[doPostFormArtemis]", var10);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static HttpResponse doPostFormImgArtemis(Map<String, String> path, Map<String, String> paramMap, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema != null && !StringUtils.isEmpty(httpSchema)) {
            HttpResponse response = null;

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
                    headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                }

                if (header != null) {
                    headers.putAll(header);
                }

                CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
                Request request = new Request(Method.POST_FORM_RESPONSE, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                request.setBodys(paramMap);
                Response response1 = Client.execute(request);
                response = response1.getResponse();
            } catch (Exception var11) {
                logger.error("the Artemis PostForm Request is failed[doPostFormImgArtemis]", var11);
            }

            return response;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
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
                logger.error("the Artemis PostString Request is failed[doPostStringArtemis]", var11);
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
                logger.error("the Artemis PostString Request is failed[doPostStringArtemis]", var10);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static HttpResponse doPostStringImgArtemis(Map<String, String> path, String body, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
        String httpSchema = (String)path.keySet().toArray()[0];
        if (httpSchema != null && !StringUtils.isEmpty(httpSchema)) {
            HttpResponse responseStr = null;

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
                Request request = new Request(Method.POST_STRING_RESPONSE, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                request.setStringBody(body);
                Response response = Client.execute(request);
                responseStr = response.getResponse();
            } catch (Exception var11) {
                logger.error("the Artemis PostString Request is failed[doPostStringArtemis]", var11);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static String doPostBytesArtemis(Map<String, String> path, byte[] bytesBody, Map<String, String> querys, String accept, String contentType, Map<String, String> header) {
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

                if (bytesBody != null) {
                    headers.put("Content-MD5", MessageDigestUtil.base64AndMD5(bytesBody));
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
                Request request = new Request(Method.POST_BYTES, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                request.setBytesBody(bytesBody);
                Response response = Client.execute(request);
                responseStr = getResponseResult(response);
            } catch (Exception var11) {
                logger.error("the Artemis PostBytes Request is failed[doPostBytesArtemis]", var11);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static String doPostBytesArtemis(Map<String, String> path, byte[] bytesBody, Map<String, String> querys, String accept, String contentType) {
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

                if (bytesBody != null) {
                    headers.put("Content-MD5", MessageDigestUtil.base64AndMD5(bytesBody));
                }

                if (StringUtils.isNotBlank(contentType)) {
                    headers.put("Content-Type", contentType);
                } else {
                    headers.put("Content-Type", "application/text;charset=UTF-8");
                }

                CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
                Request request = new Request(Method.POST_BYTES, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                request.setBytesBody(bytesBody);
                Response response = Client.execute(request);
                responseStr = getResponseResult(response);
            } catch (Exception var10) {
                logger.error("the Artemis PostBytes Request is failed[doPostBytesArtemis]", var10);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static String doPutStringArtemis(Map<String, String> path, String body, String accept, String contentType) {
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

                if (StringUtils.isNotBlank(body)) {
                    headers.put("Content-MD5", MessageDigestUtil.base64AndMD5(body));
                }

                if (StringUtils.isNotBlank(contentType)) {
                    headers.put("Content-Type", contentType);
                } else {
                    headers.put("Content-Type", "application/text;charset=UTF-8");
                }

                CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
                Request request = new Request(Method.PUT_STRING, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setStringBody(body);
                Response response = Client.execute(request);
                responseStr = getResponseResult(response);
            } catch (Exception var9) {
                logger.error("the Artemis PutString Request is failed[doPutStringArtemis]", var9);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static String doPutBytesArtemis(Map<String, String> path, byte[] bytesBody, String accept, String contentType) {
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

                if (bytesBody != null) {
                    headers.put("Content-MD5", MessageDigestUtil.base64AndMD5(bytesBody));
                }

                if (StringUtils.isNotBlank(contentType)) {
                    headers.put("Content-Type", contentType);
                } else {
                    headers.put("Content-Type", "application/text;charset=UTF-8");
                }

                CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
                Request request = new Request(Method.PUT_BYTES, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setBytesBody(bytesBody);
                Response response = Client.execute(request);
                responseStr = getResponseResult(response);
            } catch (Exception var9) {
                logger.error("the Artemis PutBytes Request is failed[doPutBytesArtemis]", var9);
            }

            return responseStr;
        } else {
            throw new RuntimeException("http鍜宧ttps鍙傛暟閿欒\ue1e4httpSchema: " + httpSchema);
        }
    }

    public static String doDeleteArtemis(Map<String, String> path, Map<String, String> querys, String accept, String contentType) {
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
                }

                Request request = new Request(Method.DELETE, httpSchema + ArtemisConfig.host, (String)path.get(httpSchema), ArtemisConfig.appKey, ArtemisConfig.appSecret, 100);
                request.setHeaders(headers);
                request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
                request.setQuerys(querys);
                Response response = Client.execute(request);
                responseStr = getResponseResult(response);
            } catch (Exception var9) {
                logger.error("the Artemis DELETE Request is failed[doDeleteArtemis]", var9);
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
            logger.error("the Artemis Request is Failed,statusCode:" + statusCode + " errorMsg:" + msg);
        } else {
            responseStr = response.getBody();
            logger.info("the Artemis Request is Success,statusCode:" + statusCode + " SuccessMsg:" + response.getBody());
        }

        return responseStr;
    }
}

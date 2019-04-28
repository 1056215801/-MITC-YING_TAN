package com.dnake.util;

import com.dnake.enums.InterfaceParamEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 网络通信
 *
 * @author Mr.Deng
 * @date 10:18 2018/11/13
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * Company: mitesofor
 * </p>
 */
@Slf4j
public class HttpClientUtil {
    /**
     * 发送get请求,并返回JSON数据 Https
     *
     * @param url 路径
     * @return JSON数据
     */
    public static String getMethodRequestResponse(String url) {
        String result;
        HttpClient httpClient = initHttpClient();
        String charSetName = "utf-8";
        // 创建一个方法实例.
        GetMethod getMethod = new GetMethod(url);
        getMethod.addRequestHeader( "Connection", "close");
        // 提供定制的重试处理程序是必要的
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        try {
            // 执行getMethod.
            int status = httpClient.executeMethod(getMethod);
            if (HttpStatus.SC_OK == status) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(getMethod.getResponseBodyAsStream(), charSetName));
                StringBuilder stringBuffer = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                result = stringBuffer.toString();
            } else {
                result = "error:" + status;
                log.error("HttpClientUtil get to:[{}] error!", url);
            }
        } catch (Exception e) {
            result = "error:" + e;
            log.error("HttpClientUtil get to:[" + url + "] error!", e);
        } finally {
            getMethod.releaseConnection();
        }
        return result;
    }

    /**
     * 初始化通信为Https
     *
     * @return HttpClient
     * @author Mr.Deng
     * @date 11:26 2018/11/8
     */
    private static HttpClient initHttpClient() {
        ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
        Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);

        return httpClient;
    }

    /**
     * 发送post请求,并返回JSON数据
     *
     * @param url           访问地址
     * @param data          数据
     * @param authorization 凭证
     * @return String
     */
    public static String postMethodRequestResponse(String url, NameValuePair[] data, String authorization) {
        String result = StringUtils.EMPTY;
        HttpClient httpClient = initHttpClient();
        httpClient.getParams().setBooleanParameter( "http.protocol.expect-continue" , false );
        httpClient.getParams().setContentCharset("utf-8");

//		PostMethod postMethod = new UTF8PostMethod(url);// 创建PostMethod的子类UTF8PostMethod来设置编码
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader( "Connection", "close");

        System.out.println("发送http请求api调用");
        int status;

        try {
            if (StringUtils.isNotBlank(authorization)) {
                postMethod.setRequestHeader(InterfaceParamEnum.Authorization.getParam(), authorization);
            }
//			postMethod.setRequestHeader("Content-Type", "application/json");//
//			postMethod.setRequestHeader("Connection", "Keep-Alive"); 
            postMethod.setRequestBody(data);
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(3, true));
            status = httpClient.executeMethod(postMethod);
            if (HttpStatus.SC_OK == status) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
                StringBuilder stringBuffer = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                result = stringBuffer.toString();
            } else {
                log.error("HttpClientUtil post to:[{}] error, HttpStatus:{}!", url, status);
            }
        } catch (Exception e) {
            log.error("HttpClientUtil post to:[" + url + "] error!", e);
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }

    /**
     * APP通信Post请求Https
     *
     * @param url           地址
     * @param data          数据
     * @param authorization 授权令牌
     * @param sign          api校验参数
     * @return String
     * @author Mr.Deng
     * @date 11:28 2018/11/8
     */
    public static String apiPostMethodRequestResponse(String url, NameValuePair[] data, String authorization,
                                                      String sign) {
        String result = StringUtils.EMPTY;
        HttpClient httpClient = initHttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader( "Connection", "close");
        int status;

        try {
            if (StringUtils.isNotBlank(authorization)) {
                postMethod.setRequestHeader(InterfaceParamEnum.Authorization.getParam(), authorization);
            }
            if (StringUtils.isNotBlank(sign)) {
                postMethod.setRequestHeader("sign", sign);
            }
            postMethod.setRequestBody(data);
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(3, true));
            status = httpClient.executeMethod(postMethod);
            // 处理返回的Json字符串
            if (HttpStatus.SC_OK == status) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
                StringBuilder stringBuffer = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                result = stringBuffer.toString();
            } else {
                log.error("HttpClientUtil post to:[{}] error, HttpStatus:{}!", url, status);
            }
        } catch (Exception e) {
            log.error("HttpClientUtil post to:[" + url + "] error!", e);
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }
}

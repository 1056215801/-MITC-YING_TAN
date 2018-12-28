package com.mit.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.IOException;
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
        BufferedReader reader = null;
        try {
            // 执行getMethod.
            int status = httpClient.executeMethod(getMethod);
            if (HttpStatus.SC_OK == status) {
                reader = new BufferedReader(
                        new InputStreamReader(getMethod.getResponseBodyAsStream(), charSetName));
                StringBuilder stringBuffer = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                result = stringBuffer.toString();
                reader.close();
            } else {
                result = "error:" + status;
                log.error("HttpClientUtil get to:[{}] error!", url);
            }
        } catch (Exception e) {
            result = "error:" + e;
            log.error("HttpClientUtil get to:[" + url + "] error!", e);
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            getMethod.releaseConnection();
        }
        return result;
    }

    /**
     * 发送get请求,并返回JSON数据 Https
     *
     * @param url 路径
     * @return JSON数据
     */
    public static String getMethodRequestResponse(String url, String cookie) {
        String result;
        HttpClient httpClient = initHttpClient();
        String charSetName = "utf-8";
        // 创建一个方法实例.
        GetMethod getMethod = new GetMethod(url);
        getMethod.addRequestHeader( "Connection", "close");
        getMethod.setRequestHeader("cookie", cookie);
        // 提供定制的重试处理程序是必要的
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
        BufferedReader reader = null;
        try {
            // 执行getMethod.
            int status = httpClient.executeMethod(getMethod);
            if (HttpStatus.SC_OK == status) {
                reader = new BufferedReader(
                        new InputStreamReader(getMethod.getResponseBodyAsStream(), charSetName));
                StringBuilder stringBuffer = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                result = stringBuffer.toString();
                reader.close();
            } else {
                result = "error:" + status;
                log.error("HttpClientUtil get to:[{}] error!", url);
            }
        } catch (Exception e) {
            result = "error:" + e;
            log.error("HttpClientUtil get to:[" + url + "] error!", e);
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
        return httpClient;
    }
}

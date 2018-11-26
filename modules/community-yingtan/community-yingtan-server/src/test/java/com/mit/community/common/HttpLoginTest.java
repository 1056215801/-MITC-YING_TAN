package com.mit.community.common;

import org.apache.commons.httpclient.NameValuePair;
import org.junit.Test;

/**
 * http获取平台数据测试
 *
 * @author shuyy
 * @date 2018/11/26
 * @company mitesofor
 */
public class HttpLoginTest {

    @Test
    public void test(){
        HttpLogin httpLogin = new HttpLogin();
        httpLogin.login();
        String url = "http://cmp.ishanghome.com/cmp/deviceAlarm/load";
        NameValuePair[] data = {new NameValuePair("page",  "1"),
                new NameValuePair("list",  "10")};

        String post = httpLogin.post(url, data, httpLogin.getCookie());
        System.out.println(post);
    }

}

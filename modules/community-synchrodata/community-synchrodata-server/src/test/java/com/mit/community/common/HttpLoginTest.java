package com.mit.community.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

/**
 * http获取平台数据测试
 * @author shuyy
 * @date 2018/11/26
 * @company mitesofor
 */
public class HttpLoginTest {

    @Test
    public void test() {
        HttpLogin httpLogin = new HttpLogin("ytyuehu", "654321");
        httpLogin.loginUser();
        int s = 2;
        for (int i = 1; i <= s; i++) {
            String url = "http://cmp.ishanghome.com/cmp/deviceAlarm/load";
            NameValuePair[] data = {new NameValuePair("page", i + ""), new NameValuePair("list", "10")};
            String post = httpLogin.post(url, data, httpLogin.getCookie());
            JSONObject jsonObject = JSONObject.parseObject(post);
            JSONArray list = jsonObject.getJSONArray("list");
            Integer total = jsonObject.getInteger("total");
            s = jsonObject.getInteger("lastPage");
            System.out.println(list);
        }
    }

    @Test
    public void ss() {
        HttpLogin httpLogin = new HttpLogin("ytkxwtxq", "654321");
        httpLogin.loginUser();
        for (Header h : httpLogin.getHeaders()) {
            if ("Location".equals(h.getName())) {
                System.out.println(h.toString());
            }
        }
    }

    @Test
    public void t() {
        // 登陆 Url
        String loginUrl = "http://cmp.ishanghome.com/mp/login";
        // 需登陆后访问的 Url
        HttpClient httpClient = new HttpClient();
        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);
        // 设置登陆时要求的信息，用户名和密码
        NameValuePair[] data = {new NameValuePair("username", "ytyuehu"),
                new NameValuePair("password", "654321")};
        postMethod.setRequestBody(data);
        try {
            // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            httpClient.executeMethod(postMethod);
            // 获得登陆后的 Cookie
            Cookie[] cookies = httpClient.getState().getCookies();
            StringBuilder tmpCookies = new StringBuilder();
            for (Cookie c : cookies) {
                tmpCookies.append(c.toString())
                        .append(";");
                System.out.println("cookies = " + c.toString() + "");
            }
            String cookie = tmpCookies.toString();
            Header[] headers = postMethod.getResponseHeaders();
            for (Header h : headers) {
                if (h.getName().equals("Location")) {
                    System.out.println(h.toString());
                }
            }
            System.out.println(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

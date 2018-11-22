package com.mit.community.util;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * java通过httpclient获取cookie模拟登录
 *
 * @author shuyy
 * @date 2018/11/14 15:17
 * @company mitesofor
 */

@Component
public class HttpLogin implements CommandLineRunner {

    private String cookie;

    private void login() {
        // 登陆 Url
        String loginUrl = "http://cmp.ishanghome.com/cmp/login";
        // 需登陆后访问的 Url
//        String dataUrl = "http://cmp.ishanghome.com/cmp/household/getStepThreeInfo";
        HttpClient httpClient = new HttpClient();
        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);
        // 设置登陆时要求的信息，用户名和密码
        NameValuePair[] data = {new NameValuePair("username", "mitadmin"),
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
                tmpCookies.append(c.toString()).append(";");
                System.out.println("cookies = " + c.toString());
            }
            this.cookie = tmpCookies.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run(String... args) {
        this.login();
    }
}
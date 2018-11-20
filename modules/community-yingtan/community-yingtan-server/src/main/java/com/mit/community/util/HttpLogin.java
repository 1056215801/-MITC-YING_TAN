package com.mit.community.util;

import net.sf.json.JSONArray;
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

    public void login() {
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

    /**
     * 获取身份认证信息credentialNum
     *
     * @param householdId 房主id
     * @param retryNum    重试次数
     * @return String
     * @author Mr.Deng
     * @date 14:03 2018/11/20
     */
    public String postOne(String householdId, int retryNum) throws IOException {
        String url = "http://cmp.ishanghome.com/cmp/household/getStepOneInfo";
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        NameValuePair[] data = {new NameValuePair("householdId", householdId)};
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("cookie", cookie);
        //
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        int status = httpClient.executeMethod(postMethod);
        if (status != 200) {
            if (retryNum > 10) {
                return null;
            }
            this.login();
            retryNum++;
            return this.postOne(householdId, retryNum);
        }
        System.out.println(postMethod.getResponseBodyAsString());
        return postMethod.getResponseBodyAsString();
    }

    /**
     * 获取图片
     *
     * @param householdId 房主id
     * @return String
     * @throws IOException
     */
    public String post(String householdId, int retryNum) throws IOException {
        String url = "http://cmp.ishanghome.com/cmp/household/getStepThreeInfo";
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        NameValuePair[] data = {new NameValuePair("householdId", householdId)};
        postMethod.setRequestBody(data);
        postMethod.setRequestHeader("cookie", cookie);
        // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        int status = httpClient.executeMethod(postMethod);
        if (status != 200) {
            if (retryNum > 10) {
                return null;
            }
            this.login();
            retryNum++;
            return this.post(householdId, retryNum);
        }
        return postMethod.getResponseBodyAsString();
    }

    /**
     * 获取图片
     *
     * @param url 图片地址
     * @return String
     */
    public int get(String url) {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
        try {
            return httpClient.executeMethod(getMethod);
        } catch (IOException e) {
            e.printStackTrace();
            return 404;
        }
    }

    @Override
    public void run(String... args) throws Exception {
        this.login();
    }

    public static void main(String[] args) {
        HttpLogin httpLogin = new HttpLogin();
        try {
            String post = httpLogin.postOne("43707", 1);
            if (post != null) {
                JSONObject parse = JSONObject.fromObject(post);
                JSONArray houseList = parse.getJSONArray("houseList");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
package com.mit.community.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.NameValuePair;
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
        HttpLogin httpLogin = new HttpLogin("ytyuehu","654321");
        httpLogin.login();
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

}

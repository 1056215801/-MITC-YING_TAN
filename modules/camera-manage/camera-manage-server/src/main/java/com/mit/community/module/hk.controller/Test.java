package com.mit.community.module.hk.controller;

import com.alibaba.fastjson.JSONObject;
import com.mit.community.util.ArtemisConfig;
import com.mit.community.util.ArtemisHttpUtil;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args){
        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "192.168.1.230:443";
        // 秘钥Appkey
        ArtemisConfig.appKey = "20534145";
        // 秘钥AppSecret
        ArtemisConfig.appSecret = "seK6hzFoKlRA7Xqsm6wQ";
        String ARTEMIS_PATH = "/artemis";
        String getRootApi = ARTEMIS_PATH + "/api/resource/v1/regions";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        /**
         * STEP4：设置参数提交方式
         */
        String contentType = "application/json";
        /**
         * STEP5：组装请求参数
         */
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 20);
        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        System.out.println("请求路径："+ getRootApi + ",请求参数："+ body + ",返回结果：" + result);
    }
}

package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dnake.common.DnakeAppApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.entity.DnakeAppUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * dnake接口调用
 *
 * @author shuyy
 * @date 2018/12/7
 * @company mitesofor
 */
@Slf4j
@Service
public class DnakeAppApiService {

    /**
     * 发送手机验证码
     * @author shuyy
     * @date 2018/12/7 10:05
     * @company mitesofor
     */
    public String getRegisterSmsCode(String telNum) {
        String url = "/auth/base/getRegisterSmsCode";
        Map<String, Object> map = new HashMap<>();
        map.put("telNum", telNum);
        map.put("clusterAccountId", "pMXYTG6tXMzPHpErs0VYBjmiHBatkWEs");
        String invoke = DnakeAppApiUtil.invoke(url, map);
        log.info(invoke);
        JSONObject jsonObject = JSON.parseObject(invoke);
        String smsVerifyCode = jsonObject.getString("smsVerifyCode");
        return smsVerifyCode;
    }

    /**
     * 验证手机验证码
     * @author shuyy
     * @date 2018/12/7 10:05
     * @company mitesofor
     */
    public boolean checkSmsCode(String telNum, String smsCode) {
        String url = "/auth/base/checkSmsCode";
        Map<String, Object> map = new HashMap<>();
        map.put("telNum", telNum);
        map.put("smsCode", smsCode);
        String invoke = DnakeAppApiUtil.invoke(url, map);
        log.info(invoke);
        JSONObject jsonObject = JSON.parseObject(invoke);
        if(jsonObject.get("errorCode") == null){
            return true;
        }
        return false;
    }

    public void login(String cellphone, String password) {
        String url = "/auth/base/login";
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", cellphone);
        map.put("password", password);
        map.put("registrationId", DnakeAppUser.registrationId);
        map.put("platform", DnakeAppUser.platform);
        map.put("clusterAccountId", DnakeAppUser.clusterAccountid);
        String invoke = DnakeAppApiUtil.invoke(url, map);
        System.out.println(invoke);
    }

}

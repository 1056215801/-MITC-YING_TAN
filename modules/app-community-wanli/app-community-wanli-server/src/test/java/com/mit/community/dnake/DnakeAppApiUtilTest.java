package com.mit.community.dnake;

import com.dnake.common.DnakeAppApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.entity.DnakeAppUser;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * dnake api调用测试
 * @author shuyy
 * @date 2018/12/7 13:50
 * @company mitesofor
*/
public class DnakeAppApiUtilTest {
    /**
     * 注册
     * @author shuyy
     * @date 2018/12/7 10:05
     * @company mitesofor
    */
    @Test
    public void register() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/base/register";
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", "13064102937");
        map.put("password", "123456");
        String invoke = DnakeAppApiUtil.invoke(url, map);
        System.out.println(invoke);
    }

    /**
     * 发送手机验证码
     * @author shuyy
     * @date 2018/12/7 10:05
     * @company mitesofor
     */
    @Test
    public void getRegisterSmsCode() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/base/getRegisterSmsCode";
        Map<String, Object> map = new HashMap<>();
        map.put("telNum", "13064102937");
        map.put("clusterAccountId", "pMXYTG6tXMzPHpErs0VYBjmiHBatkWEs");
        String invoke = DnakeAppApiUtil.invoke(url, map);
        System.out.println(invoke);
    }

    /**
     * 验证手机验证码
     * @author shuyy
     * @date 2018/12/7 10:05
     * @company mitesofor
     */
    @Test
    public void checkSmsCode() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/base/checkSmsCode";
        Map<String, Object> map = new HashMap<>();
        map.put("telNum", "13064102937");
        map.put("smsCode", "103601");
        String invoke = DnakeAppApiUtil.invoke(url, map);
        System.out.println(invoke);
    }

    @Test
    public void login() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/base/login";
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", "13064102937");
        map.put("password", "123456");
        map.put("registrationId", DnakeAppUser.registrationId);
        map.put("platform", DnakeAppUser.platform);
        map.put("clusterAccountId", DnakeAppUser.clusterAccountid);
        String invoke = DnakeAppApiUtil.invoke(url, map);
        System.out.println(invoke);
    }
}

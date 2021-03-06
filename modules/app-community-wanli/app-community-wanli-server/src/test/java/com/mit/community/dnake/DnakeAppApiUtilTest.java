package com.mit.community.dnake;

import com.dnake.common.DnakeAppApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.entity.DnakeAppUser;
import com.google.common.collect.Maps;
import com.mit.common.util.UUIDUtils;
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
    // @Test
    @Test
    public void register() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/base/register";
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", "13064102937");
        String s = UUIDUtils.generateShortUuid();
        map.put("password", s);
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        System.out.println(invoke);
    }

    /**
     * 发送手机验证码
     * @author shuyy
     * @date 2018/12/7 10:05
     * @company mitesofor
     */
    // @Test
    public void getRegisterSmsCode() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/base/getRegisterSmsCode";
        Map<String, Object> map = new HashMap<>();
        map.put("telNum", "13064102937");
        map.put("clusterAccountId", "pMXYTG6tXMzPHpErs0VYBjmiHBatkWEs");
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        System.out.println(invoke);
    }

    /**
     * 验证手机验证码
     * @author shuyy
     * @date 2018/12/7 10:05
     * @company mitesofor
     */
    // @Test
    public void checkSmsCode() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/base/checkSmsCode";
        Map<String, Object> map = new HashMap<>();
        map.put("telNum", "13064102937");
        map.put("smsCode", "103601");
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        System.out.println(invoke);
    }

    @Test
    public void login() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/base/login";
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", "18779158391");
        map.put("password", "123456");
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        map.put("registrationId", dnakeAppUser.getRegistrationId());
        map.put("platform", dnakeAppUser.getPlatform());
        map.put("clusterAccountId", DnakeConstants.CLUSTER_ACCOUNT_ID);
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        System.out.println(invoke);
    }

    @Test
    public void highGrade() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/api/common/inviteCode/highGrade";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(4);
        map.put("time", "[{\"start_time\":\"1517212800\",\"end_time\":\"1517220000\",\"once\":0,\"room\":0101}]");
        map.put("appUserId", "156781");
        map.put("deviceGroupId", "735");
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        System.out.println(invoke);
    }

    @Test
    public void httpOpenDoor(String cellphone, String communityCode, String deviceNum) {
        String url = "/auth/api/device/unlock";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("appUserId", 156781);
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("deviceNum", "AB900DX87689ef7f9270");
        DnakeAppUser dnakeAppUser = new DnakeAppUser();
        String invoke = DnakeAppApiUtil.invoke(url, map, dnakeAppUser);
        System.out.println(invoke);
    }

}

package com.mit.community.dnake;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.constant.DnakeWebConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dnake 调用测试
 *
 * @author shuyy
 * @date 2018/11/13
 * @company mitesofor
 */
public class DnakeInvokeTest {

    /***
     *  门禁记录
     * @author shuyy
     * @date 2018/11/16 15:26
     * @company mitesofor
     */
    // @Test
    public void getAccessControlList() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/device/getAccessControlList";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
//        map.put("deviceName", "凯翔演示-进门");
//        map.put("deviceNum", "AB900DX8880285879170");
        map.put("pageSize", "10");
        map.put("pageNum", "1");
        map.put("accountId", DnakeWebConstants.ACCOUNT_ID);
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

    // @Test
    public void queryVisitorInfo() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/visitor/queryVisitorInfo";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("visitorId", "37137");
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

    // @Test
    public void getHouseholdList() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/household/getHouseholdList";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "2c58fbed7bce49778da3b1717241df25");
        map.put("pageNum", "1");
        map.put("pageSize", "10");
        String result = DnakeWebApiUtil.invoke(url, map);
        JSONArray jsonArray = JSON.parseObject(result).getJSONArray("householdList");
        System.out.println(jsonArray.size());
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

    // @Test
    public void queryHouseholdInfoByName() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/household/queryHouseholdInfoByName";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("householdName", "舒园园");
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

    // @Test
    public void getVisitorList() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/visitor/list";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("pageSize", "100");
        map.put("pageNum", "1");

//        map.put("householdName", "舒园园");
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

    // @Test
    public void queryHouseholdInfo() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/household/queryHouseholdInfo";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("id", "37137");
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

    // @Test
    public void register() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/base/register";
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginName", "1307212680");
        map.put("password", "123456");
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

    // @Test
    public void getRegisterSmsCode() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/base/getRegisterSmsCode";
        HashMap<String, Object> map = new HashMap<>();
        map.put("telNum", "1307212680");
        map.put("clusterAccountId", "pMXYTG6tXMzPHpErs0VYBjmiHBatkWEs");
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

    /**
     * 保存住户
     * @author shuyy
     * @date 2018/12/19 16:13
     * @company mitesofor
     */
    @Test
    public void saveOrUpdateHousehold() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/household/saveOrUpdateHouseholdMore";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("id", "52833");
        List<Map<String, Object>> houseList = Lists.newArrayListWithCapacity(2);
        Map<String, Object> h = Maps.newHashMapWithExpectedSize(4);
        h.put("zoneId", "363");
        h.put("buildingId", "423");
        h.put("unitId", "565");
        h.put("roomId", "15448");
        houseList.add(h);
        String s = JSON.toJSONString(houseList);
        map.put("houseList", s);
        map.put("mobile", "17679015638");

        map.put("householdName", "舒园园");
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

    /**
     * 住户授权
     *
     * @author shuyy
     * @date 2018/12/19 16:13
     * @company mitesofor
     */
    @Test
    public void authorizeHousehold() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/household/authorizeHousehold";
        HashMap<String, Object> map = new HashMap<>();
        map.put("householdId", "56303");
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("expiryDate", "2020-10-01");
        map.put("deviceGroups", "735");
        map.put("appOperateType", "1");
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

    /**
     * 注销用户
     *
     * @author shuyy
     * @date 2018/12/19 16:13
     * @company mitesofor
     */
    @Test
    public void operateHousehold() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1//household/operateHousehold";
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "56323");
        map.put("operateType", "0");
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }
}

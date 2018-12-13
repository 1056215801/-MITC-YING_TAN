package com.mit.community.dnake;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.constant.DnakeWebConstants;
import org.junit.Test;

import java.util.HashMap;

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
    public void getAccessControlList(){
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
    public void queryVisitorInfo(){
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
    public void getHouseholdList(){
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
    public void queryHouseholdInfoByName(){
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1//household/queryHouseholdInfoByName";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("householdName", "舒园园");
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

    // @Test
    public void getVisitorList(){
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
    public void queryHouseholdInfo(){
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
    public void register(){
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
    public void getRegisterSmsCode(){
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

     @Test
    public void saveOrUpdateHousehold(){
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/household/saveOrUpdateHousehold";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("householdName", "邓诚");
        map.put("buildingId", "423");
        map.put("unitId", "565");
        map.put("roomId", "24058");
        map.put("mobile", "13064102937");

//        map.put("householdName", "舒园园");
        String result = DnakeWebApiUtil.invoke(url, map);
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }

}

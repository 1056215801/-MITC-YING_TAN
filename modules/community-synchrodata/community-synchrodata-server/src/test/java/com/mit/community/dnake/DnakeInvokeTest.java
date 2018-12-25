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
    @Test
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
//    @Test
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
    @Test
    public void getHouseholdList(){
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/household/getHouseholdList";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("householdName", "舒园园");
//        map.put("mobile", "15083669779");
        map.put("pageNum", "1");
        map.put("pageSize", "10");
        String result = DnakeWebApiUtil.invoke(url, map);
        JSONArray jsonArray = JSON.parseObject(result).getJSONArray("householdList");
        System.out.println(jsonArray.size());
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }
//    @Test
    public void getHouseholdListMore(){
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/household/getHouseholdListMore";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
//        map.put("householdName", "舒园园");
//        map.put("mobile", "15083669779");
        map.put("pageNum", "1");
        map.put("pageSize", "100");
        String result = DnakeWebApiUtil.invoke(url, map);
        JSONArray jsonArray = JSON.parseObject(result).getJSONArray("householdList");
        System.out.println(jsonArray.size());
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println(end - startTime);
    }
//    @Test
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

//    @Test
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

//    @Test
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

//    @Test
    public void queryClusterCommunity(){
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/community/queryClusterCommunity";
        HashMap<String, Object> map = new HashMap<>();
        map.put("clusterAccountId", DnakeConstants.CLUSTER_ACCOUNT_ID);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        System.out.println(invoke);
    }
    /**
     * 查询设备组
     * @author shuyy
     * @date 2018/12/10 8:46
     * @company mitesofor
    */
//    @Test
    public void getDeviceGroupList(){
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/deviceGroup/getDeviceGroupList";
        HashMap<String, Object> map = new HashMap<>();
        map.put("communityCode", "b41bf2c76a3a4d5aaece65c15cfc350b");
        map.put("pageSize", "100");
        map.put("pageNum", "1");
        String invoke = DnakeWebApiUtil.invoke(url, map);
        System.out.println(invoke);
    }
   

}

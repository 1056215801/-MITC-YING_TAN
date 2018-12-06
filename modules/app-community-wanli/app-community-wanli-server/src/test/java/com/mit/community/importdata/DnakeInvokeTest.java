package com.mit.community.importdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dnake.common.DnakeAppApiUtil;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.constant.DnakeWebConstants;
import com.dnake.entity.DnakeAppUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.MyKey;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dnake 调用测试
 * @author shuyy
 * @date 2018/11/13
 * @company mitesofor
 */
public class DnakeInvokeTest {

    @Test
    public void getConfig() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        DnakeAppUser.loginName = "15770732701";
        DnakeAppUser.password = "654321";
        String url = "/auth/api/device/getConfig";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("deviceNum", "AB900DX87689ef7f9270");
        String invoke = DnakeAppApiUtil.invoke(url, map);
        System.out.println(invoke);
    }

    @Test
    public void callForwarding() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        DnakeAppUser.loginName = "18779158391";
        DnakeAppUser.password = "123456";
        DnakeAppApiUtil.apiLogin();
        String url = "/auth/api/common/callForwarding";
        Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(2);
        map1.put("sipMobile", "18179131120");
//        map1.put("sipMobile", "18779158391");
        map1.put("appUserId", DnakeAppUser.appUserId);
        String invoke = DnakeAppApiUtil.invoke(url, map1);
        System.out.println(invoke);
    }

    @Test
    public void highGrade() {
        List<Object> list = Lists.newArrayListWithCapacity(3);
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/api/common/inviteCode/highGrade";
        String[] times = {"2018/12/7 8:15:33,2018/12/7 19:15:33"};

        DnakeAppUser.loginName = "15770732701";
        DnakeAppUser.password = "654321";
        DnakeAppApiUtil.apiLogin();

        for (int i = 0; i < times.length; i++) {
            String[] unixTimes = times[i].split(",");
            Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
            map.put("startTime", DateToTimeStamp(unixTimes[0]));
            map.put("endTime", DateToTimeStamp(unixTimes[1]));
            map.put("once", 0);
            list.add(map);
        }
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
        System.out.println(jsonArray);

        Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(7);
        map1.put("time", jsonArray);
        map1.put("appUserId", DnakeAppUser.appUserId);
//        map1.put("room", "0101");
        map1.put("deviceGroupId", "735");
        map1.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
//        map1.put("householdId", "52763");
        System.out.println(map1);

        String invoke = DnakeAppApiUtil.invoke(url, map1);
        System.out.println(invoke);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param dateStr 字符串日期 如：yyyy-MM-dd HH:mm:ss
     * @return unix时间戳字符串
     */
    public static String DateToTimeStamp(String dateStr) {
        String result = StringUtils.EMPTY;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            result = String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void getDeviceGroup() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/api/device/getDeviceGroup";
        DnakeAppUser.loginName = "15770732701";
        DnakeAppUser.password = "654321";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
//        map.put("communityCode", "0125caffaae1472b996390e869129cc7");
        String invoke = DnakeAppApiUtil.invoke(url, map);
        System.out.println(invoke);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    //注册app账号
    @Test
    public void getAppRegister() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/base/register";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("loginName", "15770732701");
        map.put("password", "654321");
        String invoke = DnakeAppApiUtil.invoke(url, map);
        System.out.println(invoke);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    @Test
    public void loginAPP() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        DnakeAppUser.loginName = "15770732701";
        DnakeAppUser.password = "654321";
        DnakeAppApiUtil.apiLogin();
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    @Test
    public void myKey() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/api/user/myKey";
        DnakeAppUser.loginName = "15770732701";
        DnakeAppUser.password = "654321";
        DnakeAppApiUtil.apiLogin();
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("appUserId", DnakeAppUser.appUserId);
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
//        map.put("communityCode", "0125caffaae1472b996390e869129cc7");
        String invoke = DnakeAppApiUtil.invoke(url, map);
        JSONObject jsonObject = JSON.parseObject(invoke);
        JSONArray devices = jsonObject.getJSONArray("devices");
        System.out.println(invoke);
        List<MyKey> myKeyList = Lists.newArrayListWithExpectedSize(10);
        List<MyKey> myKeyList1 = Lists.newArrayListWithExpectedSize(10);
        Map<String, Object> maps = Maps.newHashMapWithExpectedSize(2);
        for (Object json : devices) {
            MyKey myKey = JSONObject.parseObject(json.toString(), MyKey.class);
            String buildingCode = myKey.getBuildingCode();
            if (StringUtils.isNotBlank(buildingCode)) {
                myKeyList.add(myKey);
            } else {
                myKeyList1.add(myKey);
            }
        }
        maps.put("单元机", myKeyList);
        maps.put("小区门口机", myKeyList1);
        System.out.println(maps);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    @Test
    public void open() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/auth/api/device/unlock";
        DnakeAppUser.loginName = "15770732701";
        DnakeAppUser.password = "654321";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("appUserId", "147061");
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("deviceNum", "AB900DX87689ef7f9270");
        String invoke = DnakeAppApiUtil.invoke(url, map);
        System.out.println(invoke);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    @Test
    public void visitCode() {
        long startTime = System.currentTimeMillis();
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        DnakeAppUser.loginName = "15770732701";
        DnakeAppUser.password = "654321";
        DnakeAppApiUtil.apiLogin();
        String url = "/auth/api/common/inviteCode";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(6);
        map.put("dateTag", "0");
        map.put("appUserId", DnakeAppUser.appUserId);
        map.put("times", "1");
        map.put("deviceGroupId", "0");
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        String invoke = DnakeAppApiUtil.invoke(url, map);
        System.out.println(invoke);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    /***
     *  门禁记录
     * @author shuyy
     * @date 2018/11/16 15:26
     * @company mitesofor
     */
    @Test
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

    @Test
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

    @Test
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

    @Test
    public void queryHouseholdInfoByName() {
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

    @Test
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

    @Test
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

}

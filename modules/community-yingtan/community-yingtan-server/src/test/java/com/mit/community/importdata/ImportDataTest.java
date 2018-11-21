package com.mit.community.importdata;

import com.alibaba.fastjson.JSON;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.entity.DnakeAppUser;
import com.mit.community.entity.modelTest.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导入数据测试
 *
 * @author shuyy
 * @date 2018/11/13
 * @company mitesofor
 */
public class ImportDataTest {

    /**
     * 获取集群所有小区
     *
     * @author Mr.Deng
     * @date 16:37 2018/11/13
     */
    @Test
    public void queryClusterCommunity() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/community/queryClusterCommunity";
        HashMap<String, Object> map = new HashMap<>();
        map.put("clusterAccountId", DnakeAppUser.clusterAccountid);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        //处理返回json数据
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("communityList");
        List<ClusterCommunityTest> clusterCommunities = JSON.parseArray(jsonArray.toString(), ClusterCommunityTest.class);
        for (ClusterCommunityTest c : clusterCommunities) {
        }
    }

    /**
     * 获取分区列表
     *
     * @author Mr.Deng
     * @date 17:36 2018/11/13
     */
    @Test
    public void getZoneList() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/zone/getZoneList";
        Map<String, Object> map = new HashMap<>();
//        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("communityCode", "0125caffaae1472b996390e869129cc7");
        map.put("zoneStatus", 1);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        System.out.println(invoke);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("zoneList");
        List<ZoneList> zoneLists = JSON.parseArray(jsonArray.toString(), ZoneList.class);
        for (ZoneList s : zoneLists) {
            System.out.println(s.getZoneId());
        }
    }

    /**
     * 获取楼栋列表
     *
     * @author Mr.Deng
     * @date 17:44 2018/11/13
     */
    @Test
    public void getBuildingList() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/building/getBuildingList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("zoneId", 363);
//        map.put("buildingStatus", "1");
        String invoke = DnakeWebApiUtil.invoke(url, map);
        System.out.println(invoke);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("buildingList");
        List<BuildingTest> buildings = JSON.parseArray(jsonArray.toString(), BuildingTest.class);
        for (BuildingTest b : buildings) {
            System.out.println(b.getBuildingId());
        }
    }

    /**
     * 获取单元
     *
     * @author Mr.Deng
     * @date 18:01 2018/11/13
     */
    @Test
    public void getUnitList() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1//unit/getUnitList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("zoneId", "363");
        map.put("buildingId", "618");
//        map.put("unitStatus", 1);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        System.out.println(invoke);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("unitList");
        List<UnitTest> units = JSON.parseArray(jsonArray.toString(), UnitTest.class);
        for (UnitTest u : units) {
            System.out.println(u.getUnitId());
        }
    }

    /**
     * 获取房间列表
     *
     * @author Mr.Deng
     * @date 18:02 2018/11/13
     */
    @Test
    public void getRoomList() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/room/getRoomList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("zoneId", 363);
        map.put("buildingId", 423);
        map.put("unitId", 565);
        map.put("roomStatus", 1);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        //{"roomNum":"0101","roomStatus":1,"roomId":15448}
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("roomList");
        List<RoomTest> rooms = JSON.parseArray(jsonArray.toString(), RoomTest.class);
        for (RoomTest r : rooms) {
            System.out.println(r.getRoomNum());
        }
    }

    /**
     * 获取住户列表
     *
     * @author Mr.Deng
     * @date 18:10 2018/11/13
     */
    @Test
    public void getHouseholdList() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/household/getHouseholdList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        map.put("zoneId", 363);
        map.put("buildingId", 423);
        map.put("pageSize", 100);
        map.put("pageNum", 5);

        String invoke = DnakeWebApiUtil.invoke(url, map);
        System.out.println(invoke);
        //{"householdName":"舒园园","householdType":8,"buildingName":"二号楼","authorizeStatus":6,"householdId":37137,"zoneName":"珉轩智能大厦","doorDeviceGroupIds":"","householdStatus":1,"unitName":"一单元","roomNum":"0101","gender":0,"residenceTime":"2100-01-01 00:00:00","appDeviceGroupIds":"629","mobile":"13064102937"}
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("householdList");
        List<HouseTest> houses = JSON.parseArray(jsonArray.toString(), HouseTest.class);
        for (HouseTest h : houses) {
            System.out.println(h.getHouseholdName());
        }
    }

    /**
     * 获取访客列表
     *
     * @author Mr.Deng
     * @date 18:21 2018/11/13
     */
    @Test
    public void getVisitorList() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/visitor/getVisitorList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        String invoke = DnakeWebApiUtil.invoke(url, map);
        System.out.println(invoke);
        //"visitorList":[{"visitorStatus":1,"buildingName":"凯翔外滩门口机1","expiryDate":"2018-10-16 23:59:59","zoneName":"鹰潭人脸测试","inviteName":"严波","inviteType":1,"unitName":"一单元","roomNum":"0201","inviteMobile":"13407901037","visitorId":4796}
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("visitorList");
        List<VisitorTest> visitors = JSON.parseArray(jsonArray.toString(), VisitorTest.class);
        for (VisitorTest v : visitors) {
            System.out.println(v.getVisitorId() + "-》" + v.getInviteName());
        }
    }

    /**
     * 获取设备列表
     *
     * @author Mr.Deng
     * @date 10:02 2018/11/14
     */
    @Test
    public void getDeviceList() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/device/getDeviceList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        String invoke = DnakeWebApiUtil.invoke(url, map);
        System.out.println(invoke);
        //"deviceList":[{"deviceNum":"AB900DX8880285879170","deviceName":"凯翔演示-出门","deviceType":"W","deviceCode":"2","deviceStatus":0,"buildingCode":"","deviceSip":"61723086239699","deviceId":2602,"unitCode":""}
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("deviceList");
        List<DeviceTest> devices = JSON.parseArray(jsonArray.toString(), DeviceTest.class);
        for (DeviceTest d : devices) {
            System.out.println(d.getDeviceId() + "-》" + d.getDeviceNum());
        }
    }

    /**
     * 获取设备组列表
     *
     * @author Mr.Deng
     * @date 10:09 2018/11/14
     */
    @Test
    public void getDeviceGroupList() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/deviceGroup/getDeviceGroupList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        String invoke = DnakeWebApiUtil.invoke(url, map);
        //"deviceGroupList":[{"deviceGroupId":"629","deviceGroupName":"公共权限组","groupType":"2","deviceList":[{"deviceNum":"AB900DX8880285879170","deviceName":"凯翔演示-出门","deviceType":"W","deviceStatus":"0","buildingCode":"","deviceId":"2602","unitCode":""},{"deviceNum":"AB900DX88801e86a7770","deviceName":"凯翔演示-进门","deviceType":"W","deviceStatus":"1","buildingCode":"","unitId":"996","deviceId":"2572","unitCode":"","buildingId":"603"}]}]
        JSONObject json = JSONObject.fromObject(invoke);
        JSONArray deviceGroupList = json.getJSONArray("deviceGroupList");
        for (int i = 0; i < deviceGroupList.size(); i++) {
            JSONObject jsonObject = JSONObject.fromObject(deviceGroupList.get(i));
            System.out.println(jsonObject.get("deviceGroupId") + "->" + jsonObject.get("deviceGroupName") +
                    "->" + jsonObject.get("groupType") + "->" + "deviceList:");
            JSONArray jsonArray = jsonObject.getJSONArray("deviceList");
            List<DeviceTest> devices = JSON.parseArray(jsonArray.toString(), DeviceTest.class);
            for (DeviceTest d : devices) {
                System.out.println(d.getDeviceId() + "-》" + d.getDeviceNum());
            }
        }
    }

    /**
     * 获取邀请码列表
     *
     * @author Mr.Deng
     * @date 10:57 2018/11/14
     */
    @Test
    public void getInviteCodeList() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/visitor/getInviteCodeList";
        Map<String, Object> map = new HashMap<>();
        map.put("appUserId", 77626);
        map.put("pageIndex", 0);
        map.put("pageSize", 100);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        //"list":[{"modifytime":"2018-11-14 11:17:14","validityEndTime":"2018-11-14 23:59:59","dataStatus":1,"inviteCode":"8291","deviceGroupId":0,"useTimes":1,"inviteCodeType":2}
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        List<InviteCode> inviteCodes = JSON.parseArray(jsonArray.toString(), InviteCode.class);
        for (InviteCode i : inviteCodes) {
            System.out.println(i.getInviteCode() + "->" + i.getModifytime());
        }
    }

    /**
     * 获取门禁记录
     *
     * @author Mr.Deng
     * @date 16:37 2018/11/13
     */
    @Test
    public void getAccessControlList() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/device/getAccessControlList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", "ab497a8a46194311ad724e6bf79b56de");
        String invoke = DnakeWebApiUtil.invoke(url, map);
        System.out.println(invoke);
        //{"accessControlList":[{"householdName":"严波","accessTime":"2018-11-14 11:17:14","buildingName":"2栋","householdId":22376,"buildingCode":"0002","accessImgUrl":"http://image.ishanghome.com/1542165434.jpg","zoneName":"珉轩智能大厦","householdMobile":"13407901037","id":2464172,"deviceNum":"AB900DX88801e86a7770","deviceName":"凯翔演示-进门","interactiveType":2,"unitName":"一单元","cardNum":"8291","unitCode":"01"}
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("accessControlList");
        List<AccessControlTest> accessControls = JSON.parseArray(jsonArray.toString(), AccessControlTest.class);
        for (AccessControlTest a : accessControls) {
            System.out.println(a.getHouseholdName() + "->" + a.getAccessImgUrl());
        }
    }

    /**
     * 获取呼叫记录
     *
     * @author Mr.Deng
     * @date 11:35 2018/11/14
     */
    @Test
    public void getDeviceCallList() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/device/getDeviceCallList";
        Map<String, Object> map = new HashMap<>();
        map.put("communityCode", "047cd4ab796a419a80a4d362b9da1c8f");
        String invoke = DnakeWebApiUtil.invoke(url, map);
        System.out.println(invoke);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("deviceCallList");
        List<DeviceCallTest> deviceCalls = JSON.parseArray(jsonArray.toString(), DeviceCallTest.class);
        for (DeviceCallTest d : deviceCalls) {
            System.out.println(d.getDeviceName() + "->" + d.getCallImgUrl());
        }
    }

}

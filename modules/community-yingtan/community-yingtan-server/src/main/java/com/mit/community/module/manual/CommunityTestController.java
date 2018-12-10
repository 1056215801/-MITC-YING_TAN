package com.mit.community.module.manual;

import com.alibaba.fastjson.JSON;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.entity.DnakeAppUser;
import com.mit.common.util.DateUtils;
import com.mit.community.common.HttpLogin;
import com.mit.community.entity.*;
import com.mit.community.entity.modelTest.*;
import com.mit.community.service.*;
import com.mit.community.util.IdCardInfoExtractorUtil;
import com.mit.community.util.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区控制器
 * @author Mr.Deng
 * @date 2018/11/14 15:17
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@RestController
public class CommunityTestController {

    private final ClusterCommunityService clusterCommunityService;

    private final ZoneService zoneService;

    private final BuildingService buildingService;

    private final UnitService unitService;

    private final RoomService roomService;

    private final HouseHoldService houseService;

    private final VisitorService visitorService;

    private final DeviceService deviceService;

    private final AccessControlService accessControlService;

    private final DeviceCallService deviceCallService;

    private final HttpLogin httpLogin;

    private final IdCardInfoExtractorUtil idCardInfoExtractorUtil;

    @Autowired
    public CommunityTestController(ClusterCommunityService clusterCommunityService,
                                   ZoneService zoneService,
                                   BuildingService buildingService,
                                   UnitService unitService,
                                   RoomService roomService,
                                   HouseHoldService houseService,
                                   VisitorService visitorService,
                                   DeviceService deviceService,
                                   AccessControlService accessControlService,
                                   DeviceCallService deviceCallService, HttpLogin httpLogin,
                                   IdCardInfoExtractorUtil idCardInfoExtractorUtil) {
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
        this.buildingService = buildingService;
        this.unitService = unitService;
        this.roomService = roomService;
        this.houseService = houseService;
        this.visitorService = visitorService;
        this.deviceService = deviceService;
        this.accessControlService = accessControlService;
        this.deviceCallService = deviceCallService;
        this.httpLogin = httpLogin;
        this.idCardInfoExtractorUtil = idCardInfoExtractorUtil;
    }

    /**
     * 解析身份证信息
     * @param idCard 身份证号码
     * @return
     * @author Mr.Deng
     * @date 11:29 2018/11/21
     */
    @RequestMapping("getIdCardInfo")
    public Result getIdCardInfo(String idCard) {
        IdCardInfo idCardInfo = idCardInfoExtractorUtil.idCardInfo(idCard);
        return Result.success(idCardInfo);
    }

    /**
     * 保存小区信息
     * @return
     * @author Mr.Deng
     * @date 15:59 2018/11/14
     */
    @RequestMapping("saveClusterCommunity")
    public Result saveClusterCommunity() {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/community/queryClusterCommunity";
        HashMap<String, Object> map = new HashMap<>();
        map.put("clusterAccountId", DnakeConstants.CLUSTER_ACCOUNT_ID);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        //处理返回json数据
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("communityList");
        List<ClusterCommunityTest> clusterCommunities = JSON.parseArray(jsonArray.toString(), ClusterCommunityTest.class);
        for (ClusterCommunityTest c : clusterCommunities) {
            ClusterCommunity cc = new ClusterCommunity();
            cc.setAddress(c.getAddress());
            cc.setAreaName(c.getAreaName());
            cc.setCityName(c.getCityName());
            cc.setCommunityCode(c.getCommunityCode());
            cc.setCommunityId(Integer.parseInt(c.getCommunityId()));
            cc.setCommunityName(c.getCommunityName());
            cc.setProvinceName(c.getProvinceName());
            cc.setStreetName(c.getStreetName());
            clusterCommunityService.save(cc);
        }
        return Result.success("OK");
    }

    /**
     * 添加分区信息
     * @return
     * @author Mr.Deng
     * @date 16:19 2018/11/14
     */
    @RequestMapping("getZoneList")
    public Result getZoneList() {
        List<ClusterCommunity> clusters = clusterCommunityService.list();
        for (ClusterCommunity c : clusters) {
            DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
            String url = "/v1//zone/list";
            Map<String, Object> map = new HashMap<>();
            map.put("communityCode", c.getCommunityCode());
            map.put("zoneStatus", 1);
            String invoke = DnakeWebApiUtil.invoke(url, map);
            JSONObject jsonObject = JSONObject.fromObject(invoke);
            JSONArray jsonArray = jsonObject.getJSONArray("zoneList");
            List<ZoneList> zoneLists = JSON.parseArray(jsonArray.toString(), ZoneList.class);
            for (ZoneList s : zoneLists) {
                Zone zone = new Zone();
                zone.setCommunityCode(c.getCommunityCode());
                zone.setZoneCode(s.getZoneCode());
                zone.setZoneId(Integer.parseInt(s.getZoneId()));
                zone.setZoneName(s.getZoneName());
                zone.setZoneStatus(Integer.parseInt(s.getZoneStatus()));
                zone.setZoneType(Integer.parseInt(s.getZoneType()));
                zoneService.save(zone);
            }
        }
        return Result.success("OK");
    }

    /**
     * 添加楼栋信息
     * @return result
     * @author Mr.Deng
     * @date 16:41
     */
    @RequestMapping("getBuildingList")
    public Result getBuildingList() {
        List<Zone> zoneList = zoneService.list();
        for (Zone z : zoneList) {
            DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
            String url = "/v1/building/list";
            Map<String, Object> map = new HashMap<>();
            map.put("communityCode", z.getCommunityCode());
            map.put("zoneId", z.getZoneId());
            map.put("buildingStatus", "1");
            String invoke = DnakeWebApiUtil.invoke(url, map);
            JSONObject jsonObject = JSONObject.fromObject(invoke);
            JSONArray jsonArray = jsonObject.getJSONArray("buildingList");
            List<BuildingTest> buildings = JSON.parseArray(jsonArray.toString(), BuildingTest.class);
            for (BuildingTest b : buildings) {
                Building building = new Building();
                building.setCommunityCode(z.getCommunityCode());
                building.setZoneId(z.getZoneId());
                building.setBuildingCode(b.getBuildingCode());
                building.setBuildingId(Integer.parseInt(b.getBuildingId()));
                building.setBuildingName(b.getBuildingName());
                building.setBuildingStatus(Integer.parseInt(b.getBuildingStatus()));
                buildingService.save(building);
            }
        }
        return Result.success("OK");
    }

    /**
     * 获取单元
     * @return
     * @author Mr.Deng
     * @date 16:54 2018/11/14
     */
    @RequestMapping("getUnitList")
    public Result getUnitList() {
        List<Building> buildingList = buildingService.list();
        for (Building b : buildingList) {
            DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
            String url = "/v1//unit/list";
            Map<String, Object> map = new HashMap<>();
            map.put("communityCode", b.getCommunityCode());
            map.put("zoneId", b.getZoneId());
            map.put("buildingId", b.getBuildingId());
            map.put("unitStatus", 1);
            String invoke = DnakeWebApiUtil.invoke(url, map);
            JSONObject jsonObject = JSONObject.fromObject(invoke);
            JSONArray jsonArray = jsonObject.getJSONArray("unitList");
            List<UnitTest> units = JSON.parseArray(jsonArray.toString(), UnitTest.class);
            for (UnitTest u : units) {
                Unit unit = new Unit();
                unit.setCommunityCode(b.getCommunityCode());
                unit.setZoneId(b.getZoneId());
                unit.setBuildingId(b.getBuildingId());
                unit.setUnitCode(u.getUnitCode());
                unit.setUnitId(Integer.parseInt(u.getUnitId()));
                unit.setUnitName(u.getUnitName());
                unit.setUnitStatus(Integer.parseInt(u.getUnitStatus()));
                unitService.save(unit);
            }
        }
        return Result.success("Ok");
    }

    /**
     * 获取房间信息
     * @return
     * @author Mr.Deng
     * @date 17:44 2018/11/14
     */
    @RequestMapping("getRoomList")
    public Result getRoomList() {
        List<Unit> unitList = unitService.list();
        for (Unit u : unitList) {
            DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
            String url = "/v1/room/list";
            Map<String, Object> map = new HashMap<>();
            map.put("communityCode", u.getCommunityCode());
            map.put("zoneId", u.getZoneId());
            map.put("buildingId", u.getBuildingId());
            map.put("unitId", u.getUnitId());
            map.put("roomStatus", 1);
            String invoke = DnakeWebApiUtil.invoke(url, map);
            JSONObject jsonObject = JSONObject.fromObject(invoke);
            JSONArray jsonArray = jsonObject.getJSONArray("roomList");
            List<RoomTest> rooms = JSON.parseArray(jsonArray.toString(), RoomTest.class);
            for (RoomTest r : rooms) {
                Room room = new Room();
                room.setCommunityCode(u.getCommunityCode());
                room.setBuildingId(u.getBuildingId());
                room.setZoneId(u.getZoneId());
                room.setUnitId(u.getUnitId());
                room.setRoomId(Integer.parseInt(r.getRoomId()));
                room.setRoomNum(r.getRoomNum());
                room.setRoomStatus(Integer.parseInt(r.getRoomStatus()));
                roomService.save(room);
            }
        }
        return Result.success("OK");
    }

    /**
     * 添加住户信息
     * @return
     * @author Mr.Deng
     * @date 18:57 2018/11/14
     */
    @RequestMapping("getHouseholdList")
    public Result getHouseholdList() {
        List<Room> roomList = roomService.list();
        for (Room r : roomList) {
            DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
            String url = "/v1/household/getHouseholdList";
            Map<String, Object> map = new HashMap<>();
            map.put("communityCode", r.getCommunityCode());
            map.put("zoneId", r.getZoneId());
            map.put("buildingId", r.getBuildingId());
            map.put("unitId", r.getUnitId());
            map.put("roomNum", r.getRoomNum());
            String invoke = DnakeWebApiUtil.invoke(url, map);
            JSONObject jsonObject = JSONObject.fromObject(invoke);
            JSONArray jsonArray = jsonObject.getJSONArray("householdList");
            List<HouseTest> houses = JSON.parseArray(jsonArray.toString(), HouseTest.class);
            for (HouseTest h : houses) {
                HouseHold house = new HouseHold();
//                house.setAppDeviceGroupIds(Integer.valueOf(h.getAppDeviceGroupIds()));
                house.setAuthorizeStatus(Integer.valueOf(h.getAuthorizeStatus()));
                house.setBuildingId(r.getBuildingId());
                house.setBuildingName(h.getBuildingName());
                house.setCommunityCode(r.getCommunityCode());
//                house.setDoorDeviceGroupIds(h.getDoorDeviceGroupIds());
                house.setGender(Integer.valueOf(h.getGender()));
                house.setHouseholdId(Integer.parseInt(h.getHouseholdId()));
                house.setHouseholdName(h.getHouseholdName());
                house.setHouseholdStatus(Integer.valueOf(h.getHouseholdStatus()));
                house.setHouseholdType(Integer.parseInt(h.getHouseholdType()));
                house.setMobile(h.getMobile());
//                house.setResidenceTime(LocalDateTime.parse(h.getResidenceTime()));
                house.setRoomNum(h.getRoomNum());
                house.setSipAccount(h.getSipAccount());
                house.setSipPassword(h.getSipPassword());
                house.setUnitId(r.getUnitId());
                house.setUnitName(h.getUnitName());
                house.setZoneId(r.getZoneId());
                house.setZoneName(h.getZoneName());
                houseService.save(house);
            }
        }
        return Result.success("OK");
    }

    /**
     * 添加访客信息
     * @return
     * @author Mr.Deng
     * @date 19:55 2018/11/14
     */
    @RequestMapping("getVisitorList")
    public Result getVisitorList() {
        List<Unit> unitList = unitService.list();
        for (Unit u : unitList) {
            DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
            String url = "/v1/visitor/list";
            Map<String, Object> map = new HashMap<>();
            map.put("communityCode", u.getCommunityCode());
            map.put("zoneId", u.getZoneId());
            map.put("buildingId", u.getBuildingId());
            map.put("unitId", u.getUnitId());
            String invoke = DnakeWebApiUtil.invoke(url, map);
            JSONObject jsonObject = JSONObject.fromObject(invoke);
            JSONArray jsonArray = jsonObject.getJSONArray("visitorList");
            List<VisitorTest> visitors = JSON.parseArray(jsonArray.toString(), VisitorTest.class);
            for (VisitorTest v : visitors) {
                Visitor visitor = new Visitor();
                visitor.setBuildingId(u.getBuildingId());
                visitor.setBuildingName(v.getBuildingName());
                visitor.setCommunityCode(u.getCommunityCode());
                visitor.setExpiryDate(v.getExpiryDate());
                visitor.setInviteMobile(v.getInviteMobile());
                visitor.setInviteName(v.getInviteName());
                visitor.setInviteType(Integer.parseInt(v.getInviteType()));
                visitor.setRoomNum(v.getRoomNum());
                visitor.setUnitId(u.getUnitId());
                visitor.setUnitName(v.getUnitName());
                visitor.setVisitorId(Integer.parseInt(v.getVisitorId()));
                visitor.setVisitorStatus(Integer.parseInt(v.getVisitorStatus()));
                visitor.setZoneId(u.getZoneId());
                visitor.setZoneName(v.getZoneName());
                visitorService.save(visitor);
            }
        }
        return Result.success("OK");
    }

    /**
     * 添加设备信息
     * @return
     * @author Mr.Deng
     * @date 9:50 2018/11/15
     */
    @RequestMapping("getDeviceList")
    public Result getDeviceList() {
        List<ClusterCommunity> clusterCommunityList = clusterCommunityService.list();
        for (ClusterCommunity c : clusterCommunityList) {
            DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
            String url = "/v1/device/list";
            Map<String, Object> map = new HashMap<>();
            map.put("communityCode", c.getCommunityCode());
            String invoke = DnakeWebApiUtil.invoke(url, map);
            JSONObject jsonObject = JSONObject.fromObject(invoke);
            JSONArray jsonArray = jsonObject.getJSONArray("deviceList");
            List<DeviceTest> devices = JSON.parseArray(jsonArray.toString(), DeviceTest.class);
            for (DeviceTest d : devices) {
                Device device = new Device();
                device.setBuildingId(d.getBuildingId() != null ? d.getBuildingId() : "");
                device.setBuildingCode(d.getBuildingCode());
                device.setCommunityCode(c.getCommunityCode());
                device.setDeviceCode(d.getDeviceCode());
                device.setDeviceId(d.getDeviceId());
                device.setDeviceName(d.getDeviceName());
                device.setDeviceNum(d.getDeviceNum());
                device.setDeviceSip(d.getDeviceSip());
//                device.setDeviceStatus(d.getDeviceStatus());
                device.setDeviceType(d.getDeviceType());
                device.setUnitCode(d.getUnitCode());
                device.setUnitId(d.getUnitId() != null ? d.getUnitId() : "");
                deviceService.save(device);
            }
        }
        return Result.success("OK");
    }

    /**
     * 获取门禁记录
     * @return
     * @author Mr.Deng
     * @date 11:25 2018/11/15
     */
    @RequestMapping("getAccessControlList")
    public Result getAccessControlList() {
        List<ClusterCommunity> clusterCommunityList = clusterCommunityService.list();
        for (ClusterCommunity c : clusterCommunityList) {
            DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
            String url = "/v1/device/getAccessControlList";
            Map<String, Object> map = new HashMap<>();
            map.put("communityCode", c.getCommunityCode());
            String invoke = DnakeWebApiUtil.invoke(url, map);
            System.out.println(invoke);
            JSONObject jsonObject = JSONObject.fromObject(invoke);
            JSONArray jsonArray = jsonObject.getJSONArray("accessControlList");
            List<AccessControlTest> accessControls = JSON.parseArray(jsonArray.toString(), AccessControlTest.class);
            for (AccessControlTest a : accessControls) {
                AccessControl accessControl = new AccessControl();
                accessControl.setAccessImgUrl(a.getAccessImgUrl());
                LocalDateTime accessTimeLocal = DateUtils.parseStringToDateTime(a.getAccessTime(), null);
                accessControl.setAccessTime(accessTimeLocal);
                accessControl.setBuildingCode(a.getBuildingCode());
                accessControl.setBuildingName(a.getBuildingName());
                accessControl.setCardNum(a.getCardNum());
                accessControl.setCommunityCode(c.getCommunityCode());
                accessControl.setDeviceName(a.getDeviceName());
                accessControl.setDeviceNum(a.getDeviceNum());
                accessControl.setHouseholdId(Integer.valueOf(a.getHouseholdId()));
                accessControl.setHouseholdMobile(a.getHouseholdMobile());
                accessControl.setHouseholdName(a.getHouseholdName());
                accessControl.setInteractiveType(Short.valueOf(a.getInteractiveType()));
                accessControl.setUnitCode(a.getUnitCode());
                accessControl.setUnitName(a.getUnitName());
                accessControl.setZoneName(a.getZoneName());
                accessControlService.save(accessControl);
            }
        }
        return Result.success("OK");
    }

    /**
     * 获取呼叫记录
     * @return
     * @author Mr.Deng
     * @date 12:08 2018/11/15
     */
    @RequestMapping("getDeviceCallList")
    public Result getDeviceCallList() {
        List<Room> roomList = roomService.list();
        for (Room r : roomList) {
            DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
            String url = "/v1/device/list";
            Map<String, Object> map = new HashMap<>();
            map.put("communityCode", r.getCommunityCode());
            map.put("zoneId", r.getZoneId());
            map.put("buildingId", r.getBuildingId());
            map.put("unitId", r.getUnitId());
            String invoke = DnakeWebApiUtil.invoke(url, map);
            JSONObject jsonObject = JSONObject.fromObject(invoke);
            JSONArray jsonArray = jsonObject.getJSONArray("deviceCallList");
            List<DeviceCallTest> deviceCalls = JSON.parseArray(jsonArray.toString(), DeviceCallTest.class);
            for (DeviceCallTest d : deviceCalls) {
                DeviceCall deviceCall = new DeviceCall();
                deviceCall.setBuildingId(r.getBuildingId());
                deviceCall.setCallDuration(d.getCallDuration());
                deviceCall.setCallImgUrl(d.getCallImgUrl());
                deviceCall.setCallTime(d.getCallTime());
                deviceCall.setCallType(d.getCallType());
                deviceCall.setCommunityCode(r.getCommunityCode());
                deviceCall.setDeviceName(d.getDeviceName());
                deviceCall.setDeviceNum(d.getDeviceNum());
                deviceCall.setOpenDoorType(d.getOpenDoorType());
                deviceCall.setReceiver(d.getReceiver());
                deviceCall.setRoomNum(d.getRoomNum());
                deviceCall.setUnitId(r.getUnitId());
                deviceCall.setZoneId(r.getZoneId());
                deviceCallService.save(deviceCall);
            }
        }
        return Result.success("OK");
    }

}

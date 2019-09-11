package com.mit.community.module.dnake.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.entity.entity.DeviceGroup;
import com.mit.community.entity.DeviceInfo;
import com.mit.community.population.service.PersonBaseInfoService;
import com.mit.community.population.service.PersonLabelsService;
import com.mit.community.service.*;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 改写狄耐克权限组模块
 *
 * @author xq
 * @date 2019/7/15
 * @company mitesofor
 */

@RestController
@RequestMapping("/permissionGroupControoler")
@Slf4j
@Api(tags = "狄耐克替代接口")
public class PermissionGroupControoler {
    @Autowired
    private RedisService redisService;
    @Autowired
    private PermissionGroupService permissionGroupService;
    @Autowired
    private DeviceDeviceGroupService deviceDeviceGroupService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private PersonLabelsService personLabelsService;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private DeviceGroupService deviceGroupService;
    @Autowired
    private AuthorizeHouseholdDeviceGroupService authorizeHouseholdDeviceGroupService;
    @Autowired
    private ClusterCommunityService clusterCommunityService;
    @Autowired
    private UserService userService;
    @Autowired
    private DevicePerceptionService devicePerceptionService;

    @Autowired
    private RoomService roomService;
    @Autowired
    private DeviceNoticeService deviceNoticeService;

    /**
     *
     * @param request
     * @param pageNum
     * @param pageSize
     * @param deviceNum 设备编号
     * @param deviceType  设备类型
     * @param deviceStatus  设备状态
     * @return
     */
    @PostMapping("/listPage")
    @ApiOperation(value = "分页获取权限组信息", notes = "")
    public Result getListPage(HttpServletRequest request, Integer pageNum, Integer pageSize, String deviceNum, Integer deviceType, Integer deviceStatus){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        Page<DeviceGroup> page = permissionGroupService.getPage(pageNum, pageSize, deviceNum, deviceType, deviceStatus, communityCode);
        List<DeviceGroup> list = page.getRecords();
        if (!list.isEmpty()) {
            for (int i=0; i<list.size(); i++) {
                List<DeviceInfo> listDeviceInfo = devicePerceptionService.getDevicesByDeviceGroupId(list.get(i).getDeviceGroupId());
                if (!listDeviceInfo.isEmpty()) {
                    for (int a=0; a<listDeviceInfo.size(); a++) {
                        String timeDiffi = listDeviceInfo.get(a).getTimeDiffi();
                        if (StringUtils.isNotBlank(timeDiffi)) {
                            if (Integer.parseInt(timeDiffi) > 10) {
                                listDeviceInfo.get(a).setDeviceStatus(2);//2离线
                            } else {
                                listDeviceInfo.get(a).setDeviceStatus(1);//1在线
                            }
                        } else {
                            listDeviceInfo.get(a).setDeviceStatus(2);
                        }
                    }

                }
                list.get(i).setDevice(listDeviceInfo);
            }

        }
        page.setRecords(list);
        return Result.success(page);
    }

    /**
     *

     * @param deviceGroupName 权限组名称
     * @param groupType 组类别
     * @param deviceNum 设备编号
     * @return
     */
    @PostMapping("/updateAuthGroup")
    @ApiOperation(value = "更新权限组信息", notes = "")
    public Result updateAuthGroup(Integer deviceGroupId, String deviceGroupName, int groupType, String deviceNum) {
        deviceGroupService.updateAuthGroup(deviceGroupId, deviceGroupName, groupType, deviceNum);
        return Result.success("更新成功");
    }

    @PostMapping("/deleteAuthGroup")
    @ApiOperation(value = "删除权限组信息", notes = "")
    public Result deleteAuthGroup(Integer deviceGroupId) {
        deviceGroupService.deleteAuthGroup(deviceGroupId);
        return Result.success("删除成功");
    }

    /**
     *
     * @param request
     * @param deviceGroupName 组名称
     * @param groupType 组类别
     * @param deviceNum 设备编号
     * @return
     */
    @PostMapping("/saveAuthGroup")
    @ApiOperation(value = "新增权限组信息", notes = "")
    public Result saveAuthGroup(HttpServletRequest request, String deviceGroupName, int groupType, String deviceNum) {
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        deviceGroupService.saveAuthGroup(communityCode, deviceGroupName, groupType, deviceNum);
        return Result.success("保存成功");
    }


    @PostMapping("/getZonelist")
    @ApiOperation(value = "获取分区信息", notes = "")
    public Result getZonelist(HttpServletRequest request){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        List<Zone> list = zoneService.listByCommunityCode(communityCode);
        return Result.success(list);
    }

    @PostMapping("/getBuildinglist")
    @ApiOperation(value = "获取楼栋信息", notes = "zoneId 分区id")
    public Result getBuildinglist(HttpServletRequest request, Integer zoneId){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        List<Building> list = buildingService.listByZoneId(zoneId);
        return Result.success(list);
    }

    @PostMapping("/getUnitlist")
    @ApiOperation(value = "获取单元信息", notes = "buildingId 楼栋id")
    public Result getUnitlist(HttpServletRequest request, Integer buildingId){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        List<Unit> list = unitService.listByBuildingId(buildingId);
        return Result.success(list);
    }

    @ApiOperation(value = "获取房间列表")
    @PostMapping("/getRoomlist")
    @ApiImplicitParam(name="unitId",value="单元id",required=true,paramType = "query")
    public Result getRoomlist(Integer unitId){
        EntityWrapper<Room> wrapper=new EntityWrapper<>();
        wrapper.eq("unit_id",unitId);
        wrapper.eq("room_status",1);
        List<Room> roomList = roomService.selectList(wrapper);
        return Result.success(roomList);
    }

    /**
     * 根据单元楼栋获取设备列表
     * @param request
     * @param pageNum
     * @param pageSize
     * @param unitId  单元id
     * @param buildingId  楼栋id
     * @return
     */
    /*@PostMapping("/getDevicePage")
    @ApiOperation(value = "获取设备列表", notes = "")
    public Result getDevicePage(HttpServletRequest request,Integer pageNum, Integer pageSize, Integer unitId, Integer buildingId){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        Page<Device> page = deviceService.getDevicePage( pageNum, pageSize, unitId, buildingId,communityCode);
        List<Device> list = page.getRecords();
        if (!list.isEmpty()) {
            for (int i=0; i<list.size(); i++) {
                String groupName = personLabelsService.getGroupName(list.get(i).getDeviceId());
                if (StringUtils.isNotBlank(groupName)) {
                    list.get(i).setDeviceGroupName(groupName);
                }
                String timeCha = personLabelsService.getTimeCha(list.get(i).getDeviceId());
                if (StringUtils.isNotBlank(timeCha)) {
                    if (Integer.parseInt(timeCha) > 10) {
                        list.get(i).setDeviceStatus(0);
                    }
                    if (Integer.parseInt(timeCha) < 10) {
                        list.get(i).setDeviceStatus(1);
                    }
                }
            }
        }
        page.setRecords(list);
        return Result.success(page);
    }*/

    /**
     * 根据所在社区获取权限组
     * @param request
     * @return
     */
    @PostMapping("/getAuthGroup")
    @ApiOperation(value = "获取权限组", notes = "")
    public Result getAuthGroup(HttpServletRequest request){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        List<DeviceGroup> list = deviceGroupService.getByCommunityCode(communityCode);
        return Result.success(list);
    }

    @PostMapping("/addHoldAuthGroup")
    @ApiOperation(value = "将住户添加到权限组", notes = "houseHoidId 住户id，deviceGroupId 权限组id")
    public Result addHoldAuthGroup(Integer houseHoidId, String deviceGroupId){
        authorizeHouseholdDeviceGroupService.addHoldAuthGroup(houseHoidId, deviceGroupId);
        return Result.success("添加成功");
    }

    /**
     * app保存应用接口
     * @param deviceGroupId
     * @return
     */
    @PostMapping("/saveDeviceInfo")
    @ApiOperation(value = "保存设备应用信息", notes = "")
    public Result saveDeviceInfo(HttpServletRequest request, String deviceId, String communityCode,String unitId, String unitCode, String buildingId, String buildingCode, String deviceName, String deviceNum, String deviceType,
                                 String deviceCode, String deviceSip, String coordinate, Integer deviceGroupId,String deviceMac, String verison, String cardHand){
        /*String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }*/
        /*String deviceId = null;//这个参数要前端传过来，用dnake_device_info表记录的id
        String maxDeviceId = personLabelsService.getMaxDeviceId();
        if(StringUtils.isNotBlank(maxDeviceId)){
            int a = Integer.parseInt(maxDeviceId) + 1 ;
            deviceId = a + "";
        } else {
            deviceId = "1";
        }*/
        /*ClusterCommunity clusterCommunity = clusterCommunityService.getByCommunityCode(communityCode);
        String communityName = clusterCommunity.getCommunityName();//社区名称
        Building building = buildingService.getBybuildingCode(buildingCode, communityCode);
        String buildingName = building.getBuildingName();
        Unit unit = unitService.getByUnitCode(unitCode, communityCode);
        String unitName = unit.getUnitName();*/
        Device device = new Device();
        device.setCoordinate(coordinate);
        device.setCommunityCode(communityCode);
        device.setBuildingId(buildingId);
        device.setUnitId(unitId);
        device.setDeviceName(deviceName);
        device.setDeviceNum(deviceNum);//生成规则待定
        device.setDeviceType(deviceType);
        device.setDeviceStatus(0);
        device.setDeviceCode(deviceCode);
        device.setDeviceSip(deviceSip);
        device.setBuildingCode(buildingCode);
        device.setUnitCode(unitCode);
        device.setDeviceId(deviceId);
        device.setGmtCreate(LocalDateTime.now());
        device.setGmtModified(LocalDateTime.now());
        device.setVerison(verison);
        device.setCardHand(cardHand);
        deviceService.insert(deviceGroupId,device);

        return Result.success("添加成功");
    }

    /**
     * 根据mac码删除设备
     * @param request
     * @param deviceMac
     * @return
     */
    @PostMapping("/deleteBunding")
    @ApiOperation(value = "删除设备（接触绑定）", notes = "")
    public Result deleteBunding(HttpServletRequest request,String deviceMac){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        EntityWrapper<DeviceDeviceGroup> wrapper = new EntityWrapper<>();
        wrapper.eq("device_mac",deviceMac);
        deviceDeviceGroupService.delete(wrapper);
        return Result.success("删除成功");
    }



    @PostMapping("/addDeviceDugPeople")
    @ApiOperation(value = "增加设备调试人员", notes = "")
    public Result addDeviceDugPeople(HttpServletRequest request,String name, String cellPhone, String sex, String expireTime, String remarks){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        String accountType = user.getAccountType();
        userService.addDeviceDugAccount(name, cellPhone, sex, communityCode, accountType,expireTime, remarks);
        return Result.success("添加成功");
    }

    @PostMapping("/getPageDeviceDugPeople")
    @ApiOperation(value = "分页查询设备调试人员", notes = "")
    public Result getPageDeviceDugPeople(HttpServletRequest request,String cellPhone, Integer pageNum, Integer pageSize){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();

        Page<DugPeopleInfo> page = personLabelsService.pageDeviceDugPeople(communityCode, cellPhone, pageNum, pageSize);
        return Result.success(page);
    }

    @PostMapping("/deviceDugPeopleChange")
    @ApiOperation(value = "停用/启用 设备调试人员", notes = "changeType 1启用，2停用")
    public Result deviceDugPeopleChange(HttpServletRequest request,String cellPhone, Integer changeType ){
        userService.deviceDugPeopleChange(cellPhone, changeType);
        return Result.success("ok");
    }

    @PostMapping("/deleteDeviceDugPeople")
    @ApiOperation(value = "删除设备调试人员", notes = "")
    public Result deleteDeviceDugPeople(HttpServletRequest request,String cellPhone){
        userService.deleteDeviceDugPeople(cellPhone);
        return Result.success("ok");
    }

    @PostMapping("/saveDeviceNotice")
    @ApiOperation(value = "保存设备公告", notes = "type 公告类型（//1紧急；2通知；3活动；4提示；5新闻）；canal 发布渠道（1滚动文字；2图片轮播）；startTime 开始时间（yyyy-MM-dd）;endTime 开始时间（yyyy-MM-dd）;" +
            "playTime 播放时间段（07:00-09:30;13:00-14:50）")
    public Result saveDeviceNotice(HttpServletRequest request,String title, Integer type, Integer canal, String startTime, String endTime, String playTime, String content, MultipartFile[] photos){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        ClusterCommunity clusterCommunity = clusterCommunityService.getByCommunityCode(communityCode);
        if (canal == 2 && photos == null) {
            return Result.error("图片不能为空");
        }
        deviceNoticeService.save(communityCode, clusterCommunity.getCommunityName(), title, type, canal, startTime, endTime, playTime, content, photos);
        return Result.success("ok");
    }

    @PostMapping("/deviceNoticeListPage")
    @ApiOperation(value = "分页获取设备公告", notes = "")
    public Result deviceNoticeListPage(HttpServletRequest request,String title, Integer type, String startTime, String endTime, Integer status, Integer pageNum, Integer pageSize){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        Page<DeviceNotice> page = deviceNoticeService.deviceNoticeListPage(communityCode, title, type, startTime, endTime, status, pageNum, pageSize);
        return Result.success(page);
    }

    @PostMapping("/deleteDeviceNotice")
    @ApiOperation(value = "删除设备公告", notes = "")
    public Result deleteDeviceNotice(HttpServletRequest request,Integer id){
        deviceNoticeService.deleteDeviceNotice(id);
        return Result.success("ok");
    }

    @PostMapping("/deviceNoticeChange")
    @ApiOperation(value = "停用/启用 设备公告", notes = "changeType 1启用，2停用")
    public Result deviceNoticeChange(HttpServletRequest request,Integer id, Integer status){
        deviceNoticeService.deviceNoticeChange(id, status);
        return Result.success("ok");
    }

    @PostMapping("/deleteDeviceNoticePhoto")
    @ApiOperation(value = "删除设备公告图片", notes = "id：图片记录id")
    public Result deleteDeviceNoticePhoto(HttpServletRequest request,Integer id){
        deviceNoticeService.deleteDeviceNoticePhoto(id);
        return Result.success("ok");
    }

    @PostMapping("/addDeviceNoticePhoto")
    @ApiOperation(value = "增加设备公告图片", notes = "id：公告记录id")
    public Result addDeviceNoticePhoto(HttpServletRequest request,Integer id, MultipartFile photo){
        deviceNoticeService.addDeviceNoticePhoto(id, photo);
        return Result.success("ok");
    }

    @PostMapping("/updateDeviceNotice")
    @ApiOperation(value = "修改设备公告", notes = "id：公告记录id")
    public Result updateDeviceNotice(HttpServletRequest request,Integer id, String title, Integer type, Integer canal, String startTime, String endTime, String playTime, String content){
        deviceNoticeService.updateDeviceNotice(id, title, type, canal, startTime, endTime, playTime, content);
        return Result.success("ok");
    }

}

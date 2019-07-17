package com.mit.community.module.dnake.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.entity.entity.DeviceGroup;
import com.mit.community.population.service.PersonBaseInfoService;
import com.mit.community.population.service.PersonLabelsService;
import com.mit.community.service.*;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 这里的device和dnake_device_info通过mac码关联
 */

@RestController
@RequestMapping("/permissionGroupControoler")
@Slf4j
@Api(tags = "权限组")
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
                List<DeviceDeviceGroup> group =  deviceDeviceGroupService.getGroupsByDeviceGroupId(list.get(i).getDeviceGroupId());
                if (!group.isEmpty()) {
                    Device device = null;
                    List<Device> devices = new ArrayList<>();
                    for (int a=0; a<group.size();a++) {
                        device = deviceService.getByDeviceNumAndCommunityCode(communityCode,group.get(a).getDeviceNum());
                        if (device != null) {
                            String timeCha = personLabelsService.getTimeCha(device.getDeviceId());
                            if (StringUtils.isNotBlank(timeCha)) {
                                if (Integer.parseInt(timeCha) > 10) {
                                    device.setDeviceStatus(0);
                                }
                                if (Integer.parseInt(timeCha) < 10) {
                                    device.setDeviceStatus(1);
                                }
                            }
                            devices.add(device);
                        }
                    }
                    list.get(i).setDevice(devices);
                }
            }
        }
        page.setRecords(list);
        return Result.success(page);
    }

    @PostMapping("/updateAuthGroup")
    @ApiOperation(value = "更新权限组信息", notes = "")
    public Result updateAuthGroup(Integer id, String deviceGroupName, int groupType, String deviceNum) {
        deviceGroupService.updateAuthGroup(id, deviceGroupName, groupType, deviceNum);
        return Result.success("更新成功");
    }

    @PostMapping("/saveAuthGroup")
    @ApiOperation(value = "新增权限组信息", notes = "")
    public Result updateAuthGroup(HttpServletRequest request, String deviceGroupName, int groupType, String deviceNum) {
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

    @PostMapping("/getDevicePage")
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
    }

    /**
     * 将人员添加到权限组
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
     * @param houseHoidId
     * @param deviceGroupId
     * @return
     */
    @PostMapping("/saveDeviceInfo")
    @ApiOperation(value = "保存设备应用信息", notes = "")
    public Result saveDeviceInfo(HttpServletRequest request, String unitId, String unitCode, String buildingId, String buildingCode, String deviceName, String deviceNum, String deviceType,
                                 String deviceCode, String deviceSip, String coordinate, Integer deviceGroupId,String deviceMac, String verison, String cardHand){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        String deviceId = null;
        String maxDeviceId = personLabelsService.getMaxDeviceId();
        if(StringUtils.isNotBlank(maxDeviceId)){
            int a = Integer.parseInt(maxDeviceId) + 1 ;
            deviceId = a + "";
        } else {
            deviceId = "1";
        }
        Device device = new Device();
        device.setCommunityCode(communityCode);
        device.setBuildingId(buildingId);
        device.setUnitId(unitId);
        device.setDeviceName(deviceName);
        //device.setDeviceNum(deviceNum);//生成规则待定
        device.setDeviceType(deviceType);
        device.setDeviceStatus(0);
        device.setDeviceCode(deviceCode);
        device.setDeviceSip(deviceSip);
        device.setBuildingCode(buildingCode);
        device.setUnitCode(unitCode);
        device.setDeviceId(deviceId);
        device.setGmtCreate(LocalDateTime.now());
        device.setGmtModified(LocalDateTime.now());
        /*device.setDeviceMac(deviceMac);
        device.setVerison(verison);
        device.setCardHand(cardHand);*/
        deviceService.insert(deviceGroupId,device);

        return Result.success("添加成功");
    }


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


}

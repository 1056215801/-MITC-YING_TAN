package com.mit.community.module.dnake.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.entity.entity.DeviceGroup;
import com.mit.community.population.service.PersonLabelsService;
import com.mit.community.service.*;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deviceBindController")
@Slf4j
@Api(tags = "门禁设备绑定")
public class DeviceBindController {
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private DeviceGroupService deviceGroupService;
    @Autowired
    private PersonLabelsService personLabelsService;
    @Autowired
    private ClusterCommunityService clusterCommunityService;
    @Autowired
    private DeviceDeviceGroupService deviceDeviceGroupService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DnakeDeviceInfoService dnakeDeviceInfoService;


    @PostMapping("/getBaseInfo")
    @ApiOperation(value = "获取编号、卡头、软件版本", notes = "")
    public Result getBaseInfo(String id, String communityCode){
        Device device = null;
        if (StringUtils.isNotBlank(id)) {
            device = deviceService.getByDnakeDeviceInfoId(Integer.parseInt(id));
            DnakeDeviceInfo dnakeDeviceInfo = dnakeDeviceInfoService.getById(id);
            if (device == null){
                device = new Device();
                device.setDeviceMac(dnakeDeviceInfo.getMac());
                String a = UUID.randomUUID().toString().replaceAll("-","");
                device.setDeviceNum("AB900" + a.substring(a.length()-15,a.length()));
                device.setVerison("1.8.1 20190617");
            } else {
                Building building = buildingService.getByBuidingId(Integer.parseInt(device.getBuildingId()));
                device.setBuildingName(building.getBuildingName());
                Unit unit = unitService.getByUnitId(Integer.parseInt(device.getUnitId()));
                device.setUnitName(unit.getUnitName());
                Zone zone = zoneService.getByZoneId(Integer.parseInt(device.getZoneId()));
                device.setZoneName(zone.getZoneName());
                DeviceDeviceGroup deviceDeviceGroup = deviceDeviceGroupService.getByDeviceNum(device.getDeviceNum());
                DeviceGroup deviceGroup = deviceGroupService.getById(deviceDeviceGroup.getDeviceGroupId());
                device.setDeviceGroupId(String.valueOf(deviceGroup.getDeviceGroupId()));
                device.setDeviceGroupName(deviceGroup.getDeviceGroupName());
            }
        } else {
            device = new Device();
            String a = UUID.randomUUID().toString().replaceAll("-","");
            device.setDeviceNum("AB900" + a.substring(a.length()-15,a.length()));
            device.setVerison("1.8.1 20190617");
        }
        return Result.success(device);
    }

    @PostMapping("/getDeviceList")
    @ApiOperation(value = "获取设备列表", notes = "")
    public Result getDeviceList(String ip){
        DeviceList deviceList = new DeviceList();
        List<DnakeDeviceDetailsInfo> allDevice = personLabelsService.getUnBindDevice(ip);
        if (!allDevice.isEmpty()) {
            List<DnakeDeviceDetailsInfo> unBind = new ArrayList<>();
            List<DnakeDeviceDetailsInfo> bind = new ArrayList<>();
            for (DnakeDeviceDetailsInfo dnakeDeviceDetailsInfo : allDevice) {
                if (StringUtils.isNotBlank(dnakeDeviceDetailsInfo.getCommunityCode())) {
                    bind.add(dnakeDeviceDetailsInfo);
                } else {
                    unBind.add(dnakeDeviceDetailsInfo);
                }
            }
            if (!bind.isEmpty()) {
                for (DnakeDeviceDetailsInfo dnakeDeviceDetailsInfo : bind) {
                    ClusterCommunity clusterCommunity = clusterCommunityService.getByCommunityCode(dnakeDeviceDetailsInfo.getCommunityCode());
                    dnakeDeviceDetailsInfo.setCommunityName(clusterCommunity.getCommunityName());
                    Building building = buildingService.getByBuidingId(dnakeDeviceDetailsInfo.getBuildingId());
                    dnakeDeviceDetailsInfo.setBuildingName(building.getBuildingName());
                    Unit unit = unitService.getByUnitId(dnakeDeviceDetailsInfo.getUnitId());
                    dnakeDeviceDetailsInfo.setUnitName(unit.getUnitName());
                    Zone zone = zoneService.getByZoneId(dnakeDeviceDetailsInfo.getZoneId());
                    dnakeDeviceDetailsInfo.setZoneName(zone.getZoneName());
                }
            }
            deviceList.setBind(bind);
            deviceList.setUnBind(unBind);
        }
        return Result.success(deviceList);
    }

    @PostMapping("/deleteBunding")
    @ApiOperation(value = "删除设备（接触绑定）", notes = "")
    @Transactional
    public Result deleteBunding(HttpServletRequest request,String deviceNum){
        EntityWrapper<DeviceDeviceGroup> wrapper = new EntityWrapper<>();
        wrapper.eq("device_num",deviceNum);
        deviceDeviceGroupService.delete(wrapper);

        EntityWrapper<Device> wrapperDevice = new EntityWrapper<>();
        wrapperDevice.eq("device_num",deviceNum);
        deviceService.delete(wrapperDevice);
        return Result.success("删除成功");
    }

    @PostMapping("/getZonelist")
    @ApiOperation(value = "获取分区信息", notes = "communityName 小区code")
    public Result getZonelist(String communityCode){
        List<Zone> list = zoneService.listByCommunityCode(communityCode);
        return Result.success(list);
    }


    @PostMapping("/getBuildinglist")
    @ApiOperation(value = "获取楼栋信息", notes = "zoneId 分区id")
    public Result getBuildinglist(Integer zoneId){
        List<Building> list = buildingService.listByZoneId(zoneId);
        return Result.success(list);

    }

    @PostMapping("/getUnitlist")
    @ApiOperation(value = "获取单元信息", notes = "buildingId 楼栋id")
    public Result getUnitlist(Integer buildingId){
        List<Unit> list = unitService.listByBuildingId(buildingId);
        return Result.success(list);
    }


    /**
     * 根据所在社区获取权限组
     * @param request
     * @return
     */
    @PostMapping("/getAuthGroup")
    @ApiOperation(value = "获取权限组", notes = "communityCode 社区code")
    public Result getAuthGroup(HttpServletRequest request, String communityCode){
        List<DeviceGroup> list = deviceGroupService.getByCommunityCode(communityCode);
        return Result.success(list);
    }

    @PostMapping("/saveDeviceInfo")
    @ApiOperation(value = "保存设备应用信息", notes = "")
    public Result saveDeviceInfo(HttpServletRequest request, String deviceId, String communityCode,String unitId, String zoneId, String zoneCode, String unitCode, String buildingId, String buildingCode, String deviceName, String deviceNum,
                                 String deviceCode, String coordinate, Integer deviceGroupId, String cardHandSecond, String cardHand, String version){

        String a = String.valueOf(System.currentTimeMillis());
        String deviceSip = "61723" + a.substring(a.length()-9,a.length());
        Device device = new Device();
        device.setCoordinate(coordinate);
        device.setCommunityCode(communityCode);
        device.setBuildingId(buildingId);
        device.setUnitId(unitId);
        device.setDeviceName(deviceName);
        device.setDeviceNum(deviceNum);
        device.setDeviceType("M");
        device.setDeviceStatus(0);
        device.setDeviceCode(deviceCode);
        device.setDeviceSip(deviceSip);
        device.setBuildingCode(buildingCode);
        device.setUnitCode(unitCode);
        device.setDeviceId(String.valueOf(System.currentTimeMillis()));
        device.setGmtCreate(LocalDateTime.now());
        device.setGmtModified(LocalDateTime.now());
        device.setDnakeDeviceInfoId(deviceId);
        device.setVerison(version);
        device.setCardHand(cardHand);
        device.setCardHandSecond(cardHandSecond);
        device.setZoneId(zoneId);
        device.setZoneCode(zoneCode);
        deviceService.insert(deviceGroupId,device);

        return Result.success("添加成功");
    }

}

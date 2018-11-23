package com.mit.community.module.bigdata.controller;

import com.google.common.collect.Maps;
import com.mit.community.entity.AgeConstruction;
import com.mit.community.entity.Device;
import com.mit.community.service.AgeConstructionService;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.DeviceService;
import com.mit.community.service.HouseHoldService;
import com.mit.community.util.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 大数据平台
 *
 * @author shuyy
 * @date 2018/11/16
 * @company mitesofor
 */
@RestController
@RequestMapping("bigData")
public class BigDataController {

    private final ClusterCommunityService clusterCommunityService;

    private final DeviceService deviceService;

    private final HouseHoldService houseHoldService;

    private final AgeConstructionService ageConstructionService;

    @Autowired
    public BigDataController(ClusterCommunityService clusterCommunityService, DeviceService deviceService, HouseHoldService houseHoldService, AgeConstructionService ageConstructionService) {
        this.clusterCommunityService = clusterCommunityService;
        this.deviceService = deviceService;
        this.houseHoldService = houseHoldService;
        this.ageConstructionService = ageConstructionService;
    }

    /***
     * @param communityCode 小区code
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/22 16:40
     * @company mitesofor
    */
    @GetMapping("/countAlarmPercent")
    @ApiOperation(value = "告警按时完成率、设备正常率、环比", notes = "返回参数：alarmFinishOnTimePercent 告警按时完成率、" +
            "deviceHealthPercent 设备正常率、" +
            "alarmFinishOnTimeChain 告警按时完成环比、deviceHealthChain 设备正常环比")
    public Result countAlarmPercent(String communityCode) {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(4);
        if (StringUtils.isBlank(communityCode)) {
            List<String> communityNameList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭");
            List<Device> devices = deviceService.listByCommunityCodeList(communityNameList);
            List<Device> healthDevices = devices.parallelStream().filter(item -> item.getDeviceStatus() == 1).collect(Collectors.toList());
            map.put("alarmFinishOnTimePercent", 0.96);
            map.put("deviceHealthPercent", (float) healthDevices.size() / (float) devices.size());
            map.put("alarmFinishOnTimeChain", 0.34);
            map.put("deviceHealthChain", 0.2);
        } else {
            List<Device> devices = deviceService.listByCommunityCode(communityCode);
            List<Device> healthDevices = devices.parallelStream().filter(item -> item.getDeviceStatus() == 1).collect(Collectors.toList());
            map.put("alarmFinishOnTimePercent", 0.96);
            map.put("deviceHealthPercent", (float) healthDevices.size() / (float) devices.size());
            map.put("alarmFinishOnTimeChain", 0.34);
            map.put("deviceHealthChain", 0.2);
        }
        return Result.success(map);
    }

    /***
     * @param communityCode 小区code
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/22 16:40
     * @company mitesofor
    */
    @GetMapping("/countAgeConstruction")
    @ApiOperation(value = "人口结构分析", notes = "返回参数：childNum 小孩人数、" +
            "youngNum 少年人数、" +
            "youthNum 青年人数、middleNum 中年人数、oldNum 老年人数")
    public Result countAgeConstruction(String communityCode) {
        if (StringUtils.isBlank(communityCode)) {
            List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭");
            return Result.success(ageConstructionService.getByCommunityCodeList(communityCodeList));
        } else {
            AgeConstruction ageConstruction = ageConstructionService.getByCommunityCode(communityCode);
            return Result.success(ageConstruction);
        }
    }

}

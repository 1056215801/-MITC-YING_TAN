package com.mit.community.module.bigdata.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.AgeConstruction;
import com.mit.community.entity.Device;
import com.mit.community.entity.PopulationRush;
import com.mit.community.service.*;
import com.mit.community.util.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    private final AccessControlService accessControlService;

    private final PopulationRushService populationRushService;

    @Autowired
    public BigDataController(ClusterCommunityService clusterCommunityService, DeviceService deviceService, HouseHoldService houseHoldService, AgeConstructionService ageConstructionService, AccessControlService accessControlService, PopulationRushService populationRushService) {
        this.clusterCommunityService = clusterCommunityService;
        this.deviceService = deviceService;
        this.houseHoldService = houseHoldService;
        this.ageConstructionService = ageConstructionService;
        this.accessControlService = accessControlService;
        this.populationRushService = populationRushService;
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

    /***
     * @param communityCode 小区code
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/23 14:13
     * @company mitesofor
    */
    @GetMapping("/countPopulationDistributionByCommunityCode")
    @ApiOperation(value = "外来人口分布，按省统计", notes = "传参：communityCode 小区code，不传则查询鹰潭市所有小区。返回参数：province 省份、" +
            "num 人数")
    public Result countPopulationDistributionByCommunityCode(String communityCode) {
        if (StringUtils.isBlank(communityCode)) {
            List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭");
            return Result.success(houseHoldService.countPopulationDistributionByCommunityCodeList(communityCodeList));
        } else {
            return Result.success(houseHoldService.countPopulationDistributionByCommunityCode(communityCode));
        }
    }

    /***
     * @param communityCode 小区code
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/23 14:13
     * @company mitesofor
    */
    @GetMapping("/countPopulationDistributionByCommunityCodeAndProvince")
    @ApiOperation(value = "外来人口分布，按市统计", notes = "传参：communityCode 小区code，不传则查询鹰潭市所有小区。返回参数：city 市、" +
            "num 人数")
    public Result countPopulationDistributionByCommunityCodeAndProvince(String communityCode, String province) {
        if (StringUtils.isBlank(communityCode)) {
            List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭");
            return Result.success(houseHoldService.countPopulationDistributionByCommunityCodeListAndProvince(communityCodeList,
            		province));
        } else {
            return Result.success(houseHoldService.countPopulationDistributionByCommunityCodeAndProvince(communityCode, province));
        }
    }

    /***
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/23 14:49
     * @company mitesofor
    */
    @GetMapping("/listAccessControlPageByCommunitCode")
    @ApiOperation(value = "实时通行记录", notes = "传参：communityCode 小区code，不传则查询鹰潭市所有小区， pageNum 当前页，" +
            "required， pageSize 分页大小 required。" +
            "\n返回参数：householdName 姓名、device_name 卡口和进/出、roomNum 房号、access_time 访问时间" +
            "num 人数")
    public Result listAccessControlPageByCommunitCode(String communityCode, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(communityCode)) {
            List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭");
            return Result.success(accessControlService.listByCommunityCodeListPage(communityCodeList, pageNum, pageSize));
        } else {
            ArrayList<String> list = Lists.newArrayListWithCapacity(1);
            list.add(communityCode);
            return Result.success(accessControlService.listByCommunityCodeListPage(list, pageNum, pageSize));
        }
    }

    /**
     * @param communityCode
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/11/24 10:53
     * @company mitesofor
    */
    @GetMapping("/listPopulationRushByCommunityCode")
    @ApiOperation(value = "人流高峰分析", notes = "传参：communityCode 小区code，不传则查询鹰潭市所有小区" +
            "\n返回参数：monday 星期一、tuesday 星期二人数、wednesday 星期三人数、thursday 星期四人数" +
            "friday 星期五人数、 saturday 星期六人数、 sunday 星期天人数")
    public Result listPopulationRushByCommunityCode(String communityCode) {
        if (StringUtils.isBlank(communityCode)) {
            List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭");
            PopulationRush populationRush = populationRushService.listByCommunityCodeList(communityCodeList);
            return Result.success(populationRush);
        } else {
            PopulationRush populationRush = populationRushService.listByCommunityCode(communityCode);
            return Result.success(populationRush);        }
    }

    /**
     * @param communityCode
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/11/24 10:53
     * @company mitesofor
    */
    @GetMapping("/countInterActiveTypeByCommunityCode")
    @ApiOperation(value = "通行记录", notes = "传参：communityCode 小区code，不传则查询鹰潭市所有小区" +
            "\n返回参数：interactive_type # 开门方式（0：其他开门；1：刷卡开门；2：密码开门；3：APP开门；4：分机开门；5：二维码开门； 6：蓝牙开门；7：按钮开门；8：手机开门;9：人脸识别；10:固定密码；11：http开门；）、" +
            "num 数量")
    public Result countInterActiveTypeByCommunityCode(String communityCode) {
        if (StringUtils.isBlank(communityCode)) {
            List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭");
            List<Map<String, Object>> list = accessControlService.countInterActiveTypeByCommunityCodeList(communityCodeList);
            return Result.success(list);
        } else {
            List<Map<String, Object>> maps = accessControlService.countInterActiveTypeByCommunityCode(communityCode);
            return Result.success(maps);
        }
    }
    /**
     * @param communityCode
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/11/24 10:53
     * @company mitesofor
    */
    @GetMapping("/countIdentityTypeByCommunityCode")
    @ApiOperation(value = "人员分类", notes = "传参：communityCode 小区code，不传则查询鹰潭市所有小区" +
            "\n返回参数：identity_type  身份类型（身份类型：1、群众、2、境外人员、3、孤寡老人、4、信教人员、5、留守儿童、6、上访人员、99、其他）、" +
            "num 数量")
    public Result countIdentityTypeByCommunityCode(String communityCode) {
        if (StringUtils.isBlank(communityCode)) {
            List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭");
            List<Map<String, Object>> maps = houseHoldService.countIdentityTypeByCommunityCodeList(communityCodeList);
            return Result.success(maps);
        } else {
            List<Map<String, Object>> maps = houseHoldService.countIdentityTypeByCommunityCode(communityCode);
            return Result.success(maps);
        }
    }



}

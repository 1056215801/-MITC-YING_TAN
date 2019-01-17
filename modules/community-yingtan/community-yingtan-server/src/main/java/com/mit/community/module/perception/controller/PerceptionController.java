package com.mit.community.module.perception.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.AccessControl;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Device;
import com.mit.community.entity.RoomTypeConstruction;
import com.mit.community.service.*;
import com.mit.community.util.HttpUtil;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 智慧社区感知平台控制类
 *
 * @author Mr.Deng
 * @date 2018/11/16 9:02
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@RestController
@RequestMapping(value = "/perception")
@Slf4j
@Api(value = "智慧社区感知平台后台", tags = {"感知平台"})
public class PerceptionController {

    private final BuildingService buildingService;
    private final RoomService roomService;
    private final HouseHoldService houseHoldService;
    private final ClusterCommunityService clusterCommunityService;
    private final VisitorService visitorService;
    private final AccessControlService accessControlService;
    private final RoomTypeConstructionService roomTypeConstructionService;
    private final WarningService warningService;
    private final HouseholdRoomService householdRoomService;
    private final DeviceService deviceService;

    @Autowired
    public PerceptionController(BuildingService buildingService, RoomService roomService,
                                HouseHoldService houseHoldService, ClusterCommunityService clusterCommunityService,
                                VisitorService visitorService, AccessControlService accessControlService,
                                RoomTypeConstructionService roomTypeConstructionService, WarningService warningService, HouseholdRoomService householdRoomService, DeviceService deviceService) {
        this.buildingService = buildingService;
        this.roomService = roomService;
        this.houseHoldService = houseHoldService;
        this.clusterCommunityService = clusterCommunityService;
        this.visitorService = visitorService;
        this.accessControlService = accessControlService;
        this.roomTypeConstructionService = roomTypeConstructionService;
        this.warningService = warningService;
        this.householdRoomService = householdRoomService;
        this.deviceService = deviceService;
    }

    /**
     * 查询当前地区天气
     *
     * @param local 地区名拼音
     * @return Result
     * @author Mr.Deng
     * @date 16:06 2018/11/13
     */
    @GetMapping("/getWeatherInfo")
    @ApiOperation(value = "天气", notes = "获取当前地区天气 ;参数：local 地区名拼音 ")
    public Result getWeatherInfo(String local) {
        if (StringUtils.isBlank(local)) {
            return Result.error("地址不能为空！");
        } else {
            String url = "https://api.seniverse.com/v3/weather/now.json?" +
                    "key=adfeskm2upezsis0" +
                    "&location=" + local +
                    "&language=zh-Hans" +
                    "&unit=c";
            String s = HttpUtil.sendGet(url);
            JSONObject json = JSON.parseObject(s);
            return Result.success(json);
        }
    }

    /**
     * 查询所有小区code，通过城市名
     *
     * @param cityName 城市名
     * @return result
     * @author Mr.Deng
     * @date 11:56 2018/11/21
     */
    @GetMapping("/listByCityName")
    @ApiOperation(value = "获取所有小区Code", notes = "返回参数：community_code 小区code" +
            " community_name 小区名字 city_name 小区所在城市")
    public Result listByCityName(String cityName) {
        if (StringUtils.isNotBlank(cityName)) {
            List<ClusterCommunity> clusterCommunities = clusterCommunityService.listByCityName(cityName);
            return Result.success(clusterCommunities);
        } else {
            return Result.error("参数不能为空！");
        }
    }

    /**
     * 小区综合统计数据
     *
     * @return result
     * @author Mr.Deng
     * @date 9:10 2018/11/16
     */
    @GetMapping("/countCommunityStatistics")
    @ApiOperation(value = "小区综合统计数据(房屋信息统计,人员信息统计)",
            notes = "房屋信息统计：buildingSize 楼栋总数、roomSize 房屋总数、houseHoldSize 住户总数" +
                    "ParkingSpace 车位总数、buildingManager 栋长人数、\n" +
                    "人员信息统计：realTimeVisitor 已到访实时访客、attention 关爱/关注进出" +
                    "neighborhoodCommittee 居委干部、property 物业人员、CommunityPolice 社区民警")
    public Result countCommunityStatistics(String communityCode) {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        Integer buildingSize;
        Integer roomSize;
        Integer houseHoldSize;
        Integer visitorSize;
        List<Map<String, Object>> maps;
        int attention = 0;
        if (StringUtils.isNoneBlank(communityCode)) {
            buildingSize = buildingService.countByCommunityCode(communityCode);
            roomSize = roomService.countByCommunityCode(communityCode);
            houseHoldSize = houseHoldService.listByCommunityCode(communityCode).size();
            visitorSize = visitorService.countByCommunityCode(communityCode);
            maps = houseHoldService.countIdentityTypeByCommunityCode(communityCode);
            // 车位
            map.put("ParkingSpace", 460);
            // 社区民警
            map.put("CommunityPolice", 10);
            //居委干部
            map.put("neighborhoodCommittee", 2);
            // 楼长
            map.put("buildingManager", 10);
            // 物业
            map.put("property", 20);
        } else {
            List<String> communityCodeList = listCommunityCodes("鹰潭市");
            buildingSize = buildingService.countByCommunityCodes(communityCodeList);
            roomSize = roomService.countByCommunityCodes(communityCodeList);
            houseHoldSize = houseHoldService.listByCommunityCode(communityCode).size();
            visitorSize = visitorService.countByCommunityCodes(communityCodeList);
            maps = houseHoldService.countIdentityTypeByCommunityCodeList(communityCodeList);
            // 车位
            map.put("ParkingSpace", 460);
            // 社区民警
            map.put("CommunityPolice", 10);
            //居委干部
            map.put("neighborhoodCommittee", 2);
            // 楼长
            map.put("buildingManager", 10);
            // 物业
            map.put("property", 20);
        }
        if (!maps.isEmpty()) {
            for (Map<String, Object> map1 : maps) {
                String identityType = map1.get("identity_type").toString();
                if ("3".equals(identityType) || "4".equals(identityType) || "5".equals(identityType)) {
                    int num = Integer.parseInt(map1.get("num").toString());
                    attention += num;
                }
            }
        }
        //楼栋总数
        map.put("buildingSize", buildingSize);
        //房屋
        map.put("roomSize", roomSize);
        // 住户
        map.put("houseHoldSize", houseHoldSize);
        // 实时访客
        map.put("realTimeVisitor", visitorSize);
        // 关爱/关注进出
        map.put("attention", attention);
        return Result.success(map);
    }

    /**
     * 查询男女比例
     *
     * @return result
     * @author Mr.Deng
     * @date 16:18 2018/11/19
     */
    @GetMapping("/countSex")
    @ApiOperation(value = "男女比例", notes = "参数：communityCode 小区code")
    public Result countSex(String communityCode) {
        Map<String, Object> sex;
        if (StringUtils.isNoneBlank(communityCode)) {
            sex = houseHoldService.mapSexByCommunityCode(communityCode);
        } else {
            List<String> list = listCommunityCodes("鹰潭市");
            sex = houseHoldService.listSexByCommunityCodeList(list);
        }
        return Result.success(sex);
    }

    /**
     * 人口数据感知
     *
     * @return result
     * @author Mr.Deng
     * @date 17:36 2018/11/19
     */
    @GetMapping("/countPopulationDataPerception")
    @ApiOperation(value = "人口数据感知", notes = "返回参数：localPopulation 本地人口、foreignPopulation 外地人口、" +
            "overseasPopulation 境外人口、other 其他")
    public Result countPopulationDataPerception(String communityCode) {
        Map<String, Integer> map = Maps.newHashMapWithExpectedSize(4);
        Map<String, Integer> fieldLocalPeople = houseHoldService.getFieldLocalPeople(communityCode);
        //本地人口
        map.put("localPopulation", fieldLocalPeople.get("local"));
        //外地人口
        map.put("foreignPopulation", fieldLocalPeople.get("field"));
        //境外人口
        map.put("overseasPopulation", 0);
        //其他
        map.put("other", fieldLocalPeople.get("other"));
        return Result.success(map);
    }

    /**
     * 人口总数、驻留总数、总通行次数、预警总数
     *
     * @return result
     * @author Mr.Deng
     * @date 17:47 2018/11/19
     */
    @GetMapping("/countPerception")
    @ApiOperation(value = "人口总数、驻留总数、总通行次数、预警总数", notes = "返回参数：totalPopulation 人口总数、" +
            "totalResident 驻留总数、realTimeAccess 实时进出、totalEarlyWarning 预警总数")
    public Result countPerception(String communityCode) {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(4);
        Integer houseHoldSize;
        Integer accessControlSize;
        Integer totalEarlySize;
        long remainNum;
        if (StringUtils.isNoneBlank(communityCode)) {
            houseHoldSize = houseHoldService.countByCommunityCode(communityCode);
            //实时进出
            accessControlSize = accessControlService.countByCommunityCode(communityCode);
            // 驻留
            remainNum = accessControlService.countRemainPeopleByCommunityCode(communityCode);
            // 预警总数
            totalEarlySize = warningService.countByCommunityCode(communityCode);
        } else {
            List<String> list = listCommunityCodes("鹰潭市");
            houseHoldSize = houseHoldService.countByCommunityCodeList(list);
            accessControlSize = accessControlService.countByCommunityCodes(list);
            remainNum = accessControlService.countRemainPeopleByCommunityCodes(list);
            totalEarlySize = warningService.countByCommunityCodeList(list);
            map.put("totalEarlyWarning", 50);
        }
        remainNum = remainNum < 0 ? 0 : remainNum;
        houseHoldSize = houseHoldSize < 0 ? 0 : houseHoldSize;
        accessControlSize = accessControlSize < 0 ? 0 : accessControlSize;
        totalEarlySize = totalEarlySize < 0 ? 0 : totalEarlySize;
        map.put("totalResident", remainNum);
        // 人口
        map.put("totalPopulation", houseHoldSize);
        // 实时进出
        map.put("realTimeAccess", accessControlSize);
        map.put("totalEarlyWarning", totalEarlySize);
        return Result.success(map);
    }

    /**
     * 房屋数据感知
     *
     * @return result
     * @author Mr.Deng
     * @date 17:22 2018/11/19
     */
    @GetMapping("/countHousingDataPerception")
    @ApiOperation(value = "房屋数据感知", notes = "不传默认返回鹰潭所有小区房屋数据的总和。 \n" +
            "外来人口房屋数量 foreignPopulation;" +
            "外来人口其他房屋数量 foreignOther;" +
            "外来人口自住房屋数量 foreignSelf;" +
            "外来人口租赁房屋数量 foreignRent;" +
            "外来人口闲置房屋数量 foreignLeisure;  \n" +
            "本市人口房屋数量 innerPopulation;" +
            "本市人口其他房屋数量 innerOther;" +
            "本市人口自住房屋数量 innerSelf;" +
            "本市人口租赁房屋数量 innerRent;" +
            "本市人口闲置房屋数量 innerLeisure;")
    public Result countHousingDataPerception(String communityCode) {
        List<String> communityCodes;
        RoomTypeConstruction roomTypeConstruction;
        if (StringUtils.isNotBlank(communityCode)) {
            roomTypeConstruction = roomTypeConstructionService.getByCommunityCode(communityCode);
        } else {
            communityCodes = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
            Integer innerPopulation = 0;
            Integer foreignPopulation = 0;
            Integer foreignOther = 0;
            Integer foreignSelf = 0;
            Integer foreignRent = 0;
            Integer foreignLeisure = 0;
            Integer innerOther = 0;
            Integer innerSelf = 0;
            Integer innerRent = 0;
            Integer innerLeisure = 0;
            if (!communityCodes.isEmpty()) {
                for (String code : communityCodes) {
                    RoomTypeConstruction byCommunityCode = roomTypeConstructionService.getByCommunityCode(code);
                    innerPopulation += byCommunityCode.getInnerPopulation();
                    foreignPopulation += byCommunityCode.getForeignPopulation();
                    foreignOther += byCommunityCode.getForeignOther();
                    foreignSelf += byCommunityCode.getForeignSelf();
                    foreignRent += byCommunityCode.getForeignRent();
                    foreignLeisure += byCommunityCode.getForeignLeisure();
                    innerOther += byCommunityCode.getInnerOther();
                    innerSelf += byCommunityCode.getInnerSelf();
                    innerRent += byCommunityCode.getInnerRent();
                    innerLeisure += byCommunityCode.getInnerLeisure();
                }
            }
            roomTypeConstruction = new RoomTypeConstruction();
            roomTypeConstruction.setInnerPopulation(innerPopulation);
            roomTypeConstruction.setForeignPopulation(foreignPopulation);
            roomTypeConstruction.setForeignOther(foreignOther);
            roomTypeConstruction.setForeignSelf(foreignSelf);
            roomTypeConstruction.setForeignRent(foreignRent);
            roomTypeConstruction.setForeignLeisure(foreignLeisure);
            roomTypeConstruction.setInnerOther(innerOther);
            roomTypeConstruction.setInnerSelf(innerSelf);
            roomTypeConstruction.setInnerRent(innerRent);
            roomTypeConstruction.setInnerLeisure(innerLeisure);
        }

        return Result.success(roomTypeConstruction);
    }

    /**
     * 人员通行感知
     *
     * @return result
     * @author Mr.Deng
     * @date 9:00 2018/11/20
     */
    @GetMapping("/countPersonnelAccess")
    @ApiOperation(value = "人员通行感知", notes = "返回参数：numThisDistrict 本小区通行人数、sizeThisDistrict 本小区通行人次  \n" +
            "numStranger 陌生人通行人数、sizeStranger 陌生人通行人次  \n" +
            "numFocus 重点关注通行人数、sizeFocus 重点关注通信人次")
    public Result countPersonnelAccess(String communityCode) {
        List<Map<String, Integer>> list = Lists.newArrayListWithCapacity(2);
        Map<String, Integer> number = Maps.newHashMapWithExpectedSize(3);
        Map<String, Integer> size = Maps.newHashMapWithExpectedSize(3);
        number.put("numThisDistrict", accessControlService.getPassNumber(communityCode));
        size.put("sizeThisDistrict", accessControlService.getPassPersonTime(communityCode));
        number.put("numStranger", visitorService.getPassNumber(communityCode));
        size.put("sizeStranger", visitorService.getPassPersonTime(communityCode));
        if (StringUtils.isNotBlank(communityCode)) {
            number.put("numFocus", 3);
            size.put("sizeFocus", 3);
        } else {
            number.put("numFocus", 30);
            size.put("sizeFocus", 30);
        }
        list.add(number);
        list.add(size);
        return Result.success(list);
    }

    /**
     * 查询小区code，通过城市名
     *
     * @param cityName 城市名
     * @return 小区code列表
     * @author Mr.Deng
     * @date 14:07 2018/11/21
     */
    private List<String> listCommunityCodes(String cityName) {
        List<ClusterCommunity> clusterCommunities = clusterCommunityService.listByCityName(cityName);
        return clusterCommunities.parallelStream().map(ClusterCommunity::getCommunityCode).collect(Collectors.toList());
    }

    /**
     * @param deviceName 设备name
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-15 10:55
     * @company mitesofor
    */
    @GetMapping("/getCurrentAccess")
    @ApiOperation(value = "获取当前门禁", notes = "identityType:1、群众、2、境外人员、3、孤寡老人、4、信教人员、5、留守儿童、6、上访人员、99、其他")
    public Result getCurrentAccess(String deviceName) {
        Device device = deviceService.getByDevice(deviceName);
        if(device == null){
            return Result.error("错误");
        }
        AccessControl accessControl = accessControlService.getCurrentAccess(deviceName, device.getCommunityCode());
        Map<String, Object> map = Maps.newHashMap();
        map.put("device", device);
        map.put("access", accessControl);
        return Result.success(map);
    }


}

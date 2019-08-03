package com.mit.community.module.perception.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.service.*;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.HttpUtil;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    private final RedisService redisService;
    private SysUser user;
    @Autowired
    private WarningConfigService warningConfigService;
    @Autowired
    private PerceptionService perceptionService;
    @Autowired
    private PersonBaseInfoService personBaseInfoService;

    @Autowired
    public PerceptionController(BuildingService buildingService, RoomService roomService,
                                HouseHoldService houseHoldService, ClusterCommunityService clusterCommunityService,
                                VisitorService visitorService, AccessControlService accessControlService,
                                RoomTypeConstructionService roomTypeConstructionService, WarningService warningService,
                                HouseholdRoomService householdRoomService, DeviceService deviceService, RedisService redisService) {
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
        this.redisService = redisService;
    }

    private void SetSysUser(HttpServletRequest request) {
        String sessionId = CookieUtils.getSession(request);
        user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
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
    public Result countCommunityStatistics(HttpServletRequest request, String communityCode) {
        SetSysUser(request);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        Integer buildingSize = 0;
        Integer roomSize = 0;
        Integer houseHoldSize = 0;
        Integer visitorSize = 0;
        List<Map<String, Object>> maps = null;
        int attention = 0;
        if (StringUtils.isNoneBlank(communityCode)) {
            buildingSize = buildingService.countByCommunityCode(communityCode);
            roomSize = roomService.countByCommunityCode(communityCode);
            houseHoldSize = houseHoldService.listByCommunityCode(communityCode).size();
            visitorSize = visitorService.countByCommunityCode(communityCode);
            maps = houseHoldService.countIdentityTypeByCommunityCode(communityCode);
            // 车位
            map.put("ParkingSpace", 0);
            // 社区民警
            map.put("CommunityPolice", 0);
            //居委干部
            map.put("neighborhoodCommittee", 0);
            // 楼长
            map.put("buildingManager", 0);
            // 物业
            map.put("property", 0);
        } else {
            if (user == null) {
                return Result.error("请登录");
            }
            String areaName = user.getAreaName();
            if ("湾里区".equals(areaName)) {
                List<String> communityCodeList = listCommunityCodesByArea("湾里区");
                buildingSize = buildingService.countByCommunityCodes(communityCodeList);
                roomSize = roomService.countByCommunityCodes(communityCodeList);
                houseHoldSize = houseHoldService.listByCommunityCode(communityCode).size();
                visitorSize = visitorService.countByCommunityCodes(communityCodeList);
                maps = houseHoldService.countIdentityTypeByCommunityCodeList(communityCodeList);
                // 车位
                map.put("ParkingSpace", 0);
                // 社区民警
                map.put("CommunityPolice", 0);
                //居委干部
                map.put("neighborhoodCommittee", 0);
                // 楼长
                map.put("buildingManager", 0);
                // 物业
                map.put("property", 0);
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
    public Result countSex(HttpServletRequest request, String communityCode) {
        SetSysUser(request);
        Map<String, Object> sex = null;
        if (StringUtils.isNoneBlank(communityCode)) {
            sex = houseHoldService.mapSexByCommunityCode(communityCode);
        } else {
            if (user == null) {
                return Result.error("请登录");
            }
            String areaName = user.getAreaName();
            if ("湾里区".equals(areaName)) {
                List<String> list = listCommunityCodesByArea("湾里区");
                sex = houseHoldService.listSexByCommunityCodeList(list);
            } else {
                List<String> list = listCommunityCodes("鹰潭市");
                sex = houseHoldService.listSexByCommunityCodeList(list);
            }
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
    public Result countPopulationDataPerception(HttpServletRequest request, String communityCode) {
        SetSysUser(request);
        Map<String, Integer> map = Maps.newHashMapWithExpectedSize(4);
        if (user == null) {
            return Result.error("请登录");
        }
        String areaName = user.getAreaName();
        if ("湾里区".equals(areaName)) {
            Map<String, Integer> fieldLocalPeople = houseHoldService.getFieldLocalPeopleWithWanli(communityCode);
            //本地人口
            map.put("localPopulation", fieldLocalPeople.get("local"));
            //外地人口
            //map.put("foreignPopulation", fieldLocalPeople.get("field"));
            map.put("foreignPopulation", 0);
            //境外人口
            map.put("overseasPopulation", 0);
            //其他
            //map.put("other", fieldLocalPeople.get("other"));
            map.put("other", 0);
        } else {
            Map<String, Integer> fieldLocalPeople = houseHoldService.getFieldLocalPeople(communityCode);
            //本地人口
            map.put("localPopulation", fieldLocalPeople.get("local"));
            //外地人口
            map.put("foreignPopulation", fieldLocalPeople.get("field"));
            //境外人口
            map.put("overseasPopulation", 0);
            //其他
            map.put("other", fieldLocalPeople.get("other"));
        }
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
    public Result countPerception(HttpServletRequest request, String communityCode) {
        SetSysUser(request);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(4);
        Integer houseHoldSize = 0;
        Integer accessControlSize = 0;
        Integer totalEarlySize = 0;
        long remainNum = 0;
        if (StringUtils.isNoneBlank(communityCode)) {
            houseHoldSize = houseHoldService.countByCommunityCode(communityCode);
            //实时进出
            accessControlSize = accessControlService.countByCommunityCode(communityCode);
            // 驻留
            remainNum = accessControlService.countRemainPeopleByCommunityCode(communityCode);
            // 预警总数
            totalEarlySize = warningService.countByCommunityCode(communityCode);
        } else {
            if (user == null) {
                return Result.error("请登录");
            }
            String areaName = user.getAreaName();
            if ("湾里区".equals(areaName)) {
                List<String> list = listCommunityCodesByArea("湾里区");
                houseHoldSize = houseHoldService.countByCommunityCodeList(list);
                accessControlSize = accessControlService.countByCommunityCodes(list);
                remainNum = accessControlService.countRemainPeopleByCommunityCodes(list);
                totalEarlySize = warningService.countByCommunityCodeList(list);
            } else {
                List<String> list = listCommunityCodes("鹰潭市");
                houseHoldSize = houseHoldService.countByCommunityCodeList(list);
                accessControlSize = accessControlService.countByCommunityCodes(list);
                remainNum = accessControlService.countRemainPeopleByCommunityCodes(list);
                totalEarlySize = warningService.countByCommunityCodeList(list);
                map.put("totalEarlyWarning", 50);
            }
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
    public Result countHousingDataPerception(HttpServletRequest request, String communityCode) {
        SetSysUser(request);
        List<String> communityCodes;
        RoomTypeConstruction roomTypeConstruction;
        if (StringUtils.isNotBlank(communityCode)) {
            roomTypeConstruction = roomTypeConstructionService.getByCommunityCode(communityCode);
        } else {
            if (user == null) {
                return Result.error("请登录");
            }
            String areaName = user.getAreaName();
            if ("湾里区".equals(areaName)) {
                communityCodes = clusterCommunityService.listCommunityCodeListByAreaName("湾里区");
            } else {
                communityCodes = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
            }
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
                    if (byCommunityCode != null) {
                        innerPopulation += byCommunityCode.getInnerPopulation() == null ? 0 : byCommunityCode.getInnerPopulation();
                        foreignPopulation += byCommunityCode.getForeignPopulation() == null ? 0 : byCommunityCode.getForeignPopulation();
                        foreignOther += byCommunityCode.getForeignOther() == null ? 0 : byCommunityCode.getForeignOther();
                        foreignSelf += byCommunityCode.getForeignSelf() == null ? 0 : byCommunityCode.getForeignSelf();
                        foreignRent += byCommunityCode.getForeignRent() == null ? 0 : byCommunityCode.getForeignRent();
                        foreignLeisure += byCommunityCode.getForeignLeisure() == null ? 0 : byCommunityCode.getForeignLeisure();
                        innerOther += byCommunityCode.getInnerOther() == null ? 0 : byCommunityCode.getInnerOther();
                        innerSelf += byCommunityCode.getInnerSelf() == null ? 0 : byCommunityCode.getInnerSelf();
                        innerRent += byCommunityCode.getInnerRent() == null ? 0 : byCommunityCode.getInnerRent();
                        innerLeisure += byCommunityCode.getInnerLeisure() == null ? 0 : byCommunityCode.getInnerLeisure();
                    }
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
        if (StringUtils.isNotBlank(communityCode)) {
            number.put("numThisDistrict", accessControlService.getPassNumber(communityCode));
            size.put("sizeThisDistrict", accessControlService.getPassPersonTime(communityCode));
            number.put("numStranger", visitorService.getPassNumber(communityCode));
            size.put("sizeStranger", visitorService.getPassPersonTime(communityCode));
        }else {
            number.put("numThisDistrict", 0);
            size.put("sizeThisDistrict", 0);
            number.put("numStranger", 0);
            size.put("sizeStranger", 0);
        }

        if (StringUtils.isNotBlank(communityCode)) {
            number.put("numFocus", 0);
            size.put("sizeFocus", 0);
        } else {
            number.put("numFocus", 0);
            size.put("sizeFocus", 0);
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
     * @Author HuShanLin
     * @Date 15:24 2019/6/27
     * @Description:~根据地区名称查询小区code
     */
    private List<String> listCommunityCodesByArea(String areaName) {
        List<ClusterCommunity> clusterCommunities = clusterCommunityService.listByAreaName(areaName);
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
        System.out.println("===="+deviceName);
        Device device = deviceService.getByDevice(deviceName);
        if (device == null) {
            return Result.error("错误");
        }
        AccessControl accessControl = accessControlService.getCurrentAccess(deviceName, device.getCommunityCode());
        WarningInfo warningInfo = null;
        if(accessControl != null){
            if(StringUtils.isNotBlank(accessControl.getHouseholdMobile())){
                warningInfo = new WarningInfo();
                System.out.println("===="+accessControl.getHouseholdMobile());
                HouseHold houseHold = houseHoldService.getByCellPhone(accessControl.getHouseholdMobile());
                if (houseHold != null) {
                    if (true) {
                        String[] labels = personBaseInfoService.getLabelsByMobile(houseHold.getMobile(), houseHold.getCommunityCode()).split(",");
                        System.out.println("=============="+labels[0]);
                        System.out.println("=============="+houseHold.getHouseholdName());
                        for (int i=0; i<labels.length ; i++) {
                            WarningConfig warningConfig = warningConfigService.getByLabelAndCommunity(labels[i],houseHold.getCommunityCode());
                            if (warningConfig != null) {
                                warningInfo.setIsWarning(warningConfig.getIsWarning());
                                warningInfo.setLabels(labels[i]);
                                warningInfo.setWarningInfo(warningConfig.getWarningInfo());
                                break;
                            }
                        }
                    }
                }
            }
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("device", device);
        map.put("access", accessControl);
        map.put("warningInfo", warningInfo);
        return Result.success(map);
    }

    @PostMapping("/carPerception")
    @ApiOperation(value = "车辆感知", notes = "int type (1：当日，2：本月)")
    public Result carPerception(HttpServletRequest request, String communityCode, int type) {
        SetSysUser(request);
        List<String> communityCodes;
        if (user == null) {
            return Result.error("请登录");
        }
        CarPerception carPerception = new CarPerception();
        if (StringUtils.isNotBlank(communityCode)) {
            communityCodes = new ArrayList<>();
            communityCodes.add(communityCode);
            carPerception = perceptionService.getCarPerception(type, communityCodes);
        } else {
            String areaName = user.getAreaName();
            if ("湾里区".equals(areaName)) {
                communityCodes = clusterCommunityService.listCommunityCodeListByAreaName("湾里区");
                carPerception = perceptionService.getCarPerception(type, communityCodes);
            }

        }
        return Result.success(carPerception);
    }

    @PostMapping("/warnPerception")
    @ApiOperation(value = "报警感知", notes = "int type (1：7天，2：30天)")
    public Result warnPerception(HttpServletRequest request, String communityCode, int type) {
        SetSysUser(request);
        List<String> communityCodes;
        if (user == null) {
            return Result.error("请登录");
        }
        WarnPerception warnPerception = new WarnPerception();
        if (StringUtils.isNotBlank(communityCode)) {
            communityCodes = new ArrayList<>();
            communityCodes.add(communityCode);
            warnPerception = perceptionService.getWarnPerception(type, communityCodes);
        } else {
            String areaName = user.getAreaName();
            if ("湾里区".equals(areaName)) {
                communityCodes = clusterCommunityService.listCommunityCodeListByAreaName("湾里区");
                warnPerception = perceptionService.getWarnPerception(type, communityCodes);
            }

        }
        return Result.success(warnPerception);
    }

    @PostMapping("/devicePerception")
    @ApiOperation(value = "设备感知", notes = "")
    public Result devicePerception(HttpServletRequest request, String communityCode) {
        SetSysUser(request);
        List<String> communityCodes;
        if (user == null) {
            return Result.error("请登录");
        }
        DevicePerception devicePerception = new DevicePerception();
        /*if (StringUtils.isNotBlank(communityCode)) {
            communityCodes = new ArrayList<>();
            communityCodes.add(communityCode);
            devicePerception = perceptionService.getDevicePerception(communityCodes);
        } else {
            String areaName = user.getAreaName();
            if ("湾里区".equals(areaName)) {
                communityCodes = clusterCommunityService.listCommunityCodeListByAreaName("湾里区");
                devicePerception = perceptionService.getDevicePerception(communityCodes);
            }

        }*/
        Map<String,String> yg = new HashedMap();
        Map<String,String> dc = new HashedMap();
        Map<String,String> jg = new HashedMap();
        Map<String,String> sxj = new HashedMap();
        if ("a5f53a2248794c678766edad485392ff".equals(communityCode)){//南标
            yg.put("lx","0");
            yg.put("zc","2");
            yg.put("yc","0");
            yg.put("gj","0");
            devicePerception.setYg(yg);

            dc.put("lx","0");
            dc.put("zc","1");
            dc.put("yc","0");
            dc.put("gj","0");
            devicePerception.setDc(dc);

            jg.put("lx","0");
            jg.put("zc","1");
            jg.put("yc","0");
            jg.put("gj","0");
            devicePerception.setJg(jg);

            sxj.put("lx","0");
            sxj.put("zc","7");
            sxj.put("yc","0");
            sxj.put("gj","0");
            devicePerception.setSxj(sxj);
        } else if ("b181746d9bd1444c80522f9923c59b80".equals(communityCode)) {//利雅轩
            yg.put("lx","0");
            yg.put("zc","2");
            yg.put("yc","0");
            yg.put("gj","0");
            devicePerception.setYg(yg);

            dc.put("lx","0");
            dc.put("zc","1");
            dc.put("yc","0");
            dc.put("gj","0");
            devicePerception.setDc(dc);

            jg.put("lx","0");
            jg.put("zc","1");
            jg.put("yc","0");
            jg.put("gj","0");
            devicePerception.setJg(jg);

            sxj.put("lx","0");
            sxj.put("zc","17");
            sxj.put("yc","0");
            sxj.put("gj","0");
            devicePerception.setSxj(sxj);
        } else {
            yg.put("lx","0");
            yg.put("zc","4");
            yg.put("yc","0");
            yg.put("gj","0");
            devicePerception.setYg(yg);

            dc.put("lx","0");
            dc.put("zc","2");
            dc.put("yc","0");
            dc.put("gj","0");
            devicePerception.setDc(dc);

            jg.put("lx","0");
            jg.put("zc","2");
            jg.put("yc","0");
            jg.put("gj","0");
            devicePerception.setJg(jg);

            sxj.put("lx","0");
            sxj.put("zc","24");
            sxj.put("yc","0");
            sxj.put("gj","0");
            devicePerception.setSxj(sxj);
        }


        return Result.success(devicePerception);
    }


    @GetMapping("/countCommunityStatisticsChange")
    @ApiOperation(value = "顶栏各项数据统计",
            notes = "房屋信息统计：buildingSize 楼栋总数、roomSize 房屋总数、houseHoldSize 住户总数" +
                    "ParkingSpace 车位总数、buildingManager 栋长人数、\n" +
                    "人员信息统计：realTimeVisitor 已到访实时访客、attention 关爱/关注进出" +
                    "neighborhoodCommittee 居委干部、property 物业人员、CommunityPolice 社区民警")
    public Result countCommunityStatisticsChange(HttpServletRequest request, String communityCode) {
        SetSysUser(request);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        Integer buildingSize = 0;
        Integer roomSize = 0;
        Integer houseHoldSize = 0;
        Integer visitorSize = 0;
        List<Map<String, Object>> maps = null;
        int attention = 0;
        List<String> communityCodeList = new ArrayList<>();
        if (StringUtils.isNoneBlank(communityCode)) {
            communityCodeList.add(communityCode);
            buildingSize = buildingService.countByCommunityCode(communityCode);
            roomSize = roomService.countByCommunityCode(communityCode);
            houseHoldSize = houseHoldService.listByCommunityCode(communityCodeList).size()-1;
            visitorSize = visitorService.countByCommunityCode(communityCode);
            maps = houseHoldService.countIdentityTypeByCommunityCode(communityCode);
        } else {
            if (user == null) {
                return Result.error("请登录");
            }
            String areaName = user.getAreaName();
            if ("湾里区".equals(areaName)) {
                //communityCodeList = listCommunityCodesByArea("湾里区");
                communityCodeList.add("a5f53a2248794c678766edad485392ff");
                communityCodeList.add("b181746d9bd1444c80522f9923c59b80");
                buildingSize = buildingService.countByCommunityCodes(communityCodeList);
                roomSize = roomService.countByCommunityCodes(communityCodeList);
                houseHoldSize = houseHoldService.listByCommunityCode(communityCodeList).size() - 2;
                visitorSize = visitorService.countByCommunityCodes(communityCodeList);
                maps = houseHoldService.countIdentityTypeByCommunityCodeList(communityCodeList);
            } else {
                communityCodeList = listCommunityCodes("鹰潭市");
                buildingSize = buildingService.countByCommunityCodes(communityCodeList);
                roomSize = roomService.countByCommunityCodes(communityCodeList);
                houseHoldSize = houseHoldService.listByCommunityCode(communityCodeList).size();
                visitorSize = visitorService.countByCommunityCodes(communityCodeList);
                maps = houseHoldService.countIdentityTypeByCommunityCodeList(communityCodeList);
                // 车位
                map.put("ParkingSpace", 0);
                // 社区民警
                map.put("CommunityPolice", 0);
                //居委干部
                map.put("neighborhoodCommittee", 0);
                // 楼长
                map.put("buildingManager", 0);
                // 物业
                map.put("property", 0);
            }
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

        // 关爱/关注进出
        map.put("attention", attention);
        if ("a5f53a2248794c678766edad485392ff".equals(communityCode)){//南标
            map.put("houseHoldSize", 77);
            //车位
            map.put("ParkingSpace", 24);
            // 社区民警
            map.put("CommunityPolice", 1);
            //居委干部
            map.put("neighborhoodCommittee", 1);
            // 楼长
            map.put("buildingManager", 2);
            // 物业
            map.put("property", 0);
            map.put("sxj",7);
            map.put("mj",2);
            map.put("jg",1);
            map.put("jjan",1);
            map.put("yg",2);
            map.put("dc",1);
            map.put("dz",0);
            map.put("deviceCount",14);
        } else if ("b181746d9bd1444c80522f9923c59b80".equals(communityCode)) {//利雅轩
            map.put("houseHoldSize", 52);
            map.put("ParkingSpace", 33);
            // 社区民警
            map.put("CommunityPolice", 1);
            //居委干部
            map.put("neighborhoodCommittee", 1);
            // 楼长
            map.put("buildingManager", 6);
            // 物业
            map.put("property", 5);
            map.put("sxj",17);
            map.put("mj",2);
            map.put("jg",1);
            map.put("jjan",2);
            map.put("yg",2);
            map.put("dc",1);
            map.put("dz",0);
            map.put("deviceCount",25);
        } else {
            map.put("houseHoldSize", 129);
            //车位
            map.put("ParkingSpace", 57);
            // 社区民警
            map.put("CommunityPolice", 2);
            //居委干部
            map.put("neighborhoodCommittee", 2);
            // 楼长
            map.put("buildingManager", 8);
            // 物业
            map.put("property", 5);
            map.put("sxj",24);
            map.put("mj",4);
            map.put("jg",2);
            map.put("jjan",3);
            map.put("yg",4);
            map.put("dc",2);
            map.put("dz",0);
            map.put("deviceCount",49);
        }

        /*int sxj = 0;
        //int sxj = perceptionService.getSxjCount(communityCodeList);//摄像机
        map.put("sxj",0);
        int mj = perceptionService.getMjCount(communityCodeList);//门禁
        map.put("mj",mj);
        int jg = perceptionService.getJgCount(communityCodeList);//井盖
        map.put("jg",jg);
        int jjan = 0;//紧急按钮
        map.put("jjan",jjan);
        int yg = perceptionService.getYgCount(communityCodeList);//烟感smoke_detector
        map.put("yg",yg);
        int dc = 0;//地磁
        map.put("dc",dc);
        int dz = 0;//道闸
        map.put("dz",dz);
        int deviceCount = sxj + mj + jg+ jjan + yg + dc + dz;
        map.put("deviceCount",deviceCount);*/
        int population = perceptionService.getPerceptionService(communityCodeList);//人口总数
        map.put("population",population);
        map.put("earlyWarning",0);//预警

        return Result.success(map);
    }


    @PostMapping("/warnInfo")
    @ApiOperation(value = "报警信息", notes = "")
    public Result warnInfo(HttpServletRequest request, String communityCode){
        SetSysUser(request);
        List<String> communityCodeList = new ArrayList<>();
        if(StringUtils.isNotBlank(communityCode)&&!"".equals(communityCode)){
            communityCodeList.add(communityCode);
        } else {
            communityCodeList.add("a5f53a2248794c678766edad485392ff");
            communityCodeList.add("b181746d9bd1444c80522f9923c59b80");
        }

        WarnInfo info = perceptionService.getWarnInfoByType(communityCodeList);
        if (info != null) {
            if ("紧急按钮报警".equals(info.getProblem())) {
                info.setWarnInfo(info.getPlace()+"-"+info.getName()+"("+info.getCyrPhone()+")");
                info.setPlace("监护人:"+info.getJhrPhone());
            }
        }

        return Result.success(info);
    }


}

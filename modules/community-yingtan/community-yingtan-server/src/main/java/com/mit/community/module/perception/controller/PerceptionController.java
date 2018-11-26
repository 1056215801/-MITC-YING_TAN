package com.mit.community.module.perception.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

    @Autowired
    public PerceptionController(BuildingService buildingService, RoomService roomService,
                                HouseHoldService houseHoldService, ClusterCommunityService clusterCommunityService,
                                VisitorService visitorService, AccessControlService accessControlService) {
        this.buildingService = buildingService;
        this.roomService = roomService;
        this.houseHoldService = houseHoldService;
        this.clusterCommunityService = clusterCommunityService;
        this.visitorService = visitorService;
        this.accessControlService = accessControlService;
    }

    /**
     * 获取当前地区天气
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
            return Result.success(json, "OK");
        }
    }

    /**
     * 通过城市名获取所有小区code
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
            List<Map<String, Object>> list = clusterCommunityService.listByCityName(cityName);
            return Result.success(list, "ok");
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
    @ApiOperation(value = "小区综合统计数据", notes = "返回参数：buildingSize 楼栋总数、roomSize 房屋总数、" +
            "ParkingSpace 车位总数、CommunityPolice 社区民警、neighborhoodCommittee 居委干部、buildingManager 楼长、" +
            "property 物业、realTimeVisitor 实时访客、attention 关爱/关注进出")
    public Result countCommunityStatistics(String communityCode) {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        int buildingSize;
        int roomSize;
        int houseHoldSize;
        int visitorSize;
        if (StringUtils.isNoneBlank(communityCode)) {
            buildingSize = buildingService.listByCommunityCode(communityCode).size();
            roomSize = roomService.listByCommunityCode(communityCode).size();
            houseHoldSize = houseHoldService.listByCommunityCode(communityCode).size();
            visitorSize = visitorService.listByCommunityCode(communityCode).size();
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
            // 关爱/关注进出
            map.put("attention", 320);
        } else {
            List<String> list = listCommunityCodes("鹰潭市");
            buildingSize = buildingService.listByCommunityCodes(list).size();
            roomSize = roomService.listByCommunityCodes(list).size();
            houseHoldSize = houseHoldService.listByCommunityCodes(list).size();
            visitorSize = visitorService.listByCommunityCodes(list).size();
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
            // 关爱/关注进出
            map.put("attention", 320);
        }
        //楼栋总数
        map.put("buildingSize", buildingSize);
        //房屋
        map.put("roomSize", roomSize);
        // 住户
        map.put("houseHoldSize", houseHoldSize);
        // 实时访客
        map.put("realTimeVisitor", visitorSize);
        return Result.success(map, "OK");
    }

    /**
     * 获取男女比例
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
            sex = houseHoldService.listSexByCommunityCode(communityCode);
        } else {
            List<String> list = listCommunityCodes("鹰潭市");
            sex = houseHoldService.listSexByCommunityCodes(list);
        }
        return Result.success(sex, "ok");
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
        if (StringUtils.isNoneBlank(communityCode)) {
            //本地人口
            map.put("localPopulation", 1847);
            //外地人口
            map.put("foreignPopulation", 423);
            //境外人口
            map.put("overseasPopulation", 0);
            //其他
            map.put("other", 32);
        } else {
            //本地人口
            map.put("localPopulation", 18470);
            //外地人口
            map.put("foreignPopulation", 4230);
            //境外人口
            map.put("overseasPopulation", 10);
            //其他
            map.put("other", 320);
        }
        return Result.success(map, "OK");
    }

    /**
     * 统计数据
     *
     * @return result
     * @author Mr.Deng
     * @date 17:47 2018/11/19
     */
    @GetMapping("/countPerception")
    @ApiOperation(value = "页头数据统计", notes = "返回参数：totalPopulation 人口总数、totalResident 驻留总数、" +
            "realTimeAccess 实时进出、totalEarlyWarning 预警总数")
    public Result countPerception(String communityCode) {
        Map<String, Integer> map = Maps.newHashMapWithExpectedSize(4);
        int houseHoldSize;
        int accessControlSize;
        if (StringUtils.isNoneBlank(communityCode)) {
            houseHoldSize = houseHoldService.listByCommunityCode(communityCode).size();
            accessControlSize = accessControlService.listByCommunityCode(communityCode).size();
            // 驻留
            map.put("totalResident", 2);
            // 预警总数
            map.put("totalEarlyWarning", 4);
        } else {
            List<String> list = listCommunityCodes("鹰潭市");
            houseHoldSize = houseHoldService.listByCommunityCodes(list).size();
            accessControlSize = accessControlService.listByCommunityCodes(list).size();
            map.put("totalResident", 30);
            map.put("totalEarlyWarning", 50);
        }
        // 人口
        map.put("totalPopulation", houseHoldSize);
        // 实时进出
        map.put("realTimeAccess", accessControlSize);

        return Result.success(map, "OK");
    }

    /**
     * 房屋数据感知
     *
     * @return result
     * @author Mr.Deng
     * @date 17:22 2018/11/19
     */
    @GetMapping("/countHousingDataPerception")
    @ApiOperation(value = "房屋数据感知", notes = "房屋数据感知-上面为本市数据，下面的数据为外来数据")
    public Result countHousingDataPerception(String communityCode) {
        List<Map<String, Object>> list = Lists.newArrayListWithCapacity(2);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(3);
        Integer[] t;
        Integer[] ts;
        if (StringUtils.isNotBlank(communityCode)) {
            t = new Integer[]{620, 184, 49};
            ts = new Integer[]{139, 74, 23};
        } else {
            t = new Integer[]{621, 300, 149};
            ts = new Integer[]{340, 120, 130};
        }
        map.put("date", t);
        map1.put("date", ts);
        list.add(map);
        list.add(map1);
        return Result.success(list, "OK");
    }

    /**
     * 人员通行感知
     *
     * @return result
     * @author Mr.Deng
     * @date 9:00 2018/11/20
     */
    @GetMapping("/countPersonnelAccess")
    @ApiOperation(value = "人员通行感知", notes = "返回参数：numThisDistrict 本小区通行人数、sizeThisDistrict 本小区通行人次、" +
            "numStranger 陌生人通行人数、sizeStranger 陌生人通行人次、numFocus 重点关注通行人数、sizeFocus 重点关注通信人次")
    public Result countPersonnelAccess(String communityCode) {
        List<Map<String, Integer>> list = Lists.newArrayListWithCapacity(2);
        Map<String, Integer> number = Maps.newHashMapWithExpectedSize(3);
        Map<String, Integer> size = Maps.newHashMapWithExpectedSize(3);
        if (StringUtils.isNotBlank(communityCode)) {
            number.put("numThisDistrict", 1);
            number.put("numStranger", 2);
            number.put("numFocus", 3);
            size.put("sizeThisDistrict", 1);
            size.put("sizeStranger", 2);
            size.put("sizeFocus", 3);
        } else {
            number.put("numThisDistrict", 10);
            number.put("numStranger", 20);
            number.put("numFocus", 30);
            size.put("sizeThisDistrict", 10);
            size.put("sizeStranger", 20);
            size.put("sizeFocus", 30);
        }
        list.add(number);
        list.add(size);
        return Result.success(list, "OK");
    }

    /**
     * 获取某个城市的所有小区code
     *
     * @param cityName 城市名
     * @return 小区code列表
     * @author Mr.Deng
     * @date 14:07 2018/11/21
     */
    private List<String> listCommunityCodes(String cityName) {
        List<Map<String, Object>> list = clusterCommunityService.listByCityName(cityName);
        List<String> communityCodes = Lists.newArrayListWithCapacity(list.size());
        for (Map<String, Object> maps : list) {
            communityCodes.add(maps.get("community_code").toString());
        }
        return communityCodes;
    }

}

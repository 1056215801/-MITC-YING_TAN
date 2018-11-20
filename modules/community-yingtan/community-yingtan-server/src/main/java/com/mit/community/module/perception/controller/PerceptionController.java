package com.mit.community.module.perception.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.service.BuildingService;
import com.mit.community.service.HouseHoldService;
import com.mit.community.service.RoomService;
import com.mit.community.util.HttpUtil;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    @Autowired
    public PerceptionController(BuildingService buildingService, RoomService roomService,
                                HouseHoldService houseHoldService) {
        this.buildingService = buildingService;
        this.roomService = roomService;
        this.houseHoldService = houseHoldService;
    }

    /**
     * 获取当前地区天气
     *
     * @param local 地区名拼音
     * @return Result
     * @author Mr.Deng
     * @date 16:06 2018/11/13
     */
    @RequestMapping(value = "/getWeatherInfo", method = RequestMethod.GET)
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
     * 小区综合统计数据
     *
     * @return result
     * @author Mr.Deng
     * @date 9:10 2018/11/16
     */
    @RequestMapping(value = "/countCommunityStatisticsOne", method = RequestMethod.GET)
    @ApiOperation(value = "小区综合统计数据->左", notes = "返回参数：buildingSize 楼栋总数、roomSize 房屋总数、" +
            "ParkingSpace 车位总数、CommunityPolice 社区民警")
    public Result countCommunityStatisticsOne() {
        Map<String, Object> map = new HashMap<>(8);
        int buildingSize = buildingService.list().size();
        int roomSize = roomService.list().size();
        int houseHoldSize = houseHoldService.list().size();
        //楼栋总数
        map.put("buildingSize", buildingSize);
        //房屋
        map.put("roomSize", roomSize);
        // 住户
        map.put("houseHoldSize", houseHoldSize);
        // 车位
        map.put("ParkingSpace", 460);
        // 社区民警
        map.put("CommunityPolice", 10);
        return Result.success(map, "OK");
    }

    /**
     * 小区综合统计数据
     *
     * @return result
     * @author Mr.Deng
     * @date 9:10 2018/11/16
     */
    @RequestMapping(value = "/countCommunityStatisticsTwo", method = RequestMethod.GET)
    @ApiOperation(value = "小区综合统计数据->右", notes = "返回参数：neighborhoodCommittee 居委干部、buildingManager 楼长、" +
            "property 物业、realTimeVisitor 实时访客、attention 关爱/关注进出")
    public Result countCommunityStatisticsTwo() {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(8);
        //居委干部
        map.put("neighborhoodCommittee", 2);
        // 楼长
        map.put("buildingManager", 10);
        // 物业
        map.put("property", 20);
        // 实时访客
        map.put("realTimeVisitor", 450);
        // 关爱/关注进出
        map.put("attention", 320);
        return Result.success(map, "Ok");
    }

    /**
     * 获取男女比例
     *
     * @return
     * @author Mr.Deng
     * @date 16:18 2018/11/19
     */
    @RequestMapping(value = "/countSex", method = RequestMethod.GET)
    @ApiOperation(value = "男女比例", notes = "参数：communityCode 小区code")
    public Result countSex(String communityCode) {
        if (StringUtils.isNoneBlank(communityCode)) {
            Map<String, Object> sex = houseHoldService.getSex(communityCode);
            return Result.success(sex, "ok");
        } else {
            return Result.error("参数不能为空！");
        }
    }

    /**
     * 人口数据感知
     *
     * @return result
     * @author Mr.Deng
     * @date 17:36 2018/11/19
     */
    @RequestMapping(value = "/countPopulationDataPerception", method = RequestMethod.GET)
    @ApiOperation(value = "人口数据感知", notes = "返回参数：localPopulation 本地人口、foreignPopulation 外地人口、" +
            "overseasPopulation 境外人口、other 其他")
    public Result countPopulationDataPerception() {
        Map<String, Integer> map = Maps.newHashMapWithExpectedSize(4);
        //本地人口
        map.put("localPopulation", 1847);
        //外地人口
        map.put("foreignPopulation", 423);
        //境外人口
        map.put("overseasPopulation", 0);
        //其他
        map.put("other", 32);
        return Result.success(map, "OK");
    }

    /**
     * 统计数据
     *
     * @return result
     * @author Mr.Deng
     * @date 17:47 2018/11/19
     */
    @RequestMapping(value = "/countPerception", method = RequestMethod.GET)
    @ApiOperation(value = "页头数据统计", notes = "返回参数：totalPopulation 人口总数、totalResident 驻留总数、" +
            "realTimeAccess 实时进出、totalEarlyWarning 预警总数")
    public Result countPerception() {
        Map<String, Integer> map = Maps.newHashMapWithExpectedSize(4);
        // 人口
        map.put("totalPopulation", 1);
        // 驻留
        map.put("totalResident", 2);
        // 实时进出
        map.put("realTimeAccess", 3);
        // 预警总数
        map.put("totalEarlyWarning", 4);
        return Result.success(map, "OK");
    }

    /**
     * 房屋数据感知
     *
     * @return result
     * @author Mr.Deng
     * @date 17:22 2018/11/19
     */
    @RequestMapping(value = "/countHousingDataPerception", method = RequestMethod.GET)
    @ApiOperation(value = "房屋数据感知", notes = "房屋数据感知-上面为本市数据，下面的数据为外来数据")
    public Result countHousingDataPerception() {
        List<Map<String, Object>> list = Lists.newArrayListWithCapacity(2);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        Map<String, Object> map1 = Maps.newHashMapWithExpectedSize(3);
        Integer[] t = {620, 184, 49};
        Integer[] ts = {139, 74, 23};
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
    @RequestMapping(value = "/countPersonnelAccess", method = RequestMethod.GET)
    @ApiOperation(value = "人员通行感知", notes = "返回参数：numThisDistrict 本小区通行人数、sizeThisDistrict 本小区通行人次、" +
            "numStranger 陌生人通行人数、sizeStranger 陌生人通行人次、numFocus 重点关注通行人数、sizeFocus 重点关注通信人次")
    public Result countPersonnelAccess() {
        List<Map<String, Integer>> list = Lists.newArrayListWithCapacity(2);
        Map<String, Integer> number = Maps.newHashMapWithExpectedSize(3);
        Map<String, Integer> size = Maps.newHashMapWithExpectedSize(3);
        number.put("numThisDistrict", 1);
        number.put("numStranger", 2);
        number.put("numFocus", 3);
        size.put("sizeThisDistrict", 1);
        size.put("sizeStranger", 2);
        size.put("sizeFocus", 3);
        list.add(number);
        list.add(size);
        return Result.success(list, "OK");
    }

}

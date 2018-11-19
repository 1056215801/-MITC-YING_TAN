package com.mit.community.module.perception.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mit.community.service.*;
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
    @RequestMapping(value = "/getCommunityStatistics", method = RequestMethod.GET)
    @ApiOperation(value = "小区综合统计数据->左", notes = "返回参数：buildingSize 楼栋总数、roomSize 房屋总数、" +
            "ParkingSpace 车位总数、CommunityPolice 社区民警")
    public Result getCommunityStatistics() {
        Map<String, Object> map = new HashMap<>(8);
        int buildingSize = buildingService.getBuildingList().size();
        int roomSize = roomService.getRoomList().size();
        int houseHoldSize = houseHoldService.getHouseList().size();
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

}

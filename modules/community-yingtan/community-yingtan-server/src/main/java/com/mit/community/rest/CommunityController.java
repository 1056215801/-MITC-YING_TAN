package com.mit.community.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mit.community.service.*;
import com.mit.community.util.HttpUtil;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = "/community")
@Slf4j
@Api(value = "智慧社区感知平台后台", tags = {"感知平台"})
public class CommunityController {

    private final ClusterCommunityService clusterCommunityService;
    private final ZoneService zoneService;
    private final BuildingService buildingService;
    private final UnitService unitService;
    private final RoomService roomService;
    private final HouseHoldService houseHoldService;
    private final VisitorService visitorService;
    private final DeviceService deviceService;
    private final AccessControlService accessControlService;
    private final DeviceCallService deviceCallService;

    @Autowired
    public CommunityController(ClusterCommunityService clusterCommunityService, ZoneService zoneService,
                               BuildingService buildingService, UnitService unitService, RoomService roomService,
                               HouseHoldService houseHoldService, VisitorService visitorService,
                               DeviceService deviceService, AccessControlService accessControlService,
                               DeviceCallService deviceCallService) {
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
        this.buildingService = buildingService;
        this.unitService = unitService;
        this.roomService = roomService;
        this.houseHoldService = houseHoldService;
        this.visitorService = visitorService;
        this.deviceService = deviceService;
        this.accessControlService = accessControlService;
        this.deviceCallService = deviceCallService;
    }

    /**
     * 获取当前地区天气
     *
     * @param local 地区名拼音
     * @return Result
     * @author Mr.Deng
     * @date 16:06 2018/11/13
     */
    @RequestMapping(value = "/getWeatherInfo", method = RequestMethod.POST)
    @ApiOperation(value = "天气", notes = "获取当前地区天气 ;参数：local 地区名拼音 ")
    public Result getWeatherInfo(String local) {
        if (local.isEmpty()) {
            return Result.error("地址不能为空！");
        } else {
            String url = "https://api.seniverse.com/v3/weather/now.json?" +
                    "key=adfeskm2upezsis0" +
                    "&location=" + local +
                    "&language=zh-Hans&unit=c";
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
    @RequestMapping(value = "/getSyntheticalStatistics", method = RequestMethod.POST)
    @ApiOperation(value = "小区综合统计数据", notes = "返回参数：buildingSize 楼栋总数、roomSize 房屋总数、" +
            "车位总数、社区民警、居委干部、楼长人员、物业人员")
    public Result getSyntheticalStatistics() {
        Map<String, Object> map = new HashMap<>(8);
        int buildingSize = buildingService.getBuildingList().size();
        int roomSize = roomService.getRoomList().size();
        int houseHoldSize = houseHoldService.getHouseList().size();

        map.put("buildingSize", buildingSize);
        map.put("roomSize", roomSize);
        map.put("houseHoldSize", houseHoldSize);

        return Result.success(map, "OK");
    }

}

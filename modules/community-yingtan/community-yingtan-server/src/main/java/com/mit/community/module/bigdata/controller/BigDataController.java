package com.mit.community.module.bigdata.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.HouseHoldService;
import com.mit.community.service.RoomService;
import com.mit.community.util.Result;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    private final RoomService roomService;
    private final HouseHoldService houseHoldService;
    private final ClusterCommunityService clusterCommunityService;

    @Autowired
    public BigDataController(RoomService roomService, HouseHoldService houseHoldService, ClusterCommunityService clusterCommunityService) {
        this.roomService = roomService;
        this.houseHoldService = houseHoldService;
        this.clusterCommunityService = clusterCommunityService;
    }

    /**
     * 房屋属性比例
     *
     * @param communityCode 小区code
     * @return result
     * @author Mr.Deng
     * @date 17:09 2018/11/21
     */
    @GetMapping("/countHouseProperty")
    @ApiOperation(value = "房屋属性比例", notes = "参数：communityCode 小区code(传入参数查询该小区数据，不传入查询全部数据)")
    public Result countHouseProperty(String communityCode) {
        Map<String, Integer> map = Maps.newHashMapWithExpectedSize(4);

        if (StringUtils.isNotBlank(communityCode)) {

        } else {
            List<String> list = listCommunityCodes("鹰潭市");

        }
        //空闲
        map.put("idle", 0);
        //自住
        map.put("liveAlone", 20);
        //出租
        map.put("rent", 30);
        //未知
        map.put("unknown", 0);
        return Result.success(map, "OK");
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

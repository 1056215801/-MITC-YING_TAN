package com.mit.community.module.system.controller;

import com.google.common.collect.Maps;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.service.*;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 用户
 * @author shuyy
 * @date 2018/12/12
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
@Api(tags = "用户")
public class UserController {

    private final UserService userService;
    private final HouseHoldService houseHoldService;
    private final ClusterCommunityService clusterCommunityService;
    private final UserLabelService userLabelService;
    private final HouseholdRoomService householdRoomService;
    private final RedisService redisService;

    @Autowired
    public UserController(UserService userService, HouseHoldService houseHoldService,
                          ClusterCommunityService clusterCommunityService, UserLabelService userLabelService, HouseholdRoomService householdRoomService, RedisService redisService) {
        this.userService = userService;
        this.houseHoldService = houseHoldService;
        this.clusterCommunityService = clusterCommunityService;
        this.userLabelService = userLabelService;
        this.householdRoomService = householdRoomService;
        this.redisService = redisService;
    }

    /**
     * @param mac       mac
     * @param cellphone 手机号
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/12 16:58
     * @company mitesofor
     */
    @GetMapping("/getDetail")
    @ApiOperation(value = "查询用户详情", notes = "传参：cellphone 手机号")
    public Result getDetail(String mac, String cellphone) {
        HashMap<Object, Object> map = Maps.newHashMapWithExpectedSize(4);
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        ClusterCommunity community = null;
        List<UserLabel> userLabels = userLabelService.listByUserId(user.getId());
        List<HouseholdRoom> householdRooms = null;
        HouseHold houseHold = houseHoldService.getByHouseholdId(user.getHouseholdId());
        if (houseHold != null) {
            String communityCode = houseHold.getCommunityCode();
            community = clusterCommunityService.getByCommunityCode(communityCode);
            householdRooms = householdRoomService.listByHouseholdId(houseHold.getHouseholdId());
        }
        map.put("user", user);
        map.put("household", houseHold);
        map.put("community", community);
        map.put("label", userLabels);
        map.put("householdRooms", householdRooms);
        return Result.success(map);
    }

}

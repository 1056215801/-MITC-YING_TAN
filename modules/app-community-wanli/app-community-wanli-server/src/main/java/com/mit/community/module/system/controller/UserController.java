package com.mit.community.module.system.controller;

import com.google.common.collect.Maps;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.User;
import com.mit.community.entity.UserLabel;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.HouseHoldService;
import com.mit.community.service.UserLabelService;
import com.mit.community.service.UserService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 用户
 *
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

    @Autowired
    public UserController(UserService userService, HouseHoldService houseHoldService, ClusterCommunityService clusterCommunityService, UserLabelService userLabelService) {
        this.userService = userService;
        this.houseHoldService = houseHoldService;
        this.clusterCommunityService = clusterCommunityService;
        this.userLabelService = userLabelService;
    }

    /**
     * @param mac
     * @param cellphone
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/12 16:58
     * @company mitesofor
    */
    @GetMapping("/getDetail")
    @ApiOperation(value = "查询用户详情", notes = "传参：cellphone 手机号")
    public Result getDetail(String mac, String cellphone) {
        User user = userService.getByCellphone(cellphone);
        user.setPassword(StringUtils.EMPTY);
        HouseHold houseHold = houseHoldService.getByCellphone(cellphone);
        String communityCode = houseHold.getCommunityCode();
        ClusterCommunity community = clusterCommunityService.getByCommunityCode(communityCode);
        HashMap<Object, Object> map = Maps.newHashMapWithExpectedSize(2);
        List<UserLabel> userLabels = userLabelService.listByUserId(user.getId());
        map.put("user", user);
        map.put("household", houseHold);
        map.put("community", community);
        map.put("label", userLabels);
        return Result.success(map);
    }



}

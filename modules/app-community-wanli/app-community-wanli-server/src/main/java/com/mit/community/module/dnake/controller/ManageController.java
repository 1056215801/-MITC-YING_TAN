package com.mit.community.module.dnake.controller;

import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.User;
import com.mit.community.entity.entity.DeviceGroup;
import com.mit.community.population.service.PersonLabelsService;
import com.mit.community.service.*;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/manageController")
@Slf4j
@Api(tags = "狄耐克门禁机管理")
public class ManageController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private PermissionGroupService permissionGroupService;
    @Autowired
    private DeviceDeviceGroupService deviceDeviceGroupService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private PersonLabelsService personLabelsService;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private DeviceGroupService deviceGroupService;
    @Autowired
    private AuthorizeHouseholdDeviceGroupService authorizeHouseholdDeviceGroupService;

    @PostMapping("/getAuthGroup")
    @ApiOperation(value = "获取权限组", notes = "")
    public Result getAuthGroup(HttpServletRequest request, String cellphone){
        String communityCode = null;
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        //communityCode = user
        List<DeviceGroup> list = deviceGroupService.getByCommunityCode(communityCode);
        return Result.success(list);
    }
}

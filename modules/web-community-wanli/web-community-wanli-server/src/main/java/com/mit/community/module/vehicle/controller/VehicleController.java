package com.mit.community.module.vehicle.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.AccessRecord;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.VehicleInfo;
import com.mit.community.service.AccessRecordService;
import com.mit.community.service.RedisService;
import com.mit.community.service.VehicleInfoService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Author HuShanLin
 * @Date Created in 18:06 2019/6/27
 * @Company: mitesofor </p>
 * @Description:~车辆管理
 */
@RequestMapping(value = "/vehicleController")
@RestController
@Slf4j
@Api(tags = "车辆管理")
public class VehicleController {

    @Autowired
    private VehicleInfoService vehicleInfoService;
    @Autowired
    private AccessRecordService accessRecordService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/list")
    @ApiOperation(value = "车辆信息列表", notes = "carnum:车号;carphone:车主手机号;brand:品牌")
    public Result listVehicles(HttpServletRequest request, String communityCode, String carnum, String carphone, String brand,
                               Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        Page<VehicleInfo> page = vehicleInfoService.listPage(communityCode, carnum, carphone, brand, pageNum, pageSize);
        return Result.success(page);
    }

    @RequestMapping("/listRecords")
    @ApiOperation(value = "进出记录列表", notes = "")
    public Result listRecords(HttpServletRequest request, String communityCode, String carnum, String accessType, String carphone,
                              LocalDateTime begintime, LocalDateTime endtime, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        Page<AccessRecord> page = accessRecordService.listPage(communityCode, carnum, accessType, carphone, begintime, endtime,
                pageNum, pageSize);
        return Result.success(page);
    }
}

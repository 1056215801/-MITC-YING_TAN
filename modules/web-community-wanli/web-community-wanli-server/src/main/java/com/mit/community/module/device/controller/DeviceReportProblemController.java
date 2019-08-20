package com.mit.community.module.device.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.DeviceReportProblem;
import com.mit.community.entity.SysUser;
import com.mit.community.service.DeviceReportProblemService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
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

/**
 * 设备问题（故障）报警
 *
 * @author xq
 * @date 2019/08/08
 * @company mitesofor
 */

@RestController
@RequestMapping("/deviceReportProblemController")
@Slf4j
@Api(tags = "设备问题（故障）报警")
public class DeviceReportProblemController {
    @Autowired
    private DeviceReportProblemService deviceReportProblemService;
    @Autowired
    private RedisService redisService;

    @PostMapping("/listPage")
    @ApiOperation(value = "分页获取设备故障报警信息", notes = "")
    public Result listPage(HttpServletRequest request, Integer deviceType, Integer pageNum, Integer pageSize){
        String communityCode = null;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (StringUtils.isBlank(communityCode)) {
            communityCode = user.getCommunityCode();
        }
        Page<DeviceReportProblem> page = deviceReportProblemService.selectPage(communityCode, deviceType, pageNum, pageSize);
        return Result.success(page);
    }





}

package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.WebFeedBack;
import com.mit.community.service.FeedBackService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping(value = "/feedBack")
@RestController
@Slf4j
@Api(tags = "意见反馈")
public class FeedBackController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private FeedBackService feedBackService;

    /**
     * 受理反馈意见
     */
    /*@PatchMapping("/receive")
    @ApiOperation(value = "受理反馈意见", notes = "输入参数：id 反馈意见id")
    public Result receive(javax.servlet.http.HttpServletRequest request, Integer id) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        feedBackService.receive(id, user.getAdminName());
        return Result.success("受理成功");
    }*/

    /**
     * 意见反馈
     * @author xiong
     */
    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询反馈意见", notes = "输入参数：status 处理状态（关联feedback_status数据字典表），gmtCreateTimeStart 反馈开始时间，gmtCreateTimeEnd 反馈结束时间")
    public Result listPage(HttpServletRequest request, String status,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeEnd,Integer pageNum, Integer pageSize
                           ){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        Page<WebFeedBack> page =feedBackService.listPage(/*user.getCommunityCode()*/"003", status, gmtCreateTimeStart, gmtCreateTimeEnd, pageNum, pageSize);
        return Result.success(page);
    }

    /*@GetMapping("/test")
    public Result testListPage(Integer pageNum, Integer pageSize,
                               String status,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeStart,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeEnd){
        Page<Test> page =  feedBackService.listTestPage(*//*user.getCommunityCode()*//*"003", status, gmtCreateTimeStart, gmtCreateTimeEnd, pageNum, pageSize);
        return Result.success(page);
    }*/

}

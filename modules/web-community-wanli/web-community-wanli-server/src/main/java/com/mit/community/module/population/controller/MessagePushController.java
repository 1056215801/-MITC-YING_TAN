package com.mit.community.module.population.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.entity.Message;
import com.mit.community.entity.entity.MessageCheck;
import com.mit.community.population.service.MessagePushService;
import com.mit.community.population.service.MessageService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.PushUtil;
import com.mit.community.util.Result;
import com.mit.community.util.WebPush;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/messagePush")
@RestController
@Slf4j
@Api(tags = "消息推送")
public class MessagePushController {
    @Autowired
    private MessagePushService messagePushService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisService redisService;

    @PostMapping("/pushPerson")
    @ApiOperation(value = "消息推送", notes = "")
    public Result pushPerson(@RequestParam(required = false, defaultValue = "0") Integer ageStart,
                             @RequestParam(required = false, defaultValue = "0") Integer ageEnd, String sex,
                             String edu, String job, String marriage, String politics,
                             String rycf, String rysx, String title, String outline, String content, Integer userId,
                             HttpServletRequest request) {
        if (userId == null) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            userId = user.getId();
        }
        messagePushService.pushMessage(ageStart, ageEnd, sex, edu, job, marriage, politics, rycf, rysx, title, outline, content, userId);
        return Result.success("通知发送成功");
    }

    @PostMapping("/pushUser")
    @ApiOperation(value = "指定人员消息推送", notes = "")
    public Result pushUser(HttpServletRequest request, String title, String outline, String content, String idNum) {
        Integer userId = null;
        if (userId == null) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            userId = user.getId();
        }
        messagePushService.pushUser(title, outline, content, userId, idNum);
        return Result.success("通知发送成功");
    }


    @GetMapping("/messageListPage")
    @ApiOperation(value = "分页查询消息", notes = "输入参数：validateTimeStart 起始有效时间，validateTimeEnd 停止有效时间，title 标题 ，code 分类，status 状态 ")
    public Result listPage(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime timeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime timeEnd,
            String content, Integer pageNum, Integer pageSize) {
        Page<Message> page = messageService.listPage(timeStart, timeEnd, content, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/messageAcceptListPage")
    @ApiOperation(value = "消息详情", notes = "")
    public Result messageAcceptListPage(Integer messageId, String name,
                                        @RequestParam(required = false, defaultValue = "0") Integer status,
                                        Integer pageNum, Integer pageSize) {
        Page<MessageCheck> page = messagePushService.messageAcceptListPage(messageId, name, status, pageNum, pageSize);
        return Result.success(page);
    }


}

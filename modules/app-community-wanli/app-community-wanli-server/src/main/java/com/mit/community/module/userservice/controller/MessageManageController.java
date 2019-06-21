package com.mit.community.module.userservice.controller;

import com.mit.community.entity.entity.Message;
import com.mit.community.population.service.MessagePushService;
import com.mit.community.service.MessageManageService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/messageManageController")
@Slf4j
@Api(tags = "通知消息管理")
public class MessageManageController {
    @Autowired
    private MessageManageService messageManageService;

    @Autowired
    private MessagePushService messagePushService;

    @PostMapping("/messageNoReadCount")
    @ApiOperation(value = "获取未读通知消息数量", notes = "")
    public Result messageNoReadCount(Integer userId){
        int count = messageManageService.getMessageNoReadCount(userId);
        return Result.success(count);
    }

    @PostMapping("/getMessage")
    @ApiOperation(value = "获取所有通知消息", notes = "")
    public Result messageAll(Integer userId){
        List<Message> list = messagePushService.getMessage(userId);
        return Result.success(list);
    }

}

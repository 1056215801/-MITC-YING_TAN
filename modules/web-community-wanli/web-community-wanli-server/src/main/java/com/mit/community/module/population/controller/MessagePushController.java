package com.mit.community.module.population.controller;

import com.alibaba.fastjson.JSONArray;
import com.mit.community.util.PushUtil;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/messagePush")
@RestController
@Slf4j
@Api(tags = "消息推送")
public class MessagePushController {

    @PostMapping("/pushPerson")
    @ApiOperation(value = "消息推送", notes = "传参：用户id 数组json")
    public Result pushPerson(String message) {
        PushUtil.sendAllsetNotification(message);
        return Result.success("通知发送成功");
    }
}

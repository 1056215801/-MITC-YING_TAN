package com.mit.community.module.message.controller;

import com.mit.community.service.NoticeReadUserService;
import com.mit.community.service.NoticeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息服务
 *
 * @author shuyy
 * @date 2019-01-02
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/message")
@Slf4j
@Api(tags = "消息服务")
public class MessageController {
    @Autowired
    private NoticeReadUserService noticeReadUserService;
    @Autowired
    private NoticeService noticeService;

  /*  @GetMapping("/getTotal")
    @ApiOperation(value = "我的-统计数据", notes = "输入参数：cellphone 手机号；communityCode小区code")
    public Result getTotal(String cellphone, String communityCode) {
        noticeService.
        noticeReadUserService.listNoticeReadUserByUserIdAndNoticeIdList(, )

    }*/

}

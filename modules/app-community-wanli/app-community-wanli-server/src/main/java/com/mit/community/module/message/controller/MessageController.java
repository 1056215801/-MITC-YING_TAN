package com.mit.community.module.message.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.User;
import com.mit.community.entity.VisitorMessage;
import com.mit.community.service.*;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    @Autowired
    private RedisService redisService;
    @Autowired
    private ExpressInfoService expressInfoService;
    @Autowired
    private SysMessagesService sysMessagesService;
    @Autowired
    private LostFoundService lostFoundService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private OldMedicalService oldMedicalService;
    @Autowired
    private VisitorMessageService visitorMessageService;

    /**
     * @param cellphone
     * @param communityCode
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2019-01-02 16:16
     * @company mitesofor
     */
    @GetMapping("/getTotal")
    @ApiOperation(value = "统计未读消息", notes = "输入参数：cellphone 手机号；communityCode小区code")
    public Result getTotal(String cellphone, String communityCode) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        Integer notReadNotice = noticeService.getNotReadNotice(communityCode, user.getId());
        Integer notSysMessage = sysMessagesService.countNotReadNum(user.getId());
        Integer notReadExpress = expressInfoService.countNotRead(user.getId());

        Integer notReadLostFound = lostFoundService.countNotRead(communityCode, user.getId());
        Integer notReadPromotion = promotionService.countNotReadNum(communityCode, user.getId());
        Integer notReadOld = oldMedicalService.countNotRead(communityCode, user.getId());
        Integer notMessageRead = visitorMessageService.countNotRead(cellphone);
        Map<String, Integer> map = Maps.newHashMapWithExpectedSize(7);
        map.put("notice", notReadNotice);
        map.put("sysMessage", notSysMessage);
        map.put("express", notReadExpress);
        map.put("lostFound", notReadLostFound);
        map.put("promotion", notReadPromotion);
        map.put("old", notReadOld);
        map.put("visitorMessag", notMessageRead);
        return Result.success(map);
    }

    /**
     *
     * @param cellphone
     * @param pageNum
     * @param pageSize
     * @return com.mit.community.util.Result
     * @throws 
     * @author shuyy
     * @date 2019-01-03 10:38
     * @company mitesofor
    */
    @GetMapping("/listVisitorMessagePage")
    @ApiOperation(value = "分页访客消息", notes = "输入参数：cellphone 手机号；输出参数：status 1、未读、2、已读")
    public Result listVisitorMessagePage(String cellphone, Integer pageNum, Integer pageSize) {
        Page<VisitorMessage> visitorMessagePage = visitorMessageService
                .listPage(cellphone, pageNum, pageSize);
        return Result.success(visitorMessagePage);
    }
}

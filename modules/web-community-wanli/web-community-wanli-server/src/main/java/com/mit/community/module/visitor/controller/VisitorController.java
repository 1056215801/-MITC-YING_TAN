package com.mit.community.module.visitor.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.Visitor;
import com.mit.community.service.RedisService;
import com.mit.community.service.VisitorService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 访客记录
 * @author  xiong
 * @date 2019/4/16
 */
@RestController
@RequestMapping(value = "/visitorRecord")
@Slf4j
@Api(tags = "访客记录")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/listPage")
    @ApiOperation(value = "访客记录分页查询", notes = "inviteName 邀请人姓名，inviteMobile 邀请人号码，codeType 验证码类型（关联字典表code为visitor_code_type），codeStatus 验证码使用状态（关联字典表，code为visitor_code_status） "
    + "gmtCreateTimeStart 到访开始时间，gmtCreateTimeEnd 到访结束时间")
    public Result listPage(HttpServletRequest request, String inviteName, String inviteMobile, String codeType, String codeStatus,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeEnd,Integer pageNum, Integer pageSize){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        Page<Visitor> page = visitorService.listWebPage(communityCode, inviteName, inviteMobile, codeType, codeStatus, gmtCreateTimeStart, gmtCreateTimeEnd, pageNum, pageSize);
        return Result.success(page);
    }
}

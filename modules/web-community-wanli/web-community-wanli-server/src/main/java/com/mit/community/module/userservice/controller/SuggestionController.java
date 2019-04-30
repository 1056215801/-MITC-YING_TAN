package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.Suggestion;
import com.mit.community.entity.SysUser;
import com.mit.community.service.RedisService;
import com.mit.community.service.SuggestionService;
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
 * 投诉建议
 * @author  xiong
 * @date 2019/04/17
 */
@RequestMapping(value = "/suggestionController")
@RestController
@Slf4j
@Api(tags = "投诉建议")
public class SuggestionController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SuggestionService suggestionService;

    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询投诉建议", notes="输入参数：type 投诉类型（关联字典表，code为suggestion_type。（1投诉建议，2公共设施，3邻里纠纷，4噪音扰民，5停车秩序" +
            " 6服务态度，7业务流程，8工程维修，9售后服务，10其他））、status 处理状态（处理状态，关联字典表，code为suggestion_type）、gmtCreateTimeStart 投诉开始时间、gmtCreateTimeEnd 投诉结束时间")
    public Result listPage(HttpServletRequest request, String type, String status,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeEnd, Integer pageNum, Integer pageSize){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String communityCode = user.getCommunityCode();
        Page<Suggestion> suggestions = suggestionService.listPage(communityCode, type, status, gmtCreateTimeStart, gmtCreateTimeEnd, pageNum, pageSize);
        return Result.success(suggestions);

    }
}

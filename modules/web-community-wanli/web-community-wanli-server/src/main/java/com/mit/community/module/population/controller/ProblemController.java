package com.mit.community.module.population.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.HandleProblemInfo;
import com.mit.community.entity.SysUser;
import com.mit.community.service.ProblemHandleService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/problem")
@Slf4j
@Api(tags = "上报事件获取")
public class ProblemController {
    @Autowired
    private ProblemHandleService problemHandleService;
    @Autowired
    private RedisService redisService;

    @PostMapping("/getHandleProblem")
    @ApiOperation(value = "获取上报事件", notes = "输入参数：")
    public Result getHandleProblem(HttpServletRequest request, String problemType, String status, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeStart,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeEnd, Integer pageNum, Integer pageSize){
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        Page<HandleProblemInfo> page = problemHandleService.getWebProblem(user.getCommunityCode(),problemType, status, gmtCreateTimeStart, gmtCreateTimeEnd, pageNum, pageSize);
        return Result.success(page);
    }
}

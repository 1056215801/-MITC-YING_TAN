package com.mit.community.module.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.Investigation;
import com.mit.community.entity.SysUser;
import com.mit.community.service.InvestigationService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.DateUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @Author: HuShanLin
 * @Date: Create in 2019/5/31 19:53
 * @Company mitesofor
 * @Description:民意调查
 */
@RequestMapping("/investigation")
@RestController
@Slf4j
@Api(tags = "民意调查")
public class InvestigationController {

    @Autowired
    private InvestigationService investigationService;
    @Autowired
    private RedisService redisService;

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/31 19:58
     * @Company mitesofor
     * @Description:~分页查询调查问卷
     */
    @RequestMapping("/list")
    @ApiOperation(value = "民意调查列表分页查询")
    public Result listPages(HttpServletRequest request, String communityCode,
                            String title, Integer status, String begintime, String endtime,
                            Integer pageNum, Integer pageSize) throws ParseException {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        Page<Investigation> page = investigationService.listPages(user.getCommunityCode(), title, status, DateUtils.dateStrToLocalDateTime(begintime),
                DateUtils.dateStrToLocalDateTime(endtime), pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/1 9:46
     * @Company mitesofor
     * @Description:~保存民意调查模板
     */
    @RequestMapping("/save")
    @ApiOperation(value = "保存民意调查模板")
    public Result save(HttpServletRequest request, Integer id, String title, String begintime,
                       String endtime, Integer status) throws ParseException {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String msg = investigationService.save(user.getCommunityCode(), id, title, user.getId(),
                DateUtils.strToLocalDateTime(begintime), DateUtils.strToLocalDateTime(endtime), status);
        if (msg.equals("success")) {
            return Result.success(msg);
        }
        return Result.error(msg);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/1 9:50
     * @Company mitesofor
     * @Description:~移除民意调查
     */
    @RequestMapping("/remove")
    @ApiOperation(value = "移除民意调查")
    public Result remove(Integer id) {
        String msg = investigationService.remove(id);
        if (msg.equals("success")) {
            return Result.success(msg);
        }
        return Result.error(msg);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/1 9:50
     * @Company mitesofor
     * @Description:~启用民意调查
     */
    @RequestMapping("/enable")
    @ApiOperation(value = "启用民意调查")
    public Result enable(Integer id) {
        String msg = investigationService.enable(id);
        if (msg.equals("success")) {
            return Result.success(msg);
        }
        return Result.error(msg);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/1 9:50
     * @Company mitesofor
     * @Description:~停用民意调查
     */
    @RequestMapping("/stop")
    @ApiOperation(value = "停用民意调查")
    public Result stop(Integer id) {
        String msg = investigationService.stop(id);
        if (msg.equals("success")) {
            return Result.success(msg);
        }
        return Result.error(msg);
    }
}

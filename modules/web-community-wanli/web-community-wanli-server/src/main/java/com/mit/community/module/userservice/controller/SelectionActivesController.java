package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.SelectionActivities;
import com.mit.community.entity.SysUser;
import com.mit.community.service.RedisService;
import com.mit.community.service.SelectionActivitiesService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.FastDFSClient;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 精选活动
 *
 * @author shuyy
 * @date 2018/12/25
 * @company mitesofor
 */
@RequestMapping(value = "/selectionActives")
@RestController
@Slf4j
@Api(tags = "精选活动")
public class SelectionActivesController {

    @Autowired
    private SelectionActivitiesService selectionActivitiesService;
    @Autowired
    private RedisService redisService;

    /**
     * 保存
     * @param request request
     * @param title       标题
     * @param introduce   简介
     * @param externalUrl 外接地址
     * @param validTime   有效期
     * @param issueTime   发布期
     * @param image       图片地址
     * @param notes       备注
     * @param content     内容
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/25 11:52
     * @company mitesofor
    */
    @PostMapping("/save")
    @ApiOperation(value = "保存", notes = "输入参数：title 标题， introduce 简介， externalUrl 外接url， validTime 2018-01-01 00:00:00，issueTime 2018-01-01 00:00:00, image 图片, notes 备注，content 内容  ")
    public Result save(HttpServletRequest request, String title, String introduce, String externalUrl, LocalDateTime validTime, LocalDateTime issueTime, MultipartFile image, String notes, String content) throws Exception {
        String s = FastDFSClient.getInstance().uploadFile(image);
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        selectionActivitiesService.save(title, introduce, externalUrl, validTime, issueTime,
                user.getName(), s, notes, content);
        return Result.success("保存成功");
    }


    /**
     * @param request request
     * @param title       标题
     * @param introduce   简介
     * @param externalUrl 外接地址
     * @param validTime   有效期
     * @param issueTime   发布期
     * @param image       图片地址
     * @param notes       备注
     * @param content     内容
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/25 11:52
     * @company mitesofor
     */
    @PatchMapping("/update")
    @ApiOperation(value = "修改", notes = "输入参数：id id, title 标题， introduce 简介， externalUrl 外接url， validTime 2018-01-01 00:00:00，issueTime 2018-01-01 00:00:00, image 图片, notes 备注，content 内容  ")
    public Result update(HttpServletRequest request, Integer id, String title, String introduce, String externalUrl, LocalDateTime validTime, LocalDateTime issueTime, MultipartFile image, String notes, String content) throws Exception {
        String s = null;
        if(image != null){
            s = FastDFSClient.getInstance().uploadFile(image);
        }
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        selectionActivitiesService.update(id, title, introduce, externalUrl, validTime, issueTime,
                user.getName(), s, notes, content);
        return Result.success("修改成功");
    }

    /**
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/25 11:58
     * @company mitesofor
    */
    @DeleteMapping("/remove")
    @ApiOperation(value = "删除", notes = "输入参数：id id")
    public Result remove(Integer id){
        selectionActivitiesService.remove(id);
        return Result.success("删除成功");
    }

    /**
     * @param validTimeStart 过期开始时间
     * @param validTimeEnd 过期结束时间
     * @param issueTimeStart 发布开始时间
     * @param issueTimeEnd 发布结束时间
     * @param pageNum 当前页
     * @param pageSize 分页大小
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/25 13:39
     * @company mitesofor
    */
    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询", notes = "输入参数：validTimeStart 过期开始时间、 validTimeEnd 过期结束时间、issueTimeStart 发布开始时间，issueTimeEnd 发布结束时间 ")
    public Result listPage(LocalDateTime validTimeStart, LocalDateTime validTimeEnd, LocalDateTime issueTimeStart,
                         LocalDateTime issueTimeEnd, Integer pageNum, Integer pageSize){
        Page<SelectionActivities> page = selectionActivitiesService.listPage(validTimeStart,
                validTimeEnd, issueTimeStart,
                issueTimeEnd, pageNum, pageSize);
        return Result.success(page);
    }


}
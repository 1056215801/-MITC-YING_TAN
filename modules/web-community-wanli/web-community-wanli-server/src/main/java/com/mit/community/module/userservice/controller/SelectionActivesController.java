package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.SelectionActivities;
import com.mit.community.entity.SysUser;
import com.mit.community.feigin.SelectionActivitiesFeigin;
import com.mit.community.service.RedisService;
import com.mit.community.service.SelectionActivitiesService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.DateUtils;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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
    @Autowired
    private SelectionActivitiesFeigin selectionActivitiesFeigin;

    /**
     * 保存
     *
     * @param request     request
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
    @ApiOperation(value = "保存", notes = "输入参数：title 标题， introduce 简介， " +
            "externalUrl 外接url， validTime 2018-01-01 00:00:00，issueTime 2018-01-01 00:00:00, image 图片, " +
            "notes 备注，content 内容  ")
    public Result save(HttpServletRequest request,
                       Integer id,
                       String title,
                       String introduce,
                       String externalUrl,
                       String validateTime,
                       String releasetime,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validTime,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime issueTime,
                       String portraitFileDomain,
                       String portraitFileName,
                       String notes, String content) throws Exception {
        String imgUrl = portraitFileDomain + portraitFileName;
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        selectionActivitiesService.save(user.getCommunityCode(), id, title, introduce, externalUrl,
                DateUtils.strToLocalDateTime(validateTime), DateUtils.strToLocalDateTime(releasetime),
                user.getAdminName(), imgUrl, notes, content);
        return Result.success("保存成功");
    }

    /**
     * @param request     request
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
    public Result update(HttpServletRequest request, Integer id, String title, String introduce, String externalUrl,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validTime,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime issueTime, MultipartFile image,
                         String notes, String content) throws Exception {
        if (id != null) {
            String s = null;
            if (image != null) {
                s = UploadUtil.upload(image);
            }
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            selectionActivitiesService.update(id, title, introduce, externalUrl, validTime, issueTime,
                    user.getAdminName(), s, notes, content);
            return Result.success("修改成功");
        }
        return Result.error("id 不能为空");
    }

    /**
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/25 11:58
     * @company mitesofor
     */
    @RequestMapping("/remove")
    @ApiOperation(value = "删除", notes = "输入参数：id id")
    public Result remove(Integer id) {
        selectionActivitiesService.remove(id);
        return Result.success("删除成功");
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/23 17:07
     * @Company mitesofor
     * @Description:~启用接口
     */
    @RequestMapping("/enable")
    @ApiOperation(value = "启用")
    public Result enable(Integer id) {
        String res = selectionActivitiesService.enable(id);
        if (!res.equals("success")) {
            return Result.error("启用失败");
        }
        return Result.success("启用成功");
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/23 17:08
     * @Company mitesofor
     * @Description:~停用接口
     */
    @RequestMapping("/stop")
    @ApiOperation(value = "停用")
    public Result stop(Integer id) {
        String res = selectionActivitiesService.stop(id);
        if (!res.equals("success")) {
            return Result.error("启用失败");
        }
        return Result.success("启用成功");
    }

    /**
     * @param validTimeStart 过期开始时间
     * @param validTimeEnd   过期结束时间
     * @param issueTimeStart 发布开始时间
     * @param issueTimeEnd   发布结束时间
     * @param pageNum        当前页
     * @param pageSize       分页大小
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/25 13:39
     * @company mitesofor
     */
    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询", notes = "输入参数：validTimeStart 过期开始时间、 validTimeEnd 过期结束时间、issueTimeStart 发布开始时间，issueTimeEnd 发布结束时间 ")
    public Result listPage(HttpServletRequest request,
                           String title,
                           Integer status,
                           String begintime,
                           String endtime,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validTimeEnd,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime issueTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime issueTimeEnd,
                           Integer pageNum, Integer pageSize, String communityCode) throws ParseException {
        if (StringUtils.isNotBlank(begintime)) {
            issueTimeStart = DateUtils.strToLocalDateTime(begintime);
        }
        if (StringUtils.isNotBlank(endtime)) {
            issueTimeEnd = DateUtils.strToLocalDateTime(endtime);
        }
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = user.getCommunityCode();
        }
        Page<SelectionActivities> page = selectionActivitiesService.listPage(title, status, communityCode, validTimeStart,
                validTimeEnd, issueTimeStart, issueTimeEnd, pageNum, pageSize);
        return Result.success(page);
    }


    /**
     * @param selectionActivitiesId
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/25 16:30
     * @company mitesofor
     */
    @GetMapping("/getBySelectionActivitiesId")
    @ApiOperation(value = "查询详情", notes = "输入参数：selectionActivitiesId")
    public Result getBySelectionActivitiesId(Integer selectionActivitiesId) {
        return selectionActivitiesFeigin.getBySelectionActivitiesId(selectionActivitiesId);
    }

}
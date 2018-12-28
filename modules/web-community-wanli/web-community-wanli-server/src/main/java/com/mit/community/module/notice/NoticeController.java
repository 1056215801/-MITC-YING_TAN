package com.mit.community.module.notice;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.Notice;
import com.mit.community.entity.SysUser;
import com.mit.community.service.NoticeService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知通告
 *
 * @author shuyy
 * @date 2018/12/26
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/notice")
@Slf4j
@Api(tags = "通知通告")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private RedisService redisService;

    /**
     * @param request      request
     * @param title        标题
     * @param code         code
     * @param synopsis     简介
     * @param publisher    发布人
     * @param content      内容
     * @param releaseTime  发布时间
     * @param validateTime 过期时间
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/26 9:18
     * @company mitesofor
     */
    @PostMapping("/releaseNotice")
    @ApiOperation(value = "发布通知通告信息", notes = "输入参数：title 标题、" +
            "code 类型(查询字典notice_type)、" +
            "releaseTime 发布时间；synopsis 简介、publisher 发布人 validateTime 过期时间")
    public Result releaseNotice(HttpServletRequest request, String title, String code, String synopsis, String publisher,
                                String content,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime releaseTime,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validateTime) {
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(code)
                && StringUtils.isNotBlank(synopsis) && StringUtils.isNotBlank(publisher)
                && StringUtils.isNotBlank(content) && validateTime != null) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            noticeService.releaseNotice(user.getCommunityCode(), title, code, synopsis, publisher, user.getId(),
                    content, releaseTime, validateTime);
            return Result.success("发布成功！");
        }
        return Result.error("参数不能为空");
    }

    /**
     * @param request      request
     * @param title        标题
     * @param code         code
     * @param synopsis     简介
     * @param publisher    发布人
     * @param content      内容
     * @param releaseTime  发布时间
     * @param validateTime 过期时间
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/26 9:18
     * @company mitesofor
     */
    @PostMapping("/updateNotice")
    @ApiOperation(value = "修改通知通告信息", notes = "输入参数：title 标题、" +
            "code 类型(查询字典notice_type)、" +
            "releaseTime 发布时间；synopsis 简介、publisher 发布人 validateTime 过期时间")
    public Result updateNotice(HttpServletRequest request, Integer id, String title, String code, String synopsis, String publisher,
                               String content,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime releaseTime,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validateTime) {
        if (id != null) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            noticeService.updateNotice(id, title, code,
                    synopsis, publisher, user.getId(),
                    content, releaseTime, validateTime);
            return Result.success("修改成功！");
        }
        return Result.error("参数不能为空");
    }

    /**
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/26 9:18
     * @company mitesofor
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除通知通告信息", notes = "输入参数：id id")
    public Result remove(Integer id) {
        if (id != null) {
            noticeService.remove(id);
            return Result.success("删除成功！");
        }
        return Result.error("参数不能为空");
    }


    /**
     * 分页查询
     * @param request request
     * @param releaseTimeStart 发布开始时间
     * @param releaseTimeEnd 发布结束时间
     * @param validateTimeStart 过期开始时间
     * @param validateTimeEnd 过期结束时间
     * @param publisher 发布人
     * @param pageNum 当前页
     * @param pageSize 分页大小
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/26 11:22
     * @company mitesofor
    */
    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询通知通告信息", notes = "输入参数：releaseTimeStart 发布开始时间， releaseTimeEnd 发布结束时间，validateTimeStart 过期开始时间，validateTimeEnd 过期结束时间，publisher 发布人   ")
    public Result listPage(HttpServletRequest request, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime releaseTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime releaseTimeEnd,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validateTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validateTimeEnd, String publisher, Integer pageNum, Integer pageSize) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        Page<Notice> page = noticeService.listPage(user.getCommunityCode(),
                releaseTimeStart, releaseTimeEnd, validateTimeStart, validateTimeEnd, publisher, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * @param id id
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/26 11:26
     * @company mitesofor
    */
    @GetMapping("/getById")
    @ApiOperation(value = "获取通知通告信息详情", notes = "输入参数：id id")
    public Result getById(Integer id) {
        if(id == null){
            return Result.error("参数错误");
        }
        List<Object> noticInfoByNotiveId = noticeService.getNoticInfoByNotiveId(id);
        return Result.success(noticInfoByNotiveId);
    }



}

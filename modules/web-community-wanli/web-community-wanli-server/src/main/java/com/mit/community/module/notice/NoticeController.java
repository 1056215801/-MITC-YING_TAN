package com.mit.community.module.notice;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.Notice;
import com.mit.community.entity.SysUser;
import com.mit.community.service.NoticeService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 通知通告（app公告）
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
            "code 类型(查询字典notice_type)、" + "publishWay 发布渠道（关联字典表publish_way_code）、" +
            "releaseTime 发布时间；synopsis 简介、publisher 发布人 、validateTime 过期时间")
    public Result releaseNotice(HttpServletRequest request, String title, String code, String publishWay, String synopsis, String publisher,
                                String content, MultipartFile image,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime releaseTime,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validateTime/*,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime*/
                                ) throws Exception{
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(code) && StringUtils.isNotBlank(publishWay)
                && StringUtils.isNotBlank(synopsis) && StringUtils.isNotBlank(publisher)
                && StringUtils.isNotBlank(content) && validateTime != null && image != null) {
            String imageUrl = UploadUtil.upload(image);
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            noticeService.releaseWebNotice(user.getCommunityCode(), title, code, publishWay, synopsis, publisher, user.getId(),
                    content, releaseTime, validateTime, imageUrl);
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
    @ApiOperation(value = "修改通知通告信息", notes = "输入参数：title 标题、content 内容、" +
            "code 类型(查询字典notice_type)、" + "publishWay 发布渠道、status 状态（1已启用，2已停用，3已过期）、" +
            "releaseTime 发布时间；synopsis 简介、publisher 发布人 validateTime 过期时间")
    public Result updateNotice(HttpServletRequest request, Integer id, String title, String code, String synopsis, String publisher,
                               String content, String publishWay, Integer status,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime releaseTime,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validateTime/*,
                               @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime*/) {
        if (id != null) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            noticeService.updateNotice(id, title, code,
                    synopsis, publisher, user.getId(),
                    content, publishWay, releaseTime, validateTime, status);
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
     * @param request           request
     * @param releaseTimeStart  发布开始时间
     * @param releaseTimeEnd    发布结束时间
     * @param validateTimeStart 过期开始时间
     * @param validateTimeEnd   过期结束时间
     * @param publisher         发布人
     * @param pageNum           当前页
     * @param pageSize          分页大小
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/26 11:22
     * @company mitesofor
     */
    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询通知通告信息", notes = "输入参数：validateTimeStart 起始有效时间，validateTimeEnd 停止有效时间，title 标题 ，code 分类，status 状态 ")
    public Result listPage(HttpServletRequest request, /*@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime releaseTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime releaseTimeEnd,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validateTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validateTimeEnd,String publisher*/
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validateTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime validateTimeEnd,
                           String title, String code, Integer status, Integer pageNum, Integer pageSize) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        //Page<Notice> page = noticeService.listPage(user.getCommunityCode(),
                //releaseTimeStart, releaseTimeEnd, validateTimeStart, validateTimeEnd, publisher, pageNum, pageSize);
        Page<Notice> page = noticeService.listPage(user.getCommunityCode(),
                validateTimeStart, validateTimeEnd, title, code, status, pageNum, pageSize);
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
        if (id == null) {
            return Result.error("参数错误");
        }
        Notice notice = noticeService.getNoticeInfoByNoticeId(id);
        return Result.success(notice);
    }

}

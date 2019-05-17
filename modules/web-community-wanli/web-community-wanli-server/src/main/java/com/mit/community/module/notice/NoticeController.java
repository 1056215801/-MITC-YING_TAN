package com.mit.community.module.notice;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.Notice;
import com.mit.community.entity.NoticeVo;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.UploadFileVo;
import com.mit.community.mapper.NoticeMapper;
import com.mit.community.service.NoticeService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.DateUtils;
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
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 通知通告（app公告）
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
    @Autowired
    private NoticeMapper noticeMapper;

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
    public Result releaseNotice(HttpServletRequest request,
                                Integer id,//主键
                                Integer noticeState,//公告状态
                                String title,//标题
                                String noticeType,//公告类型
                                String noticeChannel,//发布渠道
                                String portraitFileDomain,//公告图片域名
                                String portraitFileName,//公告图片名称
                                String noticeContent,//公告内容
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String startTime,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String endTime) throws Exception {
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(noticeType) && StringUtils.isNotBlank(noticeChannel)
                && StringUtils.isNotBlank(noticeContent)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            noticeService.releaseWebNotice(user.getCommunityCode(), id, title, noticeType, noticeChannel, noticeContent, user.getAdminName(), user.getId(),
                    null, DateUtils.dateStrToLocalDateTime(startTime), DateUtils.dateStrToLocalDateTime(endTime), null, portraitFileDomain, portraitFileName);
            return Result.success("发布成功！");
        }
        return Result.error("参数不能为空");
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/7 11:36
     * @Company mitesofor
     * @Description:~上传公告图片的接口
     */
    @RequestMapping("/uploadFile")
    public UploadFileVo uploadFile(MultipartFile image) {
        UploadFileVo uploadFileVo = new UploadFileVo();
        uploadFileVo.setOriginalFileName(image.getOriginalFilename());
        String imageUrl = null;
        if (image != null) {
            try {
                imageUrl = UploadUtil.upload(image);
                uploadFileVo.setFilePath(imageUrl);
                uploadFileVo.setFileDomain(imageUrl.substring(0, imageUrl.lastIndexOf("/") + 1));
                uploadFileVo.setFileName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.length()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return uploadFileVo;
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/7 11:35
     * @Company mitesofor
     * @Description:~上传公告图片的接口
     */
    @RequestMapping("/uploadBase64Imgs")
    public List<String> uploadBase64Imgs(@RequestParam("data") String[] base64) {
        List<String> list = new ArrayList<>();
        BASE64Decoder decoder = new BASE64Decoder();
        for (String base : base64) {
            try {
                //Base64解码
                byte[] b = decoder.decodeBuffer(base);
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {// 调整异常数据
                        b[i] += 256;
                    }
                }
                String url = UploadUtil.uploadWithByte(b);
                list.add(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
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
     *
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

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/7 15:54
     * @Company mitesofor
     * @Description:~通知通告修改对象
     */
    @RequestMapping("/getNoticeById")
    public Result getNoticeById(Integer id) {
        if (id == null) {
            return Result.error("参数错误");
        }
        NoticeVo noticeVo = noticeMapper.getNoticeById(id);
        noticeVo.setStartTime(noticeVo.getStartTime().substring(0, 11));
        noticeVo.setEndTime(noticeVo.getEndTime().substring(0, 11));
        return Result.success(noticeVo);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/7 17:33
     * @Company mitesofor
     * @Description:~删除公告
     */
    @RequestMapping("/delete")
    public Result delete(HttpServletRequest request) {
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 0) {
                return Result.error("参数错误");
            } else {
                for (int i = 0; i < paramValues.length; i++) {
                    noticeMapper.deleteNoticeById(Integer.valueOf(paramValues[i]));
                }
            }
        }
        return Result.success("删除成功");
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/8 9:11
     * @Company mitesofor
     * @Description:~停用公告
     */
    @RequestMapping("/stop")
    public Result stop(HttpServletRequest request) {
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 0) {
                return Result.error("参数错误");
            } else {
                for (int i = 0; i < paramValues.length; i++) {
                    noticeMapper.stopOrEnable(2, Integer.valueOf(paramValues[i]));
                }
            }
        }
        return Result.success("停用成功");
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/5/8 9:13
     * @Company mitesofor
     * @Description:~启用公告
     */
    public Result enable(HttpServletRequest request) {
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 0) {
                return Result.error("参数错误");
            } else {
                for (int i = 0; i < paramValues.length; i++) {
                    noticeMapper.stopOrEnable(1, Integer.valueOf(paramValues[i]));
                }
            }
        }
        return Result.success("启用成功");
    }
}

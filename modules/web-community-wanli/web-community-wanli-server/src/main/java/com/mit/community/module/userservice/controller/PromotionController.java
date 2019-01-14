package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.Promotion;
import com.mit.community.entity.SysUser;
import com.mit.community.service.PromotionService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 促销
 *
 * @author shuyy
 * @date 2018/12/27
 * @company mitesofor
 */
@RequestMapping(value = "/promotion")
@RestController
@Slf4j
@Api(tags = "促销")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private RedisService redisService;

    /**
     *
     * @param request request
     * @param promotionType 促销类型
     * @author shuyy
     * @date 2018/12/27 14:58
     * @company mitesofor
    */
    @PostMapping("/save")
    @ApiOperation(value = "发布促销", notes = "输入参数：promotionType 促销类型，关联数据字典code   promotion_type，" +
            "title 标题， img 图片， issuer 发布人， issuerPhone 发布电话，" +
            "promotionAddress 地址， issueTime 发布时间，discount 折扣率，activityContent 活动内容，" +
            "startTime 开始时间， endTime 结束时间, content 内容  ")
    public Result save(HttpServletRequest request, String promotionType, String title, MultipartFile img, String issuer, String issuerPhone,
                       String promotionAddress, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime issueTime, Float discount,
                       String activityContent,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime, String content) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String imgUrl = UploadUtil.upload(img);
        promotionService.save(promotionType, title, imgUrl, issuer, issuerPhone, promotionAddress, issueTime, discount,
                activityContent, startTime, endTime, user.getCommunityCode(), content);
        return Result.success("发布成功");
    }

    /**
     *
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/27 14:58
     * @company mitesofor
     */
    @PatchMapping("/update")
    @ApiOperation(value = "更新促销", notes = "输入参数：promotionType 促销类型，关联数据字典code   promotion_type，" +
            "title 标题， img 图片， issuer 发布人， issuerPhone 发布电话，" +
            "promotionAddress 地址， issueTime 发布时间，discount 折扣率，activityContent 活动内容，" +
            "startTime 开始时间， endTime 结束时间, content 内容  ")
    public Result update(Integer id, String promotionType, String title, MultipartFile img, String issuer, String issuerPhone,
                       String promotionAddress, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime issueTime, Float discount,
                       String activityContent,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime, String content) throws Exception {
        String imgUrl = null;
        if(img != null){
            imgUrl = UploadUtil.upload(img);
        }
        promotionService.update(id, promotionType, title, imgUrl, issuer,
                issuerPhone, promotionAddress,
                issueTime, discount, activityContent, startTime, endTime,
                content);
        return Result.success("更新成功");
    }

    /**
     *
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/27 14:58
     * @company mitesofor
     */
    @PatchMapping("/remove")
    @ApiOperation(value = "删除促销", notes = "输入参数：id")
    public Result remove(Integer id) throws Exception {
       promotionService.remove(id);
        return Result.success("删除成功");
    }

    @PatchMapping("/listPage")
    @ApiOperation(value = "分页查询促销", notes = "输入参数：id")
    public Result listPage(HttpServletRequest request,
                         String promotionType, String title, String issuer, String issuerPhone,
                         LocalDateTime issueTimeStart, LocalDateTime issueTimeEnd,
                         LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        Page<Promotion> page = promotionService.listPage(user.getCommunityCode(), promotionType, title, issuer, issuerPhone, issueTimeStart, issueTimeEnd,
                startTime, endTime, pageNum, pageSize);
        return Result.success(page);
    }


}

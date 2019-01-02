package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.LostFound;
import com.mit.community.entity.SysUser;
import com.mit.community.service.LostFoundService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.FastDFSClient;
import com.mit.community.util.Result;
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
 * 失物招领
 *
 * @author shuyy
 * @date 2018/12/26
 * @company mitesofor
 */
@RequestMapping(value = "/lostFound")
@RestController
@Slf4j
@Api(tags = "失物招领")
public class LostFoudController {

    @Autowired
    private LostFoundService lostFoundService;
    @Autowired
    private RedisService redisService;


    /**
     * @param request     request
     * @param title       标题
     * @param img         图片
     * @param issuer      发布人
     * @param issuerPhone 发布电话
     * @param picAddress  捡到地址
     * @param content     内容
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/27 11:34
     * @company mitesofor
     */
    @PostMapping("/save")
    @ApiOperation(value = "发布失物招领", notes = "输入参数：title 标题， img 图片， issuer 发布人， issuerPhone 发布电话，" +
            "picAddress 捡到地址， pickTime 捡到时间，content 内容 ")
    public Result save(HttpServletRequest request, String title, MultipartFile img, String issuer,
                       String issuerPhone,
                       String picAddress,
                       String receiverAddress,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime pickTime, String content) throws Exception {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String imgUrl = FastDFSClient.getInstance().uploadFile(img);
        lostFoundService.save(title, imgUrl, issuer, issuerPhone, picAddress,
                receiverAddress, pickTime, user.getCommunityCode(), content);
        return Result.success("发布成功");
    }

    /**
     * 更新
     *
     * @param title           标题
     * @param img             图片
     * @param issuer          发布人
     * @param issuerPhone     发布电话
     * @param picAddress      捡到地址
     * @param pickTime       捡到时间
     * @param receiver        领取人
     * @param receivePhone    领取电话
     * @param receiverAddress 领取地址
     * @param receiverTime    领取时间
     * @param receiverStatus  领取状态
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/27 11:47
     * @company mitesofor
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改失物招领", notes = "输入参数：title 标题， img 图片， issuer 发布人， issuerPhone 发布电话，" +
            "picAddress 捡到地址， pickTime 捡到时间， receiver 领取人，receivePhone 领取电话，receiverAddress 领取地址，receiverTime 领取时间，receiverStatus 领取状态（1、未领取；2、已领取）, content 内容 ")
    public Result update(Integer id, String title, MultipartFile img, String issuer, String issuerPhone,
                         String picAddress, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime pickTime,
                         String receiver, String receivePhone,
                         String receiverAddress, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime receiverTime, Boolean receiverStatus, String content) throws Exception {
        String imgUrl = null;
        if (img != null) {
            imgUrl = FastDFSClient.getInstance().uploadFile(img);
        }
        lostFoundService.update(id, title, imgUrl, issuer, issuerPhone, picAddress,
                pickTime, receiver, receivePhone, receiverAddress, receiverTime, receiverStatus, content);
        return Result.success("操作成功");
    }

    /**
     * 删除
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/27 11:51
     * @company mitesofor
    */
    @DeleteMapping("/remove")
    @ApiOperation(value = "删除失物招领", notes = "输入参数：id id")
    public Result remove(Integer id) {
        lostFoundService.remove(id);
        return Result.success("删除成功");
    }

    /**
     *
     * @param request
     * @param title 标题
     * @param issuer 发布人
     * @param issuerPhone 发布电话
     * @param receiver 领取人
     * @param receivePhone 领取电话
     * @param receiverTimeStart 领取开始时间
     * @param receiverTimeEnd 领取结束时间
     * @param receiverStatus 领取状态
     * @param pageNum 当前页
     * @param pageSize 分页大小
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/27 11:56
     * @company mitesofor
    */
    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询失物招领", notes = "输入参数：title 标题，issuer 发布人， issuerPhone 发布电话，pickTimeStart 捡到开始时间，pickTimeEnd 捡到结束时间，" +
            "receiver 领取人，receivePhone 领取电话，receiverTimeStart 领取开始时间， receiverTimeEnd 领取结束时间  ")
    public Result listPage(HttpServletRequest request,String title, String issuer, String issuerPhone,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime pickTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime pickTimeEnd,
                           String receiver, String receivePhone,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime receiverTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime receiverTimeEnd,
                         Integer receiverStatus, Integer pageNum, Integer pageSize) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        Page<LostFound> page = lostFoundService.listPage(user.getCommunityCode(), title, issuer, issuerPhone,
                pickTimeStart, pickTimeEnd,
                receiver, receivePhone, receiverTimeStart, receiverTimeEnd,
                receiverStatus, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 获取详情
     * @param id id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/27 13:50
     * @company mitesofor
    */
    @GetMapping("/getById")
    @ApiOperation(value = "获取详情", notes = "输入参数：id id")
    public Result getById(Integer id) {
        LostFound lostFount = lostFoundService.getLostFountInfo(id);
        return Result.success(lostFount);
    }


}
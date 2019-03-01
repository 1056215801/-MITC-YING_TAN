package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.OldMedical;
import com.mit.community.entity.SysUser;
import com.mit.community.service.OldMedicalService;
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

/**
 * 老人体检
 * @author Mr.Deng
 * @date 2019/1/2 16:49
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@RequestMapping(value = "/OldMedical")
@RestController
@Slf4j
@Api(tags = "老人体检")
public class OldMedicalController {

    private final OldMedicalService oldMedicalService;
    private final RedisService redisService;

    @Autowired
    public OldMedicalController(OldMedicalService oldMedicalService, RedisService redisService) {
        this.oldMedicalService = oldMedicalService;
        this.redisService = redisService;
    }

    /**
     * 添加老人体检信息
     * @param title     标题
     * @param contacts  联系人
     * @param phone     联系人手机号
     * @param address   登记地址
     * @param startTime 活动开始时间
     * @param endTime   活动结束时间
     * @param content   活动详情
     * @return result
     * @author Mr.Deng
     * @date 17:09 2019/1/2
     */
    @PostMapping("/save")
    @ApiOperation(value = "发布老人体检", notes = "输入参数：title 标题，contacts 联系人，phone 联系人手机号,address 活动地址，" +
            "content 活动详情，startTime 开始时间（yyyy-MM-dd HH:mm:ss），endTime 结束时间（yyyy-MM-dd HH:mm:ss）")
    public Result save(HttpServletRequest request, String title, String contacts, String phone, String address, String content,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        oldMedicalService.save(user.getCommunityCode(), title, user.getAdminName(), contacts, phone, address, startTime, endTime, content);
        return Result.success("发布成功");
    }

    /**
     * 更新数据
     * @param id        老人体检id
     * @param title     标题
     * @param contacts  联系人
     * @param phone     联系人手机号
     * @param address   地址
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param content   详情
     * @return result
     * @author Mr.Deng
     * @date 17:26 2019/1/2
     */
    @PatchMapping("/update")
    @ApiOperation(value = "更新老人体检数据", notes = "输入参数：title 标题，contacts 联系人，phone 联系人手机号,address 活动地址，" +
            " content 活动详情，startTime 开始时间（yyyy-MM-dd HH:mm:ss），endTime 结束时间（yyyy-MM-dd HH:mm:ss）")
    public Result update(Integer id, String title, String contacts, String phone, String address,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime, String content) {
        oldMedicalService.update(id, title, contacts, phone, address, startTime, endTime, content);
        return Result.success("更新成功");
    }

    /**
     * 删除老人体检
     * @param oldMedicalId 老人体检id
     * @return result
     * @author Mr.Deng
     * @date 17:37 2019/1/2
     */
    @PatchMapping("/remove")
    @ApiOperation(value = "删除老人体检", notes = "输入参数：oldMedicalId 老人体检id")
    public Result remove(Integer oldMedicalId) {
        oldMedicalService.remove(oldMedicalId);
        return Result.success("删除成功");
    }

    /**
     * 分页
     * @param request         request
     * @param title           标题
     * @param issuer          发布人
     * @param issuerTimeStart 发布时间开始
     * @param issuerTimeEnd   发布时间结束
     * @param status          活动状态：1、未开始 2、进行中 3、已结束
     * @param contacts        联系人
     * @param phone           联系人手机号
     * @param address         登记地址
     * @param startTime       活动开始时间开始
     * @param startTimeLast   活动开始时间结束
     * @param endTime         活动结束时间开始
     * @param endTimeLast     活动结束时间结束
     * @param pageNum         页数
     * @param pageSize        一页显示数量
     * @return result
     * @author Mr.Deng
     * @date 9:14 2019/1/3
     */
    @GetMapping("/listPage")
    @ApiOperation(value = "分页查询老人体检", notes = "输入参数：title 标题，issuer 发布人，contacts 联系人，phone 联系人手机号，" +
            "address 活动地址,status 活动状态：1、未开始 2、进行中 3、已结束" +
            "<br/>issuerTimeStart 发布开始时间（yyyy-MM-dd HH:mm:ss）" +
            "<br/>issuerTimeEnd 发布结束时间（yyyy-MM-dd HH:mm:ss）" +
            "<br/>startTime  活动开始时间开始（yyyy-MM-dd HH:mm:ss）" +
            "<br/>startTimeLast 活动开始时间结束（yyyy-MM-dd HH:mm:ss）" +
            "<br/>endTime 活动结束时间开始（yyyy-MM-dd HH:mm:ss）" +
            "<br/>endTimeLast 活动结束时间结束（yyyy-MM-dd HH:mm:ss）")
    public Result listPage(HttpServletRequest request, String title, String issuer,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime issuerTimeStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime issuerTimeEnd, Integer status,
                           String contacts, String phone, String address, Integer pageNum, Integer pageSize,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTimeLast,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTimeLast) {
        if (pageSize != null && pageNum != null) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            Page<OldMedical> page = oldMedicalService.listPage(user.getCommunityCode(), title, issuer, issuerTimeStart, issuerTimeEnd, status, contacts,
                    phone, address, startTime, startTimeLast, endTime, endTimeLast, pageNum, pageSize);
            return Result.success(page);
        }
        return Result.error("分页参数不能为空");
    }

    /**
     * 查询详情
     * @param id id
     * @return result
     * @author Mr.Deng
     * @date 9:23 2019/1/3
     */
    @GetMapping("/getById")
    @ApiOperation(value = "查询详情", notes = "输入参数：id 老人体检id")
    public Result getById(Integer id) {
        OldMedical oldMedical = oldMedicalService.getByOldMedicalId(id);
        oldMedical.setReadNum(null);
        return Result.success(oldMedical);
    }

}

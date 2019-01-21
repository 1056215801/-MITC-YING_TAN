package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.Constants;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.ExpressAddress;
import com.mit.community.entity.ExpressInfo;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.User;
import com.mit.community.service.ExpressAddressService;
import com.mit.community.service.ExpressInfoService;
import com.mit.community.service.RedisService;
import com.mit.community.service.UserService;
import com.mit.community.util.CookieUtils;
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
import java.time.LocalDateTime;

/**
 * 社区快递控制类
 * @author Mr.Deng
 * @date 2018/12/26 15:49
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@RequestMapping(value = "/expressAddress")
@RestController
@Slf4j
@Api(tags = "社区快递")
public class ExpressContorller {
    @Autowired
    private ExpressAddressService expressAddressService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ExpressInfoService expressInfoService;
    @Autowired
    private UserService userService;

    /**
     * 添加快递领取地址信息
     * @param request request
     * @param name    快递名
     * @param address 领取地址
     * @param image   快递图标
     * @return result
     * @author Mr.Deng
     * @date 16:05 2018/12/26
     */
    @PostMapping("/saveExpressAddress")
    @ApiOperation(value = "添加快递领取地址信息", notes = "输入参数：name 快递名称；address 领取地址；image 图片")
    public Result saveExpressAddress(HttpServletRequest request, String name, String address, MultipartFile image) throws Exception {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(address) && image != null) {
            String imageUrl = UploadUtil.upload(image);
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            expressAddressService.save(user.getCommunityCode(), name, address, imageUrl, user.getAdminName());
            return Result.success("添加成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 修改快递位置信息
     * @param request request
     * @param id      id
     * @param name    快递名
     * @param address 领取位置
     * @param image   图片
     * @return result
     * @author Mr.Deng
     * @date 17:15 2018/12/26
     */
    @PatchMapping("/'updateExpressAddress")
    @ApiOperation(value = "修改快递位置信息", notes = "输入参数：id 快递位置id ；name 快递名；address 领取位置 ；image 图片")
    public Result updateExpressAddress(HttpServletRequest request, Integer id, String name, String address, MultipartFile image) throws Exception {
        if (id != null) {
            String imageUrl = null;
            if (image != null) {
                imageUrl = UploadUtil.upload(image);
            }
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            expressAddressService.update(id, name, address, imageUrl, user.getAdminName());
            return Result.success("更新成功");
        }
        return Result.error("id不能为空");
    }

    /**
     * 删除快递位置信息
     * @param id 快递位置信息 id
     * @return result
     * @author Mr.Deng
     * @date 17:33 2018/12/26
     */
    @DeleteMapping("/removeExpressAddress")
    @ApiOperation(value = "删除快递位置信息", notes = "输入参数： id 快递位置信息id")
    public Result removeExpressAddress(Integer id) {
        if (id != null) {
            expressAddressService.remove(id);
            return Result.success("删除成功");
        }
        return Result.error("id不能为空");
    }

    /**
     * 分页获取本小区快递地址信息
     * @param name           快递名称
     * @param address        快递地址
     * @param createUserName 领取位置
     * @param pageNum        页数
     * @param pageSize       一页数量
     * @return result
     * @author Mr.Deng
     * @date 9:26 2018/12/27
     */
    @GetMapping("/listPageExpressAddress")
    @ApiOperation(value = "分页获取小区快递地址信息", notes = "输入参数：name 快递名；address 领取位置 ；createUserName 添加人；pageNum 页数； pageSize 一页数量")
    public Result listPageExpressAddress(HttpServletRequest request, String name, String address, String createUserName,
                                         Integer pageNum, Integer pageSize) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        Page<ExpressAddress> page = expressAddressService.listPage(user.getCommunityCode(), name, address, createUserName, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 添加快递信息
     * @param request          request
     * @param cellphone        电话号码
     * @param expressAddressId 快递领取位置id
     * @param waybillNum       运单编号
     * @return result
     * @author Mr.Deng
     * @date 17:26 2018/12/27
     */
    @PostMapping("/saveExpressInfo")
    @ApiOperation(value = "添加快递信息", notes = "输入参数：cellphone 快递手机号; expressAddressId 快递位置id; waybillNum 运单编号")
    public Result saveExpressInfo(HttpServletRequest request, String cellphone, Integer expressAddressId, String waybillNum) {
        if (StringUtils.isNotBlank(cellphone) && expressAddressId != null && StringUtils.isNotBlank(waybillNum)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            User user1 = userService.getByCellphone(cellphone);
            if (user1 != null) {
                ExpressInfo expressInfo = new ExpressInfo(user.getCommunityCode(), user1.getId(), expressAddressId, waybillNum, 2, Constants.NULL_LOCAL_DATE_TIME,
                        StringUtils.EMPTY, StringUtils.EMPTY, user.getAdminName(), null);
                expressInfoService.save(expressInfo);
                return Result.success("快递信息添加成功");
            }
            return Result.error("添加失败,手机号用户没有注册账号");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 修改快递信息数据
     * @param request          request
     * @param id               快递信息id
     * @param userId           app用户id
     * @param waybillNum       运单编号
     * @param expressAddressId 快递领取位置id
     * @param receiver         领取人姓名
     * @param receiverPhone    领取人电话号码
     * @return result
     * @author Mr.Deng
     * @date 8:57 2018/12/28
     */
    @PatchMapping("/updateExpressInfo")
    @ApiOperation(value = "修改快递信息数据", notes = "输入参数：（id必传参数，其余看需求）<br/>id 快递信息id " +
            "<br/>userId app用户id " +
            "<br/>waybillNum 运单编号" +
            "<br/>expressAddressId 快递领取位置id " +
            "<br/>receiver 领取人姓名" +
            "<br/>receiverPhone 领取人电话号码")
    public Result updateExpressInfo(HttpServletRequest request, Integer id, Integer userId, String waybillNum,
                                    Integer expressAddressId, String receiver, String receiverPhone) {
        if (id != null) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            expressInfoService.update(id, userId, waybillNum, expressAddressId, user.getAdminName(), receiver, receiverPhone);
            return Result.success("修改成功");
        }
        return Result.error("id不能为空");
    }

    /**
     * 删除快递信息，根据快递信息id
     * @param id 快递信息id
     * @return result
     * @author Mr.Deng
     * @date 9:26 2018/12/28
     */
    @DeleteMapping("/removeExpressInfo")
    @ApiOperation(value = "删除快递信息，根据快递信息id", notes = "输入参数：id 快递信息id")
    public Result removeExpressInfo(Integer id) {
        if (id != null) {
            expressInfoService.remove(id);
            return Result.success("删除成功");
        }
        return Result.error("id不能为空");
    }

    /**
     * 分页查询快递信息
     * @param request          request
     * @param userId           app用户id
     * @param expressAddressId 快递位置信息id
     * @param waybillNum       运单号
     * @param receiveStatus    领取状态1、已领取2、未领取
     * @param receiveTimeStart 领取开始时间 yyyy-MM-dd HH:mm:ss
     * @param receiveTimeEnd   领取结束时间 yyyy-MM-dd HH:mm:ss
     * @param receiver         领取人
     * @param receiverPhone    领取人手机号
     * @param createUserName   添加人
     * @param pageNum          页数
     * @param pageSize         一页数量
     * @return result
     * @author Mr.Deng
     * @date 9:59 2018/12/28
     */
    @GetMapping("/expressInfoPage")
    @ApiOperation(value = "分页查询快递信息", notes = "输入参数：" +
            "<br/>userId           app用户id" +
            "<br/> expressAddressId 快递位置信息id" +
            "<br/>waybillNum       运单号" +
            "<br/>receiveStatus    领取状态1、已领取2、未领取" +
            "<br/>receiveTimeStart 领取开始时间 yyyy-MM-dd HH:mm:ss" +
            "<br/>receiveTimeEnd   领取结束时间 yyyy-MM-dd HH:mm:ss" +
            "<br/>receiver         领取人" +
            "<br/>receiverPhone    领取人手机号" +
            "<br/>createUserName   添加人" +
            "<br/>pageNum          页数" +
            "<br/>pageSize         一页数量")
    public Result expressInfoPage(HttpServletRequest request, Integer userId, Integer expressAddressId, String waybillNum, Integer receiveStatus,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime receiveTimeStart,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime receiveTimeEnd,
                                  String receiver, String receiverPhone, String createUserName, Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            Page<ExpressInfo> page = expressInfoService.listPage(user.getCommunityCode(), userId, expressAddressId, waybillNum,
                    receiveStatus, receiveTimeStart, receiveTimeEnd, receiver, receiverPhone, createUserName, pageNum, pageSize);
            return Result.success(page);
        }
        return Result.error("分页参数不能为空");
    }

}

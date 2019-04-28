package com.mit.community.module.pass.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.ApplyKey;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.SysUser;
import com.mit.community.entity.User;
import com.mit.community.feigin.PassThroughFeign;
import com.mit.community.service.*;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import com.mit.community.util.ThreadPoolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 住户-通行
 *
 * @author shuyy
 * @date 2018/12/14 15:18
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/passThrough")
@Slf4j
@Api(tags = "住户-通行模块接口")
public class PassThroughController {


    private final ApplyKeyService applyKeyService;

    private final RedisService redisService;

    private final AppUserService appUserService;

    private final UserService userService;

    private final DnakeAppApiService dnakeAppApiService;

    private final PassThroughFeign passThroughFeign;

    private final HouseHoldService houseHoldService;


    @Autowired
    public PassThroughController(ApplyKeyService applyKeyService,
                                 RedisService redisService,
                                 UserService userService,
                                 AppUserService appUserService,
                                 DnakeAppApiService dnakeAppApiService,
                                 PassThroughFeign passThroughFeign,
                                 HouseHoldService houseHoldService) {
        this.applyKeyService = applyKeyService;
        this.redisService = redisService;
        this.appUserService = appUserService;
        this.userService = userService;
        this.dnakeAppApiService = dnakeAppApiService;
        this.passThroughFeign = passThroughFeign;
        this.houseHoldService = houseHoldService;
    }

    /**
     * 查询设备列表
     *
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-23 13:57
     * @company mitesofor
     */
    @GetMapping("/listDeviceGroup")
    @ApiOperation(value = "设备组列表")
    public Result listDeviceGroup(HttpServletRequest request) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        Result result = null;
        try {
            result = passThroughFeign.getDeviceGroup(sysUser.getCommunityCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 审批钥匙
     *
     * @param request           request
     * @param applyKeyId        申请钥匙记录id
     * @param residenceTime     过期时间
     * @param deviceGroupIdList 设备组id列表
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 10:02
     * @company mitesofor
     */
    @PostMapping("/approveKey")
    @ApiOperation(value = "审批钥匙", notes = "传参：applyKeyId 申请钥匙id，residenceTime 申请钥匙记录id," +
            " deviceGroupIdList 设备组id列表")
    public Result approveKey(HttpServletRequest request, Integer applyKeyId,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate residenceTime,
                             @RequestParam("deviceGroupIdList[]") List<String> deviceGroupIdList) {
        // 更新申请钥匙记录
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String checkPerson = sysUser.getAdminName();
        String status = applyKeyService.approval(applyKeyId, checkPerson, residenceTime,
                deviceGroupIdList);
        if (!"success".equals(status)) {
            return Result.error(status);
        }
        ThreadPoolUtil.execute(new Thread(() -> {
            ApplyKey applyKey = applyKeyService.selectById(applyKeyId);
            Integer creatorUserId = applyKey.getCreatorUserId();
            User user = userService.getById(creatorUserId);
            passThroughFeign.hoseholdUpdate(user.getCellphone());
        }));
        return Result.success("审批成功");
    }

    /**
     * 审批钥匙
     *
     * @param applyKeyId 申请钥匙记录id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 10:02
     * @company mitesofor
     */
    @PostMapping("/refuseKey")
    @ApiOperation(value = "拒绝审批钥匙", notes = "传参：applyKeyId 申请钥匙id")
    public Result refuseKey(Integer applyKeyId) {
        // 更新申请钥匙记录
        ApplyKey applyKey = new ApplyKey();
        applyKey.setId(applyKeyId);
        applyKey.setStatus(3);
        applyKeyService.update(applyKey);
        return Result.success("拒绝审批成功");
    }

    /**
     * 分页查询申请钥匙信息
     *
     * @param zoneId           分区id
     * @param buildingId       楼栋id
     * @param unitId           单元id
     * @param roomId           房间id
     * @param contactPerson    联系人
     * @param contactCellphone 联系人电话
     * @param status           状态
     * @param pageNum          当前页
     * @param pageSize         分页大小
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/14 16:36
     * @company mitesofor
     */
    @GetMapping("/listApplyKeyPage")
    @ApiOperation(value = "分页查询申请钥匙数据", notes = "输入参数：为空则不作为过滤条件。<br/>" +
            "zoneId 分区id，buildingId 楼栋id, unitId 单元id, roomId 房间id, contactPerson 联系人," +
            " contactCellphone 联系人手机号；status 1、申请中，2、审批通过； pageNum 当前页； pageSize 分页大小")
    public Result listApplyKeyPage(HttpServletRequest request, Integer zoneId,
                                   String communityCode, Integer buildingId, Integer unitId,
                                   Integer roomId, String contactPerson, String contactCellphone, Integer status,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateStart,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateEnd, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        Page<ApplyKey> page = applyKeyService.listByPage(null, communityCode, zoneId, buildingId, unitId, roomId, contactPerson, contactCellphone, status, gmtCreateStart, gmtCreateEnd, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 分页查询住户信息
     *
     * @param request
     * @param zoneId
     * @param communityCode
     * @param buildingId
     * @param unitId
     * @param roomId
     * @param contactPerson
     * @param contactCellphone
     * @param status
     * @param search_validEndFlag
     * @param select_autyType
     * @return
     */
    @GetMapping("/listHouseholdByCommunityCode")
    @ApiOperation(value = "分页查询住户信息数据", notes = "输入参数：为空则不作为过滤条件。<br/>" +
            "zoneId 分区id，buildingId 楼栋id, unitId 单元id, roomId 房间id, contactPerson 联系人," +
            " contactCellphone 联系人手机号；status 0-注销，1-正常；search_validEndFlag 有效期字段；select_autyType 授权类型；" +
            "pageNum 当前页； pageSize 分页大小")
    public Result listHouseholdByCommunityCode(HttpServletRequest request,
                                               Integer zoneId,
                                               String communityCode,
                                               Integer buildingId,
                                               Integer unitId,
                                               Integer roomId,
                                               String contactPerson,
                                               String contactCellphone,
                                               Integer householdType,
                                               Integer status,
                                               Integer search_validEndFlag,//有效期标识：1-即将到期，2-已过期
                                               Integer select_autyType,//授权类型（二进制相加而来，查询时后台需要拆分）
                                               Integer pageNum, Integer pageSize) {
        //逻辑代码
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        //分页查询
        Page<HouseHold> page = houseHoldService.listHouseholdByPage(request, zoneId, communityCode, buildingId, unitId, roomId, contactPerson, contactCellphone, householdType, status,
                search_validEndFlag, select_autyType, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 保存住户房屋信息
     *
     * @param request
     * @param response
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/saveHouseholdInfoByStepOne", method = RequestMethod.POST)
    @ApiOperation(value = "保存住户房屋信息", notes = "输入参数：为空则不作为过滤条件。<br/>" +
            "houseProperties 房屋数组，roomIdArr 房屋ID数组, householdStr 住户信息, certificateStr 身份信息, householdId 住户id," +
            "realNameFlag 实名标记；realCertificateStr 未成年人身份；mobile 住户手机；contactType 联系人类型")
    public Integer SaveHouseholdInfoByStepOne(HttpServletRequest request,
                                              HttpServletResponse response,
                                              String communityCode,
                                              @RequestBody JSONObject jsonObject) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        String msg = houseHoldService.SaveHouseholdInfoByStepOne(communityCode, jsonObject);
        if (!msg.contains("success")) {
            return -1;
        }
        return 1;
    }

    /**
     * 保存住户授权信息
     *
     * @param householdId
     * @param appAuthFlag
     * @param directCall
     * @param tellNum
     * @param fileNames
     * @param faceAuthFlag
     * @param deviceGIds
     * @param validityEndDate
     * @param initValidityEndDate
     * @param csReturn
     * @return
     */
    @RequestMapping(value = "/saveHouseholdInfoByStepThree", method = RequestMethod.POST)
    @ApiOperation(value = "保存住户授权信息")
    public Integer SaveHouseholdInfoByStepThree(Integer editFlag,
                                                Integer householdId,
                                                Integer appAuthFlag,
                                                Integer directCall,
                                                String tellNum,
                                                String fileNames,
                                                Integer faceAuthFlag,
                                                String deviceGIds,
                                                String validityEndDate,
                                                String initValidityEndDate,
                                                Boolean csReturn,
                                                String cardListArr) {
        String msg = houseHoldService.SaveHouseholdInfoByStepThree(editFlag, householdId, appAuthFlag, directCall, tellNum,
                faceAuthFlag, deviceGIds, validityEndDate, cardListArr);
        if (!msg.contains("success")) {
            return -1;
        }
        return 1;
    }

    /**
     * 注销住户
     *
     * @param request
     * @param communityCode
     * @param ids
     * @return
     */
    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    @ApiOperation(value = "注销住户", notes = "参数：住户id数组")
    public Integer LogOutHousehold(HttpServletRequest request, String communityCode, String ids) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        String msg = houseHoldService.logOut(communityCode, ids);
        if (!msg.contains("success")) {
            return -1;
        }
        return 1;
    }

    /**
     * 停用住户
     *
     * @param request
     * @param communityCode
     * @param id
     * @return
     */
    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    @ApiOperation(value = "停用住户", notes = "参数：住户id")
    public Integer Stop(HttpServletRequest request, String communityCode, Integer id) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        String msg = houseHoldService.Stop(communityCode, id);
        if (!msg.contains("success")) {
            return -1;
        }
        return 1;
    }

    /**
     * @param communityCode 小区code
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-22 9:31
     * @company mitesofor
     */
    @ApiOperation(value = "查询分区信息，通过小区code")
    @GetMapping("/listZoneByCommunityCode")
    public Result listZoneByCommunityCode(String communityCode, HttpServletRequest request, HttpSession session) {
        Object hello = session.getAttribute("hello");
        System.out.println(hello);
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        return passThroughFeign.listZoneByCommunityCode(communityCode);
    }

    /**
     * @param zoneId 分区id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-22 9:31
     * @company mitesofor
     */
    @ApiOperation(value = "查询楼栋，通过分区id")
    @GetMapping("/listBuildingByZoneId")
    public Result listBuildingByZoneId(Integer zoneId) {
        return passThroughFeign.listBuildingByZoneId(zoneId);
    }


    /**
     * @param buildingId 楼栋id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-22 9:35
     * @company mitesofor
     */
    @ApiOperation(value = "查询单元信息，通过楼栋id")
    @GetMapping("/listUnitByBuildingId")
    public Result listUnitByBuildingId(Integer buildingId) {
        return passThroughFeign.listUnitByBuildingId(buildingId);
    }

    /**
     * @param unitId 单元id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-22 9:36
     * @company mitesofor
     */
    @ApiOperation(value = "查询房间信息，通过单元id")
    @GetMapping("/listRoomByUnitId")
    public Result listRoomByUnitId(Integer unitId) {
        return passThroughFeign.listRoomByUnitId(unitId);
    }

    @ApiOperation(value = "查询设备组列表，通过社区编号")
    @GetMapping("/getDeviceGroupList")
    public Result getDeviceGroupList(HttpServletRequest request, String communityCode) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        return houseHoldService.getDeviceGroupList(communityCode);
    }

    /**
     * 修改手机号码
     *
     * @param mobile
     * @param householdId
     * @return
     */
    @RequestMapping("/alterMobile")
    @ApiOperation(value = "修改手机号码")
    public Integer AlterMobile(String mobile, Integer householdId) {
        String msg = houseHoldService.AlterMobile(mobile, householdId);
        if (!msg.contains("success")) {
            return -1;
        }
        return 1;
    }

}

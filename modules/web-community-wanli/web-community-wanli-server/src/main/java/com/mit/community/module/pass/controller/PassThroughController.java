package com.mit.community.module.pass.controller;

import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.ApplyKey;
import com.mit.community.entity.SysUser;
import com.mit.community.feigin.PassThroughFeign;
import com.mit.community.service.ApplyKeyService;
import com.mit.community.service.DnakeAppApiService;
import com.mit.community.service.RedisService;
import com.mit.community.service.AppUserService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    private final AppUserService userService;

    private final DnakeAppApiService dnakeAppApiService;

    private final PassThroughFeign passThroughFeign;

    @Autowired
    public PassThroughController(ApplyKeyService applyKeyService, RedisService redisService, AppUserService userService, DnakeAppApiService dnakeAppApiService, PassThroughFeign passThroughFeign) {
        this.applyKeyService = applyKeyService;
        this.redisService = redisService;
        this.userService = userService;
        this.dnakeAppApiService = dnakeAppApiService;
        this.passThroughFeign = passThroughFeign;
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
    @PatchMapping("/approveKey")
    @ApiOperation(value = "审批钥匙", notes = "传参：applyKeyId 申请钥匙id，residenceTime 申请钥匙记录id," +
            " deviceGroupIdList 设备组id列表")
    public Result approveKey(HttpServletRequest request, Integer applyKeyId,
                             String residenceTime, @RequestParam("deviceGroupIdList") List<String> deviceGroupIdList) {
        // 更新申请钥匙记录
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String checkPerson = sysUser.getAdminName();
        String status = applyKeyService.approval(applyKeyId, checkPerson, residenceTime,
                deviceGroupIdList);
        if (!"success".equals(status)) {
            return Result.error(status);
        }
        return Result.success("审批成功");
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
                                   Integer roomId, String contactPerson, String contactCellphone, Integer status, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        List<ApplyKey> list = applyKeyService.listByPage(null, communityCode, zoneId, buildingId, unitId, roomId, contactPerson, contactCellphone, status, pageNum, pageSize);
        return Result.success(list);
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
    public Result listZoneByCommunityCode(String communityCode) {
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


}

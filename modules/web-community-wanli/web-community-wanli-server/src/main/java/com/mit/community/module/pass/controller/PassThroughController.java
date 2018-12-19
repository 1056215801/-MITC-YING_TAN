package com.mit.community.module.pass.controller;

import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.ApplyKey;
import com.mit.community.entity.SysUser;
import com.mit.community.service.ApplyKeyService;
import com.mit.community.service.DnakeAppApiService;
import com.mit.community.service.RedisService;
import com.mit.community.service.UserService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

    private final UserService userService;

    private final DnakeAppApiService dnakeAppApiService;

    @Autowired
    public PassThroughController(ApplyKeyService applyKeyService, RedisService redisService, UserService userService, DnakeAppApiService dnakeAppApiService) {
        this.applyKeyService = applyKeyService;
        this.redisService = redisService;
        this.userService = userService;
        this.dnakeAppApiService = dnakeAppApiService;
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
        String checkPerson = sysUser.getName();
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
    public Result listApplyKeyPage(Integer zoneId, Integer buildingId, Integer unitId,
                                   Integer roomId, String contactPerson, String contactCellphone, Integer status, Integer pageNum, Integer pageSize) {
        String communityCode = "";
        List<ApplyKey> list = applyKeyService.listByPage(null, communityCode, zoneId, buildingId, unitId, roomId, contactPerson, contactCellphone, status, pageNum, pageSize);
        return Result.success(list);
    }
}

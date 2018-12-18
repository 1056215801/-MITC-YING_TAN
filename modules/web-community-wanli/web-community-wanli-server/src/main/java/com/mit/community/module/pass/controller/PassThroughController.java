package com.mit.community.module.pass.controller;

import com.mit.community.entity.ApplyKey;
import com.mit.community.service.ApplyKeyService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    public PassThroughController(ApplyKeyService applyKeyService) {
        this.applyKeyService = applyKeyService;
    }

    /**
     * 审批钥匙
     *
     * @param cellphone   手机号
     * @param applyKeyId  申请钥匙id
     * @param checkPerson 审批人
     * @return Result
     * @author Mr.Deng
     * @date 15:36 2018/12/3
     */
    @PatchMapping("/approveKey")
    @ApiOperation(value = "审批钥匙", notes = "传参：cellphone 手机号，applyKeyId 申请钥匙id，checkPerson 审批人 ")
    public Result approveKey(String cellphone, Integer applyKeyId, String checkPerson) {
        applyKeyService.updateByCheckPerson(applyKeyId, checkPerson);
        return Result.success("审批成功");
    }

    /**
     * 分页查询申请钥匙信息
     * @param zoneId 分区id
     * @param buildingId 楼栋id
     * @param unitId 单元id
     * @param roomId 房间id
     * @param contactPerson 联系人
     * @param contactCellphone 联系人电话
     * @param status 状态
     * @param pageNum 当前页
     * @param pageSize 分页大小
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
        List<ApplyKey> list = applyKeyService.listByPage(null,communityCode, zoneId, buildingId, unitId, roomId, contactPerson, contactCellphone, status, pageNum, pageSize);
        return Result.success(list);
    }
}

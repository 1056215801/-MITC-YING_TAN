package com.mit.community.module.data.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.model.ChargingStatus;
import com.mit.community.service.data.service.ChargingStatusService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 充电桩感知
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/charging_status")
@Slf4j
@Api(value = "充电桩感知", tags = {"充电桩感知"})
public class ChargingStatusController {

    @Autowired
    private ChargingStatusService chargingStatusService;

    /**
     * 分页
     * @param deviceNum
     * @param devicePalce
     * @param pageNum
     * @param pageSize
     * @return com.mit.community.rest.util.Result
     * @throws 
     * @author shuyy
     * @date 2019-01-04 14:53
     * @company mitesofor
    */
    @RequestMapping(value = "/listPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询", notes = "占用情况（充电中，空闲）" +
            "device_status 设备状态（正常，故障，掉线）")
    public Result listPage(String communityCode,
                           String deviceNum, String devicePalce, Short status,
                           Short deviceStatus,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate gmtUploadStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate gmtUploadEnd,
                           Integer pageNum,
                           Integer pageSize) {
        Page<ChargingStatus> page = chargingStatusService.listPage(communityCode, deviceNum,
                devicePalce, status, deviceStatus, gmtUploadStart, gmtUploadEnd, pageNum, pageSize);
        return Result.success(page);
    }

    @RequestMapping(value = "/insertData", method = RequestMethod.POST)
    @ApiOperation(value = "插入数据", notes = "插入数据")
    public Result insertData(){
//        chargingStatusService.delete(null);
        chargingStatusService.insertDataKXWT();
        chargingStatusService.insertDataXJB();
        chargingStatusService.insertDataNY();
        return Result.success("成功");
    }


}
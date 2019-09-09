package com.mit.community.module.device.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.SmokeDetectorStatus;
import com.mit.community.service.SmokeDetectorStatusService;

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
import java.util.List;

/**
 * 烟感感知
 *
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/smokeDetectorStatus")
@Slf4j
@Api(value = "烟感感知", tags = {"烟感感知"})
public class SmokeDetectorStatusController {

    @Autowired
    private SmokeDetectorStatusService smokeDetectorStatusService;

    /**
     * 分页
     *
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
    @ApiOperation(value = "分页查询", notes = "status 水压情况（压力不足、缺水、压力过大、水压正常）" +
            "device_status 设备状态（正常，故障，掉线）")
    public Result listPage(String communityCode,
                           String deviceNum, String devicePalce, Short status,
                           Short deviceStatus,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate gmtUploadStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate gmtUploadEnd,
                           Integer pageNum,
                           Integer pageSize) {
     /*   Page<SmokeDetectorStatus> page = smokeDetectorStatusService.listPage(communityCode, deviceNum,
                devicePalce, status, deviceStatus, gmtUploadStart, gmtUploadEnd, pageNum, pageSize);*/
      /*  return Result.success(page);*/
      return null;
    }

    @RequestMapping(value = "/insertData", method = RequestMethod.POST)
    @ApiOperation(value = "插入数据", notes = "插入数据")
    public Result insertData() {
//        smokeDetectorStatusService.delete(null);
     /*   smokeDetectorStatusService.insertDataKXWT();
        smokeDetectorStatusService.insertDataXJB();
        smokeDetectorStatusService.insertDataYWHDHY();*/
        smokeDetectorStatusService.insertDataNY();
        return Result.success("成功");
    }


   @RequestMapping(value = "/selectDeviceByOneNet", method = RequestMethod.POST)
    @ApiOperation(value = "从oneNet获取烟感设备信息", notes = "从oneNet获取烟感设备信息")
    public Result selectDeviceByThirdOnLine() {
       String keyWords="烟感";
       List<SmokeDetectorStatus> list= smokeDetectorStatusService.selectDeviceByThirdOnLine(keyWords);

        /*smokeDetectorStatusService.delete(null);
        smokeDetectorStatusService.insertDataKXWT();
        smokeDetectorStatusService.insertDataXJB();
        smokeDetectorStatusService.insertDataYWHDHY();
        smokeDetectorStatusService.insertDataNY();*/
        return Result.success(list,"成功");
    }




}
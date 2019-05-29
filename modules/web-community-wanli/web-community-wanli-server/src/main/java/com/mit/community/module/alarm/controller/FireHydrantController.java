package com.mit.community.module.alarm.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.model.FireHydrant;
import com.mit.community.service.alarm.service.FireHydrantService;
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
 * 消防栓水压报警
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/fireHydrant")
@Slf4j
@Api(value = "消防栓水压报警", tags = {"消防栓水压报警"})
public class FireHydrantController {

    @Autowired
    private FireHydrantService fireHydrantService;

    /**
     * 分页
     * @param deviceNum
     * @param devicePalce
     * @param warnType
     * @param warnStatus
     * @param gmtWarnStart
     * @param gmtWarnEnd
     * @param pageNum
     * @param pageSize
     * @return com.mit.community.rest.util.Result
     * @throws 
     * @author shuyy
     * @date 2019-01-04 14:53
     * @company mitesofor
    */
    @RequestMapping(value = "/listPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询", notes = "warnType 1、压力不足、缺水、压力过大 ")
    public Result listPage(String communityCode, String deviceNum, String devicePalce, Short warnType,
                           Short warnStatus,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate gmtWarnStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate gmtWarnEnd,
                           Integer pageNum, Integer pageSize) {

        Page<FireHydrant> page = fireHydrantService.listPage(communityCode, deviceNum,
                devicePalce,
                warnType, warnStatus,
                gmtWarnStart,
                gmtWarnEnd, pageNum, pageSize);
        return Result.success(page);
    }

    @RequestMapping(value = "/insertData", method = RequestMethod.POST)
    @ApiOperation(value = "插入数据", notes = "插入数据")
    public Result insertData(){
        fireHydrantService.delete(null);
        fireHydrantService.insertDataKXWT();
        fireHydrantService.insertDataXJB();
        fireHydrantService.insertDataYWHDHY();
        fireHydrantService.insertDataNY();
        return Result.success("成功");
    }

}
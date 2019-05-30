package com.mit.community.module.population.controller;

import com.mit.community.service.CarInfoService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 车辆信息
 */

@RequestMapping(value = "/carInfo")
@RestController
@Slf4j
@Api(tags = "车辆信息")
public class CarInfoController {
    @Autowired
    private CarInfoService carInfoService;

    @PostMapping("/save")
    @ApiOperation(value = "保存车辆信息", notes = "传参：String cph 车牌号, String cx 车型, String ys 颜色, String pp 品牌, String xh 型号, String pl 排量, String fdjh 发动机号, String jsz 驾驶证, String xsz 行驶证,"
    + "LocalDateTime szrq 生产日期, LocalDateTime gmrq 购买日期, Integer person_baseinfo_id")
    public Result save(String cph, String cx, String ys, String pp, String xh, String pl, String fdjh, String jsz, String xsz,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime szrq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime gmrq, Integer person_baseinfo_id){
        carInfoService.save(cph, cx, ys, pp, xh, pl, fdjh, jsz, xsz, szrq, gmrq, person_baseinfo_id);
        return Result.success("车辆信息保存成功");

    }
}

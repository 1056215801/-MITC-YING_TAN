package com.mit.community.module.communitymanagement.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Xingzhengquhua;
import com.mit.community.service.XingzhengquhuaService;
import com.mit.community.util.Result;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 全国行政区划信息 前端控制器
 * </p>
 *
 * @author qsj
 * @since 2019-08-26
 */
@RestController
@RequestMapping("/xingzhengquhua")
@Slf4j
@Api(tags = "行政划分")
public class XingzhengquhuaController {

    @Autowired
    private XingzhengquhuaService xingzhengquhuaService;

    @ApiOperation(value = "获取省列表")
    @PostMapping("/getProvince")
    public Result getProvince(){

        List<Xingzhengquhua> xingzhengquhuaList = xingzhengquhuaService.selectListA();
        return Result.success(xingzhengquhuaList);
    }
    @ApiOperation(value = "获取市列表")
    @PostMapping("/getCity")
    public Result getCity(String provinceId){
        List<Xingzhengquhua> xingzhengquhuaList=xingzhengquhuaService.selectCityList(provinceId);
        return Result.success(xingzhengquhuaList);
    }
    @ApiOperation(value = "获取区列表")
    @PostMapping("/getDistrict")
    public Result getDistrict(String cityId){
        List<Xingzhengquhua> xingzhengquhuaList=xingzhengquhuaService.getDistrictList(cityId);
        return Result.success(xingzhengquhuaList);
    }

    @ApiOperation(value = "获取街道列表")
    @PostMapping("/getTown")
    public Result getTown(String districtId){
        List<Xingzhengquhua> xingzhengquhuaList=xingzhengquhuaService.getTownList(districtId);
       return Result.success(xingzhengquhuaList);
    }
    @ApiOperation(value = "获取居委会列表")
    @PostMapping("/getcommittee")
    public Result getCommittee(String townId){
        List<Xingzhengquhua> xingzhengquhuaList=xingzhengquhuaService.getCommittee(townId);
        return Result.success(xingzhengquhuaList);
    }
}


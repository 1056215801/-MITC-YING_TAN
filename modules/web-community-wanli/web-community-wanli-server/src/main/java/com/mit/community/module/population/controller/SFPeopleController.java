package com.mit.community.module.population.controller;

import com.mit.community.population.service.SFPeopleService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 *上访人员
 */
@RequestMapping(value = "/ssfpeople")
@RestController
@Slf4j
@Api(tags = "上访人员信息")
public class SFPeopleController {
    @Autowired
    private SFPeopleService sFPeopleService;

    @PostMapping("/save")
    @ApiOperation(value = "保存上访人员信息", notes = "传参：String sfqzxf 是否群众信访事件, int lxcs 来信次数, int ldcs 来电话次数, LocalDateTime sfsj 上访时间, int sfrs 上访人数, String sffsdd 上访发生地点, String sfrysq 上访人员诉求,\n" +
            "                       String clqkbf 处理情况办法, Integer person_baseinfo_id")
    public Result save(String sfqzxf, @RequestParam( required = false, defaultValue = "0")Integer lxcs, @RequestParam( required = false, defaultValue = "0")Integer ldcs, LocalDateTime sfsj, @RequestParam( required = false, defaultValue = "0")Integer sfrs, String sffsdd, String sfrysq,
                       String clqkbf, Integer person_baseinfo_id){
        sFPeopleService.save(sfqzxf, lxcs, ldcs, sfsj, sfrs, sffsdd, sfrysq, clqkbf, person_baseinfo_id);
        return Result.success("上访人员信息保存成功");

    }
}

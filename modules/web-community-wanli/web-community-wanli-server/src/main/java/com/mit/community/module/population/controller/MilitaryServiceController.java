package com.mit.community.module.population.controller;

import com.mit.community.population.service.MilitaryServiceService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 兵役
 */
@RequestMapping(value = "/militaryService")
@RestController
@Slf4j
@Api(tags = "兵役人员信息")
public class MilitaryServiceController {
    @Autowired
    private MilitaryServiceService militaryServiceService;

    @PostMapping("/save")
    @ApiOperation(value = "保存兵役人员基本信息", notes = "传参：String xyqk 学业情况, String zybm 专业编码, String zymc 专业名称, String zytc 专业特长, String cylb 从业类别, String jdxx 就读学校或工作单位, String zyzgzs 职业资格证书等级, String hscjdcsjjsxl 何时参加过多长时间的军师训练, String sg 身高,\n" +
            "String tz 体重, String zylysl 左眼裸眼视力, String yylysl 右眼裸眼视力, String jkzk 健康状况, String stmc 身体目测, String bsdc 病史调查, String wcqk 外出情况, String zzcs 政治初审, String bydjjl 兵役登记结论,\n" +
            "String yy 原因, String djxs 登记形式, Integer sftj 是否推荐为预征对象, String bz 备注, Integer person_baseinfo_id")
    public Result save(String xyqk, String zybm, String zymc, String zytc, String cylb, String jdxx, String zyzgzs, String hscjdcsjjsxl, String sg,
                       String tz, String zylysl, String yylysl, String jkzk, String stmc, String bsdc, String wcqk, String zzcs, String bydjjl,
                       String yy, String djxs, @RequestParam( required = false, defaultValue = "0")Integer sftj, String bz, Integer person_baseinfo_id){
        militaryServiceService.save(xyqk, zybm, zymc, zytc, cylb, jdxx, zyzgzs, hscjdcsjjsxl, sg, tz, zylysl, yylysl, jkzk, stmc, bsdc, wcqk, zzcs, bydjjl, yy, djxs, sftj, bz, person_baseinfo_id);
        return Result.success("兵役人员信息保存成功");

    }
}

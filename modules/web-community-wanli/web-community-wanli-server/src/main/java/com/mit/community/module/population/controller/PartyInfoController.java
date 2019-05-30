package com.mit.community.module.population.controller;

import com.mit.community.entity.entity.Params;
import com.mit.community.population.service.PartyInfoService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/partyInfo")
@RestController
@Slf4j
@Api(tags = "党员信息")
public class PartyInfoController {
    @Autowired
    private PartyInfoService partyInfoService;

    /*@PostMapping("/save")
    @ApiOperation(value = "保存党员基本信息", notes = "传参：LocalDateTime rdsq 入党日期, LocalDateTime zzrq 转正日期, LocalDateTime cjgzsj 参加工作时间, String rdszzb 入党时所在支部, String zzszzb 转正所在支部, String zzszdw 组织所在单位, String szdzb 所在党支部,\n" +
            "                       LocalDateTime jrdzbsj 进入党支部时间, String xrdnzw 现任党内职务, String rdjsr 入党介绍人, String yyjndf 月应交纳党费, Integer person_baseinfo_id")
    public Result save(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime rdsq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime zzrq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime cjgzsj, String rdszzb, String zzszzb, String zzszdw, String szdzb,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime jrdzbsj, String xrdnzw, String rdjsr, String yyjndf, Integer person_baseinfo_id){
        partyInfoService.save(rdsq, zzrq, cjgzsj, rdszzb, zzszzb, zzszdw, szdzb, jrdzbsj, xrdnzw, rdjsr, yyjndf, person_baseinfo_id);
        return Result.success("保存党员信息成功");

    }*/


    @GetMapping("/save")
    @ApiOperation(value = "保存党员基本信息", notes = "传参：LocalDateTime rdsq 入党日期, LocalDateTime zzrq 转正日期, LocalDateTime cjgzsj 参加工作时间, String rdszzb 入党时所在支部, String zzszzb 转正所在支部, String zzszdw 组织所在单位, String szdzb 所在党支部,\n" +
            "                       LocalDateTime jrdzbsj 进入党支部时间, String xrdnzw 现任党内职务, String rdjsr 入党介绍人, String yyjndf 月应交纳党费, Integer person_baseinfo_id")
    public Result save(@RequestParam List<Params> param) {
        System.out.println(param.get(0) + "==================");
        //partyInfoService.save(rdsq, zzrq, cjgzsj, rdszzb, zzszzb, zzszdw, szdzb, jrdzbsj, xrdnzw, rdjsr, yyjndf, person_baseinfo_id);
        return Result.success("保存党员信息成功");

    }
}

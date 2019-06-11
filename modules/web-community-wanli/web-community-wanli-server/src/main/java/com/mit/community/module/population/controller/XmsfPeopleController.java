package com.mit.community.module.population.controller;

import com.mit.community.entity.entity.XmsfPeopleInfo;
import com.mit.community.population.service.XmsfPeopleService;
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
 * 刑满释放
 */
@RequestMapping(value = "/xmsfPeople")
@RestController
@Slf4j
@Api(tags = "刑满释放人员信息")
public class XmsfPeopleController {
    @Autowired
    private XmsfPeopleService xmsfPeopleService;

    @PostMapping("/save")
    @ApiOperation(value = "保存刑满释放人员信息", notes = "传参：String sflf 是否累犯, String yzm 原罪名, String ypxq 原判刑期, String fxcs 服刑场所, String sfrq 释放日期, String wxxpglx 危险性评估类型, LocalDateTime xjrq 衔接日期, String xjqk 衔接情况,\n" +
            "LocalDateTime azrq 安置日期, String azqk 安置情况, String wazyy 未安置原因, String bjqk 帮教情况, String sfcxfz 是否重新犯罪, String cxfzzm 重新犯罪罪名")
    public Result save(String sflf, String yzm, String ypxq, String fxcs, String sfrq, String wxxpglx, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime xjrq, String xjqk,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime azrq, String azqk, String wazyy, String bjqk, String sfcxfz, String cxfzzm, Integer person_baseinfo_id){
        xmsfPeopleService.save(sflf, yzm, ypxq, fxcs, sfrq, wxxpglx, xjrq, xjqk, azrq, azqk, wazyy, bjqk, sfcxfz, cxfzzm, person_baseinfo_id);
        return Result.success("刑满释放人员信息保存成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新刑满释放人员信息", notes = "传参：String sflf 是否累犯, String yzm 原罪名, String ypxq 原判刑期, String fxcs 服刑场所, String sfrq 释放日期, String wxxpglx 危险性评估类型, LocalDateTime xjrq 衔接日期, String xjqk 衔接情况,\n" +
            "LocalDateTime azrq 安置日期, String azqk 安置情况, String wazyy 未安置原因, String bjqk 帮教情况, String sfcxfz 是否重新犯罪, String cxfzzm 重新犯罪罪名")
    public Result update(String sflf, String yzm, String ypxq, String fxcs, String sfrq, String wxxpglx, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime xjrq, String xjqk,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime azrq, String azqk, String wazyy, String bjqk, String sfcxfz, String cxfzzm, Integer person_baseinfo_id, int isDelete){
        XmsfPeopleInfo xmsfPeopleInfo = new XmsfPeopleInfo(sflf, yzm, ypxq, fxcs, sfrq, wxxpglx, xjrq, xjqk, azrq, azqk, wazyy, bjqk, sfcxfz, cxfzzm, person_baseinfo_id, isDelete);
        xmsfPeopleService.save(xmsfPeopleInfo);
        return Result.success("刑满释放人员信息更新成功");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除刑满释放人员信息", notes = "传参：Integer id  记录id")
    public Result delete(Integer id){
        xmsfPeopleService.delete(id);
        return Result.success("删除成功");
    }

}

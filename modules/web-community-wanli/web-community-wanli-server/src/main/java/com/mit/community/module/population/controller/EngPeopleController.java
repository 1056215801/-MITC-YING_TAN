package com.mit.community.module.population.controller;

import com.mit.community.population.service.EngPeopleService;
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

@RequestMapping(value = "/engpeople")
@RestController
@Slf4j
@Api(tags = "境外人员信息")
public class EngPeopleController {
    @Autowired
    private EngPeopleService engPeopleService;

    @PostMapping("/save")
    @ApiOperation(value = "保存境外人员基本信息", notes = "传参：String wwx 外文姓, String wwm 外文名, String zwm 中文姓名, String gj 国籍（地区）, String zjxy 宗教信仰, String zjdm 证件代码, String zjhm 证件号码,\n" +
            "                       LocalDateTime zjyxq 证件有效期, String lhmd 来华目的, LocalDateTime ddrq 抵达日期, LocalDateTime yjlkrq 预计离开日期, int sfzdry 是否重点关注人员, Integer person_baseinfo_id")
    public Result save(String wwx, String wwm, String zwm, String gj, String zjxy, String zjdm, String zjhm,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime zjyxq,
                       String lhmd, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime ddrq,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime yjlkrq,
                       @RequestParam(required = false, defaultValue = "0") Integer sfzdry, Integer person_baseinfo_id) {
        engPeopleService.save(wwx, wwm, zwm, gj, zjxy, zjdm, zjhm, zjyxq, lhmd, ddrq, yjlkrq, sfzdry, person_baseinfo_id);
        return Result.success("境外人员信息保存成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "保存境外人员基本信息", notes = "传参：String wwx 外文姓, String wwm 外文名, String zwm 中文姓名, String gj 国籍（地区）, String zjxy 宗教信仰, String zjdm 证件代码, String zjhm 证件号码,\n" +
            "                       LocalDateTime zjyxq 证件有效期, String lhmd 来华目的, LocalDateTime ddrq 抵达日期, LocalDateTime yjlkrq 预计离开日期, int sfzdry 是否重点关注人员, Integer person_baseinfo_id")
    public Result update(String wwx, String wwm, String zwm, String gj, String zjxy, String zjdm, String zjhm,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime zjyxq, String lhmd,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime ddrq,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime yjlkrq,
                         @RequestParam(required = false, defaultValue = "0") Integer sfzdry, Integer person_baseinfo_id, int isDelete) {
        /*EngPeopleInfo engPeopleInfo = new EngPeopleInfo(wwx, wwm, zwm, gj, zjxy, zjdm, zjhm,
                zjyxq, lhmd, ddrq, yjlkrq, sfzdry, person_baseinfo_id, isDelete);
        engPeopleService.save(engPeopleInfo);*/
        return Result.success("更新人员信息保存成功");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除境外人员信息", notes = "传参：Integer id  记录id")
    public Result delete(Integer id) {
        engPeopleService.delete(id);
        return Result.success("删除成功");
    }
}

package com.mit.community.module.population.controller;

import com.mit.community.entity.entity.SQJZPeopleinfo;
import com.mit.community.population.service.SQJZPeopleService;
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
 * 社区矫正人员
 */
@RequestMapping(value = "/sQJZPeople")
@RestController
@Slf4j
@Api(tags = "社区矫正人员信息")
public class SQJZPeopleController {
    @Autowired
    private SQJZPeopleService sQJZPeopleService;

    @PostMapping("/save")
    @ApiOperation(value = "保存社区矫正人员信息", notes = "传参：String sqjzrybh 社区矫正人员编号, String yjycs 原羁押场所, String jzlb 矫正类别, String ajlb 案件类别, String jtzm 具体罪名, String ypxq 原判刑期, LocalDateTime ypxkssj 原判刑开始日期, LocalDateTime ypxjssj 原判刑结束时间,\n" +
            "LocalDateTime jzkssj 矫正开始时间, LocalDateTime jzjssj 矫正结束时间, String jsfs 接收方式, String ssqk 四史情况, String sflgf 是否累惯犯, String ssqku 三涉情况, String sfjljzxz 是否建立矫正小组, String jzjclx矫正解除（终止）类型, String sfytg 是否有托管,\n" +
            "String tgyy 托管原因, String jcjdtgqk 检察监督托管情况, String tgjzqk 托管纠正情况, String sfylg 是否有漏管, String lgyy 漏管原因, String jcjdlgqk 检察监督漏管情况, String lgjzqk 漏管纠正情况, String jcqk 奖惩情况, String xfbgzx 刑罚变更执行情况,\n" +
            "String sfcxfz 是否重新犯罪, String cxfzmc 重新犯罪名称, Integer person_baseinfo_id")
    public Result save(String sqjzrybh, String yjycs, String jzlb, String ajlb, String jtzm, String ypxq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime ypxkssj, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime ypxjssj,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime jzkssj, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime jzjssj, String jsfs, String ssqk, String sflgf, String ssqku, String sfjljzxz, String jzjclx, String sfytg,
                       String tgyy, String jcjdtgqk, String tgjzqk, String sfylg, String lgyy, String jcjdlgqk, String lgjzqk, String jcqk, String xfbgzx,
                       String sfcxfz, String cxfzmc, Integer person_baseinfo_id) {
        sQJZPeopleService.save(sqjzrybh, yjycs, jzlb, ajlb, jtzm, ypxq, ypxkssj, ypxjssj, jzkssj, jzjssj, jsfs, ssqk, sflgf, ssqku, sfjljzxz, jzjclx, sfytg,
                tgyy, jcjdtgqk, tgjzqk, sfylg, lgyy, jcjdlgqk, lgjzqk, jcqk, xfbgzx, sfcxfz, cxfzmc, person_baseinfo_id);
        return Result.success("社区矫正人员信息保存成功");

    }

    @PostMapping("/update")
    @ApiOperation(value = "保存社区矫正人员信息", notes = "传参：String sqjzrybh 社区矫正人员编号, String yjycs 原羁押场所, String jzlb 矫正类别, String ajlb 案件类别, String jtzm 具体罪名, String ypxq 原判刑期, LocalDateTime ypxkssj 原判刑开始日期, LocalDateTime ypxjssj 原判刑结束时间,\n" +
            "LocalDateTime jzkssj 矫正开始时间, LocalDateTime jzjssj 矫正结束时间, String jsfs 接收方式, String ssqk 四史情况, String sflgf 是否累惯犯, String ssqku 三涉情况, String sfjljzxz 是否建立矫正小组, String jzjclx矫正解除（终止）类型, String sfytg 是否有托管,\n" +
            "String tgyy 托管原因, String jcjdtgqk 检察监督托管情况, String tgjzqk 托管纠正情况, String sfylg 是否有漏管, String lgyy 漏管原因, String jcjdlgqk 检察监督漏管情况, String lgjzqk 漏管纠正情况, String jcqk 奖惩情况, String xfbgzx 刑罚变更执行情况,\n" +
            "String sfcxfz 是否重新犯罪, String cxfzmc 重新犯罪名称, Integer person_baseinfo_id")
    public Result update(String sqjzrybh, String yjycs, String jzlb, String ajlb, String jtzm, String ypxq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime ypxkssj, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime ypxjssj,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime jzkssj, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime jzjssj, String jsfs, String ssqk, String sflgf, String ssqku, String sfjljzxz, String jzjclx, String sfytg,
                         String tgyy, String jcjdtgqk, String tgjzqk, String sfylg, String lgyy, String jcjdlgqk, String lgjzqk, String jcqk, String xfbgzx,
                         String sfcxfz, String cxfzmc, Integer person_baseinfo_id, int isDelete) {
        SQJZPeopleinfo sQJZPeopleinfo = new SQJZPeopleinfo(sqjzrybh, yjycs, jzlb, ajlb, jtzm, ypxq, ypxkssj, ypxjssj, jzkssj, jzjssj, jsfs, ssqk, sflgf, ssqku, sfjljzxz, jzjclx, sfytg,
                tgyy, jcjdtgqk, tgjzqk, sfylg, lgyy, jcjdlgqk, lgjzqk, jcqk, xfbgzx, sfcxfz, cxfzmc, person_baseinfo_id, isDelete);
        sQJZPeopleService.save(sQJZPeopleinfo);
        return Result.success("社区矫正人员信息更新成功");

    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除社区矫正人员信息", notes = "传参：Integer id  记录id")
    public Result delete(Integer id) {
        sQJZPeopleService.delete(id);
        return Result.success("删除成功");
    }
}

package com.mit.community.module.population.controller;

import com.mit.community.entity.entity.ZSZHInfo;
import com.mit.community.population.service.ZSZHService;
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
 * 肇事肇祸
 */
@RequestMapping(value = "/zszh")
@RestController
@Slf4j
@Api(tags = "肇事肇祸人员信息")
public class ZSZHController {
    @Autowired
    private ZSZHService zSZHService;

    @PostMapping("/save")
    @ApiOperation(value = "肇事肇祸人员信息", notes = "传参：String jtjjzk 家庭经济状况, String sfnrdb 是否纳入低保, String jhrsfzh 监护人公民身份号码, String jhrxm 监护人姓名, String jhrlxfs 监护人联系方式, LocalDateTime ccfbrq 初次发病日期, String mqzdlx 目前诊断类型,\n" +
            "String ywzszhs 有无肇事肇祸史, Integer zszhcs 肇事肇祸次数, LocalDateTime sczszhrq 上次肇事肇祸日期, String mqwxxpgdj 目前危险性评估登记, String zlqk 治疗情况, String zlyy 治疗医院名称,\n" +
            "String sszyzlyy 实施住院治疗原因, String jskfxljgmc 接收康复训练机构名称, String cyglry 参与管理人员, String bfqk 帮扶情况")
    public Result save(String jtjjzk, String sfnrdb, String jhrsfzh, String jhrxm, String jhrlxfs, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime ccfbrq, String mqzdlx,
                       String ywzszhs, @RequestParam( required = false, defaultValue = "0")Integer zszhcs, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime sczszhrq, String mqwxxpgdj, String zlqk, String zlyy,
                       String sszyzlyy, String jskfxljgmc, String cyglry, String bfqk, Integer person_baseinfo_id){
        zSZHService.save(jtjjzk, sfnrdb, jhrsfzh, jhrxm, jhrlxfs, ccfbrq, mqzdlx, ywzszhs, zszhcs, sczszhrq, mqwxxpgdj,
                zlqk, zlyy, sszyzlyy, jskfxljgmc, cyglry, bfqk, person_baseinfo_id);
        return Result.success("肇事肇祸人员信息保存成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "肇事肇祸人员信息", notes = "传参：String jtjjzk 家庭经济状况, String sfnrdb 是否纳入低保, String jhrsfzh 监护人公民身份号码, String jhrxm 监护人姓名, String jhrlxfs 监护人联系方式, LocalDateTime ccfbrq 初次发病日期, String mqzdlx 目前诊断类型,\n" +
            "String ywzszhs 有无肇事肇祸史, Integer zszhcs 肇事肇祸次数, LocalDateTime sczszhrq 上次肇事肇祸日期, String mqwxxpgdj 目前危险性评估登记, String zlqk 治疗情况, String zlyy 治疗医院名称,\n" +
            "String sszyzlyy 实施住院治疗原因, String jskfxljgmc 接收康复训练机构名称, String cyglry 参与管理人员, String bfqk 帮扶情况")
    public Result update(String jtjjzk, String sfnrdb, String jhrsfzh, String jhrxm, String jhrlxfs, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime ccfbrq, String mqzdlx,
                       String ywzszhs, @RequestParam( required = false, defaultValue = "0")Integer zszhcs, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime sczszhrq, String mqwxxpgdj, String zlqk, String zlyy,
                       String sszyzlyy, String jskfxljgmc, String cyglry, String bfqk, Integer person_baseinfo_id, int isDelete){
        ZSZHInfo zSZHInfo = new ZSZHInfo(jtjjzk, sfnrdb, jhrsfzh, jhrxm, jhrlxfs, ccfbrq, mqzdlx, ywzszhs, zszhcs, sczszhrq, mqwxxpgdj,
                zlqk, zlyy, sszyzlyy, jskfxljgmc, cyglry, bfqk, person_baseinfo_id, isDelete);
        zSZHService.save(zSZHInfo);
        return Result.success("肇事肇祸人员信息更新成功");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除肇事肇祸人员信息", notes = "传参：Integer id  记录id")
    public Result delete(Integer id){
        zSZHService.delete(id);
        return Result.success("删除成功");
    }
}

package com.mit.community.module.population.controller;

import com.mit.community.service.FlowPeopleService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping(value = "/flowpeople")
@RestController
@Slf4j
@Api(tags = "流动人口信息")
public class FlowPeopleController {
    @Autowired
    private FlowPeopleService flowPeopleService;

    @RequestMapping("/save")
    @ApiOperation(value = "流动人口信息保存", notes = "传参：String lryy 流入原因, String bzlx 办证类型, String zjhm 证件号码, LocalDateTime djrq 登记日期, LocalDateTime zjdqrq 证件到期日期, String zslx 住所类型,\n" +
            "                       int sfzdgzry 是否重点关注人员, String tslxfs 朋友或同事联系方式, String jtzycylxfs 家庭重要人员联系方式, String yhzgx 与户主关系, String hzsfz 户主公民身份号码, Integer person_baseinfo_id")
    public Result save(String lryy, String bzlx, String zjhm, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime djrq, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime zjdqrq, String zslx,
                       @RequestParam( required = false, defaultValue = "0")Integer sfzdgzry, String tslxfs, String jtzycylxfs, String yhzgx, String hzsfz, Integer person_baseinfo_id){
        flowPeopleService.save(lryy, bzlx, zjhm, djrq, zjdqrq, zslx, sfzdgzry, tslxfs, jtzycylxfs, yhzgx, hzsfz, person_baseinfo_id);
        return Result.success("流动人口信息保存成功");
    }
}

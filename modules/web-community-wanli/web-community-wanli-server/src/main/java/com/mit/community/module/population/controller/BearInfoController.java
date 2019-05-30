package com.mit.community.module.population.controller;

import com.mit.community.service.BearInfoService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 计生信息管理
 */
@RequestMapping(value = "/bearInfo")
@RestController
@Slf4j
@Api(tags = "计生信息")
public class BearInfoController {
    @Autowired
    private BearInfoService bearInfoService;

    @PostMapping("/save")
    @ApiOperation(value = "保存计生信息", notes = "传参：String poxm 配偶姓名, String poxb 配偶性别, String xgzdw 现工作单位或家庭住址, String djjhny 登记结婚年月, String hkxz 户口性质, String hyzk 婚姻状况, String jysssj 节育手术时间\n" +
            "    , String sslx 手术类型, String ssyy 手术医院, String ccyy 审查原因, Integer person_baseinfo_id")
    public Result save(String poxm, String poxb, String xgzdw, String djjhny, String hkxz, String hyzk, String jysssj
    , String sslx, String ssyy, String ccyy, Integer person_baseinfo_id){
        bearInfoService.save(poxm, poxb, xgzdw, djjhny, hkxz, hyzk, jysssj, sslx, ssyy, ccyy, person_baseinfo_id);
        return Result.success("保存成功");
    }
}

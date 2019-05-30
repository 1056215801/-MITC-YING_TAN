package com.mit.community.module.population.controller;

import com.mit.community.service.CXService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 传销
 */
@RequestMapping(value = "/cx")
@RestController
@Slf4j
@Api(tags = "传销人员信息")
public class CXController {
    @Autowired
    private CXService cXService;

    @PostMapping("/save")
    @ApiOperation(value = "保存涉嫌传销人员基本信息", notes = "传参：String dysxcx 第一涉嫌传销及相关情况, String drsxcx 第二涉嫌传销及相关情况, String dssxcx 第三涉嫌传销及相关情况, String bz 备注, Integer person_baseinfo_id")
    public Result save(String dysxcx, String drsxcx, String dssxcx, String bz, Integer person_baseinfo_id){
        cXService.save(dysxcx, drsxcx, dssxcx, bz, person_baseinfo_id);
        return Result.success("传销人员信息保存成功");

    }

}

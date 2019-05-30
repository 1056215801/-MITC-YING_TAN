package com.mit.community.module.population.controller;

import com.mit.community.service.CensusInfoService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/censusInfo")
@RestController
@Slf4j
@Api(tags = "户籍信息")
public class CensusInfoController {
    @Autowired
    private CensusInfoService censusInfoService;

    @PostMapping("/save")
    @ApiOperation(value = "保存户籍信息", notes = "传参：String rhyzbz 人户一致标志, String hh 户号, String yhzgx 与户主关系, String hzsfz 户主联系方式, Integer person_baseinfo_id")
    public Result save(String rhyzbz, String hh, String yhzgx, String hzsfz, Integer person_baseinfo_id) {
        censusInfoService.save(rhyzbz, hh, yhzgx, hzsfz, person_baseinfo_id);
        return Result.success("保存户籍信息成功");

    }
}

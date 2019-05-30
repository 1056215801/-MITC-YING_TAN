package com.mit.community.module.population.controller;

import com.mit.community.population.service.XDService;
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
 * 吸毒人员
 */
@RequestMapping(value = "/xd")
@RestController
@Slf4j
@Api(tags = "吸毒人员信息")
public class XDController {
    @Autowired
    private XDService xDService;

    @PostMapping("/save")
    @ApiOperation(value = "保存吸毒人员信息", notes = "传参：LocalDateTime ccfxsj 初次发现日期, String gkqk 管控情况, String gkrxm 管控人姓名, String gkrlxfs 管控人联系方式, String bfqk 帮扶情况, String bfrxm 帮扶人姓名, String bfrlxfs 帮扶人联系方式,\n" +
            "String ywfzs 有无犯罪史, String xdqk 吸毒情况, String xdyy 吸毒原因, String xdhg 吸毒后果")
    public Result save(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime ccfxsj, String gkqk, String gkrxm, String gkrlxfs, String bfqk, String bfrxm, String bfrlxfs,
                       String ywfzs, String xdqk, String xdyy, String xdhg, Integer person_baseinfo_id){
        xDService.save(ccfxsj, gkqk, gkrxm, gkrlxfs, bfqk, bfrxm, bfrlxfs, ywfzs, xdqk, xdyy, xdhg, person_baseinfo_id);
        return Result.success("吸毒人员信息保存成功");

    }
}

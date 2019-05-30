package com.mit.community.module.population.controller;

import com.mit.community.service.ZDQSNCService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 重点青少年
 */
@RequestMapping(value = "/zdqsn")
@RestController
@Slf4j
@Api(tags = "重点青少年信息")
public class ZDQSNController {
    @Autowired
    private ZDQSNCService zDQSNCService;

    @PostMapping("/save")
    @ApiOperation(value = "重点青少年人员信息", notes = "传参：String rylx 人员类型, String jtqk 家庭情况, String jhrsfz 监护人公民身份号码, String jhrxm 监护人姓名, String yjhrgx 与监护人关系, String jhrlxfs 监护人联系方式, String jhrjzxxdz 监护人居住详址,\n" +
            "                       String sfwffz 是否违法犯罪, String wffzqk 违法犯罪情况, String bfrlxfs 帮扶人姓名, String bfsd 帮扶手段, String bfqk 帮扶情况, Integer person_baseinfo_id")
    public Result save(String rylx, String jtqk, String jhrsfz, String jhrxm, String yjhrgx, String jhrlxfs, String jhrjzxxdz,
                       String sfwffz, String wffzqk, String bfrlxfs, String bfsd, String bfqk, Integer person_baseinfo_id){
        zDQSNCService.save(rylx, jtqk, jhrsfz, jhrxm, yjhrgx, jhrlxfs, jhrjzxxdz, sfwffz, wffzqk, bfrlxfs, bfsd, bfqk, person_baseinfo_id);
        return Result.success("重点青少年信息保存成功");

    }
}

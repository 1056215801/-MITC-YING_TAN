package com.mit.community.module.population.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.InfoSearch;
import com.mit.community.service.InfoSearchService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/infoSearch")
@RestController
@Slf4j
@Api(tags = "人员信息搜索")
public class InfoSearchController {
    @Autowired
    private InfoSearchService infoSearchService;

    @PostMapping("/listPage")
    @ApiOperation(value = "人员信息分页查询", notes = "传参：Integer age 年龄, String name 姓名, String idNum 身份证号码, String sex 性别, String education 学历, String job 职业, String matrimony 婚姻状况, String zzmm 政治面貌, String label 标签, Integer pageNum, Integer pageSize,String rycf 人员成分")
    public Result listPage(@RequestParam( required = false, defaultValue = "0")Integer age, String name, String idNum, String sex, String education, String job, String matrimony, String zzmm, String label, Integer pageNum, Integer pageSize, String rycf) {
        Page<InfoSearch> page = infoSearchService.listPage(age, name, idNum, sex, education, job, matrimony, zzmm, label, pageNum, pageSize, rycf);
        return Result.success(page);
    }

}

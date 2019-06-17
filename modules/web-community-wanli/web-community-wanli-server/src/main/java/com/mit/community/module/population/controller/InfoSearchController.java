package com.mit.community.module.population.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.ExcelData;
import com.mit.community.entity.entity.InfoSearch;
import com.mit.community.population.service.InfoSearchService;
import com.mit.community.service.LabelsService;
import com.mit.community.util.ExcelUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/infoSearch")
@RestController
@Slf4j
@Api(tags = "人员信息搜索")
public class InfoSearchController {
    @Autowired
    private InfoSearchService infoSearchService;
    @Autowired
    private LabelsService labelsService;

    @PostMapping("/listPage")
    @ApiOperation(value = "人员信息分页查询", notes = "传参：Integer age 年龄, String name 姓名, String idNum 身份证号码, String sex 性别, String education 学历, String job 职业, String matrimony 婚姻状况, String zzmm 政治面貌, String label 标签, Integer pageNum, Integer pageSize,String rycf 人员成分")
    public Result listPage(@RequestParam( required = false, defaultValue = "0")Integer ageStart,@RequestParam( required = false, defaultValue = "0")Integer ageEnd, String name, String idNum, String sex, String education, String job, String matrimony, String zzmm, String label, Integer pageNum, Integer pageSize, String rycf) {
        System.out.println("======"+ pageNum + ","+ pageSize+",label="+label+",matrimony="+matrimony);
        Page<InfoSearch> page = infoSearchService.listPage(ageStart, ageEnd, name, idNum, sex, education, job, matrimony, zzmm, label, pageNum, pageSize, rycf);
        List<InfoSearch> list = page.getRecords();
        if(!list.isEmpty()){
            for(int i=0;i<list.size();i++){
                System.out.println("==========id="+list.get(i).getPersonBaseInfo().getId());
                List<String> labels = labelsService.getLabelsByUserId(list.get(i).getPersonBaseInfo().getId());
                list.get(i).getPersonBaseInfo().setLabels(labels);
            }
        }
        page.setRecords(list);
        return Result.success(page);
    }

    @PostMapping("/getLabelInfo")
    @ApiOperation(value = "人员标签信息", notes = "传参：Integer person_baseinfo_id")
    public Result getLabelInfo(Integer person_baseinfo_id) {
        Map<String, Object> map = infoSearchService.getLabelInfo(person_baseinfo_id);
        return Result.success(map);
    }

}

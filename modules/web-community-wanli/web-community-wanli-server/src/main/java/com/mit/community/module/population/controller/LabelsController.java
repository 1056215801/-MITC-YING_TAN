package com.mit.community.module.population.controller;

import com.mit.community.population.service.PersonLabelsService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/labels")
@Slf4j
@Api(tags = "添加人员标签")
public class LabelsController {
    @Autowired
    private PersonLabelsService personLabelsService;

    @PostMapping("/save")
    @ApiOperation(value = "保存人员标签", notes = "输入参数：")
    public Result save(String labels,Integer userId){
        personLabelsService.saveLabels(labels, userId);
        return Result.success("保存成功");
    }

    @PostMapping("/getLabels")
    @ApiOperation(value = "获取人员标签", notes = "输入参数：")
    public Result save(Integer household){
        String labels = personLabelsService.getLabelsByHousehold(household);
        return Result.success(labels);
    }

    @PostMapping("/getPeopleCount")
    @ApiOperation(value = "获取驻留人数", notes = "输入参数：")
    public Result getPeopleCount(){//正式的需要传入楼栋信息，摄像头的地址要和小区关联
        int count = personLabelsService.getPeopleCount();
        int outCount = personLabelsService.getOutCount();
        int peopleCount = count - outCount;
        return Result.success(peopleCount);
    }

    @GetMapping("/getPeopleOue")
    @ApiOperation(value = "获取出去的人", notes = "输入参数：")
    public Result getPeopleOue(){//正式的需要传入楼栋信息，摄像头的地址要和小区关联
        String time = personLabelsService.getPeopleOue();
        return Result.success(time);
    }

}

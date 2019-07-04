package com.mit.community.module.population.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.WarningConfig;
import com.mit.community.service.WarningConfigService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/warningConfig")
@RestController
@Slf4j
@Api(tags = "报警信息配置")
public class WarningConfigController {
    @Autowired
    private WarningConfigService warningConfigService;

    @PostMapping("/list")
    @ApiOperation(value = "分页获取报警配置信息", notes = "Integer pageNum, Integer pageSize, String label:标签")
    public Result getListPage(Integer pageNum, Integer pageSize, String label){
        Page<WarningConfig> page = warningConfigService.getPage(pageNum, pageSize, label);
        return Result.success(page);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新报警配置信息", notes = "Integer id,String label,int isWarning,String warningInfo")
    public Result update(Integer id,String label,int isWarning,String warningInfo){
        warningConfigService.update(id, label, isWarning, warningInfo);
        return Result.success("更新成功");
    }

    @PostMapping("/save")
    @ApiOperation(value = "更新报警配置信息", notes = "Integer id,String label,int isWarning,String warningInfo")
    public Result save(String label,int isWarning,String warningInfo){
        warningConfigService.save(label, isWarning, warningInfo);
        return Result.success("更新成功");
    }
}

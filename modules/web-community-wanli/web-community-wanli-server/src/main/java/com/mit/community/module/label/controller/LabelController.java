package com.mit.community.module.label.controller;

import com.mit.community.constants.Constants;
import com.mit.community.entity.Label;
import com.mit.community.service.LabelService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标签
 *
 * @author shuyy
 * @date 2018/12/19
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/label")
@Slf4j
@Api(tags = "标签管理")
public class LabelController {
    @Autowired
    private LabelService labelService;

    /**
     * @param name 标签名
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 18:30
     * @company mitesofor
    */
    @PostMapping("/saveLabelSystem")
    @ApiOperation(value = "保存系统标签", notes = "传参：name 标签名")
    public Result saveLabelSystem(String name){
        if(StringUtils.isBlank(name)){
            return Result.error("参数不能为空");
        }
        labelService.save(name, Constants.LABEL_TYPE_SYSTEM, 0);
        return Result.success("保存成功");
    }

    /**
     * @param id 标签id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 18:33
     * @company mitesofor
    */
    @DeleteMapping("/removeLabel")
    @ApiOperation(value = "删除标签", notes = "传参：id 标签id")
    public Result removeLabel(Integer id){
        if(id == null){
            return Result.error("参数不能为空");
        }
        labelService.removeById(id);
        return Result.success("删除成功");
    }

    /**
     * 查询标签
     * @param type 类型
     * @param name 标签名
     * @param pageNum 当前页
     * @param pageSize 分页大小
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 20:12
     * @company mitesofor
    */
    @DeleteMapping("/listPage")
    @ApiOperation(value = "查询标签列表", notes = "传参：type 1、系统类型。 2、 用户自定义类型。 name 标签名" +
            " 2、pageNum 分页大小 required， pageSize 分页大小 required")
    public Result listPage(Short type, String name, Integer pageNum, Integer pageSize){
        List<Label> labels = labelService.listPage(type, name,
                pageNum, pageSize);
        return Result.success(labels);
    }

}

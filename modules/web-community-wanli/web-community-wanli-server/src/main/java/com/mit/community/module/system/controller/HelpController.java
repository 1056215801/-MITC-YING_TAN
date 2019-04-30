package com.mit.community.module.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.Help;
import com.mit.community.service.HelpService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 帮助
 *
 * @author shuyy
 * @date 2019-01-03
 * @company mitesofor
 */
@RequestMapping(value = "/help")
@RestController
@Slf4j
@Api(tags = "help")
public class HelpController {
    @Autowired
    private HelpService helpService;

    @PostMapping("/save")
    @ApiOperation(value = "保存帮助", notes = "传参：title 标题， content 内容， orders 排序， status 状态（1已启用，2已停用）")
    public Result save(String title, String content, Short orders, Integer status) {
        helpService.save(title, content, orders, status);
        return Result.success("保存成功");
    }

    /**
     * 修改
     *
     * @param id
     * @param title
     * @param content
     * @param orders
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2019-01-03 11:50
     * @company mitesofor
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改帮助", notes = "传参：id id, title 标题， content 内容， orders 排序, status 状态（1已启用，2已停用）")
    public Result update(Integer id, String title, String content, Short orders, Integer status) {
        helpService.update(id, title, content, orders, status);
        return Result.success("修改成功");
    }

    /**
     * @param id
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2019-01-03 11:51
     * @company mitesofor
     */
    @DeleteMapping("/remove")
    @ApiOperation(value = "修改帮助", notes = "传参：id id")
    public Result remove(Integer id) {
        helpService.remove(id);
        return Result.success("删除成功");
    }

    /**
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2019-01-03 11:53
     * @company mitesofor
     */
    @GetMapping("/list")
    @ApiOperation(value = "帮助列表", notes = "输入参数：title 标题， status 状态（1已启用，2已停用）， gmtCreateTimeStart 开始时间， gmtCreateTimeEnd 结束时间")
    public Result list(String title, Integer status,Integer pageNum, Integer pageSize,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeStart,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateTimeEnd) {
        Page<Help> page = helpService.listPage(title, status, gmtCreateTimeStart, gmtCreateTimeEnd, pageNum, pageSize);
        //List<Help> list = helpService.list();
        return Result.success(page);
    }

}


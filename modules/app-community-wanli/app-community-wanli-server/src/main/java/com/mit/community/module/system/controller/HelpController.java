package com.mit.community.module.system.controller;

import com.mit.community.entity.Help;
import com.mit.community.service.HelpService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 帮助
 * @author shuyy
 * @date 2019-01-03
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/help")
@Slf4j
@Api(tags = {"帮助"})
public class HelpController {
    private final HelpService helpService;

    @Autowired
    public HelpController(HelpService helpService) {
        this.helpService = helpService;
    }

    /**
     * 查询帮助列表
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-03 11:46
     * @company mitesofor
     */
    @GetMapping("/list")
    @ApiOperation(value = "帮助列表")
    public Result list() {
        List<Help> list = helpService.list();
        return Result.success(list);
    }

}

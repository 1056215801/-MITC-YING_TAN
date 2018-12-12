package com.mit.community.module.system.controller;

import com.mit.community.entity.Dictionary;
import com.mit.community.module.system.service.DictionaryService;
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
 * 数据字典
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/dictionary")
@Slf4j
@Api(tags = {"数据字典"})
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    /**
     * @param parentCode parentCode
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/30 10:14
     * @company mitesofor
    */
    @GetMapping("/listByParentCode")
    @ApiOperation(value = "查询数据字典，通过parentCode")
    public Result listByParentCode(String mac, String cellphone, String parentCode) {
        List<Dictionary> dictionaries = dictionaryService.listByParentCode(parentCode);
        return Result.success(dictionaries);
    }

    /**
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/30 10:14
     * @company mitesofor
     */
    @GetMapping("/listDictionaryByParentCode")
    @ApiOperation(value = "查询所有数据字典")
    public Result list(String mac, String cellphone) {
        List<Dictionary> dictionaries = dictionaryService.list();
        return Result.success(dictionaries);
    }
}

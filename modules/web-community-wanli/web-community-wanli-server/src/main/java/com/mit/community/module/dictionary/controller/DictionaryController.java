package com.mit.community.module.dictionary.controller;

import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.Dictionary;
import com.mit.community.entity.SysUser;
import com.mit.community.service.DictionaryService;
import com.mit.community.service.RedisService;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 数据字典
 * @author Mr.Deng
 * @date 2019/1/25 17:19
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@RestController
@RequestMapping(value = "/dictionary")
@Slf4j
@Api(tags = {"数据字典"})
public class DictionaryController {

    private final DictionaryService dictionaryService;
    private final RedisService redisService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService, RedisService redisService) {
        this.dictionaryService = dictionaryService;
        this.redisService = redisService;
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
    public Result listByParentCode(HttpServletRequest request, String parentCode) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (user != null) {
            if (StringUtils.isNotBlank(parentCode)) {
                List<Dictionary> dictionaries = dictionaryService.listByParentCode(parentCode);
                return Result.success(dictionaries);
            }
            return Result.error("参数不能为空");
        }
        return Result.error("请登录");
    }

    /**
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/11/30 10:14
     * @company mitesofor
     */
    @GetMapping("/listDictionaryByParentCode")
    @ApiOperation(value = "查询所有数据字典")
    public Result list(HttpServletRequest request) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (user != null) {
            List<Dictionary> dictionaries = dictionaryService.list();
            return Result.success(dictionaries);
        }
        return Result.error("请登录");
    }

    /**
     * 查询数据字典，通过Code
     * @param code code
     * @return result
     * @author Mr.Deng
     * @date 11:23 2018/12/24
     */
    @GetMapping("/getDictionariesByCode")
    @ApiOperation(value = "查询数据字典，通过Code")
    public Result getDictionariesByCode(HttpServletRequest request, String code) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (user != null) {
            if (StringUtils.isNotBlank(code)) {
                Dictionary dictionary = dictionaryService.getByCode(code);
                return Result.success(dictionary);
            }
            return Result.error("参数不能为空");
        }
        return Result.error("请登录");
    }
}

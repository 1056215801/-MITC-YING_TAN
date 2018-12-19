package com.mit.community.module.system.controller;

import com.mit.community.service.AppUserService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app用户
 *
 * @author shuyy
 * @date 2018/12/19
 * @company mitesofor
 */
@RequestMapping(value = "/appUser")
@RestController
@Slf4j
@Api(tags = "app用户")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    /**
     * @param userId user id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 16:48
     * @company mitesofor
     */
    @PostMapping("/removeUser")
    @ApiOperation(value = "删除用户", notes = "传参：userId app user id")
    public Result removeUser(Integer userId) {
        try {
            appUserService.handleRemove(userId);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

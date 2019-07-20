package com.mit.community.module.hik.test.controller;

import com.mit.community.entity.com.mit.community.entity.hik.FaceInfo;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @company mitesofor
 */
@RestController
@Slf4j
@Api(tags = "(海康视频设备信息测试接口)")
@RequestMapping("/testVedioHK")
public class TestController {

    @RequestMapping("/testVedio")
    @ApiOperation(value = "test", notes = "")
    public Result TestRealData(FaceInfo faceInfo){


        return null;
    }


}

package com.mit.community.module.pass.controller;

import com.mit.community.conmon.BaiduOcr;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 百度
 *
 * @author shuyy
 * @date 2019-01-17
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/BaiduOcr")
@Slf4j
@Api(tags = "身份证识别")
public class BaiduOcrController {

    @PostMapping("/analyze")
    @ApiOperation(value = "分析身份证", notes = "file 身份证照片，size 转成base64后3M之内")
    public Result analyze(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String invoke = BaiduOcr.invoke(bytes);
        return Result.success(invoke);
    }

}

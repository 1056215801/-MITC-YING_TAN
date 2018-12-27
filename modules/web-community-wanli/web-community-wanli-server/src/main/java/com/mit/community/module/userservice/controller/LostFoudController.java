package com.mit.community.module.userservice.controller;

import com.mit.community.service.LostFoundService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * 失物招领
 *
 * @author shuyy
 * @date 2018/12/26
 * @company mitesofor
 */
@RequestMapping(value = "/bussinessHandleController")
@RestController
@Slf4j
@Api(tags = "业务办理")
public class LostFoudController {

    @Autowired
    private LostFoundService lostFoundService;

    @PatchMapping("/save")
    @ApiOperation(value = "发布失物招领", notes = "输入参数：id 业务办理id")
    public Result save(String title, MultipartFile imgUrl, String issuer, String issuerPhone,
                       String picAddress, LocalDateTime issueTime, String communityCode, String content) {
//        FastDFSClient.
//        lostFoundService.save(title,
//                , , , , , , );

        return Result.success("");
    }
}
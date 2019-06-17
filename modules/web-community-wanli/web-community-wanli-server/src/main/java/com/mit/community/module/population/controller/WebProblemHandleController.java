package com.mit.community.module.population.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.WebHandleProblemInfo;
import com.mit.community.service.ProblemHandleService;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/problemHandle")
@Slf4j
@Api(tags = "事件处理")
public class WebProblemHandleController {
    @Autowired
    private ProblemHandleService problemHandleService;

    @GetMapping("/getWebHandleProblem")
    @ApiOperation(value = "获取上报的问题", notes = "输入参数：")
    public Result getWebHandleProblem(String problemType, String status, Integer pageNum, Integer pageSize){
        Page<WebHandleProblemInfo> page = problemHandleService.getWebHandleProblem(problemType, status, pageNum, pageSize);
        return Result.success(page);
    }

    @PostMapping("/handleProblem")
    @ApiOperation(value = "小区停车问题受理", notes = "输入参数：userId 受理人id，reportProblemId 事件id，dept 部门，content 内容,images 照片")
    public Result handleProblem(Integer userId ,Integer reportProblemId,String dept,String content,MultipartFile[] images) throws Exception{
        List<String> imageUrls = new ArrayList<>();
        if (images != null) {
            System.out.println("=====================2222");
            for (MultipartFile image : images) {
                System.out.println("====================6666");
                String imageUrl = UploadUtil.upload(image);
                imageUrls.add(imageUrl);
            }
        }problemHandleService.save(userId,reportProblemId,dept,content,imageUrls);
        return Result.success("保存成功");
    }
}

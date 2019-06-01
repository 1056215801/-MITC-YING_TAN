package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.HandleProblemInfo;
import com.mit.community.entity.ProblemSchedule;
import com.mit.community.entity.ProblemScheduleInfo;
import com.mit.community.entity.ReportProblemInfo;
import com.mit.community.service.ReportProblemService;
import com.mit.community.util.PushUtil;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/reportProblem")
@Slf4j
@Api(tags = "事件上报")
public class ReportProblemController {
    @Autowired
    private ReportProblemService reportProblemService;

    /*@PostMapping("/save")
    @ApiOperation(value = "上报事件", notes = "输入参数：userId 用户id，content 内容；problemType 事件类型、address 地址、" +
            "isOpen 公开；base64PhotoJsonArray 照片base64数组json")
    public Result save(Integer userId, String content, String problemType, String address, int isOpen, String base64PhotoJsonArray){
        reportProblemService.save( userId, content,  problemType,  address,  isOpen,  base64PhotoJsonArray);
        *//*List<String> tagsList = new ArrayList<>();
        tagsList.add("grid");*//*
        PushUtil.sendAllsetNotification("有新的问题待处理");
        return Result.success("保存成功");
    }*/

    @PostMapping("/save")
    @ApiOperation(value = "上报事件", notes = "输入参数：userId 用户id，content 内容；problemType 事件类型、address 地址、" +
            "isOpen 公开；images 图片")
    public Result save1(Integer userId, String content, String problemType, String address, Integer isOpen, MultipartFile[] images) throws Exception{
        System.out.println("===============userId="+userId+"content="+content+"problemType"+problemType+"isOpen"+isOpen);
        List<String> imageUrls = new ArrayList<>();
        if (images != null) {
            System.out.println("=====================2222");
            for (MultipartFile image : images) {
                System.out.println("====================6666");
                String imageUrl = UploadUtil.upload(image);
                imageUrls.add(imageUrl);
            }
        }
        reportProblemService.save( userId, content,  problemType,  address,  isOpen,  imageUrls);
        /*List<String> tagsList = new ArrayList<>();
        tagsList.add("grid");*/
        PushUtil.sendAllsetNotification("有新的问题待处理");
        return Result.success("保存成功");
    }

    @PostMapping("/listPage")
    @ApiOperation(value = "获取事件列表", notes = "String userId 用户id, String time 时间, String address 地址, String problemType 问题类型, Integer status 状态, Integer pageNum, Integer pageSize")
    public Result listPage(String userId, String time, String address, String problemType, @RequestParam( required = false, defaultValue = "0")Integer status, Integer pageNum, Integer pageSize) throws Exception{
        Page<ReportProblemInfo> page = reportProblemService.listPage(userId,time,address,problemType,status,pageNum,pageSize);
        return Result.success(page);
    }

    @GetMapping("/getSchedule")
    @ApiOperation(value = "获取处理进度", notes = "输入参数：reportProblemId 事件id")
    public Result getSchedule(Integer reportProblemId){
        List<HandleProblemInfo> list = reportProblemService.getSchedule(reportProblemId);
        return Result.success(list);
    }






}

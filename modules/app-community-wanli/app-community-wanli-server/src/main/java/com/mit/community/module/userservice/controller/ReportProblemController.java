package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.HandleProblemInfo;
import com.mit.community.entity.ReportProblemInfo;
import com.mit.community.entity.TaskMessage;
import com.mit.community.population.service.TaskMessageService;
import com.mit.community.service.ReportProblemService;
import com.mit.community.service.UserService;
import com.mit.community.service.WgyService;
import com.mit.community.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping(value = "/reportProblem")
@Slf4j
@Api(tags = "事件上报")
public class ReportProblemController {
    @Autowired
    private ReportProblemService reportProblemService;
    @Autowired
    private UserService userService;
    @Autowired
    private WgyService wgyService;
    @Autowired
    private TaskMessageService taskMessageService;

    public static final String MSG = "收到新的问题反馈，请登录网格助手进行处理";

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
    public Result save1(Integer userId, String content, String problemType, String address, Integer isOpen, MultipartFile[] images) throws Exception {
        System.out.println("===============userId=" + userId + "content=" + content + "problemType" + problemType + "isOpen" + isOpen);
        List<String> imageUrls = new ArrayList<>();
        if (images != null) {
            for (MultipartFile image : images) {
                String imageUrl = UploadUtil.upload(image);
                imageUrls.add(imageUrl);
            }
        }
        Integer id = reportProblemService.save(userId, content, problemType, address, isOpen, imageUrls);
        InputStream in = null;
        String[] phones = null;
        try {
            //获取配置电话号码
            in = Thread.currentThread().getContextClassLoader().getResource("params.properties").openStream();
            Properties props = new Properties();
            props.load(in);
            String s = props.getProperty("phones");
            phones = s.split(",");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //发送通知短信
        //获取手机号码
        for (int i = 0; i < phones.length; i++) {
            SmsCommunityAppUtil.sendMsg(phones[i], MSG);
        }
        //消息推送
        String title = "消息通知";
        WebPush.sendAllsetNotification(MSG,title);
        //Integer wgyId = wgyService.getWgyIdByJb("居委");
        //taskMessageService.save(id,title,MSG,wgyId,0,0,null);
        return Result.success("保存成功");
    }

    @PostMapping("/listPage")
    @ApiOperation(value = "获取事件列表", notes = "String content 搜索内容,String userId 用户id, String time 时间, String address 地址, String problemType 问题类型, Integer status 状态, Integer pageNum, Integer pageSize")
    public Result listPage(String content, String userId, String timeYear, String timeMonth,
                           String address, String problemType, @RequestParam(required = false, defaultValue = "0") Integer status,
                           Integer pageNum, Integer pageSize) throws Exception {
        Page<ReportProblemInfo> page = reportProblemService.listPage(content, userId, timeYear, timeMonth,
                address, problemType, status, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/getSchedule")
    @ApiOperation(value = "获取处理进度", notes = "输入参数：reportProblemId 事件id")
    public Result getSchedule(Integer reportProblemId) {
        List<HandleProblemInfo> list = reportProblemService.getSchedule(reportProblemId);
        return Result.success(list);
    }


}

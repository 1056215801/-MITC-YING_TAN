package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.*;
import com.mit.community.entity.entity.WarnInfo;
import com.mit.community.population.service.TaskMessageService;
import com.mit.community.service.ReportProblemService;
import com.mit.community.service.UserService;
import com.mit.community.service.WgyService;
import com.mit.community.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *用户事件上报
 * @author xq
 * @date 2019/6/28
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/reportProblem")
@Slf4j
@Api(tags = "事件上报")
public class ReportProblemController {
    @Autowired
    private ReportProblemService reportProblemService;
    @Autowired
    private TaskMessageService taskMessageService;

    public static final String MSG = "收到新的问题反馈，请登录网格助手进行处理";

    @PostMapping("/save")
    @ApiOperation(value = "上报事件", notes = "输入参数：userId 用户id，content 内容；problemType 事件类型、address 地址、" +
            "isOpen 公开；images 图片")
    public Result save(Integer userId, String content, String problemType, String address, Integer isOpen, MultipartFile[] images) throws Exception {
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
        taskMessageService.save(0,id,title,MSG,1,0,0,null);
        return Result.success("保存成功");
    }

    @PostMapping("/listPage")
    @ApiOperation(value = "获取事件列表", notes = "String content 搜索内容,String userId 用户id, String time 时间, String address 地址, String problemType 问题类型, Integer status 状态, Integer pageNum, Integer pageSize")
    public Result listPage(String content, String userId, String timeYear, String timeMonth,
                           String address, String problemType, String status,
                           Integer pageNum, Integer pageSize) throws Exception {
        Page<ReportProblemInfo> page = reportProblemService.listPage(content, userId, timeYear, timeMonth,
                address, problemType, status, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/getSchedule")
    @ApiOperation(value = "获取处理进度", notes = "输入参数：reportProblemId 事件id")
    public Result getSchedule(Integer reportProblemId) {
        ProblemScheduleAndLzdInfo problemScheduleAndLzdInfo = new ProblemScheduleAndLzdInfo();
        List<HandleProblemInfo> list = reportProblemService.getSchedule(reportProblemId);
        if (!list.isEmpty()) {
            problemScheduleAndLzdInfo.setList(list);
        }
        List<ReportProblemLzInfo> lzInfo = reportProblemService.getLzInfo(reportProblemId ,0);
        if (!lzInfo.isEmpty()) {
            problemScheduleAndLzdInfo.setLzInfo(lzInfo);
        }
        return Result.success(problemScheduleAndLzdInfo);
    }


    /**
     * 设备报警演示接口
     * @param place
     * @param type
     * @return
     */
    @PostMapping("/baojin")
    @ApiOperation(value = "报警", notes = "传参：place(利雅轩小区、南标小区)，type(烟感、地磁、井盖位移、紧急按钮)") //没有表
    @Transactional
    public Result baoJin(String place,String type){//电话号码要修改
        String communityCode = null;
        if ("南标小区".equals(place)) {
            communityCode = "a5f53a2248794c678766edad485392ff";
        } else if ("利雅轩小区".equals(place)){
            communityCode = "b181746d9bd1444c80522f9923c59b80";
        }
        if ("烟感".equals(type)){
            WarnInfo warnInfo = new WarnInfo();
            warnInfo.setPhone("152****7130");
            if ("南标小区".equals(place)) {
                warnInfo.setPlace( "南标小区-电动车雨棚");
            } else if ("利雅轩小区".equals(place)){
                warnInfo.setPlace("利雅轩小区-4栋1单元");
            }
            //warnInfo.setPlace(place + "");//这里需要补齐
            warnInfo.setProblem("疑似火情");
            warnInfo.setWarnInfo("出现大量烟雾，请及时处理");
            warnInfo.setCommunityCode(communityCode);

            String title = "消息通知";
            String MSG = "收到新的问题反馈，请登录网格助手进行处理";
            Integer id = reportProblemService.save(70, warnInfo.getPlace()+warnInfo.getWarnInfo(), warnInfo.getProblem(), warnInfo.getPlace(), 1, new ArrayList<>());
            taskMessageService.save(0,id,title,MSG,1,0,0,null);
            warnInfo.setProblemId(id);
            reportProblemService.saveBaoJin(warnInfo);
            WebPush.sendAllsetNotification(MSG,title);
            SmsCommunityAppUtil.sendMsg("18170879118", MSG);
        } else if ("地磁".equals(type)) {
            WarnInfo warnInfo = new WarnInfo();
            warnInfo.setPhone("152****7130");
            if ("南标小区".equals(place)) {
                warnInfo.setPlace( "南标小区-岗亭前消防栓旁");
            } else if ("利雅轩小区".equals(place)){
                warnInfo.setPlace("利雅轩小区-主干道");
            }
            //warnInfo.setPlace(place + "");//这里需要补齐
            warnInfo.setProblem("消防占道");
            warnInfo.setWarnInfo("消防通道被占用，请及时处理");
            warnInfo.setCommunityCode(communityCode);
            //reportProblemService.saveBaoJin(warnInfo);
            String title = "消息通知";
            String MSG = "收到新的问题反馈，请登录网格助手进行处理";
            Integer id = reportProblemService.save(70, warnInfo.getPlace()+warnInfo.getWarnInfo(), warnInfo.getProblem(), warnInfo.getPlace(), 1, new ArrayList<>());
            taskMessageService.save(0,id,title,MSG,1,0,0,null);
            warnInfo.setProblemId(id);
            reportProblemService.saveBaoJin(warnInfo);
            WebPush.sendAllsetNotification(MSG,title);
            SmsCommunityAppUtil.sendMsg("18170879118", MSG);
        } else if ("井盖位移".equals(type)) {
            WarnInfo warnInfo = new WarnInfo();
            warnInfo.setPhone("152****7130");
            if ("南标小区".equals(place)) {
                warnInfo.setPlace( "南标小区-岗亭后雨水井");
            } else if ("利雅轩小区".equals(place)){
                warnInfo.setPlace("利雅轩小区-1栋3单元前雨水井");
            }
            warnInfo.setProblem("井盖非法移位");
            warnInfo.setWarnInfo("井盖被非法移位，请及时处理");
            warnInfo.setCommunityCode(communityCode);
            //reportProblemService.saveBaoJin(warnInfo);
            String title = "消息通知";
            String MSG = "收到新的问题反馈，请登录网格助手进行处理";
            Integer id = reportProblemService.save(70, warnInfo.getPlace()+warnInfo.getWarnInfo(), warnInfo.getProblem(), warnInfo.getPlace(), 1, new ArrayList<>());
            taskMessageService.save(0,id,title,MSG,1,0,0,null);
            warnInfo.setProblemId(id);
            reportProblemService.saveBaoJin(warnInfo);
            WebPush.sendAllsetNotification(MSG,title);
            SmsCommunityAppUtil.sendMsg("18170879118", MSG);
        } else if ("紧急按钮".equals(type)) {
            WarnInfo warnInfo = new WarnInfo();
            warnInfo.setPlace(place + "");//这里需要补齐
            warnInfo.setProblem("紧急按钮报警");
            warnInfo.setName("李*新");
            if ("南标小区".equals(place)) {
                warnInfo.setPhone("187****4039");
                warnInfo.setCyrPhone("187****4039");
                warnInfo.setJhrPhone("159****46572");
                warnInfo.setWarnInfo("张凯（187****4039）紧急按钮发生报警，请及时处理");
            } else if ("利雅轩小区".equals(place)){
                warnInfo.setPhone("152****7130");
                warnInfo.setCyrPhone("152****7130");
                warnInfo.setJhrPhone("181****6078");
                warnInfo.setWarnInfo("李重新（152****7130）紧急按钮发生报警，请及时处理");
            }
            warnInfo.setCommunityCode(communityCode);
            //reportProblemService.saveBaoJin(warnInfo);
            String title = "消息通知";
            String MSG = "收到新的问题反馈，请登录网格助手进行处理";
            Integer id = reportProblemService.save(70, warnInfo.getPlace()+warnInfo.getWarnInfo(), warnInfo.getProblem(), warnInfo.getPlace(), 1, new ArrayList<>());
            taskMessageService.save(0,id,title,MSG,1,0,0,null);
            warnInfo.setProblemId(id);
            reportProblemService.saveBaoJin(warnInfo);
            WebPush.sendAllsetNotification(MSG,title);
            SmsCommunityAppUtil.sendMsg("18170879118", MSG);
        }
        return Result.success("保存成功");
    }


}

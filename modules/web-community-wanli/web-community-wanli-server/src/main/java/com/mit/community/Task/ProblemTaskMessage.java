package com.mit.community.Task;

import com.mit.community.entity.ReportProblem;
import com.mit.community.entity.TaskMessage;
import com.mit.community.entity.TaskMessageContent;
import com.mit.community.entity.TaskMessageSirInfo;
import com.mit.community.population.service.PersonLabelsService;
import com.mit.community.population.service.TaskMessageService;
import com.mit.community.service.ReportProblemService;
import com.mit.community.util.DateUtil;
import com.mit.community.util.SmsCommunityAppUtil;
import com.mit.community.util.WebPush;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProblemTaskMessage {
    @Autowired
    private TaskMessageService taskMessageService;
    @Autowired
    private PersonLabelsService personLabelsService;
    @Autowired
    private ReportProblemService reportProblemService;

    /*@Scheduled(cron = "0/1 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void task(){
        System.out.println("================");
        List<TaskMessage> list = taskMessageService.getList();//所有未查看的
        if (!list.isEmpty()) {
            for (int i=0;i<list.size();i++) {
                Date sendTime = Date.from(list.get(i).getGmtCreate().toInstant(ZoneOffset.of("+8")));
                long longDate = sendTime.getTime();
                long now = System.currentTimeMillis();
                int miao = (int)(now - longDate) / 1000;
                if (miao > 15 && list.get(i).getIsRepeat()==0) {//超过十秒并且没有向上级转发
                    //向上级转发
                    //查找该网格员上级的personbaseinfo_id
                    Integer sirPersonBaseInfoId = personLabelsService.getSirPersonBaseInfoId(list.get(i).getId());
                    //查找该网格员上级网格员的userid和网格员信息ID
                    if (sirPersonBaseInfoId != null) {
                        TaskMessageSirInfo taskMessageSirInfo = personLabelsService.getSirUserIdBySirPersonBaseInfoId(sirPersonBaseInfoId);
                        if (taskMessageSirInfo != null) {
                            List<String> messageAccept = new ArrayList<>();
                            String title = "事件未处理通知";
                            TaskMessageContent taskMessageContent = personLabelsService.getTaskMessageContent(list.get(i).getReportProblemId(), list.get(i).getWgyId());
                            String content = "您辖区内网格员"+taskMessageContent.getWgyName()+"(联系电话："+taskMessageContent.getCellPhone()+")，尚未处理用户"
                                    + taskMessageContent.getUserName() + "于" + taskMessageContent.getGmtCreate() + "上报的" + taskMessageContent.getProblemType() + "问题";
                            personLabelsService.updateMqlzd(list.get(i).getReportProblemId());
                            if (taskMessageSirInfo.getUserId() != null) {//通过app发送通知
                                messageAccept.add(taskMessageSirInfo.getUserId().toString());
                                WebPush.sendAlias(title, content, messageAccept);
                                taskMessageService.save(list.get(i).getReportProblemId(),title,content,taskMessageSirInfo.getWgyId(),0,0,null);
                            } else {//下发短信
                                //还要加下发短信
                                *//*String sirCellPhone = personLabelsService.getSirPhoneByPersonBaseInfoId(sirPersonBaseInfoId);
                                if (StringUtils.isNotBlank(sirCellPhone)) {
                                    SmsCommunityAppUtil.sendMsg(sirCellPhone, content);
                                    taskMessageService.save(list.get(i).getReportProblemId(),title,content,taskMessageSirInfo.getWgyId(),0,0, null);
                                }*//*
                                if("贤士湖长巷村".equals(taskMessageSirInfo.getJb())){
                                    SmsCommunityAppUtil.sendMsg("18170879118", "香樟住宅小区有事件未及时处置，现升级为居委处置");
                                    taskMessageService.save(list.get(i).getReportProblemId(),title,"香樟住宅小区有事件未及时处置，现升级为居委处置",taskMessageSirInfo.getWgyId(),0,0, null);
                                }
                                if("贤士湖管理处".equals(taskMessageSirInfo.getJb())){
                                    String[] phones = {"18170879118"};
                                    for(int a=0;a<phones.length;a++){
                                        SmsCommunityAppUtil.sendMsg(phones[a], "长巷村有事件未及时处置，现升级为管理处处置");
                                    }
                                    taskMessageService.save(list.get(i).getReportProblemId(),title,"长巷村有事件未及时处置，现升级为管理处处置",taskMessageSirInfo.getWgyId(),0,0, null);
                                }
                                *//*if("贤士湖管理处".equals(taskMessageSirInfo.getJb())){
                                    String[] phones = {"18170879118","13970919271","18179107779"};
                                    for(int a=0;a<phones.length;a++){
                                        SmsCommunityAppUtil.sendMsg(phones[a], "长巷村有事件未及时处置，现升级为居委处置");
                                    }
                                    taskMessageService.save(list.get(i).getReportProblemId(),title,"长巷村有事件未及时处置，现升级为居委处置",taskMessageSirInfo.getWgyId(),0,0, null);
                                }*//*
                                if("东湖区政法委".equals(taskMessageSirInfo.getJb())){
                                    String[] phones = {"18170879118"};
                                    for(int a=0;a<phones.length;a++){
                                        SmsCommunityAppUtil.sendMsg(phones[a], "贤士湖管理处有事件未及时处置，现升级为政法委处置");
                                    }
                                    taskMessageService.save(list.get(i).getReportProblemId(),title,"贤士湖管理处有事件未及时处置，现升级为政法委处置",taskMessageSirInfo.getWgyId(),0,0, null);
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/

    @Scheduled(cron = "0/1 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void menJin(){
        String create_Time = personLabelsService.getMenJinTime("艾武德");

        String  createTime = create_Time.substring(0,create_Time.length()-2);
        System.out.println("=============发现吸毒事件"+createTime);
        long create = DateUtil.parseStringToLong(createTime);
        long bd = DateUtil.parseStringToLong("2019-06-22 12:00:00");
        if (create > bd) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime time = LocalDateTime.parse(createTime,df);
            String content = personLabelsService.getContentByTime(time);
            List<String> list = new ArrayList<>();
            if(!StringUtils.isNotBlank(content)){
                System.out.println("=============发现吸毒");
                SmsCommunityAppUtil.sendMsg("18170879118", "收到新的问题反馈，请登录网格助手进行处理");
                reportProblemService.save(91,"吸毒人员聚集","吸毒","东湖区",1, list,time);
            }
        }

    }

    @Scheduled(cron = "0/1 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void task(){
        List<ReportProblem> list = reportProblemService.getXdAndSf();
        System.out.println("=======任务运行");
        if(!list.isEmpty()){
            for(int i=0;i<list.size();i++) {
                int mqlzd = list.get(i).getMqlzd();
                if (mqlzd == 0) {
                    Date sendTime = Date.from(list.get(i).getGmtCreate().toInstant(ZoneOffset.of("+8")));
                    long longDate = sendTime.getTime();
                    long now = System.currentTimeMillis();
                    int miao = (int)(now - longDate) / 1000;
                    System.out.println("=======1相差秒数"+miao);
                    if (miao > 20) {
                        System.out.println("=======进入1");
                        SmsCommunityAppUtil.sendHandleMsg("18170879118", "香樟住宅小区","居委");//有事件未及时处置，现升级为居委处置
                        ReportProblem reportProblem = list.get(i);
                        System.out.println("=======信息下发1");
                        reportProblem.setMqlzd(1);
                        reportProblem.setGmtModified(LocalDateTime.now());
                        reportProblemService.saveSendAfter(reportProblem);
                    }
                } else if (mqlzd == 1) {
                    Date sendTime = Date.from(list.get(i).getGmtModified().toInstant(ZoneOffset.of("+8")));
                    long longDate = sendTime.getTime();
                    long now = System.currentTimeMillis();
                    int miao = (int)(now - longDate) / 1000;
                    System.out.println("=======2相差秒数"+miao);
                    if (miao > 15) {
                        System.out.println("=======进入2");
                        String[] phones = {"18170879118"};
                        for(int a=0;a<phones.length;a++){
                            SmsCommunityAppUtil.sendHandleMsg(phones[a], "长巷村","管理处");//事件未及时处置，现升级为管理处处置
                            System.out.println("=======信息下发2");
                        }
                        ReportProblem reportProblem = list.get(i);
                        reportProblem.setMqlzd(2);
                        reportProblem.setGmtModified(LocalDateTime.now());
                        reportProblemService.saveSendAfter(reportProblem);
                    }

                } else if (mqlzd == 2) {
                    Date sendTime = Date.from(list.get(i).getGmtModified().toInstant(ZoneOffset.of("+8")));
                    long longDate = sendTime.getTime();
                    long now = System.currentTimeMillis();
                    int miao = (int)(now - longDate) / 1000;
                    System.out.println("=======3相差秒数"+miao);
                    if (miao > 15) {
                        System.out.println("=======进入3");
                        String[] phones = {"18170879118"};
                        for(int a=0;a<phones.length;a++){
                            SmsCommunityAppUtil.sendHandleMsg(phones[a], "贤士湖管理处","区政法委");//有事件未及时处置，现升级为政法委处置
                            System.out.println("=======信息下发3");
                        }
                        ReportProblem reportProblem = list.get(i);
                        reportProblem.setMqlzd(3);
                        reportProblem.setGmtModified(LocalDateTime.now());
                        reportProblemService.saveSendAfter(reportProblem);
                    }

                }
            }
        }
    }

}

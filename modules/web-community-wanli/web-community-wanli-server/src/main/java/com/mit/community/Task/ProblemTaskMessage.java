package com.mit.community.Task;

import com.mit.community.entity.*;
import com.mit.community.population.service.PersonLabelsService;
import com.mit.community.population.service.TaskMessageService;
import com.mit.community.service.ReportProblemService;
import com.mit.community.service.SendPhoneService;
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

//@Component
public class ProblemTaskMessage {
    @Autowired
    private TaskMessageService taskMessageService;
    @Autowired
    private PersonLabelsService personLabelsService;
    @Autowired
    private ReportProblemService reportProblemService;
    @Autowired
    private SendPhoneService sendPhoneService;

    @Scheduled(cron = "0/1 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void task(){
        List<ReportProblem> nosolveList = reportProblemService.getNosolveList();//所有没有处理的上报事件
        if (!nosolveList.isEmpty()) {
            for(int i=0;i < nosolveList.size();i++){
                Date sendTime = Date.from(nosolveList.get(i).getGmtCreate().toInstant(ZoneOffset.of("+8")));
                long longDate = sendTime.getTime();
                long now = System.currentTimeMillis();
                int miao = (int)(now - longDate) / 1000;
                if (miao > 100) {//过多长时间事件还没有处理
                    System.out.println("=====超过100秒");
                    List<TaskMessage> list = taskMessageService.getList(nosolveList.get(i).getId());//将所有该事件已经推送的信息查出
                    if (!list.isEmpty()) {
                        Date sendTime1 = Date.from(list.get(0).getGmtCreate().toInstant(ZoneOffset.of("+8")));
                        long longDate1 = sendTime.getTime();
                        if ((int)(System.currentTimeMillis() - longDate1) / 1000 > 15) {//超时往上级推送
                            System.out.println("=====超时15秒往上级推送");
                            List<WgyInfo> wgyList = personLabelsService.getWgyList(list.get(0).getWgyId());//上级网格员的信息(取最后发出的这一条)
                            /*List<String> target = new ArrayList<>();
                            List<>*/
                            if (!wgyList.isEmpty()) {
                                for (int a=0; a<wgyList.size();a++) {
                                    System.out.println("=====查找到网格员信息");
                                    String title = "事件未处理通知";
                                    if(wgyList.get(a).getPerson_baseinfo_id() != null){//有userid的发app通知
                                        System.out.println("=====app通告发送的网格员id"+wgyList.get(a).getId());
                                        List<String> messageAccept = new ArrayList<>();
                                        TaskMessageContent taskMessageContent = personLabelsService.getTaskMessageContent(list.get(0).getReportProblemId(), list.get(0).getWgyId());
                                        String content = "您辖区内网格员"+taskMessageContent.getWgyName()+"(联系电话："+taskMessageContent.getCellPhone()+")，尚未处理用户"
                                                + taskMessageContent.getUserName() + "于" + taskMessageContent.getGmtCreate() + "上报的" + taskMessageContent.getProblemType() + "问题";

                                        messageAccept.add(wgyList.get(a).getPerson_baseinfo_id().toString());
                                        WebPush.sendAlias(title, content, messageAccept);
                                        taskMessageService.save(list.get(0).getReportProblemId(),title,content,wgyList.get(a).getId(),0,0,null);
                                    } else {//没有的发短信
                                        System.out.println("=====短信发送的网格员id"+wgyList.get(a).getId());
                                        String lower = personLabelsService.getWgyDeptById(list.get(0).getWgyId());
                                        String higher = wgyList.get(a).getDept();
                                        String content = lower + "有事件未及时处置，现升级为" + higher +"处置";
                                        SmsCommunityAppUtil.sendHandleMsg(wgyList.get(a).getJtzycylxfs(), lower,higher);//事件未及时处置，现升级为管理处处置
                                        taskMessageService.save(list.get(0).getReportProblemId(),title,content,wgyList.get(a).getId(),0,0,null);
                                    }
                                }

                            } else {//没有上级，说明全部推送流程完成
                                ReportProblem reportProblem = nosolveList.get(i);
                                reportProblem.setMqlzd(10);//终结
                                reportProblem.setGmtModified(LocalDateTime.now());
                                reportProblemService.saveSendAfter(reportProblem);
                            }
                        }
                    }
                }
            }
        }

    }

    //@Scheduled(cron = "0/1 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void menJin() {
        List<MenJinInfo> infoList = personLabelsService.getMenJinList("艾武德");
        if (!infoList.isEmpty()) {
            String create_Time = infoList.get(0).getCreateTime();
            String createTime = create_Time.substring(0, create_Time.length() - 2);
            System.out.println("=============发现吸毒事件" + createTime);
            long create = DateUtil.parseStringToLong(createTime);
            long bd = DateUtil.parseStringToLong("2019-06-22 12:00:00");
            if (create > bd) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime time = LocalDateTime.parse(createTime, df);
                String content = personLabelsService.getContentByTime(time);
                List<String> list = new ArrayList<>();
                list.add(infoList.get(0).getUrl());
                if (!StringUtils.isNotBlank(content)) {
                    System.out.println("=============发现吸毒");
                    SmsCommunityAppUtil.sendMsg("18170879118", "收到新的问题反馈，请登录网格助手进行处理");
                    reportProblemService.save(70, "吸毒人员聚集", "吸毒", "东湖区", 1, list, time);
                }
            }
        }
    }

    /*@Scheduled(cron = "0/1 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void task() {
        List<ReportProblem> list = reportProblemService.getXdAndSf();
        System.out.println("=======任务运行");
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                int mqlzd = list.get(i).getMqlzd();
                if (mqlzd == 0) {
                    Date sendTime = Date.from(list.get(i).getGmtCreate().toInstant(ZoneOffset.of("+8")));
                    long longDate = sendTime.getTime();
                    long now = System.currentTimeMillis();
                    int miao = (int) (now - longDate) / 1000;
                    System.out.println("=======1相差秒数" + miao);
                    if (miao > 20) {
                        System.out.println("=======进入1");
                        List<SendPhoneInfo> phones = sendPhoneService.getPhoneByDj(2);
                        if (!phones.isEmpty()) {
                            for (int a = 0; a < phones.size(); a++) {
                                SmsCommunityAppUtil.sendHandleMsg(phones.get(a).getPhone(), "香樟住宅小区", "居委");//有事件未及时处置，现升级为居委处置
                                ReportProblem reportProblem = list.get(i);
                                System.out.println("=======信息下发1");
                                reportProblem.setMqlzd(1);
                                reportProblem.setGmtModified(LocalDateTime.now());
                                reportProblemService.saveSendAfter(reportProblem);
                            }
                        }

                    }
                } else if (mqlzd == 1) {
                    Date sendTime = Date.from(list.get(i).getGmtModified().toInstant(ZoneOffset.of("+8")));
                    long longDate = sendTime.getTime();
                    long now = System.currentTimeMillis();
                    int miao = (int) (now - longDate) / 1000;
                    System.out.println("=======2相差秒数" + miao);
                    if (miao > 15) {
                        System.out.println("=======进入2");
                        List<SendPhoneInfo> phones = sendPhoneService.getPhoneByDj(3);
                        if (!phones.isEmpty()) {
                            for (int a = 0; a < phones.size(); a++) {
                                SmsCommunityAppUtil.sendHandleMsg(phones.get(a).getPhone(), "长巷村", "管理处");//事件未及时处置，现升级为管理处处置
                                System.out.println("=======信息下发2");
                            }
                            ReportProblem reportProblem = list.get(i);
                            reportProblem.setMqlzd(2);
                            reportProblem.setGmtModified(LocalDateTime.now());
                            reportProblemService.saveSendAfter(reportProblem);
                        }

                    }
                } else if (mqlzd == 2) {
                    Date sendTime = Date.from(list.get(i).getGmtModified().toInstant(ZoneOffset.of("+8")));
                    long longDate = sendTime.getTime();
                    long now = System.currentTimeMillis();
                    int miao = (int) (now - longDate) / 1000;
                    System.out.println("=======3相差秒数" + miao);
                    if (miao > 15) {
                        System.out.println("=======进入3");
                        List<SendPhoneInfo> phones = sendPhoneService.getPhoneByDj(4);
                        if (!phones.isEmpty()) {
                            for (int a = 0; a < phones.size(); a++) {
                                SmsCommunityAppUtil.sendHandleMsg(phones.get(a).getPhone(), "贤士湖管理处", "区政法委");//有事件未及时处置，现升级为政法委处置
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
    }*/

}

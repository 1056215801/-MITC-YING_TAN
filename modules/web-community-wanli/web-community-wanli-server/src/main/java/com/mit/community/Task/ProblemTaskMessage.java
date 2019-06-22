package com.mit.community.Task;

import com.mit.community.entity.TaskMessage;
import com.mit.community.entity.TaskMessageContent;
import com.mit.community.entity.TaskMessageSirInfo;
import com.mit.community.population.service.PersonLabelsService;
import com.mit.community.population.service.TaskMessageService;
import com.mit.community.util.SmsCommunityAppUtil;
import com.mit.community.util.WebPush;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProblemTaskMessage {
    @Autowired
    private TaskMessageService taskMessageService;
    @Autowired
    private PersonLabelsService personLabelsService;

    @Scheduled(cron = "0/1 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void task(){
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
                                String sirCellPhone = personLabelsService.getSirPhoneByPersonBaseInfoId(sirPersonBaseInfoId);
                                if (StringUtils.isNotBlank(sirCellPhone)) {
                                    SmsCommunityAppUtil.sendMsg(sirCellPhone, content);
                                    taskMessageService.save(list.get(i).getReportProblemId(),title,content,taskMessageSirInfo.getWgyId(),1,0, LocalDateTime.now());
                                }
                            }
                        }
                    }

                }
            }
        }
    }
    
}

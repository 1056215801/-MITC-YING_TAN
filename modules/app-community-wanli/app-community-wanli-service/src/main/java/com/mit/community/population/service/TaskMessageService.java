package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ReportProblem;
import com.mit.community.entity.TaskMessage;
import com.mit.community.mapper.TaskMessageMapper;
import com.mit.community.service.ReportProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskMessageService {
    @Autowired
    private TaskMessageMapper taskMessageMapper;
    @Autowired
    private ReportProblemService reportProblemService;

    public void save(int mqlzd, Integer reportProblemId, String title, String content,Integer wgyId, int status, int isRepeat,LocalDateTime gmtModified){
        TaskMessage taskMessage = new TaskMessage(reportProblemId, title, content, wgyId, status ,isRepeat,mqlzd);//修改时间作为查看时间
        taskMessage.setGmtCreate(LocalDateTime.now());
        taskMessage.setGmtModified(gmtModified);//查看时间
        taskMessageMapper.insert(taskMessage);

        ReportProblem reportProblem = new ReportProblem();
        reportProblem.setId(reportProblemId);
        reportProblem.setMqlzd(mqlzd);
        reportProblemService.saveSendAfter(reportProblem);
    }

    public List<TaskMessage> getList(Integer id){
        EntityWrapper<TaskMessage> wrapper = new EntityWrapper<>();
        wrapper.eq("reportProblemId", id);
        wrapper.orderBy("gmt_create",false);
        List<TaskMessage> list = taskMessageMapper.selectList(wrapper);
        return list;
    }
}

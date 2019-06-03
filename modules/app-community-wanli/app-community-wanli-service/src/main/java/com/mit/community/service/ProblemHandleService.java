package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.HandleProblemInfo;
import com.mit.community.entity.ProblemSchedule;
import com.mit.community.entity.ProblemSchedulePhoto;
import com.mit.community.entity.ReportProblemInfo;
import com.mit.community.mapper.HandleProblemMapper;
import com.mit.community.mapper.ProblemHandleMapper;
import com.mit.community.mapper.ProblemScheduleMapper;
import com.mit.community.mapper.ProblemSchedulePhotoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProblemHandleService {
    @Autowired
    private ProblemHandleMapper problemHandleMapper;
    @Autowired
    private ProblemScheduleMapper problemScheduleMapper;
    @Autowired
    private ProblemSchedulePhotoMapper problemSchedulePhotoMapper;
    @Autowired
    private HandleProblemMapper handleProblemMapper;

    public int getProblemCount(){
        return problemHandleMapper.getProblemCount("停车");
    }

    public List<ReportProblemInfo> getProblem(){
        return problemHandleMapper.getProblem(0,"停车");
    }

    public List<HandleProblemInfo> getHandleProblem(){
        return handleProblemMapper.getProblemSlove(1,"停车");
    }

    public Page<HandleProblemInfo> getWebProblem(String problemType, String status, LocalDateTime gmtCreateTimeStart,
                                                 LocalDateTime gmtCreateTimeEnd, Integer pageNum, Integer pageSize){
        Page<HandleProblemInfo> page = new Page<>(pageNum, pageSize);
        EntityWrapper<HandleProblemInfo> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(problemType)) {
            wrapper.eq("a.problemType", problemType);
        }
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq("a.status", status);
        }
        if (gmtCreateTimeStart != null) {
            wrapper.ge("a.gmt_create", gmtCreateTimeStart);
        }
        if (gmtCreateTimeEnd != null) {
            wrapper.le("a.gmt_create", gmtCreateTimeEnd);
        }
        wrapper.orderBy("a.gmt_create", false);
        List<HandleProblemInfo> list = handleProblemMapper.getWebProblem(page, wrapper);
        page.setRecords(list);
        return page;
    }

    @Transactional
    public void save(Integer userId,Integer reportProblemId,String dept,String content,List<String> imageUrls){
        ProblemSchedule problemSchedule = new ProblemSchedule();
        problemSchedule.setReportProblemId(reportProblemId);
        problemSchedule.setDept(dept);
        problemSchedule.setUserId(userId);
        problemSchedule.setContent(content);
        problemSchedule.setStatus("1");
        problemSchedule.setGmtCreate(LocalDateTime.now());
        problemSchedule.setGmtModified(LocalDateTime.now());
        problemScheduleMapper.insert(problemSchedule);
        problemHandleMapper.updateProblemStatus(1,reportProblemId);

        Integer id = problemSchedule.getId();
        if (!imageUrls.isEmpty()) {
            ProblemSchedulePhoto reportProblemPhoto = null;
            for (int i=0; i < imageUrls.size(); i++) {
                reportProblemPhoto = new ProblemSchedulePhoto();
                reportProblemPhoto.setProblemScheduleId(id);
                //reportProblemPhoto.setBase64((String) parseArray.get(i));
                reportProblemPhoto.setBase64(imageUrls.get(i));
                reportProblemPhoto.setGmtCreate(LocalDateTime.now());
                reportProblemPhoto.setGmtModified(LocalDateTime.now());
                System.out.println("===================0000");
                problemSchedulePhotoMapper.insert(reportProblemPhoto);
            }
        }

    }
}

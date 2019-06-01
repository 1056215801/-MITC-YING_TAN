package com.mit.community.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.*;
import com.mit.community.mapper.ProblemScheduleMapper;
import com.mit.community.mapper.ReportProblemInfoMapper;
import com.mit.community.mapper.ReportProblemMapper;
import com.mit.community.mapper.ReportProblemPhotoMapper;
import com.mit.community.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportProblemService {
    @Autowired
    private ReportProblemMapper reportProblemMapper;
    @Autowired
    private ReportProblemPhotoMapper reportProblemPhotoMapper;
    @Autowired
    private ReportProblemInfoMapper reportProblemInfoMapper;
    @Autowired
    private ProblemScheduleMapper problemScheduleMapper;

    @Transactional
    public void save(Integer userId, String content, String problemType, String address, int isOpen, String base64PhotoJsonArray){
        ReportProblem reportProblem = new ReportProblem(userId, content, problemType, address, isOpen, 0);
        reportProblem.setGmtCreate(LocalDateTime.now());
        reportProblem.setGmtModified(LocalDateTime.now());
        reportProblemMapper.insert(reportProblem);
        Integer id = reportProblem.getId();
        if (StringUtils.isNotBlank(base64PhotoJsonArray)) {
            //JSONArray parseArray = JSONArray.parseArray(base64PhotoJsonArray);
            String[] photo = base64PhotoJsonArray.split(",");
            ReportProblemPhoto reportProblemPhoto = null;
            for (int i=0; i < photo.length; i++) {
                reportProblemPhoto = new ReportProblemPhoto();
                //reportProblemPhoto.setBase64((String) parseArray.get(i));
                reportProblemPhoto.setBase64(photo[i]);
                reportProblemPhoto.setGmtCreate(LocalDateTime.now());
                reportProblemPhoto.setGmtModified(LocalDateTime.now());
                reportProblemPhotoMapper.insert(reportProblemPhoto);
            }
        }
    }

    public void save(Integer userId, String content, String problemType, String address, Integer isOpen, List<String>imageUrls){
        ReportProblem reportProblem = new ReportProblem(userId, content, problemType, address, isOpen, 0);
        reportProblem.setGmtCreate(LocalDateTime.now());
        reportProblem.setGmtModified(LocalDateTime.now());
        reportProblemMapper.insert(reportProblem);
        Integer id = reportProblem.getId();
        if (!imageUrls.isEmpty()) {
            ReportProblemPhoto reportProblemPhoto = null;
            for (int i=0; i < imageUrls.size(); i++) {
                reportProblemPhoto = new ReportProblemPhoto();
                reportProblemPhoto.setReportProblemId(id);
                //reportProblemPhoto.setBase64((String) parseArray.get(i));
                reportProblemPhoto.setBase64(imageUrls.get(i));
                reportProblemPhoto.setGmtCreate(LocalDateTime.now());
                reportProblemPhoto.setGmtModified(LocalDateTime.now());
                System.out.println("===================0000");
                reportProblemPhotoMapper.insert(reportProblemPhoto);
            }
        }

    }

    public Page<ReportProblemInfo> listPage (String userId, String time, String address, String problemType, Integer status, Integer pageNum, Integer pageSize) throws Exception{
        Page<ReportProblemInfo> page = new Page<>(pageNum, pageSize);
        EntityWrapper<ReportProblemInfo> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(time)) {
            LocalDateTime startTime = DateUtils.dateStrToLocalDateTime(time + "-01");
            LocalDateTime endTime = DateUtils.dateStrToLocalDateTime(time + "-30");
            wrapper.ge("a.gmt_create", startTime);
            wrapper.le("gmt_create", endTime);
        }
        if (StringUtils.isNotBlank(address)) {
            wrapper.like("a.address", address);
        }
        if (StringUtils.isNotBlank(problemType)) {
            wrapper.eq("a.problemType", problemType);
        }
        if (status != 0) {
            wrapper.eq("a.status", status);
        }
        if (StringUtils.isBlank(userId)) {//所有人的
            wrapper.eq("a.isOpen", 1);//公开的
        }
        if (StringUtils.isNotBlank(userId)) {
            wrapper.eq("a.userId", userId);
        }

        wrapper.orderBy("a.gmt_create", false);
        List<ReportProblemInfo> list = reportProblemInfoMapper.selecInfoPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public List<HandleProblemInfo> getSchedule(Integer reportProblemId){
        return reportProblemInfoMapper.getSchedulePhoto(reportProblemId);
    }
}

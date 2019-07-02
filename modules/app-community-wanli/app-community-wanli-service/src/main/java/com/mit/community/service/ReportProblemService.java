package com.mit.community.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.enums.SqlLike;
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
        ReportProblem reportProblem = new ReportProblem(userId, content, problemType, address, isOpen, 0,0);
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

    public Integer save(Integer userId, String content, String problemType, String address, Integer isOpen, List<String>imageUrls){
        ReportProblem reportProblem = new ReportProblem(userId, content, problemType, address, isOpen, 0,0);
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
        return id;
    }

    public Integer save(Integer userId, String content, String problemType, String address, Integer isOpen, List<String>imageUrls,LocalDateTime create){
        ReportProblem reportProblem = new ReportProblem(userId, content, problemType, address, isOpen, 0,0);
        reportProblem.setGmtCreate(create);
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
        return id;
    }

    public Page<ReportProblemInfo> listPage (String content, String userId, String timeYear, String timeMonth,String address, String problemType, Integer status, Integer pageNum, Integer pageSize) throws Exception{
        Page<ReportProblemInfo> page = new Page<>(pageNum, pageSize);
        EntityWrapper<ReportProblemInfo> wrapper = new EntityWrapper<>();
        String timeYearSql = null;
        String timeMonthSql = null;
        if (StringUtils.isNotBlank(content)) {
            wrapper.like("a.content",content, SqlLike.DEFAULT);
        }
        if (StringUtils.isNotBlank(address)) {
            wrapper.like("a.address", address, SqlLike.DEFAULT);
        }
        if (StringUtils.isNotBlank(problemType)) {
            wrapper.eq("a.problemType", problemType);
        }
        if (status != null) {
            wrapper.eq("a.status", status);
        }
        if (StringUtils.isBlank(userId)) {//所有人的
            wrapper.eq("a.isOpen", 1);//公开的
        }
        if (StringUtils.isNotBlank(userId)) {
            wrapper.eq("a.userId", userId);
        }
        if (StringUtils.isNotBlank(timeYear)) {
            timeYearSql = "YEAR(a.gmt_create)='"+ timeYear+"'";
        }
        if (StringUtils.isNotBlank(timeMonth)) {
            timeMonthSql = "MONTH(a.gmt_create)=" + timeMonth;
        }
        wrapper.orderBy("a.gmt_create", false);
        List<ReportProblemInfo> list = reportProblemInfoMapper.selecInfoPage(page, wrapper, timeYearSql, timeMonthSql);
        page.setRecords(list);
        return page;
    }

    public List<HandleProblemInfo> getSchedule(Integer reportProblemId){
        return reportProblemInfoMapper.getSchedulePhoto(reportProblemId);
    }

   public List<ReportProblem> getXdAndSf(){
        EntityWrapper<ReportProblem> wrapper = new EntityWrapper<>();
        wrapper.eq("status",0);
        String[] type = {"吸毒","上访"};
        wrapper.in("problemType",type);
        List<ReportProblem> list = reportProblemMapper.selectList(wrapper);
        return list;
    }

    public void saveSendAfter(ReportProblem reportProblem){
        reportProblemMapper.updateById(reportProblem);
    }

    public List<ReportProblem> getNosolveList(){
        EntityWrapper<ReportProblem> wrapper = new EntityWrapper<>();
        wrapper.eq("status",0);
        wrapper.ne("mqlzd",10);//推送流程没有走完的
        List<ReportProblem> list = reportProblemMapper.selectList(wrapper);
        return list;
    }
}

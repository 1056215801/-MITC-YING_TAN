package com.mit.community.population.service;

import com.mit.community.entity.*;
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mit.community.entity.entity.DeviceGroup;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PersonLabelsService {
    @Autowired
    private PersonLabelsMapper labelsMapper;

    public void saveLabels(String labels, Integer userId) {
        labelsMapper.saveLabels(labels, userId);
    }

    public String getLabelsByHousehold(Integer household) {
        return labelsMapper.getLabelsByHousehold(household);
    }

    public String getRkcfByIdNum(String idNum) {
        return labelsMapper.getRkcfByIdNum(idNum);
    }

    public Integer getSirPersonBaseInfoId(Integer id) {
        return labelsMapper.getSirPersonBaseInfoId(id);
    }

    public TaskMessageSirInfo getSirUserIdBySirPersonBaseInfoId(Integer person_baseinfo_id) {
        return labelsMapper.getSirUserIdBySirPersonBaseInfoId(person_baseinfo_id);
    }

    public TaskMessageContent getTaskMessageContent(Integer reportProblemId, Integer wgyId) {
        return labelsMapper.getTaskMessageContent(reportProblemId, wgyId);
    }

    public int getPeopleCount() {
        return labelsMapper.getPeopleCount();
    }

    public int getOutCount() {
        return labelsMapper.getOutCount();
    }

    public String getSirPhoneByPersonBaseInfoId(Integer person_baseinfo_id) {
        return labelsMapper.getSirPhoneByPersonBaseInfoId(person_baseinfo_id);
    }

    public void updateMqlzd(Integer id) {
        labelsMapper.updateMqlzd(id);
    }


    public PeopleOut getPeopleOue() {
        List<PeopleOut> list = labelsMapper.getPeopleOue();
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
    public String getMenJinTime(String name){
        return labelsMapper.getMenJinTime(name);
    }

    public String getContentByTime(LocalDateTime time){
        return labelsMapper.getContentByTime(time);
    }

    public List<MenJinInfo> getMenJinList(String name){
        return labelsMapper.getMenJinList(name);
    }

    public List<WgyInfo> getWgyList(Integer wgyId) {
        return labelsMapper.getWgyList(wgyId);
    }

    public String getWgyDeptById(Integer id){
        return labelsMapper.getWgyDeptById(id);
    }

    public String getTimeCha(String id) {
        return labelsMapper.getTimeCha(id);
    }

    public String getGroupName(String id){
        return labelsMapper.getGroupName(id);
    }

    public String getMaxDeviceId(){
        return labelsMapper.getMaxDeviceId();
    }
}

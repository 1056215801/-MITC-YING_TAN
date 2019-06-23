package com.mit.community.population.service;

import com.mit.community.entity.TaskMessageContent;
import com.mit.community.entity.TaskMessageSirInfo;
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PersonLabelsService {
    @Autowired
    private PersonLabelsMapper labelsMapper;

    public void saveLabels(String labels,Integer userId){
        labelsMapper.saveLabels(labels, userId);
    }

    public String getLabelsByHousehold(Integer household){
        return labelsMapper.getLabelsByHousehold(household);
    }

    public int getRkcfByIdNum(String idNum){
        return labelsMapper.getRkcfByIdNum(idNum);
    }

    public Integer getSirPersonBaseInfoId(Integer id) {
        return labelsMapper.getSirPersonBaseInfoId(id);
    }

    public TaskMessageSirInfo getSirUserIdBySirPersonBaseInfoId(Integer person_baseinfo_id){
        return labelsMapper.getSirUserIdBySirPersonBaseInfoId(person_baseinfo_id);
    }

    public TaskMessageContent getTaskMessageContent(Integer reportProblemId, Integer wgyId){
        return labelsMapper.getTaskMessageContent(reportProblemId, wgyId);
    }
    public int getPeopleCount(){
        return labelsMapper.getPeopleCount();
    }

    public int getOutCount(){
        return labelsMapper.getOutCount();
    }

    public String getSirPhoneByPersonBaseInfoId(Integer person_baseinfo_id){
        return labelsMapper.getSirPhoneByPersonBaseInfoId(person_baseinfo_id);
    }

    public void updateMqlzd(Integer id){
        labelsMapper.updateMqlzd(id);
    }

    public String getPeopleOue(){
        return labelsMapper.getPeopleOue();
    }

    public String getMenJinTime(String name){
        return labelsMapper.getMenJinTime(name);
    }

    public String getContentByTime(LocalDateTime time){
        return labelsMapper.getContentByTime(time);
    }

}

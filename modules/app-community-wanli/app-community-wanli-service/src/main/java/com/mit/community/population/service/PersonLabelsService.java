package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.*;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mit.community.entity.entity.DeviceGroup;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PersonLabelsService {
    @Autowired
    private PersonLabelsMapper labelsMapper;
    @Autowired
    private PersonBaseInfoService personBaseInfoService;

    public void saveLabels(String labels, Integer userId) {
        labelsMapper.saveLabels(labels, userId);
    }

    public String getLabelsByHousehold(Integer household) {
        return labelsMapper.getLabelsByHousehold(household);
    }

    public String getRkcfByIdNum(String idNum) {
        return labelsMapper.getRkcfByIdNum(idNum);
    }

    public String getRkcfByMobile(String mobile, String communityCode) {
        /*String ret = labelsMapper.getRkcfByMobile(mobile);
        if(){
            return ret;
        } else{
            return null;
        }*/
        PersonBaseInfo personBaseInfo = personBaseInfoService.getPersonByMobile(mobile, communityCode);
        if (personBaseInfo != null) {
            if (personBaseInfo.getRksx() == 0) {
                return "";
            } else {
                return String.valueOf(personBaseInfo.getRksx());
            }
        }
        return null;
        //return labelsMapper.getRkcfByMobile(mobile);
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

    public List<OwnerShipInfo> getOwnerInfo(String cph){
        return labelsMapper.getOwnerInfo(cph);
    }

    public List<DnakeDeviceDetailsInfo> getUnBindDevice(String ip){
        return labelsMapper.getUnBindDevice(ip);
    }

    public Page<DeviceBugPeople> pageDeviceDugPeople(String communityCode, Integer pageNum, Integer pageSize) {
        Page<DeviceBugPeople>  page = new Page<>(pageNum, pageSize);
        EntityWrapper<DeviceBugPeople> wrapper = new EntityWrapper<> ();
        wrapper.eq("a.serialnumber", communityCode);
        wrapper.in("a.identity_type","(1,2)");
        List<DeviceBugPeople> list = labelsMapper.pageDeviceDugPeople(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public DeviceIsOnline getIsOnline(@Param("deviceNum")String deviceNum){
        return labelsMapper.getIsOnline(deviceNum);
    }
}

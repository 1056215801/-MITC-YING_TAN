package com.mit.community.population.service;

import com.mit.community.entity.entity.XmsfPeopleInfo;
import com.mit.community.mapper.mapper.XmsfPeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class XmsfPeopleService {
    @Autowired
    private XmsfPeopleMapper xmsfPeopleMapper;

    public void save(String sflf, String yzm, String ypxq, String fxcs, String sfrq, String wxxpglx, LocalDateTime xjrq, String xjqk,
                       LocalDateTime azrq, String azqk, String wazyy, String bjqk, String sfcxfz, String cxfzzm, Integer person_baseinfo_id){
        XmsfPeopleInfo xmsfPeopleInfo = new XmsfPeopleInfo(sflf, yzm, ypxq, fxcs, sfrq, wxxpglx, xjrq, xjqk, azrq, azqk, wazyy, bjqk, sfcxfz, cxfzzm, person_baseinfo_id);
        xmsfPeopleInfo.setGmtCreate(LocalDateTime.now());
        xmsfPeopleInfo.setGmtModified(LocalDateTime.now());
        xmsfPeopleMapper.insert(xmsfPeopleInfo);
    }

    public void save(XmsfPeopleInfo xmsfPeopleInfo){
        xmsfPeopleInfo.setGmtCreate(LocalDateTime.now());
        xmsfPeopleInfo.setGmtModified(LocalDateTime.now());
        xmsfPeopleMapper.insert(xmsfPeopleInfo);
    }
}

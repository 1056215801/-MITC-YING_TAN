package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.XmsfPeopleInfo;
import com.mit.community.mapper.mapper.XmsfPeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class XmsfPeopleService {
    @Autowired
    private XmsfPeopleMapper xmsfPeopleMapper;

    public void save(String sflf, String yzm, String ypxq, String fxcs, String sfrq, String wxxpglx, LocalDateTime xjrq, String xjqk,
                       LocalDateTime azrq, String azqk, String wazyy, String bjqk, String sfcxfz, String cxfzzm, Integer person_baseinfo_id){
        /*XmsfPeopleInfo xmsfPeopleInfo = new XmsfPeopleInfo(sflf, yzm, ypxq, fxcs, sfrq, wxxpglx, xjrq, xjqk, azrq, azqk, wazyy, bjqk, sfcxfz, cxfzzm, person_baseinfo_id);
        XmsfPeopleInfo xmsfPeopleInfo = new XmsfPeopleInfo(sflf, yzm, ypxq, fxcs, sfrq, wxxpglx, xjrq, xjqk, azrq, azqk, wazyy, bjqk, sfcxfz, cxfzzm, person_baseinfo_id,0);
        xmsfPeopleInfo.setGmtCreate(LocalDateTime.now());
        xmsfPeopleInfo.setGmtModified(LocalDateTime.now());
        xmsfPeopleMapper.insert(xmsfPeopleInfo);*/
    }

    public void save(XmsfPeopleInfo xmsfPeopleInfo){
        EntityWrapper<XmsfPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", xmsfPeopleInfo.getPerson_baseinfo_id());
        List<XmsfPeopleInfo> list = xmsfPeopleMapper.selectList(wrapper);
        if (list.isEmpty()) {
            xmsfPeopleInfo.setGmtCreate(LocalDateTime.now());
            xmsfPeopleInfo.setGmtModified(LocalDateTime.now());
            xmsfPeopleMapper.insert(xmsfPeopleInfo);
        } else {
            xmsfPeopleInfo.setGmtModified(LocalDateTime.now());
            EntityWrapper<XmsfPeopleInfo> update = new EntityWrapper<>();
            wrapper.eq("person_baseinfo_id", xmsfPeopleInfo.getPerson_baseinfo_id());
            xmsfPeopleMapper.update(xmsfPeopleInfo, update);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<XmsfPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<XmsfPeopleInfo> list = xmsfPeopleMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            XmsfPeopleInfo xmsfPeopleInfo = list.get(0);
            xmsfPeopleInfo.setIsDelete(1);
            EntityWrapper<XmsfPeopleInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            xmsfPeopleMapper.update(xmsfPeopleInfo, dalete);
        }
    }
}

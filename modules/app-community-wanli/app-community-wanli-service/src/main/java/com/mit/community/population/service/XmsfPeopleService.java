package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.entity.entity.XmsfPeopleInfo;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import com.mit.community.mapper.mapper.XmsfPeopleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class XmsfPeopleService {
    @Autowired
    private XmsfPeopleMapper xmsfPeopleMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    public void save(String sflf, String yzm, String ypxq, String fxcs, String sfrq, String wxxpglx, LocalDateTime xjrq, String xjqk,
                     LocalDateTime azrq, String azqk, String wazyy, String bjqk, String sfcxfz, String cxfzzm, Integer person_baseinfo_id) {
        /*XmsfPeopleInfo xmsfPeopleInfo = new XmsfPeopleInfo(sflf, yzm, ypxq, fxcs, sfrq, wxxpglx, xjrq, xjqk, azrq, azqk, wazyy, bjqk, sfcxfz, cxfzzm, person_baseinfo_id);
        XmsfPeopleInfo xmsfPeopleInfo = new XmsfPeopleInfo(sflf, yzm, ypxq, fxcs, sfrq, wxxpglx, xjrq, xjqk, azrq, azqk, wazyy, bjqk, sfcxfz, cxfzzm, person_baseinfo_id,0);
        xmsfPeopleInfo.setGmtCreate(LocalDateTime.now());
        xmsfPeopleInfo.setGmtModified(LocalDateTime.now());
        xmsfPeopleMapper.insert(xmsfPeopleInfo);*/
    }
    @Transactional
    public void save(XmsfPeopleInfo xmsfPeopleInfo) {
        EntityWrapper<XmsfPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", xmsfPeopleInfo.getPerson_baseinfo_id());
        List<XmsfPeopleInfo> list = xmsfPeopleMapper.selectList(wrapper);
        if (list.isEmpty()) {
            xmsfPeopleInfo.setGmtCreate(LocalDateTime.now());
            xmsfPeopleInfo.setGmtModified(LocalDateTime.now());
            xmsfPeopleMapper.insert(xmsfPeopleInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(xmsfPeopleInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",刑满释放";
            } else {
                label = ",刑满释放";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            xmsfPeopleInfo.setId(list.get(0).getId());
            xmsfPeopleInfo.setGmtModified(LocalDateTime.now());
            xmsfPeopleMapper.updateById(xmsfPeopleInfo);
            //EntityWrapper<XmsfPeopleInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", xmsfPeopleInfo.getPerson_baseinfo_id());
            //xmsfPeopleMapper.update(xmsfPeopleInfo, update);
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

    @Transactional
    public void saveList(List<XmsfPeopleInfo> list) {
        for(XmsfPeopleInfo azbInfo:list){
            this.save(azbInfo);
        }
    }
}

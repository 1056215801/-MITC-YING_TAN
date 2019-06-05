package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.SFPeopleInfo;
import com.mit.community.mapper.mapper.SFPeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SFPeopleService {
    @Autowired
    private SFPeopleMapper sFPeopleMapper;

    public void save(String sfqzxf, int lxcs, int ldcs, LocalDateTime sfsj, int sfrs, String sffsdd, String sfrysq,
                       String clqkbf, Integer person_baseinfo_id){
        SFPeopleInfo sFPeopleInfo = new SFPeopleInfo(sfqzxf, lxcs, ldcs, sfsj, sfrs, sffsdd, sfrysq, clqkbf, person_baseinfo_id);
        sFPeopleInfo.setGmtCreate(LocalDateTime.now());
        sFPeopleInfo.setGmtModified(LocalDateTime.now());
        sFPeopleMapper.insert(sFPeopleInfo);

    }

    public void save(SFPeopleInfo sFPeopleInfo){
        EntityWrapper<SFPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", sFPeopleInfo.getPerson_baseinfo_id());
        List<SFPeopleInfo> list = sFPeopleMapper.selectList(wrapper);
        if (list.isEmpty()) {
            sFPeopleInfo.setGmtCreate(LocalDateTime.now());
            sFPeopleInfo.setGmtModified(LocalDateTime.now());
            sFPeopleMapper.insert(sFPeopleInfo);
        } else {
            sFPeopleInfo.setGmtModified(LocalDateTime.now());
            EntityWrapper<SFPeopleInfo> update = new EntityWrapper<>();
            wrapper.eq("person_baseinfo_id", sFPeopleInfo.getPerson_baseinfo_id());
            sFPeopleMapper.update(sFPeopleInfo, update);
        }
    }
}

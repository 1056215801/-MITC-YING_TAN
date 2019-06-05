package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.EngPeopleInfo;
import com.mit.community.mapper.mapper.EngPeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EngPeopleService {
    @Autowired
    private EngPeopleMapper engPeopleMapper;

    public void save(String wwx, String wwm, String zwm, String gj, String zjxy, String zjdm, String zjhm,
                     LocalDateTime zjyxq, String lhmd, LocalDateTime ddrq, LocalDateTime yjlkrq, int sfzdry, Integer person_baseinfo_id) {
        //EngPeopleInfo engPeopleInfo = new EngPeopleInfo(wwx, wwm, zwm, gj, zjxy, zjdm, zjhm, zjyxq, lhmd, ddrq, yjlkrq, sfzdry, person_baseinfo_id);
        //engPeopleInfo.setGmtCreate(LocalDateTime.now());
        //engPeopleInfo.setGmtModified(LocalDateTime.now());
        //engPeopleMapper.insert(engPeopleInfo);
    }

    public void save(EngPeopleInfo engPeopleInfo) {
        EntityWrapper<EngPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", engPeopleInfo.getPerson_baseinfo_id());
        List<EngPeopleInfo> list = engPeopleMapper.selectList(wrapper);
        if (list.isEmpty()) {
            engPeopleInfo.setGmtCreate(LocalDateTime.now());
            engPeopleInfo.setGmtModified(LocalDateTime.now());
            engPeopleMapper.insert(engPeopleInfo);
        } else {
            engPeopleInfo.setGmtModified(LocalDateTime.now());
            EntityWrapper<EngPeopleInfo> update = new EntityWrapper<>();
            wrapper.eq("person_baseinfo_id", engPeopleInfo.getPerson_baseinfo_id());
            engPeopleMapper.update(engPeopleInfo, update);
        }
    }
}

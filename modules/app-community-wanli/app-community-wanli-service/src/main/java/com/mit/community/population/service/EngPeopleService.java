package com.mit.community.population.service;

import com.mit.community.entity.EngPeopleInfo;
import com.mit.community.mapper.EngPeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EngPeopleService {
    @Autowired
    private EngPeopleMapper engPeopleMapper;

    public void save(String wwx, String wwm, String zwm, String gj, String zjxy, String zjdm, String zjhm,
                     LocalDateTime zjyxq, String lhmd, LocalDateTime ddrq, LocalDateTime yjlkrq, int sfzdry, Integer person_baseinfo_id){
        EngPeopleInfo engPeopleInfo = new EngPeopleInfo(wwx, wwm, zwm, gj, zjxy, zjdm, zjhm, zjyxq, lhmd, ddrq, yjlkrq, sfzdry, person_baseinfo_id);
        engPeopleInfo.setGmtCreate(LocalDateTime.now());
        engPeopleInfo.setGmtModified(LocalDateTime.now());
        engPeopleMapper.insert(engPeopleInfo);
    }
}

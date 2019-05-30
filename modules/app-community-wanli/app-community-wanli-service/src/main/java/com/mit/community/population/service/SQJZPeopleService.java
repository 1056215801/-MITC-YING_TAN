package com.mit.community.population.service;

import com.mit.community.entity.entity.SQJZPeopleinfo;
import com.mit.community.mapper.mapper.SQJZPeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SQJZPeopleService {
    @Autowired
    private SQJZPeopleMapper sQJZPeopleMapper;

    public void save(String sqjzrybh, String yjycs, String jzlb, String ajlb, String jtzm, String ypxq, LocalDateTime ypxkssj, LocalDateTime ypxjssj,
                       LocalDateTime jzkssj, LocalDateTime jzjssj, String jsfs, String ssqk, String sflgf, String ssqku, String sfjljzxz, String jzjclx, String sfytg,
                       String tgyy, String jcjdtgqk, String tgjzqk, String sfylg, String lgyy, String jcjdlgqk, String lgjzqk, String jcqk, String xfbgzx,
                       String sfcxfz, String cxfzmc, Integer person_baseinfo_id){
        SQJZPeopleinfo sQJZPeopleinfo = new SQJZPeopleinfo(sqjzrybh, yjycs, jzlb, ajlb, jtzm, ypxq, ypxkssj, ypxjssj, jzkssj, jzjssj, jsfs, ssqk, sflgf, ssqku, sfjljzxz, jzjclx, sfytg,
                tgyy, jcjdtgqk, tgjzqk, sfylg, lgyy, jcjdlgqk, lgjzqk, jcqk, xfbgzx, sfcxfz, cxfzmc, person_baseinfo_id);
        sQJZPeopleinfo.setGmtCreate(LocalDateTime.now());
        sQJZPeopleinfo.setGmtModified(LocalDateTime.now());
        sQJZPeopleMapper.insert(sQJZPeopleinfo);

    }
}

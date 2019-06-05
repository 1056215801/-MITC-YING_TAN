package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.SQJZPeopleinfo;
import com.mit.community.mapper.mapper.SQJZPeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SQJZPeopleService {
    @Autowired
    private SQJZPeopleMapper sQJZPeopleMapper;

    public void save(String sqjzrybh, String yjycs, String jzlb, String ajlb, String jtzm, String ypxq, LocalDateTime ypxkssj, LocalDateTime ypxjssj,
                       LocalDateTime jzkssj, LocalDateTime jzjssj, String jsfs, String ssqk, String sflgf, String ssqku, String sfjljzxz, String jzjclx, String sfytg,
                       String tgyy, String jcjdtgqk, String tgjzqk, String sfylg, String lgyy, String jcjdlgqk, String lgjzqk, String jcqk, String xfbgzx,
                       String sfcxfz, String cxfzmc, Integer person_baseinfo_id){
        /*SQJZPeopleinfo sQJZPeopleinfo = new SQJZPeopleinfo(sqjzrybh, yjycs, jzlb, ajlb, jtzm, ypxq, ypxkssj, ypxjssj, jzkssj, jzjssj, jsfs, ssqk, sflgf, ssqku, sfjljzxz, jzjclx, sfytg,
                tgyy, jcjdtgqk, tgjzqk, sfylg, lgyy, jcjdlgqk, lgjzqk, jcqk, xfbgzx, sfcxfz, cxfzmc, person_baseinfo_id);
        sQJZPeopleinfo.setGmtCreate(LocalDateTime.now());
        sQJZPeopleinfo.setGmtModified(LocalDateTime.now());
        sQJZPeopleMapper.insert(sQJZPeopleinfo);*/

    }

    public void save(SQJZPeopleinfo sQJZPeopleinfo){
        EntityWrapper<SQJZPeopleinfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", sQJZPeopleinfo.getPerson_baseinfo_id());
        List<SQJZPeopleinfo> list = sQJZPeopleMapper.selectList(wrapper);
        if (list.isEmpty()) {
            sQJZPeopleinfo.setGmtCreate(LocalDateTime.now());
            sQJZPeopleinfo.setGmtModified(LocalDateTime.now());
            sQJZPeopleMapper.insert(sQJZPeopleinfo);
        } else {
            sQJZPeopleinfo.setGmtModified(LocalDateTime.now());
            EntityWrapper<SQJZPeopleinfo> update = new EntityWrapper<>();
            wrapper.eq("person_baseinfo_id", sQJZPeopleinfo.getPerson_baseinfo_id());
            sQJZPeopleMapper.update(sQJZPeopleinfo, update);
        }
    }
}

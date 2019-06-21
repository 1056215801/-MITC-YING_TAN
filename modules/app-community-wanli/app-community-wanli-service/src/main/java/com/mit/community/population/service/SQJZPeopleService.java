package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.entity.entity.SQJZPeopleinfo;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import com.mit.community.mapper.mapper.SQJZPeopleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SQJZPeopleService {
    @Autowired
    private SQJZPeopleMapper sQJZPeopleMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    public void save(String sqjzrybh, String yjycs, String jzlb, String ajlb, String jtzm, String ypxq, LocalDateTime ypxkssj, LocalDateTime ypxjssj,
                     LocalDateTime jzkssj, LocalDateTime jzjssj, String jsfs, String ssqk, String sflgf, String ssqku, String sfjljzxz, String jzjclx, String sfytg,
                     String tgyy, String jcjdtgqk, String tgjzqk, String sfylg, String lgyy, String jcjdlgqk, String lgjzqk, String jcqk, String xfbgzx,
                     String sfcxfz, String cxfzmc, Integer person_baseinfo_id) {
        /*SQJZPeopleinfo sQJZPeopleinfo = new SQJZPeopleinfo(sqjzrybh, yjycs, jzlb, ajlb, jtzm, ypxq, ypxkssj, ypxjssj, jzkssj, jzjssj, jsfs, ssqk, sflgf, ssqku, sfjljzxz, jzjclx, sfytg,
                tgyy, jcjdtgqk, tgjzqk, sfylg, lgyy, jcjdlgqk, lgjzqk, jcqk, xfbgzx, sfcxfz, cxfzmc, person_baseinfo_id);
        SQJZPeopleinfo sQJZPeopleinfo = new SQJZPeopleinfo(sqjzrybh, yjycs, jzlb, ajlb, jtzm, ypxq, ypxkssj, ypxjssj, jzkssj, jzjssj, jsfs, ssqk, sflgf, ssqku, sfjljzxz, jzjclx, sfytg,
                tgyy, jcjdtgqk, tgjzqk, sfylg, lgyy, jcjdlgqk, lgjzqk, jcqk, xfbgzx, sfcxfz, cxfzmc, person_baseinfo_id,0);
        sQJZPeopleinfo.setGmtCreate(LocalDateTime.now());
        sQJZPeopleinfo.setGmtModified(LocalDateTime.now());
        sQJZPeopleMapper.insert(sQJZPeopleinfo);*/

    }

    public void save(SQJZPeopleinfo sQJZPeopleinfo) {
        EntityWrapper<SQJZPeopleinfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", sQJZPeopleinfo.getPerson_baseinfo_id());
        List<SQJZPeopleinfo> list = sQJZPeopleMapper.selectList(wrapper);
        if (list.isEmpty()) {
            sQJZPeopleinfo.setGmtCreate(LocalDateTime.now());
            sQJZPeopleinfo.setGmtModified(LocalDateTime.now());
            sQJZPeopleMapper.insert(sQJZPeopleinfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(sQJZPeopleinfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",社区矫正";
            } else {
                label = ",社区矫正";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            sQJZPeopleinfo.setId(list.get(0).getId());
            sQJZPeopleinfo.setGmtModified(LocalDateTime.now());
            sQJZPeopleMapper.updateById(sQJZPeopleinfo);
            //EntityWrapper<SQJZPeopleinfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", sQJZPeopleinfo.getPerson_baseinfo_id());
            //sQJZPeopleMapper.update(sQJZPeopleinfo, update);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<SQJZPeopleinfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<SQJZPeopleinfo> list = sQJZPeopleMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            SQJZPeopleinfo sQJZPeopleinfo = list.get(0);
            sQJZPeopleinfo.setIsDelete(1);
            EntityWrapper<SQJZPeopleinfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            sQJZPeopleMapper.update(sQJZPeopleinfo, dalete);
        }
    }

    @Transactional
    public void saveList(List<SQJZPeopleinfo> list) {
        for(SQJZPeopleinfo azbInfo:list){
            this.save(azbInfo);
        }
    }
}

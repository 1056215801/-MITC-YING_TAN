package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.entity.entity.SFPeopleInfo;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import com.mit.community.mapper.mapper.SFPeopleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SFPeopleService {
    @Autowired
    private SFPeopleMapper sFPeopleMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    public void save(String sfqzxf, int lxcs, int ldcs, LocalDateTime sfsj, int sfrs, String sffsdd, String sfrysq,
                     String clqkbf, Integer person_baseinfo_id) {
        SFPeopleInfo sFPeopleInfo = new SFPeopleInfo(sfqzxf, lxcs, ldcs, sfsj.toString(), sfrs, sffsdd,
                sfrysq, clqkbf, person_baseinfo_id, 0);
        sFPeopleInfo.setGmtCreate(LocalDateTime.now());
        sFPeopleInfo.setGmtModified(LocalDateTime.now());
        sFPeopleMapper.insert(sFPeopleInfo);

    }

    public void save(SFPeopleInfo sFPeopleInfo) {
        EntityWrapper<SFPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", sFPeopleInfo.getPerson_baseinfo_id());
        List<SFPeopleInfo> list = sFPeopleMapper.selectList(wrapper);
        if (list.isEmpty()) {
            sFPeopleInfo.setGmtCreate(LocalDateTime.now());
            sFPeopleInfo.setGmtModified(LocalDateTime.now());
            sFPeopleMapper.insert(sFPeopleInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(sFPeopleInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",上访人员";
            } else {
                label = ",上访人员";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            sFPeopleInfo.setId(list.get(0).getId());
            sFPeopleInfo.setGmtModified(LocalDateTime.now());
            sFPeopleMapper.updateById(sFPeopleInfo);
            //EntityWrapper<SFPeopleInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", sFPeopleInfo.getPerson_baseinfo_id());
            //sFPeopleMapper.update(sFPeopleInfo, update);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<SFPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<SFPeopleInfo> list = sFPeopleMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            SFPeopleInfo sFPeopleInfo = list.get(0);
            sFPeopleInfo.setIsDelete(1);
            EntityWrapper<SFPeopleInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            sFPeopleMapper.update(sFPeopleInfo, dalete);
        }
    }

    @Transactional
    public void saveList(List<SFPeopleInfo> list) {
        for(SFPeopleInfo azbInfo:list){
            this.save(azbInfo);
        }
    }
}

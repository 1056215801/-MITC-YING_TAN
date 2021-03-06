package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.EngPeopleInfo;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.mapper.EngPeopleMapper;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EngPeopleService {
    @Autowired
    private EngPeopleMapper engPeopleMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    public void save(String wwx, String wwm, String zwm, String gj, String zjxy, String zjdm, String zjhm,
                     LocalDateTime zjyxq, String lhmd, LocalDateTime ddrq, LocalDateTime yjlkrq, int sfzdry, Integer person_baseinfo_id) {
        /*EngPeopleInfo engPeopleInfo = new EngPeopleInfo(wwx, wwm, zwm, gj, zjxy, zjdm, zjhm, zjyxq,
                lhmd, ddrq, yjlkrq, sfzdry, person_baseinfo_id, 0);
        engPeopleInfo.setGmtCreate(LocalDateTime.now());
        engPeopleInfo.setGmtModified(LocalDateTime.now());
        engPeopleMapper.insert(engPeopleInfo);*/
    }

    @Transactional
    public void save(EngPeopleInfo engPeopleInfo) {
        EntityWrapper<EngPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", engPeopleInfo.getPerson_baseinfo_id());
        List<EngPeopleInfo> list = engPeopleMapper.selectList(wrapper);
        if (list.isEmpty()) {
            engPeopleInfo.setGmtCreate(LocalDateTime.now());
            engPeopleInfo.setGmtModified(LocalDateTime.now());
            engPeopleMapper.insert(engPeopleInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(engPeopleInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",境外人员";
            } else {
                label = ",境外人员";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            engPeopleInfo.setId(list.get(0).getId());
            engPeopleInfo.setGmtModified(LocalDateTime.now());
            //EntityWrapper<EngPeopleInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", engPeopleInfo.getPerson_baseinfo_id());
            //engPeopleMapper.update(engPeopleInfo, update);
            engPeopleMapper.updateById(engPeopleInfo);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<EngPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<EngPeopleInfo> list = engPeopleMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            EngPeopleInfo azbInfo = list.get(0);
            azbInfo.setIsDelete(1);
            EntityWrapper<EngPeopleInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            engPeopleMapper.update(azbInfo, dalete);
        }
    }
}

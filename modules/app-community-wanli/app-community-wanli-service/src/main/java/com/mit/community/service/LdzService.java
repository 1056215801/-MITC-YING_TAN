package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.LdzInfo;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.LdzMapper;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LdzService {
    @Autowired
    private LdzMapper ldzMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    @Transactional
    public void save(LdzInfo ldzInfo) {
        EntityWrapper<LdzInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", ldzInfo.getPerson_baseinfo_id());
        List<LdzInfo> list = ldzMapper.selectList(wrapper);
        if (list.isEmpty()) {
            ldzInfo.setGmtCreate(LocalDateTime.now());
            ldzInfo.setGmtModified(LocalDateTime.now());
            ldzMapper.insert(ldzInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(ldzInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",楼栋长";
            } else {
                label = ",楼栋长";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            ldzInfo.setId(list.get(0).getId());
            ldzInfo.setGmtModified(LocalDateTime.now());
            ldzMapper.updateById(ldzInfo);
            //EntityWrapper<AzbInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", azbInfo.getPerson_baseinfo_id());
            //aZBMapper.update(azbInfo, update);
        }
    }

    @Transactional
    public void saveList(List<LdzInfo> list) {
        for(LdzInfo azbInfo:list){
            this.save(azbInfo);
        }
    }
}

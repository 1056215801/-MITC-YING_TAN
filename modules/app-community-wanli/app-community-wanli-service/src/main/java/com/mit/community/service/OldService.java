package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.OldInfo;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.OldMapper;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OldService {
    @Autowired
    private OldMapper oldMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    @Transactional
    public void save(OldInfo oldInfo) {
        EntityWrapper<OldInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", oldInfo.getPerson_baseinfo_id());
        List<OldInfo> list = oldMapper.selectList(wrapper);
        if (list.isEmpty()) {
            oldInfo.setGmtCreate(LocalDateTime.now());
            oldInfo.setGmtModified(LocalDateTime.now());
            oldMapper.insert(oldInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(oldInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",老人";
            } else {
                label = ",老人";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            oldInfo.setId(list.get(0).getId());
            oldInfo.setGmtModified(LocalDateTime.now());
            oldMapper.updateById(oldInfo);
            //EntityWrapper<AzbInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", azbInfo.getPerson_baseinfo_id());
            //aZBMapper.update(azbInfo, update);
        }
    }

    @Transactional
    public void saveList(List<OldInfo> list) {
        for(OldInfo azbInfo:list){
            this.save(azbInfo);
        }
    }

}

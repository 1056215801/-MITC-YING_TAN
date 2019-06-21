package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ZyzInfo;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.ZyzMapper;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ZyzService {
    @Autowired
    private ZyzMapper zyzMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    @Transactional
    public void save(ZyzInfo zyzInfo) {
        EntityWrapper<ZyzInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", zyzInfo.getPerson_baseinfo_id());
        List<ZyzInfo> list = zyzMapper.selectList(wrapper);
        if (list.isEmpty()) {
            zyzInfo.setGmtCreate(LocalDateTime.now());
            zyzInfo.setGmtModified(LocalDateTime.now());
            zyzMapper.insert(zyzInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(zyzInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",志愿者";
            } else {
                label = ",志愿者";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            zyzInfo.setId(list.get(0).getId());
            zyzInfo.setGmtModified(LocalDateTime.now());
            zyzMapper.updateById(zyzInfo);
            //EntityWrapper<AzbInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", azbInfo.getPerson_baseinfo_id());
            //aZBMapper.update(azbInfo, update);
        }
    }

    @Transactional
    public void saveList(List<ZyzInfo> list) {
        for(ZyzInfo azbInfo:list){
            this.save(azbInfo);
        }
    }
}

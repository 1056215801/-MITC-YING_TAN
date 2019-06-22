package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.WgyInfo;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.WgyMapper;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WgyService {
    @Autowired
    private WgyMapper wgyMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    @Transactional
    public void save(WgyInfo wgyInfo) {
        EntityWrapper<WgyInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", wgyInfo.getPerson_baseinfo_id());
        List<WgyInfo> list = wgyMapper.selectList(wrapper);
        if (list.isEmpty()) {
            wgyInfo.setGmtCreate(LocalDateTime.now());
            wgyInfo.setGmtModified(LocalDateTime.now());
            wgyMapper.insert(wgyInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(wgyInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",网格员";
            } else {
                label = ",网格员";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            wgyInfo.setId(list.get(0).getId());
            wgyInfo.setGmtModified(LocalDateTime.now());
            wgyMapper.updateById(wgyInfo);
            //EntityWrapper<AzbInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", azbInfo.getPerson_baseinfo_id());
            //aZBMapper.update(azbInfo, update);
        }
    }

    @Transactional
    public void saveList(List<WgyInfo> list) {
        for(WgyInfo azbInfo:list){
            this.save(azbInfo);
        }
    }

    public Integer getWgyIdByJb(String jb){
        Integer id = null;
        EntityWrapper<WgyInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("jb", jb);
        List<WgyInfo> list = wgyMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            id = list.get(0).getId();
        }
        return id;
    }

}

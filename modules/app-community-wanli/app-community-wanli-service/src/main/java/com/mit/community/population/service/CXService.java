package com.mit.community.population.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.CXInfo;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.mapper.CXMapper;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CXService {
    @Autowired
    private CXMapper cXMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    public void save(String dysxcx, String drsxcx, String dssxcx, String bz, Integer person_baseinfo_id) {
        CXInfo cXInfo = new CXInfo(dysxcx, drsxcx, dssxcx, bz, person_baseinfo_id, 0);
        cXInfo.setGmtCreate(LocalDateTime.now());
        cXInfo.setGmtModified(LocalDateTime.now());
        cXMapper.insert(cXInfo);
    }

    public void save(CXInfo cXInfo) {
        EntityWrapper<CXInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", cXInfo.getPerson_baseinfo_id());
        List<CXInfo> list = cXMapper.selectList(wrapper);
        if (list.isEmpty()) {
            cXInfo.setGmtCreate(LocalDateTime.now());
            cXInfo.setGmtModified(LocalDateTime.now());
            cXMapper.insert(cXInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(cXInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",疑似传销";
            } else {
                label = ",疑似传销";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            cXInfo.setId(list.get(0).getId());
            cXInfo.setGmtModified(LocalDateTime.now());
            cXMapper.updateById(cXInfo);
            //EntityWrapper<CXInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", cXInfo.getPerson_baseinfo_id());
            //cXMapper.update(cXInfo, update);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<CXInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<CXInfo> list = cXMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            CXInfo azbInfo = list.get(0);
            azbInfo.setIsDelete(1);
            EntityWrapper<CXInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            cXMapper.update(azbInfo, dalete);
        }
    }

    @Transactional
    public void saveList(List<CXInfo> list) {
        for(CXInfo azbInfo:list){
            this.save(azbInfo);
        }
    }
}

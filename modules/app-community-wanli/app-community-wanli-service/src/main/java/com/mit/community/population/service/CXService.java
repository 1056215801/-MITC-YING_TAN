package com.mit.community.population.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.CXInfo;
import com.mit.community.mapper.mapper.CXMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CXService {
    @Autowired
    private CXMapper cXMapper;

    public void save(String dysxcx, String drsxcx, String dssxcx, String bz, Integer person_baseinfo_id){
        CXInfo cXInfo = new CXInfo(dysxcx, drsxcx, dssxcx, bz, person_baseinfo_id);
        cXInfo.setGmtCreate(LocalDateTime.now());
        cXInfo.setGmtModified(LocalDateTime.now());
        cXMapper.insert(cXInfo);
    }

    public void save(CXInfo cXInfo){
        EntityWrapper<CXInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", cXInfo.getPerson_baseinfo_id());
        List<CXInfo> list = cXMapper.selectList(wrapper);
        if (list.isEmpty()) {
            cXInfo.setGmtCreate(LocalDateTime.now());
            cXInfo.setGmtModified(LocalDateTime.now());
            cXMapper.insert(cXInfo);
        } else {
            cXInfo.setGmtModified(LocalDateTime.now());
            EntityWrapper<CXInfo> update = new EntityWrapper<>();
            wrapper.eq("person_baseinfo_id", cXInfo.getPerson_baseinfo_id());
            cXMapper.update(cXInfo, update);
        }
    }
}

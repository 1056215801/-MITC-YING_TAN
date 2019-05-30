package com.mit.community.population.service;


import com.mit.community.entity.entity.CXInfo;
import com.mit.community.mapper.mapper.CXMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}

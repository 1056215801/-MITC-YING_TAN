package com.mit.community.population.service;


import com.mit.community.entity.entity.ZDQSNCInfo;
import com.mit.community.mapper.mapper.ZDQSNCMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ZDQSNCService {
    @Autowired
    private ZDQSNCMapper zDQSNCMapper;

    public void save(String rylx, String jtqk, String jhrsfz, String jhrxm, String yjhrgx, String jhrlxfs, String jhrjzxxdz,
                       String sfwffz, String wffzqk, String bfrlxfs, String bfsd, String bfqk, Integer person_baseinfo_id){
        ZDQSNCInfo zDQSNCInfo = new ZDQSNCInfo(rylx, jtqk, jhrsfz, jhrxm, yjhrgx, jhrlxfs, jhrjzxxdz, sfwffz, wffzqk, bfrlxfs, bfsd, bfqk, person_baseinfo_id);
        zDQSNCInfo.setGmtCreate(LocalDateTime.now());
        zDQSNCInfo.setGmtModified(LocalDateTime.now());
        zDQSNCMapper.insert(zDQSNCInfo);
    }
}

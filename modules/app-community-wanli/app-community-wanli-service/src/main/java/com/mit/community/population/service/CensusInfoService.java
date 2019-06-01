package com.mit.community.population.service;

import com.mit.community.entity.entity.CensusInfo;
import com.mit.community.mapper.mapper.CensusInfoMapper;
import com.mit.community.mapper.mapper.UpdateRKCFMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CensusInfoService {
    @Autowired
    private CensusInfoMapper censusInfoMapper;
    @Autowired
    private UpdateRKCFMapper updateRKCFMapper;

    @Transactional
    public void save(String rhyzbz, String hh, String yhzgx, String hzsfz, Integer person_baseinfo_id){
        CensusInfo censusInfo = new CensusInfo(rhyzbz, hh, yhzgx, hzsfz, person_baseinfo_id);
        censusInfo.setGmtCreate(LocalDateTime.now());
        censusInfo.setGmtModified(LocalDateTime.now());
        censusInfoMapper.insert(censusInfo);
        updateRKCFMapper.updaterkcf(person_baseinfo_id,1);
    }
}

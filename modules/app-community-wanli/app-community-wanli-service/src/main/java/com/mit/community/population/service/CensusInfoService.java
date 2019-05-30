package com.mit.community.population.service;

import com.mit.community.entity.CensusInfo;
import com.mit.community.mapper.CensusInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CensusInfoService {
    @Autowired
    private CensusInfoMapper censusInfoMapper;

    public void save(String rhyzbz, String hh, String yhzgx, String hzsfz, Integer person_baseinfo_id){
        CensusInfo censusInfo = new CensusInfo(rhyzbz, hh, yhzgx, hzsfz, person_baseinfo_id);
        censusInfo.setGmtCreate(LocalDateTime.now());
        censusInfo.setGmtModified(LocalDateTime.now());
        censusInfoMapper.insert(censusInfo);

    }
}

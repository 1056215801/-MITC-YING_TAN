package com.mit.community.population.service;

import com.mit.community.entity.BearInfo;
import com.mit.community.mapper.BearInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BearInfoService {
    @Autowired
    private BearInfoMapper bearInfoMapper;

    public void save(String poxm, String poxb, String xgzdw, String djjhny, String hkxz, String hyzk, String jysssj
            , String sslx, String ssyy, String ccyy, Integer person_baseinfo_id) {
        BearInfo bearInfo = new BearInfo(poxm, poxb, xgzdw, djjhny, hkxz, hyzk, jysssj, sslx, ssyy, ccyy, person_baseinfo_id);
        bearInfo.setGmtCreate(LocalDateTime.now());
        bearInfo.setGmtModified(LocalDateTime.now());
        bearInfoMapper.insert(bearInfo);
    }
}

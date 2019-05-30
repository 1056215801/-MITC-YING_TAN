package com.mit.community.population.service;

import com.mit.community.entity.entity.CarInfo;
import com.mit.community.mapper.mapper.CarInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CarInfoService {
    @Autowired
    private CarInfoMapper carInfoMapper;

    public void save(String cph, String cx, String ys, String pp, String xh, String pl, String fdjh, String jsz, String xsz,
                       LocalDateTime szrq, LocalDateTime gmrq, Integer person_baseinfo_id){
        CarInfo carInfo = new CarInfo(cph, cx, ys, pp, xh, pl, fdjh, jsz, xsz, szrq, gmrq, person_baseinfo_id);
        carInfo.setGmtCreate(LocalDateTime.now());
        carInfo.setGmtModified(LocalDateTime.now());
        carInfoMapper.insert(carInfo);
    }
}

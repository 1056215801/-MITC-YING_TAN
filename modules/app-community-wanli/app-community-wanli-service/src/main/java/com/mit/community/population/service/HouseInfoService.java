package com.mit.community.population.service;

import com.mit.community.entity.entity.HouseInfo;
import com.mit.community.mapper.mapper.HouseInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HouseInfoService {
    @Autowired
    private HouseInfoMapper houseInfoMapper;

    @Transactional
    public void save(List<HouseInfo> list){
        for (HouseInfo houseInfo:list) {
            houseInfoMapper.insert(houseInfo);
        }
    }
}

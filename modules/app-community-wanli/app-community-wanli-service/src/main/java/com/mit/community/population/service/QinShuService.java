package com.mit.community.population.service;

import com.mit.community.entity.entity.QinShuInfo;
import com.mit.community.mapper.mapper.QinShuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QinShuService {
    @Autowired
    private QinShuMapper qinShuMapper;

    @Transactional
    public void save(List<QinShuInfo> list){
        for (QinShuInfo qinShuInfo:list) {
            qinShuMapper.insert(qinShuInfo);
        }
    }
}

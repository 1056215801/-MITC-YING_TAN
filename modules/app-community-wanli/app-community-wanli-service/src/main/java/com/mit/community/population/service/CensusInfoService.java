package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.CensusInfo;
import com.mit.community.mapper.mapper.CensusInfoMapper;
import com.mit.community.mapper.mapper.UpdateRKCFMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CensusInfoService {
    @Autowired
    private CensusInfoMapper censusInfoMapper;
    @Autowired
    private UpdateRKCFMapper updateRKCFMapper;

    @Transactional
    public void save(String rhyzbz, String hh, String yhzgx, String hzsfz, Integer person_baseinfo_id) {
        CensusInfo censusInfo = new CensusInfo(rhyzbz, hh, yhzgx, hzsfz, person_baseinfo_id);
        censusInfo.setGmtCreate(LocalDateTime.now());
        censusInfo.setGmtModified(LocalDateTime.now());
        censusInfoMapper.insert(censusInfo);
        updateRKCFMapper.updaterkcf(person_baseinfo_id, 1);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/6 13:05
     * @Company mitesofor
     * @Description:~根据基本信息主键查询户籍人口信息
     */
    public CensusInfo getObjectById(Integer id) {
        EntityWrapper<CensusInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", id);
        List<CensusInfo> res = censusInfoMapper.selectList(wrapper);
        if (res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }
}

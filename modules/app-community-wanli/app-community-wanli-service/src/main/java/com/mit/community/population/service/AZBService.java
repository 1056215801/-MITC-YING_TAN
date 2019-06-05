package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.AzbInfo;
import com.mit.community.mapper.mapper.AZBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AZBService {
    @Autowired
    private AZBMapper aZBMapper;

    public void save(String grtj, String sfwf, String wffzqk, String ajlb, String gzlx, String bfqk, String bfrdh
            ,String bfrxm, String szqk, String szjgmc, Integer person_baseinfo_id) {
        AzbInfo azbInfo = new AzbInfo(grtj, sfwf, wffzqk, ajlb, gzlx, bfqk, bfrdh, bfrxm, szqk, szjgmc, person_baseinfo_id);
        azbInfo.setGmtCreate(LocalDateTime.now());
        azbInfo.setGmtModified(LocalDateTime.now());
        aZBMapper.insert(azbInfo);
    }

    public void save(AzbInfo azbInfo) {
        EntityWrapper<AzbInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", azbInfo.getPerson_baseinfo_id());
        List<AzbInfo> list = aZBMapper.selectList(wrapper);
        if (list.isEmpty()) {
            azbInfo.setGmtCreate(LocalDateTime.now());
            azbInfo.setGmtModified(LocalDateTime.now());
            aZBMapper.insert(azbInfo);
        } else {
            azbInfo.setGmtModified(LocalDateTime.now());
            EntityWrapper<AzbInfo> update = new EntityWrapper<>();
            wrapper.eq("person_baseinfo_id", azbInfo.getPerson_baseinfo_id());
            aZBMapper.update(azbInfo, update);
        }
    }
}

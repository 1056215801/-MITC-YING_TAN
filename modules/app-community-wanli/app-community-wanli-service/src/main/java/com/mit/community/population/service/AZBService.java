package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.AzbInfo;
import com.mit.community.mapper.mapper.AZBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AZBService {
    @Autowired
    private AZBMapper aZBMapper;

    public void save(String grtj, String sfwf, String wffzqk, String ajlb, String gzlx, String bfqk, String bfrdh
            , String bfrxm, String szqk, String szjgmc, Integer person_baseinfo_id) {
        AzbInfo azbInfo = new AzbInfo(grtj, sfwf, wffzqk, ajlb, gzlx, bfqk, bfrdh, bfrxm, szqk, szjgmc, person_baseinfo_id, 0);
        azbInfo.setGmtCreate(LocalDateTime.now());
        azbInfo.setGmtModified(LocalDateTime.now());
        aZBMapper.insert(azbInfo);
    }

    @Transactional
    public void save(AzbInfo azbInfo) {
        EntityWrapper<AzbInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", azbInfo.getPerson_baseinfo_id());
        List<AzbInfo> list = aZBMapper.selectList(wrapper);
        if (list.isEmpty()) {
            azbInfo.setGmtCreate(LocalDateTime.now());
            azbInfo.setGmtModified(LocalDateTime.now());
            aZBMapper.insert(azbInfo);
        } else {
            azbInfo.setId(list.get(0).getId());
            azbInfo.setGmtModified(LocalDateTime.now());
            aZBMapper.updateById(azbInfo);
            //EntityWrapper<AzbInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", azbInfo.getPerson_baseinfo_id());
            //aZBMapper.update(azbInfo, update);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<AzbInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<AzbInfo> list = aZBMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            AzbInfo azbInfo = list.get(0);
            azbInfo.setIsDelete(1);
            EntityWrapper<AzbInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            aZBMapper.update(azbInfo, dalete);
        }
    }

    public void test(Integer id) {
        AzbInfo azbInfo = new AzbInfo();
        azbInfo.setIsDelete(1);
        EntityWrapper<AzbInfo> dalete = new EntityWrapper<>();
        dalete.eq("id", id);
        aZBMapper.update(azbInfo, dalete);
    }
}

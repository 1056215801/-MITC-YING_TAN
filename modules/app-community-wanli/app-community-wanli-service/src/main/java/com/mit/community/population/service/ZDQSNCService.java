package com.mit.community.population.service;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.ZDQSNCInfo;
import com.mit.community.mapper.mapper.ZDQSNCMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ZDQSNCService {
    @Autowired
    private ZDQSNCMapper zDQSNCMapper;

    public void save(String rylx, String jtqk, String jhrsfz, String jhrxm, String yjhrgx, String jhrlxfs, String jhrjzxxdz,
                       String sfwffz, String wffzqk, String bfrlxfs, String bfsd, String bfqk, Integer person_baseinfo_id){
        ZDQSNCInfo zDQSNCInfo = new ZDQSNCInfo(rylx, jtqk, jhrsfz, jhrxm, yjhrgx, jhrlxfs, jhrjzxxdz, sfwffz, wffzqk, bfrlxfs, bfsd, bfqk, person_baseinfo_id,0);
        zDQSNCInfo.setGmtCreate(LocalDateTime.now());
        zDQSNCInfo.setGmtModified(LocalDateTime.now());
        zDQSNCMapper.insert(zDQSNCInfo);
    }

    public void save(ZDQSNCInfo zDQSNCInfo){
        EntityWrapper<ZDQSNCInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", zDQSNCInfo.getPerson_baseinfo_id());
        List<ZDQSNCInfo> list = zDQSNCMapper.selectList(wrapper);
        if (list.isEmpty()) {
            zDQSNCInfo.setGmtCreate(LocalDateTime.now());
            zDQSNCInfo.setGmtModified(LocalDateTime.now());
            zDQSNCMapper.insert(zDQSNCInfo);
        } else {
            zDQSNCInfo.setGmtModified(LocalDateTime.now());
            EntityWrapper<ZDQSNCInfo> update = new EntityWrapper<>();
            wrapper.eq("person_baseinfo_id", zDQSNCInfo.getPerson_baseinfo_id());
            zDQSNCMapper.update(zDQSNCInfo, update);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<ZDQSNCInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<ZDQSNCInfo> list = zDQSNCMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            ZDQSNCInfo zDQSNCInfo = list.get(0);
            zDQSNCInfo.setIsDelete(1);
            EntityWrapper<ZDQSNCInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            zDQSNCMapper.update(zDQSNCInfo, dalete);
        }
    }
}

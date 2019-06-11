package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.ZSZHInfo;
import com.mit.community.mapper.mapper.ZSZHMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ZSZHService {
    @Autowired
    private ZSZHMapper zSZHMapper;

    public void save(String jtjjzk, String sfnrdb, String jhrsfzh, String jhrxm, String jhrlxfs, LocalDateTime ccfbrq, String mqzdlx,
                       String ywzszhs, int zszhcs, LocalDateTime sczszhrq, String mqwxxpgdj, String zlqk, String zlyy,
                       String sszyzlyy, String jskfxljgmc, String cyglry, String bfqk, Integer person_baseinfo_id){
        ZSZHInfo zSZHInfo = new ZSZHInfo(jtjjzk, sfnrdb, jhrsfzh, jhrxm, jhrlxfs, ccfbrq, mqzdlx, ywzszhs, zszhcs, sczszhrq, mqwxxpgdj,
                zlqk, zlyy, sszyzlyy, jskfxljgmc, cyglry, bfqk, person_baseinfo_id,0);
        zSZHInfo.setGmtCreate(LocalDateTime.now());
        zSZHInfo.setGmtModified(LocalDateTime.now());
        zSZHMapper.insert(zSZHInfo);

    }

    public void save(ZSZHInfo zSZHInfo){
        EntityWrapper<ZSZHInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", zSZHInfo.getPerson_baseinfo_id());
        List<ZSZHInfo> list = zSZHMapper.selectList(wrapper);
        if (list.isEmpty()) {
            zSZHInfo.setGmtCreate(LocalDateTime.now());
            zSZHInfo.setGmtModified(LocalDateTime.now());
            zSZHMapper.insert(zSZHInfo);
        } else {
            zSZHInfo.setGmtModified(LocalDateTime.now());
            EntityWrapper<ZSZHInfo> update = new EntityWrapper<>();
            wrapper.eq("person_baseinfo_id", zSZHInfo.getPerson_baseinfo_id());
            zSZHMapper.update(zSZHInfo, update);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<ZSZHInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<ZSZHInfo> list = zSZHMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            ZSZHInfo zSZHInfo = list.get(0);
            zSZHInfo.setIsDelete(1);
            EntityWrapper<ZSZHInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            zSZHMapper.update(zSZHInfo, dalete);
        }
    }
}

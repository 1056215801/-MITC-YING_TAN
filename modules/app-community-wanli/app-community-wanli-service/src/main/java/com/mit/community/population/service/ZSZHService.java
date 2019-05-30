package com.mit.community.population.service;

import com.mit.community.entity.entity.ZSZHInfo;
import com.mit.community.mapper.mapper.ZSZHMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ZSZHService {
    @Autowired
    private ZSZHMapper zSZHMapper;

    public void save(String jtjjzk, String sfnrdb, String jhrsfzh, String jhrxm, String jhrlxfs, LocalDateTime ccfbrq, String mqzdlx,
                       String ywzszhs, int zszhcs, LocalDateTime sczszhrq, String mqwxxpgdj, String zlqk, String zlyy,
                       String sszyzlyy, String jskfxljgmc, String cyglry, String bfqk, Integer person_baseinfo_id){
        ZSZHInfo zSZHInfo = new ZSZHInfo(jtjjzk, sfnrdb, jhrsfzh, jhrxm, jhrlxfs, ccfbrq, mqzdlx, ywzszhs, zszhcs, sczszhrq, mqwxxpgdj,
                zlqk, zlyy, sszyzlyy, jskfxljgmc, cyglry, bfqk, person_baseinfo_id);
        zSZHInfo.setGmtCreate(LocalDateTime.now());
        zSZHInfo.setGmtModified(LocalDateTime.now());
        zSZHMapper.insert(zSZHInfo);

    }

    public void save(ZSZHInfo zSZHInfo){
        zSZHInfo.setGmtCreate(LocalDateTime.now());
        zSZHInfo.setGmtModified(LocalDateTime.now());
        zSZHMapper.insert(zSZHInfo);

    }
}

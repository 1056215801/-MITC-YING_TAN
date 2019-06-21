package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.entity.entity.ZSZHInfo;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import com.mit.community.mapper.mapper.ZSZHMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ZSZHService {
    @Autowired
    private ZSZHMapper zSZHMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    public void save(String jtjjzk, String sfnrdb, String jhrsfzh, String jhrxm, String jhrlxfs, LocalDateTime ccfbrq, String mqzdlx,
                     String ywzszhs, int zszhcs, LocalDateTime sczszhrq, String mqwxxpgdj, String zlqk, String zlyy,
                     String sszyzlyy, String jskfxljgmc, String cyglry, String bfqk, Integer person_baseinfo_id) {
        /*ZSZHInfo zSZHInfo = new ZSZHInfo(jtjjzk, sfnrdb, jhrsfzh, jhrxm, jhrlxfs, ccfbrq,
                mqzdlx, ywzszhs, zszhcs, sczszhrq, mqwxxpgdj, zlqk, zlyy, sszyzlyy, jskfxljgmc,
                cyglry, bfqk, person_baseinfo_id, 0);
        zSZHInfo.setGmtCreate(LocalDateTime.now());
        zSZHInfo.setGmtModified(LocalDateTime.now());
        zSZHMapper.insert(zSZHInfo);*/

    }

    public void save(ZSZHInfo zSZHInfo) {
        EntityWrapper<ZSZHInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", zSZHInfo.getPerson_baseinfo_id());
        List<ZSZHInfo> list = zSZHMapper.selectList(wrapper);
        if (list.isEmpty()) {
            zSZHInfo.setGmtCreate(LocalDateTime.now());
            zSZHInfo.setGmtModified(LocalDateTime.now());
            zSZHMapper.insert(zSZHInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(zSZHInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",肇事肇祸";
            } else {
                label = ",肇事肇祸";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            zSZHInfo.setId(list.get(0).getId());
            zSZHInfo.setGmtModified(LocalDateTime.now());
            zSZHMapper.updateById(zSZHInfo);
            //EntityWrapper<ZSZHInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", zSZHInfo.getPerson_baseinfo_id());
            //zSZHMapper.update(zSZHInfo, update);
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

    @Transactional
    public void saveList(List<ZSZHInfo> list) {
        for(ZSZHInfo azbInfo:list){
            this.save(azbInfo);
        }
    }
}

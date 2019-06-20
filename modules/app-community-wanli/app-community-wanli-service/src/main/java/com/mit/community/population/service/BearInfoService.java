package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.BearInfo;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.mapper.BearInfoMapper;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BearInfoService {
    @Autowired
    private BearInfoMapper bearInfoMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    public void save(String poxm, String poxb, String xgzdw, String djjhny, String hkxz, String hyzk, String jysssj
            , String sslx, String ssyy, String ccyy, Integer person_baseinfo_id) {
        BearInfo bearInfo = new BearInfo(poxm, poxb, xgzdw, djjhny, hkxz, hyzk, jysssj, sslx, ssyy, ccyy, person_baseinfo_id, 0);
        bearInfo.setGmtCreate(LocalDateTime.now());
        bearInfo.setGmtModified(LocalDateTime.now());
        bearInfoMapper.insert(bearInfo);
    }

    public void save(BearInfo bearInfo) {
        EntityWrapper<BearInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", bearInfo.getPerson_baseinfo_id());
        List<BearInfo> list = bearInfoMapper.selectList(wrapper);
        if (list.isEmpty()) {
            bearInfo.setGmtCreate(LocalDateTime.now());
            bearInfo.setGmtModified(LocalDateTime.now());
            bearInfoMapper.insert(bearInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(bearInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",计生";
            } else {
                label = ",计生";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            bearInfo.setGmtModified(LocalDateTime.now());
            bearInfo.setId(list.get(0).getId());
            bearInfoMapper.updateById(bearInfo);
            //EntityWrapper<BearInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", bearInfo.getPerson_baseinfo_id());
            //bearInfoMapper.update(bearInfo, update);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<BearInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<BearInfo> list = bearInfoMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            BearInfo azbInfo = list.get(0);
            azbInfo.setIsDelete(1);
            EntityWrapper<BearInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            bearInfoMapper.update(azbInfo, dalete);
        }
    }

    @Transactional
    public void saveList(List<BearInfo> list) {
        for(BearInfo azbInfo:list){
            this.save(azbInfo);
        }
    }
}

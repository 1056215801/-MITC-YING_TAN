package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.PartyInfo;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.mapper.PartyInfoMapper;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartyInfoService {
    @Autowired
    private PartyInfoMapper partyInfoMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;


    public void save(LocalDateTime rdsq, LocalDateTime zzrq, LocalDateTime cjgzsj, String rdszzb, String zzszzb, String zzszdw, String szdzb,
                     LocalDateTime jrdzbsj, String xrdnzw, String rdjsr, String yyjndf, Integer person_baseinfo_id) {
        /*PartyInfo partyInfo = new PartyInfo(rdsq, zzrq, cjgzsj, rdszzb, zzszzb, zzszdw, szdzb, jrdzbsj, xrdnzw, rdjsr, yyjndf, person_baseinfo_id);
        PartyInfo partyInfo = new PartyInfo(rdsq, zzrq, cjgzsj, rdszzb, zzszzb, zzszdw, szdzb, jrdzbsj, xrdnzw, rdjsr, yyjndf, person_baseinfo_id,0);
        partyInfo.setGmtCreate(LocalDateTime.now());
        partyInfo.setGmtModified(LocalDateTime.now());
        partyInfoMapper.insert(partyInfo);*/

    }

    @Transactional
    public void save(PartyInfo partyInfo) {
        EntityWrapper<PartyInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", partyInfo.getPerson_baseinfo_id());
        List<PartyInfo> list = partyInfoMapper.selectList(wrapper);
        if (list.isEmpty()) {
            partyInfo.setGmtCreate(LocalDateTime.now());
            partyInfo.setGmtModified(LocalDateTime.now());
            partyInfoMapper.insert(partyInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(partyInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",党员";
            } else {
                label = ",党员";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            partyInfo.setId(list.get(0).getId());
            partyInfo.setGmtModified(LocalDateTime.now());
            partyInfoMapper.updateById(partyInfo);
            //EntityWrapper<PartyInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", partyInfo.getPerson_baseinfo_id());
            //partyInfoMapper.update(partyInfo, update);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<PartyInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<PartyInfo> list = partyInfoMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            PartyInfo partyInfo = list.get(0);
            partyInfo.setIsDelete(1);
            EntityWrapper<PartyInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            partyInfoMapper.update(partyInfo, dalete);
        }
    }

    @Transactional
    public void saveList(List<PartyInfo> list) {
        for(PartyInfo azbInfo:list){
            this.save(azbInfo);
        }
    }
}

package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.MilitaryServiceInfo;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.mapper.mapper.MilitaryServiceMapper;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MilitaryServiceService {
    @Autowired
    private MilitaryServiceMapper militaryServiceMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    public void save(String xyqk, String zybm, String zymc, String zytc, String cylb, String jdxx, String zyzgzs, String hscjdcsjjsxl, String sg,
                     String tz, String zylysl, String yylysl, String jkzk, String stmc, String bsdc, String wcqk, String zzcs, String bydjjl,
                     String yy, String djxs, int sftj, String bz, Integer person_baseinfo_id) {
        MilitaryServiceInfo militaryServiceInfo = new MilitaryServiceInfo(xyqk, zybm, zymc, zytc, cylb, jdxx, zyzgzs, hscjdcsjjsxl, sg, tz, zylysl, yylysl, jkzk, stmc, bsdc, wcqk, zzcs, bydjjl, yy, djxs, sftj, bz, person_baseinfo_id, 0);
        militaryServiceInfo.setGmtCreate(LocalDateTime.now());
        militaryServiceInfo.setGmtModified(LocalDateTime.now());
        militaryServiceMapper.insert(militaryServiceInfo);

    }

    @Transactional
    public void save(MilitaryServiceInfo militaryServiceInfo) {
        EntityWrapper<MilitaryServiceInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", militaryServiceInfo.getPerson_baseinfo_id());
        List<MilitaryServiceInfo> list = militaryServiceMapper.selectList(wrapper);
        if (list.isEmpty()) {
            militaryServiceInfo.setGmtCreate(LocalDateTime.now());
            militaryServiceInfo.setGmtModified(LocalDateTime.now());
            militaryServiceMapper.insert(militaryServiceInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(militaryServiceInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",兵役";
            } else {
                label = ",兵役";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            militaryServiceInfo.setId(list.get(0).getId());
            militaryServiceInfo.setGmtModified(LocalDateTime.now());
            militaryServiceMapper.updateById(militaryServiceInfo);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<MilitaryServiceInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<MilitaryServiceInfo> list = militaryServiceMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            MilitaryServiceInfo militaryServiceInfo = list.get(0);
            militaryServiceInfo.setIsDelete(1);
            EntityWrapper<MilitaryServiceInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            militaryServiceMapper.update(militaryServiceInfo, dalete);
        }
    }

    @Transactional
    public void saveList(List<MilitaryServiceInfo> list) {
        for(MilitaryServiceInfo azbInfo:list){
            this.save(azbInfo);
        }
    }
}

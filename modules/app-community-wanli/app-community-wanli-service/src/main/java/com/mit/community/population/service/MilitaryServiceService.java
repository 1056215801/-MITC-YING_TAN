package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.MilitaryServiceInfo;
import com.mit.community.mapper.mapper.MilitaryServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MilitaryServiceService {
    @Autowired
    private MilitaryServiceMapper militaryServiceMapper;

    public void save(String xyqk, String zybm, String zymc, String zytc, String cylb, String jdxx, String zyzgzs, String hscjdcsjjsxl, String sg,
                       String tz, String zylysl, String yylysl, String jkzk, String stmc, String bsdc, String wcqk, String zzcs, String bydjjl,
                       String yy, String djxs, int sftj, String bz, Integer person_baseinfo_id){
        MilitaryServiceInfo militaryServiceInfo = new MilitaryServiceInfo(xyqk, zybm, zymc, zytc, cylb, jdxx, zyzgzs, hscjdcsjjsxl, sg, tz, zylysl, yylysl, jkzk, stmc, bsdc, wcqk, zzcs, bydjjl, yy, djxs, sftj, bz, person_baseinfo_id,0);
        militaryServiceInfo.setGmtCreate(LocalDateTime.now());
        militaryServiceInfo.setGmtModified(LocalDateTime.now());
        militaryServiceMapper.insert(militaryServiceInfo);

    }

    public void save(MilitaryServiceInfo militaryServiceInfo){
        EntityWrapper<MilitaryServiceInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", militaryServiceInfo.getPerson_baseinfo_id());
        List<MilitaryServiceInfo> list = militaryServiceMapper.selectList(wrapper);
        if (list.isEmpty()) {
            militaryServiceInfo.setGmtCreate(LocalDateTime.now());
            militaryServiceInfo.setGmtModified(LocalDateTime.now());
            militaryServiceMapper.insert(militaryServiceInfo);
        } else {
            militaryServiceInfo.setGmtModified(LocalDateTime.now());
            EntityWrapper<MilitaryServiceInfo> update = new EntityWrapper<>();
            wrapper.eq("person_baseinfo_id", militaryServiceInfo.getPerson_baseinfo_id());
            militaryServiceMapper.update(militaryServiceInfo, update);
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
}

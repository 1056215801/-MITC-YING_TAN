package com.mit.community.population.service;

import com.mit.community.entity.entity.PartyInfo;
import com.mit.community.mapper.mapper.PartyInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PartyInfoService {
    @Autowired
    private PartyInfoMapper partyInfoMapper;


    public void save(LocalDateTime rdsq, LocalDateTime zzrq, LocalDateTime cjgzsj, String rdszzb, String zzszzb, String zzszdw, String szdzb,
                       LocalDateTime jrdzbsj, String xrdnzw, String rdjsr, String yyjndf, Integer person_baseinfo_id){
        /*PartyInfo partyInfo = new PartyInfo(rdsq, zzrq, cjgzsj, rdszzb, zzszzb, zzszdw, szdzb, jrdzbsj, xrdnzw, rdjsr, yyjndf, person_baseinfo_id);
        partyInfo.setGmtCreate(LocalDateTime.now());
        partyInfo.setGmtModified(LocalDateTime.now());
        partyInfoMapper.insert(partyInfo);*/

    }

    public void save(PartyInfo partyInfo){
        partyInfo.setGmtCreate(LocalDateTime.now());
        partyInfo.setGmtModified(LocalDateTime.now());
        partyInfoMapper.insert(partyInfo);

    }
}

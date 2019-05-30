package com.mit.community.population.service;

import com.mit.community.entity.entity.StayPeopleInfo;
import com.mit.community.mapper.mapper.StayPeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StayPeopleService {
    @Autowired
    private StayPeopleMapper stayPeopleMapper;

    public void save(String jkzk, String grnsr, String rhizbz, String lsrylx, String jtzyrysfzh, String jtzycyxm, String jtzycyjkzk, String ylsrygx, String jtzycylxfs,
                       String jtzycygzxxdz, String jtnsr, String knjsq, String bfqk, Integer person_baseinfo_id){
        StayPeopleInfo stayPeopleInfo = new StayPeopleInfo(jkzk, grnsr, rhizbz, lsrylx, jtzyrysfzh, jtzycyxm, jtzycyjkzk, ylsrygx, jtzycylxfs, jtzycygzxxdz, jtnsr, knjsq, bfqk, person_baseinfo_id);
        stayPeopleInfo.setGmtCreate(LocalDateTime.now());
        stayPeopleInfo.setGmtModified(LocalDateTime.now());
        stayPeopleMapper.insert(stayPeopleInfo);

    }

}

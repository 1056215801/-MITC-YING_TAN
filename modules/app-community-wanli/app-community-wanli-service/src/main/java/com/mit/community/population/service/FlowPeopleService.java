package com.mit.community.population.service;

import com.mit.community.entity.entity.FlowPeopleInfo;
import com.mit.community.mapper.mapper.FlowPeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FlowPeopleService {
    @Autowired
    private FlowPeopleMapper flowPeopleMapper;

    public void save(String lryy, String bzlx, String zjhm, LocalDateTime djrq, LocalDateTime zjdqrq, String zslx,
                       int sfzdgzry, String tslxfs, String jtzycylxfs, String yhzgx, String hzsfz, Integer person_baseinfo_id){
        FlowPeopleInfo flowPeopleInfo = new FlowPeopleInfo(lryy, bzlx, zjhm, djrq, zjdqrq, zslx,
                sfzdgzry, tslxfs, jtzycylxfs, yhzgx, hzsfz, person_baseinfo_id);
        flowPeopleInfo.setGmtCreate(LocalDateTime.now());
        flowPeopleInfo.setGmtModified(LocalDateTime.now());
        flowPeopleMapper.insert(flowPeopleInfo);
    }
}

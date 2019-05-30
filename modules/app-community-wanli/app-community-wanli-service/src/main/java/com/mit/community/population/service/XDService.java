package com.mit.community.population.service;

import com.mit.community.entity.XDInfo;
import com.mit.community.mapper.XDMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class XDService {
    @Autowired
    private XDMapper xDMapper;
    public void save(LocalDateTime ccfxsj, String gkqk, String gkrxm, String gkrlxfs, String bfqk, String bfrxm, String bfrlxfs,
                       String ywfzs, String xdqk, String xdyy, String xdhg, Integer person_baseinfo_id){
        XDInfo xDInfo = new XDInfo(ccfxsj, gkqk, gkrxm, gkrlxfs, bfqk, bfrxm, bfrlxfs, ywfzs, xdqk, xdyy, xdhg, person_baseinfo_id);
        xDInfo.setGmtCreate(LocalDateTime.now());
        xDInfo.setGmtModified(LocalDateTime.now());
        xDMapper.insert(xDInfo);
    }
}

package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.XDInfo;
import com.mit.community.mapper.mapper.XDMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public void save(XDInfo xDInfo){
        EntityWrapper<XDInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", xDInfo.getPerson_baseinfo_id());
        List<XDInfo> list = xDMapper.selectList(wrapper);
        if (list.isEmpty()) {
            xDInfo.setGmtCreate(LocalDateTime.now());
            xDInfo.setGmtModified(LocalDateTime.now());
            xDMapper.insert(xDInfo);
        } else {
            xDInfo.setGmtModified(LocalDateTime.now());
            EntityWrapper<XDInfo> update = new EntityWrapper<>();
            wrapper.eq("person_baseinfo_id", xDInfo.getPerson_baseinfo_id());
            xDMapper.update(xDInfo, update);
        }
    }
}

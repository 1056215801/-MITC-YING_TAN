package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.entity.entity.XDInfo;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import com.mit.community.mapper.mapper.XDMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class XDService {
    @Autowired
    private XDMapper xDMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    public void save(String ccfxsj, String gkqk, String gkrxm, String gkrlxfs, String bfqk, String bfrxm, String bfrlxfs,
                     String ywfzs, String xdqk, String xdyy, String xdhg, Integer person_baseinfo_id) {
        /*XDInfo xDInfo = new XDInfo(ccfxsj, gkqk, gkrxm, gkrlxfs, bfqk, bfrxm, bfrlxfs, ywfzs, xdqk, xdyy, xdhg, person_baseinfo_id);
        XDInfo xDInfo = new XDInfo(ccfxsj, gkqk, gkrxm, gkrlxfs, bfqk, bfrxm, bfrlxfs, ywfzs, xdqk, xdyy, xdhg, person_baseinfo_id,0);
        xDInfo.setGmtCreate(LocalDateTime.now());
        xDInfo.setGmtModified(LocalDateTime.now());
        xDMapper.insert(xDInfo);*/
    }

    public void save(XDInfo xDInfo) {
        EntityWrapper<XDInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", xDInfo.getPerson_baseinfo_id());
        List<XDInfo> list = xDMapper.selectList(wrapper);
        if (list.isEmpty()) {
            xDInfo.setGmtCreate(LocalDateTime.now());
            xDInfo.setGmtModified(LocalDateTime.now());
            xDMapper.insert(xDInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(xDInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",吸毒人员";
            } else {
                label = ",吸毒人员";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            xDInfo.setId(list.get(0).getId());
            xDInfo.setGmtModified(LocalDateTime.now());
            xDMapper.updateById(xDInfo);
            //EntityWrapper<XDInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", xDInfo.getPerson_baseinfo_id());
            //xDMapper.update(xDInfo, update);
        }
    }

    public void delete(Integer id) {
        EntityWrapper<XDInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<XDInfo> list = xDMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            XDInfo xDInfo = list.get(0);
            xDInfo.setIsDelete(1);
            EntityWrapper<XDInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            xDMapper.update(xDInfo, dalete);
        }
    }

    @Transactional
    public void saveList(List<XDInfo> list) {
        for(XDInfo azbInfo:list){
            this.save(azbInfo);
        }
    }
}

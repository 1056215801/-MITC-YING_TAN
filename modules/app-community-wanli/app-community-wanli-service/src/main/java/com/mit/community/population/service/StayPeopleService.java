package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.PersonBaseInfo;
import com.mit.community.entity.entity.StayPeopleInfo;
import com.mit.community.mapper.mapper.PersonBaseInfoMapper;
import com.mit.community.mapper.mapper.StayPeopleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StayPeopleService {
    @Autowired
    private StayPeopleMapper stayPeopleMapper;
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    public void save(String jkzk, String grnsr, String rhizbz, String lsrylx, String jtzyrysfzh, String jtzycyxm, String jtzycyjkzk, String ylsrygx, String jtzycylxfs,
                     String jtzycygzxxdz, String jtnsr, String knjsq, String bfqk, Integer person_baseinfo_id) {
        StayPeopleInfo stayPeopleInfo = new StayPeopleInfo(jkzk, grnsr, rhizbz, lsrylx, jtzyrysfzh, jtzycyxm, jtzycyjkzk, ylsrygx, jtzycylxfs, jtzycygzxxdz, jtnsr, knjsq, bfqk, person_baseinfo_id, 0);
        stayPeopleInfo.setGmtCreate(LocalDateTime.now());
        stayPeopleInfo.setGmtModified(LocalDateTime.now());
        stayPeopleMapper.insert(stayPeopleInfo);

    }

    @Transactional
    public void  save(StayPeopleInfo stayPeopleInfo) {
        EntityWrapper<StayPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", stayPeopleInfo.getPerson_baseinfo_id());
        List<StayPeopleInfo> list = stayPeopleMapper.selectList(wrapper);
        if (list.isEmpty()) {
            stayPeopleInfo.setGmtCreate(LocalDateTime.now());
            stayPeopleInfo.setGmtModified(LocalDateTime.now());
            stayPeopleMapper.insert(stayPeopleInfo);

            PersonBaseInfo personBaseInfo = personBaseInfoMapper.selectById(stayPeopleInfo.getPerson_baseinfo_id());
            String label = null;
            if (StringUtils.isNotBlank(personBaseInfo.getLabel())) {
                label = personBaseInfo.getLabel() + ",留守人员";
            } else {
                label = ",留守人员";
            }
            EntityWrapper<PersonBaseInfo> updatePerson = new EntityWrapper<>();
            updatePerson.eq("id", personBaseInfo.getId());
            personBaseInfo.setLabel(label);
            personBaseInfoMapper.update(personBaseInfo, updatePerson);
        } else {
            stayPeopleInfo.setId(list.get(0).getId());
            stayPeopleInfo.setGmtModified(LocalDateTime.now());
            stayPeopleMapper.updateById(stayPeopleInfo);
            //EntityWrapper<StayPeopleInfo> update = new EntityWrapper<>();
            //wrapper.eq("person_baseinfo_id", stayPeopleInfo.getPerson_baseinfo_id());
            //stayPeopleMapper.update(stayPeopleInfo, update);
        }
    }
    //人要耐得住寂寞，才能守得住繁华。人生最痛苦的就是拿不起放不下，不属于自己的快乐，
    // 及时放手也许是一种解脱，生活中没有谁对谁错，只有适不适合。v
    // 当发现很多已经改变，更要面对的是事实。

    public void delete(Integer id) {
        EntityWrapper<StayPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", id);
        List<StayPeopleInfo> list = stayPeopleMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            StayPeopleInfo stayPeopleInfo = list.get(0);
            stayPeopleInfo.setIsDelete(1);
            EntityWrapper<StayPeopleInfo> dalete = new EntityWrapper<>();
            dalete.eq("id", id);
            stayPeopleMapper.update(stayPeopleInfo, dalete);
        }
    }

    @Transactional
    public void saveList(List<StayPeopleInfo> list) {
        for(StayPeopleInfo azbInfo:list){
            this.save(azbInfo);
        }
    }

}

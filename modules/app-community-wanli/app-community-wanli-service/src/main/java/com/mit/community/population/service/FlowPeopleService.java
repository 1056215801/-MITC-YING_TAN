package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.FlowPeopleInfo;
import com.mit.community.mapper.mapper.FlowPeopleMapper;
import com.mit.community.mapper.mapper.UpdateRKCFMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlowPeopleService {
    @Autowired
    private FlowPeopleMapper flowPeopleMapper;
    @Autowired
    private UpdateRKCFMapper updateRKCFMapper;

    @Transactional
    public void save(String lryy, String bzlx, String zjhm, LocalDateTime djrq, LocalDateTime zjdqrq, String zslx,
                     int sfzdgzry, String tslxfs, String jtzycylxfs, String yhzgx, String hzsfz, Integer person_baseinfo_id) {
        FlowPeopleInfo flowPeopleInfo = new FlowPeopleInfo(lryy, bzlx, zjhm, djrq, zjdqrq, zslx,
                sfzdgzry, tslxfs, jtzycylxfs, yhzgx, hzsfz, person_baseinfo_id);
        flowPeopleInfo.setGmtCreate(LocalDateTime.now());
        flowPeopleInfo.setGmtModified(LocalDateTime.now());
        flowPeopleMapper.insert(flowPeopleInfo);
        updateRKCFMapper.updaterkcf(person_baseinfo_id, 2);
    }

    /**
     * @Author: HuShanLin
     * @Date: Create in 2019/6/6 13:03
     * @Company mitesofor
     * @Description:~根据基本信息主键查询成分信息
     */
    public FlowPeopleInfo getObjectById(Integer id) {
        EntityWrapper<FlowPeopleInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", id);
        List<FlowPeopleInfo> res = flowPeopleMapper.selectList(wrapper);
        if (res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }
}

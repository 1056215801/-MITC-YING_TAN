package com.mit.community.population.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.entity.HouseInfo;
import com.mit.community.mapper.mapper.HouseInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HouseInfoService {
    @Autowired
    private HouseInfoMapper houseInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public void save(List<HouseInfo> list, Integer person_baseinfo_id) {
        try {
            //先删除房屋
            EntityWrapper<HouseInfo> wrapper = new EntityWrapper<>();
            wrapper.eq("person_baseinfo_id", person_baseinfo_id);
            houseInfoMapper.delete(wrapper);
            for (HouseInfo houseInfo : list) {
                houseInfo.setGmtCreate(LocalDateTime.now());
                houseInfo.setGmtModified(LocalDateTime.now());
                houseInfoMapper.insert(houseInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<HouseInfo> getHouseInfo(Integer person_baseinfo_id) {
        EntityWrapper<HouseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id", person_baseinfo_id);
        List<HouseInfo> list = houseInfoMapper.selectList(wrapper);
        return list;
    }
}

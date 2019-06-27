package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ConfigInfo;
import com.mit.community.mapper.ConfigInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigInfoService {
    @Autowired
    private ConfigInfoMapper configInfoMapper;

    public ConfigInfo getConfig(){
        EntityWrapper<ConfigInfo> wrapper = new EntityWrapper<>();
        List<ConfigInfo> list = configInfoMapper.selectList(wrapper);
        if(!list.isEmpty()){
            return list.get(0);
        } else {
            return null;
        }
    }
}

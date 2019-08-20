package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.WarningConfig;
import com.mit.community.module.pass.mapper.WarningConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarningConfigService {
    @Autowired
    private WarningConfigMapper warningConfigMapper;

    public WarningConfig getByLabel(String label){
        EntityWrapper<WarningConfig> wrapper = new EntityWrapper<>();
        wrapper.eq("label", label);
        wrapper.eq("isWarning",1);
        List<WarningConfig> list = warningConfigMapper.selectList(wrapper);
        if(!list.isEmpty()){
            return list.get(0);
        } else {
            return null;
        }
    }

    public WarningConfig getByLabelAndCommunity(String label, String communityCode){
        EntityWrapper<WarningConfig> wrapper = new EntityWrapper<>();
        wrapper.eq("label", label);
        wrapper.eq("communityCode", communityCode);
        wrapper.eq("isWarning",1);
        List<WarningConfig> list = warningConfigMapper.selectList(wrapper);
        if(!list.isEmpty()){
            return list.get(0);
        } else {
            return null;
        }
    }
}

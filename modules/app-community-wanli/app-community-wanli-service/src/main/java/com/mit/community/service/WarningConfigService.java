package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.WarningConfig;
import com.mit.community.mapper.WarningConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Page<WarningConfig> getPage(Integer pageNum, Integer pageSize, String label){
        Page<WarningConfig> page = new Page<>(pageNum, pageSize);
        EntityWrapper<WarningConfig> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(label)) {
            wrapper.eq("label", label);
        }
        wrapper.orderBy("gmt_create",false);
        List<WarningConfig> list = warningConfigMapper.selectPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public void update(Integer id,String label,int isWarning,String warningInfo){
        WarningConfig warningConfig = new WarningConfig(label,isWarning,warningInfo);
        warningConfig.setId(id);
        warningConfig.setGmtModified(LocalDateTime.now());
        warningConfigMapper.updateById(warningConfig);
    }

    public void save(String label,int isWarning,String warningInfo){
        WarningConfig warningConfig = new WarningConfig(label,isWarning,warningInfo);
        warningConfig.setGmtModified(LocalDateTime.now());
        warningConfig.setGmtCreate(LocalDateTime.now());
        warningConfigMapper.insert(warningConfig);
    }
}

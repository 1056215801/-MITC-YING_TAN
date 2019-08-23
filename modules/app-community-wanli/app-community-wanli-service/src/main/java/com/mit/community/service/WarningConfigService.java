package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.PersonLabel;
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

    public Page<WarningConfig> getPage(Integer pageNum, Integer pageSize, String label, String communityCode, Integer isWarning){
        Page<WarningConfig> page = new Page<>(pageNum, pageSize);
        EntityWrapper<WarningConfig> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(label)) {
            wrapper.eq("label", label);
        }
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("communityCode", communityCode);
        }
        if (isWarning != null) {
            wrapper.eq("isWarning", isWarning);
        }

        wrapper.orderBy("gmt_create",false);
        List<WarningConfig> list = warningConfigMapper.selectPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public void update(Integer id,int isWarning, String warningInfo){
        WarningConfig warningConfig = new WarningConfig();
        warningConfig.setId(id);
        warningConfig.setIsWarning(isWarning);
        warningConfig.setWarningInfo(warningInfo);
        warningConfig.setGmtModified(LocalDateTime.now());
        warningConfigMapper.updateById(warningConfig);
    }

    public void updateWarningInfo(Integer id,String warningInfo){
        WarningConfig warningConfig = new WarningConfig();
        warningConfig.setId(id);
        warningConfig.setWarningInfo(warningInfo);
        warningConfig.setGmtModified(LocalDateTime.now());
        warningConfigMapper.updateById(warningConfig);
    }

    public void save(String labelName, String remarks, String communityCode){
        WarningConfig warningConfig = new WarningConfig(labelName,0,null,communityCode,remarks);
        warningConfig.setGmtModified(LocalDateTime.now());
        warningConfig.setGmtCreate(LocalDateTime.now());
        warningConfigMapper.insert(warningConfig);
    }

    public List<WarningConfig> getList(String communityCode){
        EntityWrapper<WarningConfig> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("communityCode", communityCode);
        }

        wrapper.orderBy("gmt_create",false);
        List<WarningConfig> list = warningConfigMapper.selectList(wrapper);
        return list;
    }

    public void update(Integer id,String labelName,String remarks, String communityCode){
        WarningConfig warningConfig = new WarningConfig();
        warningConfig.setId(id);
        warningConfig.setLabel(labelName);
        warningConfig.setRemarks(remarks);
        warningConfig.setGmtModified(LocalDateTime.now());
        warningConfigMapper.updateById(warningConfig);
    }

    public WarningConfig getByLabelAndCommunityCode(String labelName, String communityCode){
        EntityWrapper<WarningConfig> wrapper = new EntityWrapper<>();
        wrapper.eq("label",labelName);
        wrapper.eq("communityCode",communityCode);
        List<WarningConfig> list = warningConfigMapper.selectList(wrapper);
        if(!list.isEmpty()){
            return list.get(0);
        } else {
            return null;
        }
    }

    public void delete(Integer id){
        warningConfigMapper.deleteById(id);
    }

}

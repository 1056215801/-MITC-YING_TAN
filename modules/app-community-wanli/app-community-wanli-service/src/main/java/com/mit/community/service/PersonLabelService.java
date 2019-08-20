package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.PersonLabel;
import com.mit.community.mapper.PersonLabelMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PersonLabelService {
    @Autowired
    private PersonLabelMapper personLabelMapper;

    public Page<PersonLabel> getPage(Integer pageNum, Integer pageSize, String label, String communityCode){
        Page<PersonLabel> page = new Page<>(pageNum, pageSize);
        EntityWrapper<PersonLabel> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(label)) {
            wrapper.eq("label", label);
        }
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("communityCode", communityCode);
        }

        wrapper.orderBy("gmt_create",false);
        List<PersonLabel> list = personLabelMapper.selectPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public List<PersonLabel> getList(String communityCode){
        EntityWrapper<PersonLabel> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("communityCode", communityCode);
        }

        wrapper.orderBy("gmt_create",false);
        List<PersonLabel> list = personLabelMapper.selectList(wrapper);
        return list;
    }

    public void update(Integer id,String labelName,String remarks, String communityCode){
        PersonLabel personLabel = new PersonLabel(labelName,remarks,communityCode);
        personLabel.setId(id);
        personLabel.setGmtModified(LocalDateTime.now());
        personLabelMapper.updateById(personLabel);
    }

    public void save(String labelName,String remarks, String communityCode){
        PersonLabel personLabel = new PersonLabel(labelName,remarks,communityCode);
        personLabel.setGmtModified(LocalDateTime.now());
        personLabel.setGmtCreate(LocalDateTime.now());
        personLabelMapper.insert(personLabel);
    }
}

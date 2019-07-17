package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.ZdyPersonLabel;
import com.mit.community.mapper.ZdyPersonLabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ZdyPersonLabelService {
    @Autowired
    private ZdyPersonLabelMapper zdyPersonLabelMapper;

    public List<ZdyPersonLabel> getListByPersonBaseInfoId(Integer personBaseInfoId){
        EntityWrapper<ZdyPersonLabel> wrapper = new EntityWrapper<>();
        wrapper.eq("person_baseinfo_id",personBaseInfoId);
        List<ZdyPersonLabel> list = zdyPersonLabelMapper.selectList(wrapper);
        return list;
    }

    public void save(ZdyPersonLabel zdyPersonLabel) {
        zdyPersonLabel.setGmtCreate(LocalDateTime.now());
        zdyPersonLabel.setGmtModified(LocalDateTime.now());
        zdyPersonLabelMapper.insert(zdyPersonLabel);
    }

    public void saveList(Integer person_baseinfo_id, List<ZdyPersonLabel> labels){
        if (person_baseinfo_id != null && !labels.isEmpty()){
            for (int i=0; i<labels.size(); i++) {
                EntityWrapper<ZdyPersonLabel> wrapper = new EntityWrapper<>();
                wrapper.eq("person_baseinfo_id",person_baseinfo_id);
                wrapper.eq("label",labels.get(i).getLabel());
                List<ZdyPersonLabel> list = zdyPersonLabelMapper.selectList(wrapper);
                if (!list.isEmpty()) {
                    labels.get(i).setPersonBaseinfoId(person_baseinfo_id);
                    labels.get(i).setGmtCreate(LocalDateTime.now());
                    labels.get(i).setGmtModified(LocalDateTime.now());
                    zdyPersonLabelMapper.update(labels.get(i),wrapper);
                } else {
                    labels.get(i).setPersonBaseinfoId(person_baseinfo_id);
                    labels.get(i).setGmtCreate(LocalDateTime.now());
                    labels.get(i).setGmtModified(LocalDateTime.now());
                    zdyPersonLabelMapper.insert(labels.get(i));
                }

            }
        }
    }
}

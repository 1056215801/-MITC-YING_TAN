package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.PersonBaseInfo;
import com.mit.community.module.pass.mapper.PersonBaseInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

/**
 * 人员基本信息
 *
 * @author xiong
 * @date 2019/5/25
 * @company mitesofor
 */
@Service
public class PersonBaseInfoService {
    @Autowired
    private PersonBaseInfoMapper personBaseInfoMapper;

    @Autowired
    private LabelsService labelsService;



    public String getLabelsByMobile(String cellphone, String communityCode) {
        String label = "";
        Integer id = null;
        EntityWrapper<PersonBaseInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("cellphone", cellphone);
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        List<PersonBaseInfo> list = personBaseInfoMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            id = list.get(0).getId();
            List<String> strings = labelsService.getLabelsByUserId(id);
            if(!strings.isEmpty()){
                for(int i=0; i<strings.size(); i++){
                    label = label + strings.get(i) + ",";
                }
                label = label.substring(0,label.length()-1);
            }
        }

        return label;
    }


}

package com.mit.community.service;

import com.mit.community.entity.LabelsInfo;
import com.mit.community.mapper.LabelsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabelsService {
    @Autowired
    private LabelsMapper labelsMapper;

    public List<String> getLabelsByUserId(Integer userid) {
        List<String> labels = new ArrayList<>();
        LabelsInfo labelsInfo = labelsMapper.getLabelsInfoByUserId(userid);
        if (labelsInfo != null ) {
            if (StringUtils.isNotBlank(labelsInfo.getLsryId())) {
                labels.add("stayPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getJwryId())) {
                labels.add("engPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getDyId())) {
                labels.add("partyPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getJsId())) {
                labels.add("bearPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getById())) {
                labels.add("soliderPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getXmsfId())) {
                labels.add("xmsfPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getYscxId())) {
                labels.add("yscxPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getSfId())) {
                labels.add("sfPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getSqjz())) {
                labels.add("sqjzPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getZszhId())) {
                labels.add("zszhPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getXdId())) {
                labels.add("xdPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getAzbId())) {
                labels.add("azbPeople");
            }
            if (StringUtils.isNotBlank(labelsInfo.getZdqsnId())) {
                labels.add("zdqsnPeople");
            }

        }
        labels.add("zdqsnPeople");
        labels.add("azbPeople");
        return labels;
    }
}

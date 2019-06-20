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
        //LabelsInfo labelsInfo = labelsMapper.getLabelsInfoByUserId(userid);
        String lsryId = labelsMapper.getLsryId(userid);
        if (StringUtils.isNotBlank(lsryId)) {
            labels.add("stayPeople");
        }
        String jwryId = labelsMapper.getJwryId(userid);
        if (StringUtils.isNotBlank(jwryId)) {
            labels.add("engPeople");
        }
        String dyId = labelsMapper.getDyId(userid);
        if (StringUtils.isNotBlank(dyId)) {
            labels.add("partyPeople");
        }
        String jsId = labelsMapper.getJsId(userid);
        if (StringUtils.isNotBlank(jsId)) {
            labels.add("bearPeople");
        }
        String byId = labelsMapper.getById(userid);
        if (StringUtils.isNotBlank(byId)) {
            labels.add("soliderPeople");
        }
        String xmsfId = labelsMapper.getXmsfId(userid);
        if (StringUtils.isNotBlank(xmsfId)) {
            labels.add("xmsfPeople");
        }
        String yscxId = labelsMapper.getYscxId(userid);
        if (StringUtils.isNotBlank(yscxId)) {
            labels.add("yscxPeople");
        }
        String sfId = labelsMapper.getSfId(userid);
        if (StringUtils.isNotBlank(sfId)) {
            labels.add("sfPeople");
        }
        String sqjz = labelsMapper.getSqjz(userid);
        if (StringUtils.isNotBlank(sqjz)) {
            labels.add("sqjzPeople");
        }
        String zszhId = labelsMapper.getZszhId(userid);
        if (StringUtils.isNotBlank(zszhId)) {
            labels.add("zszhPeople");
        }
        String xdId = labelsMapper.getXdId(userid);
        if (StringUtils.isNotBlank(xdId)) {
            labels.add("xdPeople");
        }
        String azbId = labelsMapper.getAzbId(userid);
        if (StringUtils.isNotBlank(azbId)) {
            labels.add("azbPeople");
        }
        String zdqsnId = labelsMapper.getZdqsnId(userid);
        if (StringUtils.isNotBlank(zdqsnId)) {
            labels.add("zdqsnPeople");
        }
        String oldId = labelsMapper.getOldId(userid);
        if (StringUtils.isNotBlank(oldId)) {
            labels.add("oldPeople");
        }
        String zyzId = labelsMapper.getZyzId(userid);
        if (StringUtils.isNotBlank(zyzId)) {
            labels.add("zyzPeople");
        }
        String wgyId = labelsMapper.getWgyId(userid);
        if (StringUtils.isNotBlank(wgyId)) {
            labels.add("wgyPeople");
        }
        String ldzId = labelsMapper.getLdzId(userid);
        if (StringUtils.isNotBlank(ldzId)) {
            labels.add("ldzPeople");
        }

        return labels;
    }
}

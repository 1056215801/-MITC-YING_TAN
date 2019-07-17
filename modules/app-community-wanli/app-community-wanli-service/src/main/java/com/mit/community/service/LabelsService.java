package com.mit.community.service;

import com.mit.community.entity.LabelsInfo;
import com.mit.community.entity.ZdyPersonLabel;
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
    @Autowired
    private ZdyPersonLabelService zdyPersonLabelService;

    public List<String> getLabelsByUserId(Integer userid) {
        List<String> labels = new ArrayList<>();
        //LabelsInfo labelsInfo = labelsMapper.getLabelsInfoByUserId(userid);
        String lsryId = labelsMapper.getLsryId(userid);
        if (StringUtils.isNotBlank(lsryId)) {
            labels.add("留守人员");
        }
        String jwryId = labelsMapper.getJwryId(userid);
        if (StringUtils.isNotBlank(jwryId)) {
            labels.add("境外人员");
        }
        String dyId = labelsMapper.getDyId(userid);
        if (StringUtils.isNotBlank(dyId)) {
            labels.add("党员");
        }
        String jsId = labelsMapper.getJsId(userid);
        if (StringUtils.isNotBlank(jsId)) {
            labels.add("计生人员");
        }
        String byId = labelsMapper.getById(userid);
        if (StringUtils.isNotBlank(byId)) {
            labels.add("兵役人员");
        }
        String xmsfId = labelsMapper.getXmsfId(userid);
        if (StringUtils.isNotBlank(xmsfId)) {
            labels.add("刑满释放人员");
        }
        String yscxId = labelsMapper.getYscxId(userid);
        if (StringUtils.isNotBlank(yscxId)) {
            labels.add("疑似传销人员");
        }
        String sfId = labelsMapper.getSfId(userid);
        if (StringUtils.isNotBlank(sfId)) {
            labels.add("上访人员");
        }
        String sqjz = labelsMapper.getSqjz(userid);
        if (StringUtils.isNotBlank(sqjz)) {
            labels.add("社区矫正人员");
        }
        String zszhId = labelsMapper.getZszhId(userid);
        if (StringUtils.isNotBlank(zszhId)) {
            labels.add("肇事肇祸等严重精神障碍患者");
        }
        String xdId = labelsMapper.getXdId(userid);
        if (StringUtils.isNotBlank(xdId)) {
            labels.add("吸毒人员");
        }
        String azbId = labelsMapper.getAzbId(userid);
        if (StringUtils.isNotBlank(azbId)) {
            labels.add("艾滋病危险人员");
        }
        String zdqsnId = labelsMapper.getZdqsnId(userid);
        if (StringUtils.isNotBlank(zdqsnId)) {
            labels.add("重点青少年");
        }
        String oldId = labelsMapper.getOldId(userid);
        if (StringUtils.isNotBlank(oldId)) {
            labels.add("六十岁以上老人");
        }
        String zyzId = labelsMapper.getZyzId(userid);
        if (StringUtils.isNotBlank(zyzId)) {
            labels.add("志愿者");
        }
        String wgyId = labelsMapper.getWgyId(userid);
        if (StringUtils.isNotBlank(wgyId)) {
            labels.add("网格员");
        }
        String ldzId = labelsMapper.getLdzId(userid);
        if (StringUtils.isNotBlank(ldzId)) {
            labels.add("楼栋长");
        }
        List<ZdyPersonLabel> list = zdyPersonLabelService.getListByPersonBaseInfoId(userid);
        if (!list.isEmpty()) {
            for (int i=0; i<list.size(); i++) {
                labels.add(list.get(i).getLabel());
            }
        }

        return labels;
    }
}

package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.entity.DeviceGroup;
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionGroupService {
    @Autowired
    private PersonLabelsMapper personLabelsMapper;

    public Page<DeviceGroup> getPage(Integer pageNum, Integer pageSize, String deviceNum, Integer deviceType, Integer deviceStatus, String communityCode){
        Page<DeviceGroup> page = new Page<>(pageNum, pageSize);
        EntityWrapper<DeviceGroup> wrapper = new EntityWrapper<>();
        String sql = null;
        wrapper.eq("a.community_code",communityCode);
        if (StringUtils.isNotBlank(deviceNum)) {
            wrapper.eq("c.device_num",deviceNum);
        }
        if (deviceType != null) {
            wrapper.eq("c.device_type",deviceType);
        }
        if (deviceStatus != null) {
            if (deviceStatus == 1){//离线
                sql = "and (select TIME_TO_SEC(TIMEDIFF(NOW(), d.gmt_create)) FROM dnake_device_info d) > 10 ORDER BY a.gmt_create DESC ";
            } else {
                sql = "and (select TIME_TO_SEC(TIMEDIFF(NOW(), d.gmt_create)) FROM dnake_device_info d) < 10 ORDER BY a.gmt_create DESC ";
            }
        }
        if (deviceStatus == null) {
            wrapper.orderBy("a.gmt_create",false);
        }

        List<DeviceGroup> list = personLabelsMapper.selectInfoPage(page, wrapper, sql);
        page.setRecords(list);
        return page;
    }
}

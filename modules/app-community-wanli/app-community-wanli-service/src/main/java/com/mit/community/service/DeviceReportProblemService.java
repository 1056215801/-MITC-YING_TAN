package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.DeviceReportProblem;
import com.mit.community.mapper.DeviceReportProblemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceReportProblemService extends ServiceImpl<DeviceReportProblemMapper, DeviceReportProblem> {
    @Autowired
    private DeviceReportProblemMapper deviceReportProblemMapper;

    public void saveList(List<DeviceReportProblem> list){
        this.insertBatch(list);
    }

    public Page<DeviceReportProblem> selectPage(String communityCode, Integer deviceType, Integer pageNum, Integer pageSize){
        Page<DeviceReportProblem> page = new Page<>(pageNum, pageSize);
        EntityWrapper<DeviceReportProblem> wrapper = new EntityWrapper<>();
        if (deviceType != null) {
            wrapper.eq("device_type",  deviceType);
        }
        List<DeviceReportProblem> list = deviceReportProblemMapper.selectPage(page, wrapper);
        page.setRecords(list);
        return page;
    }
}

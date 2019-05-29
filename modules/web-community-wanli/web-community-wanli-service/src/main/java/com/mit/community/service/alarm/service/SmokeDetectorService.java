package com.mit.community.service.alarm.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.mapper.alarm.dao.SmokeDetectorMapper;
import com.mit.community.model.SmokeDetector;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 烟感报警
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@Service
public class SmokeDetectorService {

    @Autowired
    private SmokeDetectorMapper smokeDetectorMapper;
    /**
     * 分页查询
     * @param deviceNum
     * @param warnType
     * @param warnStatus
     * @param gmtWarnStart
     * @param gmtWarnEnd
     * @param pageNum
     * @param pageSize
     * @return com.baomidou.mybatisplus.plugins.Page<com.mit.community.rest.modular.makedata.model.WellLid>
     * @throws
     * @author shuyy
     * @date 2019-01-04 14:50
     * @company mitesofor
    */
    public Page<SmokeDetector> listPage(String communityCode, String deviceNum, String devicePlace, Short warnType,
                                        Short warnStatus, LocalDate gmtWarnStart,
                                        LocalDate gmtWarnEnd, Integer pageNum,
                                        Integer pageSize) {

        EntityWrapper<SmokeDetector> wrapper = new EntityWrapper<>();
        if(StringUtils.isNotBlank(communityCode)){
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(deviceNum)) {
            wrapper.eq("device_num", deviceNum);
        }
        if (StringUtils.isNotBlank(devicePlace)) {
            wrapper.eq("device_place", devicePlace);
        }
        if (null != warnType) {
            wrapper.eq("warn_type", warnType);
        }
        if (null != warnStatus) {
            wrapper.eq("warn_status", warnStatus);
        }
        if (null != gmtWarnStart) {
            wrapper.ge("gmt_warn", gmtWarnStart);
        }
        if (null != gmtWarnEnd) {
            wrapper.le("gmt_warn", gmtWarnEnd);
        }
        wrapper.orderBy("gmt_warn", false);
        Page<SmokeDetector> page = new Page<>(pageNum, pageSize);
        List<SmokeDetector> smokeDetectors = smokeDetectorMapper.selectPage(page, wrapper);
        page.setRecords(smokeDetectors);
        return page;
    }
}

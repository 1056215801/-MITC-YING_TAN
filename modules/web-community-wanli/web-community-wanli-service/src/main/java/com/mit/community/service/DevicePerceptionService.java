package com.mit.community.service;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.entity.Device;
import com.mit.community.entity.DeviceInfo;
import com.mit.community.entity.UrgentButton;
import com.mit.community.entity.WellShift;
import com.mit.community.mapper.mapper.BaoJinMapper;
import com.mit.community.mapper.DevicePerceptionMapper;
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import com.mit.community.model.WarnInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DevicePerceptionService {
    @Autowired
    private DevicePerceptionMapper devicePerceptionMapper;
    @Autowired
    private BaoJinMapper baoJinMapper;
    @Autowired
    private PersonLabelsMapper personLabelsMapper;

    public Page<WellShift> getWellShiftPage(String communityCode, String deviceNum, Integer swStatus, Integer deviceStatus,
                                            LocalDateTime timeimeStart, LocalDateTime timeimeEnd,Integer pageNum, Integer pageSize){
        Page<WellShift> page = new Page<>(pageNum, pageSize);
        EntityWrapper<WellShift> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        if (StringUtils.isNotBlank(deviceNum)){
            wrapper.eq("a.device_num", deviceNum);
        }
        if (swStatus != null){
            wrapper.eq("a.status", swStatus);
        }
        if (deviceStatus != null){
            wrapper.eq("a.device_status", deviceStatus);
        }
        if (timeimeStart != null){
            wrapper.ge("a.gmt_upload",timeimeStart);
        }
        if (timeimeEnd != null){
            wrapper.le("a.gmt_upload",timeimeEnd);
        }
        wrapper.orderBy("a.gmt_create", false);
        List<WellShift> list = devicePerceptionMapper.getWellShiftPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public Page<WellShift> smokeListPage(String communityCode, String deviceNum, Integer swStatus, Integer deviceStatus,
                                            LocalDateTime timeimeStart, LocalDateTime timeimeEnd,Integer pageNum, Integer pageSize){
        Page<WellShift> page = new Page<>(pageNum, pageSize);
        EntityWrapper<WellShift> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        if (StringUtils.isNotBlank(deviceNum)){
            wrapper.eq("a.device_num", deviceNum);
        }
        if (swStatus != null){
            wrapper.eq("a.status", swStatus);
        }
        if (deviceStatus != null){
            wrapper.eq("a.device_status", deviceStatus);
        }
        if (timeimeStart != null){
            wrapper.ge("a.gmt_upload",timeimeStart);
        }
        if (timeimeEnd != null){
            wrapper.le("a.gmt_upload",timeimeEnd);
        }
        wrapper.orderBy("a.gmt_create", false);
        List<WellShift> list = devicePerceptionMapper.smokeListPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public Page<WellShift> dcListPage(String communityCode, String deviceNum, Integer swStatus, Integer deviceStatus,
                                         LocalDateTime timeimeStart, LocalDateTime timeimeEnd,Integer pageNum, Integer pageSize){
        Page<WellShift> page = new Page<>(pageNum, pageSize);
        EntityWrapper<WellShift> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        if (StringUtils.isNotBlank(deviceNum)){
            wrapper.eq("a.device_num", deviceNum);
        }
        if (swStatus != null){
            wrapper.eq("a.status", swStatus);
        }
        if (deviceStatus != null){
            wrapper.eq("a.device_status", deviceStatus);
        }
        if (timeimeStart != null){
            wrapper.ge("a.gmt_upload",timeimeStart);
        }
        if (timeimeEnd != null){
            wrapper.le("a.gmt_upload",timeimeEnd);
        }
        wrapper.orderBy("a.gmt_create", false);
        List<WellShift> list = devicePerceptionMapper.dcListPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public Page<UrgentButton> urgentButtonListPage(String communityCode, String name, String phone, Integer deviceStatus,
                                         LocalDateTime timeimeStart, LocalDateTime timeimeEnd, Integer pageNum, Integer pageSize){
        Page<UrgentButton> page = new Page<>(pageNum, pageSize);
        EntityWrapper<UrgentButton> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        if (StringUtils.isNotBlank(name)){
            wrapper.eq("b.name", name);//是a还是b待定
        }
        if (StringUtils.isNotBlank(phone)){
            wrapper.eq("b.cellphone", phone);
        }
        if (deviceStatus != null){
            wrapper.eq("a.device_status", deviceStatus);
        }
        if (timeimeStart != null){
            wrapper.ge("a.gmt_upload",timeimeStart);
        }
        if (timeimeEnd != null){
            wrapper.le("a.gmt_upload",timeimeEnd);
        }
        wrapper.orderBy("a.gmt_create", false);
        List<UrgentButton> list = devicePerceptionMapper.urgentButtonListPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public Page<WarnInfo> wellListPage(String communityCode, String problem , Integer pageNum, Integer pageSize){
        Page<WarnInfo> page = new Page<>(pageNum, pageSize);
        EntityWrapper<WarnInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("a.communityCode", communityCode);
        if (StringUtils.isNotBlank(problem)){
            wrapper.eq("a.problem", problem);//是a还是b待定
        }
        wrapper.orderBy("a.gmt_create", false);
        List<WarnInfo> list = devicePerceptionMapper.selectPage(page, wrapper);
        page.setRecords(list);
        return page;
    }

    public Page<DeviceInfo> menJinListPage(String communityCode, Integer zoneId, Integer buildingId, Integer unitId, String deviceName, String deicveNum, String deviceType, Integer deviceStatus , Integer pageNum, Integer pageSize){
        Page<DeviceInfo> page = new Page<>(pageNum, pageSize);
        EntityWrapper<DeviceInfo> wrapper = new EntityWrapper<>();
        String sql = null;
        wrapper.eq("a.community_code", communityCode);
        if (zoneId != null) {
            wrapper.eq("a.zone_id", zoneId);
        }
        if (buildingId != null) {
            wrapper.eq("a.building_id", buildingId);
        }
        if (unitId != null) {
            wrapper.eq("a.unit_id", unitId);
        }
        if (StringUtils.isNotBlank(deviceName)) {
            wrapper.eq("a.device_name", deviceName);
        }
        if (StringUtils.isNotBlank(deicveNum)) {
            wrapper.eq("a.device_num", deicveNum);
        }
        if (StringUtils.isNotBlank(deviceType)) {
            wrapper.eq("a.device_type", deviceType);
        }
        if (deviceStatus != null) {
            if (deviceStatus == 2){//离线
                sql = "and (select TIME_TO_SEC(TIMEDIFF(NOW(), b.gmt_modified)) FROM dnake_device_info b) > 10 ORDER BY a.gmt_create DESC ";
            } else {
                sql = "and (select TIME_TO_SEC(TIMEDIFF(NOW(), b.gmt_modified)) FROM dnake_device_info b) < 10 ORDER BY a.gmt_create DESC ";
            }
        }
        List<DeviceInfo> list = personLabelsMapper.selectMenJinPage(page, wrapper, sql);
        page.setRecords(list);
        return page;
    }
}

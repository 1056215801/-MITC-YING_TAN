package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.DeviceInfo;
import com.mit.community.mapper.DeviceInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceService {
    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    /**
     * 保存下发的token
     * @param deviceId 设备id
     * @param token
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveToken(String deviceId,String token) {
        EntityWrapper<DeviceInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("device_id", deviceId);
        List<DeviceInfo> deviceInfos = deviceInfoMapper.selectList(wrapper);
        if(deviceInfos.isEmpty()) {
            DeviceInfo deviceInfo = new DeviceInfo(null, deviceId, 0, token, null);
            deviceInfo.setGmtCreate(LocalDateTime.now());
            deviceInfo.setGmtModified(LocalDateTime.now());
            deviceInfoMapper.insert(deviceInfo);
        } else {
            deviceInfoMapper.updateToken(deviceId, token);
        }
    }
}

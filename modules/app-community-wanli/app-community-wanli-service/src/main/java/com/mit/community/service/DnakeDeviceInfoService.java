package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.DnakeDeviceInfo;
import com.mit.community.mapper.DnakeDeviceInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DnakeDeviceInfoService {
    @Autowired
    private DnakeDeviceInfoMapper dnakeDeviceInfoMapper;

    public DnakeDeviceInfo getDeviceInfoByMac(String mac){
        EntityWrapper<DnakeDeviceInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("mac", mac);
        List<DnakeDeviceInfo> list = dnakeDeviceInfoMapper.selectList(wrapper);
        if(!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void save(String mac, String ip){
        DnakeDeviceInfo dnakeDeviceInfo = new DnakeDeviceInfo(mac,ip);
        dnakeDeviceInfo.setGmtCreate(LocalDateTime.now());
        dnakeDeviceInfo.setGmtModified(LocalDateTime.now());
        dnakeDeviceInfoMapper.insert(dnakeDeviceInfo);
    }

    public void update(String mac, String ip){
        EntityWrapper<DnakeDeviceInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("mac", mac);
        DnakeDeviceInfo dnakeDeviceInfo = new DnakeDeviceInfo();
        dnakeDeviceInfo.setGmtModified(LocalDateTime.now());
        dnakeDeviceInfo.setIp(ip);
        dnakeDeviceInfoMapper.update(dnakeDeviceInfo, wrapper);
    }

    public List<DnakeDeviceInfo> getAllRecord() {
        EntityWrapper<DnakeDeviceInfo> wrapper = new EntityWrapper<>();
        List<DnakeDeviceInfo> list = dnakeDeviceInfoMapper.selectList(wrapper);
        return list;
    }

    public DnakeDeviceInfo getById(String deviceId){
        EntityWrapper<DnakeDeviceInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", deviceId);
        List<DnakeDeviceInfo> list = dnakeDeviceInfoMapper.selectList(wrapper);
        if(!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}

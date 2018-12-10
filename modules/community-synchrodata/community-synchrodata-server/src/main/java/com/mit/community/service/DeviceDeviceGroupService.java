package com.mit.community.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.DeviceDeviceGroup;
import com.mit.community.mapper.DeviceDeviceGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备和设备组关联
 *
 * @author shuyy
 * @date 2018/12/10
 * @company mitesofor
 */
@Service
public class DeviceDeviceGroupService extends ServiceImpl<DeviceDeviceGroupMapper, DeviceDeviceGroup> {

    private final DeviceDeviceGroupMapper deviceDeviceGroupMapper;

    @Autowired
    public DeviceDeviceGroupService(DeviceDeviceGroupMapper deviceDeviceGroupMapper) {
        this.deviceDeviceGroupMapper = deviceDeviceGroupMapper;
    }

    /**
     * 删除
     * @author shuyy
     * @date 2018/12/10 11:58
     * @company mitesofor
    */
    public void remove(){
        deviceDeviceGroupMapper.delete(null);
    }
}

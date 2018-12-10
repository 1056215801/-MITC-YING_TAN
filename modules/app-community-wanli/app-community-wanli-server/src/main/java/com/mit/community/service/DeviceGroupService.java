package com.mit.community.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.DeviceGroup;
import com.mit.community.mapper.DeviceGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备组
 *
 * @author shuyy
 * @date 2018/12/10
 * @company mitesofor
 */
@Slf4j
@Service
public class DeviceGroupService extends ServiceImpl<DeviceGroupMapper, DeviceGroup> {

    private final DeviceGroupMapper deviceGroupMapper;

    @Autowired
    public DeviceGroupService(DeviceGroupMapper deviceGroupMapper) {
        this.deviceGroupMapper = deviceGroupMapper;
    }

    /**
     * 删除
     *
     * @author shuyy
     * @date 2018/12/10 11:21
     * @company mitesofor
     */
    public void remove() {
        deviceGroupMapper.delete(null);
    }


}

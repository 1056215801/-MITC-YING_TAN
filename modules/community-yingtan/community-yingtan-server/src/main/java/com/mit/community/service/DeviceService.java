package com.mit.community.service;

import com.mit.community.entity.Device;
import com.mit.community.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备业务层
 *
 * @author Mr.Deng
 * @date 2018/11/15 10:08
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class DeviceService {
    private final DeviceMapper deviceMapper;

    @Autowired
    public DeviceService(DeviceMapper deviceMapper) {
        this.deviceMapper = deviceMapper;
    }

    /**
     * 添加设备信息
     *
     * @param device 设备信息
     * @author Mr.Deng
     * @date 10:09 2018/11/15
     */
    public void save(Device device) {
        deviceMapper.insert(device);
    }

    /**
     * 获取所有设备信息
     *
     * @return 设备信息列表
     * @author Mr.Deng
     * @date 10:10 2018/11/15
     */
    public List<Device> list() {
        return deviceMapper.selectList(null);
    }
}

package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.DeviceDeviceGroup;
import com.mit.community.mapper.DeviceDeviceGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备和设备组关联
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
    public void remove() {
        deviceDeviceGroupMapper.delete(null);
    }

    /**
     * 查询设备与设备组关联表，通告设备组id
     * @param deviceGroupIds 设备组id
     * @return 设备与设备组关联信息
     * @author Mr.Deng
     * @date 16:37 2018/12/13
     */
    public List<DeviceDeviceGroup> listByDeviceGroupIds(List<Integer> deviceGroupIds) {
        EntityWrapper<DeviceDeviceGroup> wrapper = new EntityWrapper<>();
        wrapper.in("device_group_id", deviceGroupIds);
        return deviceDeviceGroupMapper.selectList(wrapper);
    }

    public List<DeviceDeviceGroup> getGroupsByDeviceGroupId(Integer deviceGroupId) {
        EntityWrapper<DeviceDeviceGroup> wrapper = new EntityWrapper<>();
        wrapper.eq("device_group_id", deviceGroupId);
        return deviceDeviceGroupMapper.selectList(wrapper);
    }


}

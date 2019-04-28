package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.DeviceGroup;
import com.mit.community.mapper.DeviceGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备组
 * @author shuyy
 * @date 2018/12/10
 * @company mitesofor
 */
@Slf4j
@Service
public class DeviceGroupService extends ServiceImpl<DeviceGroupMapper, DeviceGroup> {

    @Autowired
    private DeviceGroupMapper deviceGroupMapper;

    /**
     * 查询所有的设备组信息，通过小区code
     * @param communityCode 小区code
     * @return 设备组列表
     * @author Mr.Deng
     * @date 19:34 2018/12/10
     */
    public List<DeviceGroup> getByCommunityCode(String communityCode) {
        EntityWrapper<DeviceGroup> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return deviceGroupMapper.selectList(wrapper);
    }

    /**
     * 查询所有的设备组信息，通过小区code
     * @param communityCode 小区code
     * @return 设备组列表
     * @author Mr.Deng
     * @date 19:34 2018/12/10
     */
    public DeviceGroup getById(Integer deviceGroupId) {
        EntityWrapper<DeviceGroup> wrapper = new EntityWrapper<>();
        wrapper.eq("device_group_Id", deviceGroupId);
        List<DeviceGroup> list = deviceGroupMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 删除
     * @author shuyy
     * @date 2018/12/10 11:21
     * @company mitesofor
     */
    public void remove() {
        deviceGroupMapper.delete(null);
    }

}

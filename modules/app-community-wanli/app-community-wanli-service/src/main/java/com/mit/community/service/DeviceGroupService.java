package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.DeviceDeviceGroup;
import com.mit.community.entity.entity.DeviceGroup;
import com.mit.community.mapper.DeviceGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Autowired
    private DeviceDeviceGroupService deviceDeviceGroupService;

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


    @Transactional
    public void deleteAuthGroup(Integer deviceGroupId) {
        EntityWrapper<DeviceGroup> wrapper = new EntityWrapper<>();
        wrapper.eq("device_group_Id", deviceGroupId);
        deviceGroupMapper.delete(wrapper);


        EntityWrapper<DeviceDeviceGroup> wrapperDeviceDeviceGroup = new EntityWrapper<>();
        wrapperDeviceDeviceGroup.eq("device_group_id",deviceGroupId);
        deviceDeviceGroupService.delete(wrapperDeviceDeviceGroup);
    }

    @Transactional
    public void updateAuthGroup(Integer deviceGroupId, String deviceGroupName, int groupType, String deviceNum){
        DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setDeviceGroupName(deviceGroupName);
        deviceGroup.setGroupType((short) groupType);
        deviceGroup.setGmtModified(LocalDateTime.now());
        EntityWrapper<DeviceGroup> wrapperDeviceGroup = new EntityWrapper<>();
        wrapperDeviceGroup.eq("device_group_Id", deviceGroupId);
        deviceGroupMapper.update(deviceGroup, wrapperDeviceGroup);

        if(StringUtils.isNotBlank(deviceNum)) {
            EntityWrapper<DeviceDeviceGroup> wrapper = new EntityWrapper<>();
            wrapper.eq("device_group_id",deviceGroupId);
            deviceDeviceGroupService.delete(wrapper);

            String[] deviceNums = deviceNum.split(",");
            DeviceDeviceGroup deviceDeviceGroup = null;
            for (int i=0 ; i<deviceNums.length; i++){
                deviceDeviceGroup = new DeviceDeviceGroup();
                deviceDeviceGroup.setDeviceGroupId(deviceGroupId);
                deviceDeviceGroup.setDeviceNum(deviceNums[i]);
                deviceDeviceGroup.setGmtCreate(LocalDateTime.now());
                deviceDeviceGroup.setGmtModified(LocalDateTime.now());
                deviceDeviceGroupService.insert(deviceDeviceGroup);
            }
        }
    }

    @Transactional
    public void saveAuthGroup(String communityCode, String deviceGroupName, int groupType, String deviceNum){
        int a = (int) ((Math.random()) * 1000000000);
        DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setCommunityCode(communityCode);
        deviceGroup.setDeviceGroupName(deviceGroupName);
        deviceGroup.setDeviceGroupId(a);
        deviceGroup.setGroupType((short) groupType);
        deviceGroup.setGmtModified(LocalDateTime.now());
        deviceGroup.setGmtCreate(LocalDateTime.now());
        deviceGroupMapper.insert(deviceGroup);

        if(StringUtils.isNotBlank(deviceNum)) {
            String[] deviceNums = deviceNum.split(",");
            DeviceDeviceGroup deviceDeviceGroup = null;
            for (int i=0 ; i<deviceNums.length; i++){
                deviceDeviceGroup = new DeviceDeviceGroup();
                deviceDeviceGroup.setDeviceGroupId(a);
                deviceDeviceGroup.setDeviceNum(deviceNums[i]);
                deviceDeviceGroup.setGmtCreate(LocalDateTime.now());
                deviceDeviceGroup.setGmtModified(LocalDateTime.now());
                deviceDeviceGroupService.insert(deviceDeviceGroup);
            }
        }
    }

}

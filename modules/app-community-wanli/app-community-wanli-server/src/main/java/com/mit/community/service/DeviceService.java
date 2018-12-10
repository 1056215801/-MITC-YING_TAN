package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mit.community.entity.Device;
import com.mit.community.mapper.DeviceMapper;
import com.mit.community.module.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@Service
public class DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserHouseholdService userHouseholdService;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private AuthorizeAppHouseholdDeviceGroupService authorizeAppHouseholdDeviceService;

    /**
     * 查询住户app关联的设备， 通过住户id列表
     * @param householdIdList 住户id列表
     * @return java.util.List<com.mit.community.entity.Device>
     * @author shuyy
     * @date 2018/11/30 11:07
     * @company mitesofor
    *//*
    public List<Device> listByHouseholdIdList(List<Integer> householdIdList){
        List<AuthorizeAppHouseholdDeviceGroup> authorizeAppHouseholdDevices = authorizeAppHouseholdDeviceService.listByHouseholdIdList(householdIdList);
        if(authorizeAppHouseholdDevices.isEmpty()){
            return null;
        }
        List<Integer> deviceIds = authorizeAppHouseholdDevices.parallelStream().map(AuthorizeAppHouseholdDevice::getDevice_id).collect(Collectors.toList());
        return this.listByDeviceIdList(deviceIds);
    }*/

    /**
     * 查询所有的设备，通过设备id列表
     * @param deviceIdList 设备id列表
     * @return java.util.List<com.mit.community.entity.Device>
     * @author shuyy
     * @date 2018/11/30 11:06
     * @company mitesofor
    */
    public List<Device> listByDeviceIdList(List<Integer> deviceIdList){
        EntityWrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.in("device_id", deviceIdList);
        return deviceMapper.selectList(wrapper);
    }

    /**
     * 查询关联的设备，通过小区code，手机号
     * @param CommunityCode 小区code
     * @param cellphone 手机号
     * @return java.util.List<com.mit.community.entity.Device>
     * @author shuyy
     * @date 2018/11/30 11:45
     * @company mitesofor
    *//*
    public List<Device> listDeviceByCommunityCodeAndCellphone(String CommunityCode, String cellphone){
        User user = userService.getByCellphone(cellphone);
        List<UserHousehold> userHouseholds = userHouseholdService.listByUserId(user.getId());
        List<Integer> householdIds = userHouseholds.parallelStream().map(UserHousehold::getHouseholdId).collect(Collectors.toList());
        List<HouseHold> houseHolds = houseHoldService.listByHouseholdIdList(householdIds);
        List<HouseHold> result = houseHolds.parallelStream().filter(item -> item.getCommunityCode().equals(CommunityCode)).collect(Collectors.toList());
        return this.listByHouseholdIdList(result.parallelStream().map(HouseHold::getHouseholdId).collect(Collectors.toList()));

    }*/
}

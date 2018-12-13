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
    @Autowired
    private DeviceDeviceGroupService deviceDeviceGroupService;

    /**
     * 查询住户app关联的设备编号， 通过住户id列表
     * @param householdIdList 住户id列表
     * @return java.util.List<com.mit.community.entity.Device>
     * @author shuyy
     * @date 2018/11/30 11:07
     * @company mitesofor
     */
//    public List<Device> listByHouseholdIdList(List<Integer> householdIdList) {
//        List<AuthorizeAppHouseholdDeviceGroup> authorizeAppHouseholdDeviceGroups = authorizeAppHouseholdDeviceService.listByHouseholdIdList(householdIdList);
//        if (authorizeAppHouseholdDeviceGroups.isEmpty()) {
//            return null;
//        }
//        List<Integer> deviceGroups = authorizeAppHouseholdDeviceGroups.parallelStream().map(AuthorizeAppHouseholdDeviceGroup::getDeviceGroupId).collect(Collectors.toList());
//        List<DeviceDeviceGroup> deviceDeviceGroups = deviceDeviceGroupService.listByDeviceGroupIds(deviceGroups);
//        if (deviceDeviceGroups.isEmpty()) {
//            return null;
//        }
//        List<String> deviceNums = deviceDeviceGroups.parallelStream().map(DeviceDeviceGroup::getDeviceNum).collect(Collectors.toList());
//        return this.listByDeviceNumList(deviceNums);
//    }

    /**
     * 查询所有的设备，通过设备id列表
     * @param deviceIdList 设备id列表
     * @return java.util.List<com.mit.community.entity.Device>
     * @author shuyy
     * @date 2018/11/30 11:06
     * @company mitesofor
     */
    public List<Device> listByDeviceIdList(List<Integer> deviceIdList) {
        EntityWrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.in("device_id", deviceIdList);
        return deviceMapper.selectList(wrapper);
    }

//    /**
//     * 查询所有的设备，通过设备编号列表
//     * @param deviceNum 设备编号
//     * @return 设备
//     * @author Mr.Deng
//     * @date 16:48 2018/12/13
//     */
//    public Device listByDeviceNum(String deviceNum) {
//        EntityWrapper<Device> wrapper = new EntityWrapper<>();
//        wrapper.eq("device_num", deviceNum);
//        List<Device> devices = deviceMapper.selectList(wrapper);
//        if (devices.isEmpty()) {
//            return null;
//        }
//        return devices.get(0);
//    }

    /**
     * 查询设备信息，通过小区code和设备编号
     * @param communityCode 小区code
     * @param deviceNum     设备编码
     * @return 设备信息
     * @author Mr.Deng
     * @date 14:54 2018/12/12
     */
    public Device getByDeviceNumAndCommunityCode(String communityCode, String deviceNum) {
        EntityWrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("device_num", deviceNum);
        List<Device> devices = deviceMapper.selectList(wrapper);
        if (devices.isEmpty()) {
            return null;
        }
        return devices.get(0);
    }

    /**
     * 查询关联的设备编号，通过小区code，手机号
     * @param communityCode 小区code
     * @param cellphone     手机号
     * @param type          M：单元机；W：门口机；
     * @return java.util.List<com.mit.community.entity.Device>
     * @author shuyy
     * @date 2018/11/30 11:45
     * @company mitesofor
     */
//    public List<String> listDeviceNumByCommunityCodeAndCellphone(String communityCode, String cellphone, String type) {
//        User user = userService.getByCellphone(cellphone);
//        List<UserHousehold> userHouseholds = userHouseholdService.listByUserId(user.getId());
//        List<Integer> householdIds = userHouseholds.parallelStream().map(UserHousehold::getHouseholdId).collect(Collectors.toList());
//        List<HouseHold> houseHolds = houseHoldService.listByHouseholdIdList(householdIds);
//        List<HouseHold> result = houseHolds.parallelStream().filter(item -> item.getCommunityCode().equals(communityCode)).collect(Collectors.toList());
//        List<Device> devices = this.listByHouseholdIdList(result.parallelStream().map(HouseHold::getHouseholdId).collect(Collectors.toList()));
//        List<Device> resultDevice = devices.parallelStream().filter(item -> item.getDeviceType().equals(type)).collect(Collectors.toList());
//        return resultDevice.parallelStream().map(Device::getDeviceNum).collect(Collectors.toList());
//    }
}

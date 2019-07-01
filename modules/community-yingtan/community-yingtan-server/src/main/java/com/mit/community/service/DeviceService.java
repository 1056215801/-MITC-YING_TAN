package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dnake.common.DnakeWebApiUtil;
import com.google.common.collect.Maps;
import com.mit.community.entity.Device;
import com.mit.community.module.pass.mapper.DeviceMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 设备业务层
 *
 * @author Mr.Deng
 * @date 2018/11/15 10:08
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class DeviceService extends ServiceImpl<DeviceMapper, Device> {
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

    /***
     * 查询设备，通过小区编码
     * @param communityCode 小区编码
     * @return java.util.List<com.mit.community.entity.Device>
     * @author shuyy
     * @date 2018/11/22 11:10
     * @company mitesofor
     */
    public List<Device> listByCommunityCode(String communityCode) {
        EntityWrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return deviceMapper.selectList(wrapper);
    }

    /***
     *  查询设备列表，通过小区code列表
     * @param communityCodeList 小区code列表
     * @return java.util.List<com.mit.community.entity.Device>
     * @author shuyy
     * @date 2018/11/22 15:11
     * @company mitesofor
     */
    public List<Device> listByCommunityCodeList(List<String> communityCodeList) {
        EntityWrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodeList);
        return deviceMapper.selectList(wrapper);
    }

    /***
     * 查询进口或出口设备，通过小区编码
     * @param communityCode 小区编码
     * @param flag 进、出
     * @return java.util.List<com.mit.community.entity.Device>
     * @author shuyy
     * @date 2018/11/22 11:42
     * @company mitesofor
     */
    public List<Device> listInOrOutByCommunityCode(String communityCode, String flag) {
        List<Device> devices = this.listByCommunityCode(communityCode);
        if (!devices.isEmpty()) {
            return devices.parallelStream().filter(item -> item.getDeviceName().contains(flag)).collect(Collectors.toList());
        }
        return null;
    }

    /***
     * 查询所有设备
     * @param communityCode 小区code
     * @return java.util.List<com.mit.community.entity.Device>
     * @author shuyy
     * @date 2018/11/22 10:10
     * @company mitesofor
     */
    public List<Device> listFromDnakeByCommunityCode(String communityCode) {
        String url = "/v1/device/getDeviceList";
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(1);
        map.put("communityCode", communityCode);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonObject = JSONObject.fromObject(invoke);
        JSONArray jsonArray = jsonObject.getJSONArray("deviceList");
        List<Device> devices = JSON.parseArray(jsonArray.toString(), Device.class);
        devices.forEach(device -> {
            device.setCommunityCode(communityCode);
            device.setGmtCreate(LocalDateTime.now());
            device.setGmtModified(LocalDateTime.now());
            if (device.getBuildingCode() == null) {
                device.setBuildingCode(StringUtils.EMPTY);
            }
            if (device.getBuildingId() == null) {
                device.setBuildingId(StringUtils.EMPTY);
            }
            if (device.getUnitCode() == null) {
                device.setUnitCode(StringUtils.EMPTY);
            }
            if (device.getUnitId() == null) {
                device.setUnitId(StringUtils.EMPTY);
            }
        });
        return devices;
    }

    /***
     * 删除所有设备
     * @author shuyy
     * @date 2018/11/22 10:15
     * @company mitesofor
     */
    public void remove() {
        deviceMapper.delete(null);
    }

    /**
     * @param deviceName
     * @return java.util.List<com.mit.community.entity.Device>
     * @throws
     * @author shuyy
     * @date 2019-01-15 11:18
     * @company mitesofor
     */
    public Device getByDevice(String deviceName) {
        EntityWrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.eq("device_name", deviceName);
        List<Device> devices = deviceMapper.selectList(wrapper);
        if (devices.isEmpty()) {
            return null;
        }
        return devices.get(0);
    }
}

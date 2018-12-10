package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dnake.common.DnakeWebApiUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.common.ThreadPoolUtil;
import com.mit.community.entity.DeviceDeviceGroup;
import com.mit.community.entity.DeviceGroup;
import com.mit.community.mapper.DeviceGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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

    public List<DeviceGroup> listFromDnakeByCommunityCodeList(List<String> communityCodeList) {
        List<DeviceGroup> result = Lists.newCopyOnWriteArrayList();
        CountDownLatch countDownLatch = new CountDownLatch(communityCodeList.size());
        communityCodeList.forEach(item -> ThreadPoolUtil.execute(() -> {
            try {
                // 一个小区，最多的设备组不会超过20，所以这里不管小区设备组有多少，都发送20个分页请求去查询。
                CountDownLatch pageCountDownLatch = new CountDownLatch(20);
                for (int index = 0; index < 20; index++) {
                    int tmp = index;
                    ThreadPoolUtil.execute(() -> {
                        try {
                            int pageSize = 100;
                            List<DeviceGroup> deviceGroups = listFromDnakeByCommunityCodePage(item, pageSize, tmp + 1);
                            result.addAll(deviceGroups);
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        } finally {
                            pageCountDownLatch.countDown();
                        }
                    });
                }
                pageCountDownLatch.await();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                countDownLatch.countDown();
            }
        }));
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 查询设备组
     *
     * @author shuyy
     * @date 2018/12/10 8:46
     * @company mitesofor
     */
    public List<DeviceGroup> listFromDnakeByCommunityCodePage(String communityCode, Integer pageSize, Integer pageNum) {
        String url = "/v1/deviceGroup/getDeviceGroupList";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("communityCode", communityCode);
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonResult = JSON.parseObject(invoke);
        JSONArray deviceGroupList = jsonResult.getJSONArray("deviceGroupList");
        List<DeviceGroup> result = Lists.newArrayListWithCapacity(100);
        deviceGroupList.forEach(item -> {
            JSONObject j = (JSONObject) item;
            DeviceGroup deviceGroup = j.toJavaObject(DeviceGroup.class);
            deviceGroup.setGmtCreate(LocalDateTime.now());
            deviceGroup.setGmtModified(LocalDateTime.now());
            deviceGroup.setCommunityCode(communityCode);
            result.add(deviceGroup);
            JSONArray deviceList = ((JSONObject) item).getJSONArray("deviceList");
            deviceList.forEach(device -> {
                JSONObject json = (JSONObject) device;
                DeviceDeviceGroup deviceDeviceGroup = new DeviceDeviceGroup(json.getString("deviceNum"), deviceGroup.getDeviceGroupId());
                // 有些测试数据设备num为空，这里过滤掉
                if(deviceDeviceGroup.getDevice_num() != null){
                    deviceDeviceGroup.setGmtCreate(LocalDateTime.now());
                    deviceDeviceGroup.setGmtModified(LocalDateTime.now());
                    List<DeviceDeviceGroup> deviceDeviceGroups = deviceGroup.getDeviceDeviceGroups();
                    if (deviceDeviceGroups == null) {
                        deviceDeviceGroups = Lists.newArrayListWithCapacity(100);
                        deviceGroup.setDeviceDeviceGroups(deviceDeviceGroups);
                    }
                    deviceDeviceGroups.add(deviceDeviceGroup);
                }
            });
        });
        return result;
    }

}

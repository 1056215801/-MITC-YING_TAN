package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.*;
import com.mit.community.mapper.HouseHoldMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 住户业务层
 *
 * @author Mr.Deng
 * @date 2018/11/14 19:33
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class HouseHoldService extends ServiceImpl<HouseHoldMapper, HouseHold> {
    private final HouseHoldMapper houseHoldMapper;

    private final ClusterCommunityService clusterCommunityService;

    private final ZoneService zoneService;

    private final BuildingService buildingService;

    private final UnitService unitService;




    @Autowired
    public HouseHoldService(HouseHoldMapper houseHoldMapper, ClusterCommunityService clusterCommunityService, ZoneService zoneService, BuildingService buildingService, UnitService unitService) {
        this.houseHoldMapper = houseHoldMapper;
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
        this.buildingService = buildingService;
        this.unitService = unitService;
    }

    /**
     * 添加住户信息
     *
     * @param house 住户信息
     * @author Mr.Deng
     * @date 19:34 2018/11/14
     */
    public void save(HouseHold house) {
        houseHoldMapper.insert(house);
    }

    /**
     * 获取所有住户信息
     *
     * @return 住户信息列表
     * @author Mr.Deng
     * @date 19:35 2018/11/14
     */
    public List<HouseHold> getHouseList() {
        return houseHoldMapper.selectList(null);
    }


    /***
     * 查询住户，通过社区编码
     * @param communityCodeList 社区编码列表
     * @param param 其他参数
     * @return java.util.List<com.mit.community.entity.HouseHold>
     * @author shuyy
     * @date 2018/11/20 8:50
     * @company mitesofor
     */
    public List<HouseHold> listFromDnakeByCommunityCodeList(List<String> communityCodeList, Map<String, Object> param) {
        List<HouseHold> result = Lists.newArrayListWithCapacity(12000);
        communityCodeList.forEach(item -> {
            int index = 1;
            while (true) {
                int pageSize = 100;
                List<HouseHold> houseHolds = this.listFromDnakeByCommunityCodePage(item,
                        index, pageSize,
                        param);
                boolean isEnd = false;
                if (houseHolds.size() < 100) {
                    isEnd = true;
                }
                result.addAll(houseHolds);
                if (isEnd) {
                    break;
                } else {
                    index++;
                }
            }
        });
        return result;

    }

    /***
     * 从dnake接口查询住户
     * @param communityCode 社区code
     * @param pageNum 分页num
     * @param pageSize 分页size
     * @param param 其他参数
     * @return java.util.List<com.mit.community.entity.HouseHold>
     * @author shuyy
     * @date 2018/11/19 17:39
     * @company mitesofor
     */
    private List<HouseHold> listFromDnakeByCommunityCodePage(String communityCode, Integer pageNum,
                                                             Integer pageSize, Map<String, Object> param) {
        String url = "/v1/household/getHouseholdList";
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        map.put("communityCode", communityCode);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        if (param != null && !param.isEmpty()) {
            map.putAll(param);
        }
        String result = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonResult = JSON.parseObject(result);
        JSONArray householdList = (JSONArray) jsonResult.get("householdList");

        String communityName = StringUtils.EMPTY;
        if (!householdList.isEmpty()) {
            communityName = clusterCommunityService.getByCommunityCode(communityCode).getCommunityName();
        }
        ArrayList<HouseHold> householdResultList = Lists.newArrayListWithCapacity(householdList.size());
        for (int i = 0; i < householdList.size(); i++) {
            // 构建household对象residenceTime
            Object o = householdList.get(i);
            JSONObject jsonObject = (JSONObject) o;
            HouseHold houseHold = JSON.parseObject(jsonObject.toJSONString(), HouseHold.class);
            houseHold.setCommunityName(communityName);
            houseHold.setCommunityCode(communityCode);
            String zoneName = jsonObject.getString("zoneName");
            if(zoneName.equals("凯翔国际外滩")){
                zoneName = "凯翔外滩国际";
            }
            Zone zone = zoneService.getByNameAndCommunityCode(zoneName, communityCode);
            if(zone == null){
                householdList.remove(i--);
                continue;
            }
            Integer zoneId = zone.getZoneId();
            houseHold.setZoneId(zoneId);
            houseHold.setZoneName(zoneName);
            String buildingName = jsonObject.getString("buildingName");
            Building building = buildingService.getByNameAndZoneId(buildingName, zoneId);
            if(building == null){
                householdList.remove(i--);
                continue;
            }
            Integer buildingId = building.getBuildingId();
            houseHold.setBuildingId(buildingId);
            String unitName = jsonObject.getString("unitName");
            Unit unit = unitService.getByNameAndBuildingId(unitName, buildingId);
            if(unit == null){
                householdList.remove(i--);
                continue;
            }
            Integer unitId = unit.getUnitId();
            houseHold.setUnitId(unitId);
            houseHold.setGmtCreate(LocalDateTime.now());
            houseHold.setGmtModified(LocalDateTime.now());
            if(houseHold.getSipAccount() == null){
                houseHold.setSipAccount(StringUtils.EMPTY);
            }
            if(houseHold.getSipPassword() == null){
                houseHold.setSipPassword(StringUtils.EMPTY);
            }
            if(houseHold.getHouseholdType() == null){
                houseHold.setHouseholdType(1);
            }
            // 构建授权用户设备对象
            String doorDeviceGroupIds = jsonObject.getString("doorDeviceGroupIds");
            if (StringUtils.isNotBlank(doorDeviceGroupIds)) {
                String[] devices = doorDeviceGroupIds.split(",");
                ArrayList<AuthorizeHouseholdDevice> authorizeHouseholdDeviceList = Lists.newArrayListWithCapacity(devices.length);
                for (String device : devices) {
                    AuthorizeHouseholdDevice authorizeHouseholdDevice = new AuthorizeHouseholdDevice(houseHold.getHouseholdId(), Integer.parseInt(device));
                    authorizeHouseholdDevice.setGmtCreate(LocalDateTime.now());
                    authorizeHouseholdDevice.setGmtModified(LocalDateTime.now());
                    authorizeHouseholdDeviceList.add(authorizeHouseholdDevice);
                }
                houseHold.setAuthorizeHouseholdDevices(authorizeHouseholdDeviceList);
            }
            // 构建授权app设备对象
            String appDeviceGroupIds = jsonObject.getString("appDeviceGroupIds");
            if (StringUtils.isNotBlank(appDeviceGroupIds)) {
                String[] devices = appDeviceGroupIds.split(",");
                ArrayList<AuthorizeAppHouseholdDevice> authorizeAppHouseholdDeviceArrayList = Lists.newArrayListWithCapacity(devices.length);
                for (String device : devices) {
                    AuthorizeAppHouseholdDevice authorizeAppHouseholdDevice = new AuthorizeAppHouseholdDevice(houseHold.getHouseholdId(),
                            Integer.parseInt(device));
                    authorizeAppHouseholdDevice.setGmtCreate(LocalDateTime.now());
                    authorizeAppHouseholdDevice.setGmtModified(LocalDateTime.now());
                    authorizeAppHouseholdDeviceArrayList.add(authorizeAppHouseholdDevice);
                }
                houseHold.setAuthorizeAppHouseholdDevices(authorizeAppHouseholdDeviceArrayList);
            }
            householdResultList.add(houseHold);
        }
        return householdResultList;
    }

    /***
     * 删除所有
     * @author shuyy
     * @date 2018/11/19 17:45
     * @company mitesofor
     */
    public void remove() {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("1=1; truncate household");
        houseHoldMapper.delete(wrapper);
    }

}

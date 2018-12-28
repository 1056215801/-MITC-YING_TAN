package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dnake.common.DnakeWebApiUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.common.HttpLogin;
import com.mit.community.common.ThreadPoolUtil;
import com.mit.community.constants.CommonConstatn;
import com.mit.community.constants.ConstellationUtil;
import com.mit.community.entity.*;
import com.mit.community.module.pass.mapper.HouseHoldMapper;
import com.mit.community.util.IdCardInfoExtractorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * 住户业务层
 *
 * @author Mr.Deng
 * @date 2018/11/14 19:33
 * <p>
 * Copyright: Copyright (c) 2018
 * </p>
 * <p>
 * Company: mitesofor
 * </p>
 */
@Slf4j
@Service
public class HouseHoldService extends ServiceImpl<HouseHoldMapper, HouseHold> {
    private final HouseHoldMapper houseHoldMapper;

    private final ClusterCommunityService clusterCommunityService;
    private final ZoneService zoneService;

    private final BuildingService buildingService;

    private final UnitService unitService;

    private final RoomService roomService;

    private final IdCardInfoExtractorUtil idCardInfoExtractorUtil;

    @Autowired
    private AuthorizeAppHouseholdDeviceGroupService authorizeAppHouseholdDeviceGroupService;

    @Autowired
    private HttpLogin httpLogin;

    /***
     * 统计人员分布，精确到省， 通过小区code列表
     * @param communityCodeList 小区code列表
     * @return java.util.List<java.util.Map                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               java.lang.String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               java.lang.Object>>
     * @author shuyy
     * @date 2018/11/23 14:10
     * @company mitesofor
     */
    public List<Map<String, Object>> countPopulationDistributionByCommunityCodeList(List<String> communityCodeList) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodeList);
        wrapper.groupBy("province");
        wrapper.setSqlSelect("province, count(*) num");
        return houseHoldMapper.selectMaps(wrapper);
    }


    @Autowired
    public HouseHoldService(HouseHoldMapper houseHoldMapper, ClusterCommunityService clusterCommunityService,
                            ZoneService zoneService, BuildingService buildingService, UnitService unitService, RoomService roomService,
                            IdCardInfoExtractorUtil idCardInfoExtractorUtil) {
        this.houseHoldMapper = houseHoldMapper;
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
        this.buildingService = buildingService;
        this.unitService = unitService;
        this.roomService = roomService;
        this.idCardInfoExtractorUtil = idCardInfoExtractorUtil;
    }

    /**
     * 查询住户信息，通过小区code列表
     *
     * @param communityCode 小区code
     * @return 住户信息列表
     * @author Mr.Deng
     * @date 15:11 2018/11/21
     */
    public List<HouseHold> listByCommunityCode(String communityCode) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return houseHoldMapper.selectList(wrapper);
    }

    /**
     * 统计住户列表信息,通过一组小区code
     * @param communityCodes 小区code列表
     * @return 住户列表信息
     * @author Mr.Deng
     * @date 15:11 2018/11/21
     */
    public List<HouseHold> listByCommunityCodeList(List<String> communityCodes) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodes);
        wrapper.groupBy("room_id");
        return houseHoldMapper.selectList(wrapper);
    }
    /**
     * 查询小区男女人数,通过小区code
     * @param communityCode 小区code
     * @return 男女人数
     * @author Mr.Deng
     * @date 16:40 2018/11/19
     */
    public Map<String, Object> mapSexByCommunityCode(String communityCode) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("SUM(CASE gender WHEN '0' THEN 1 else 0 END) boy" +
                ",SUM(CASE gender WHEN '1' THEN 1 else 0 END) girl");
        wrapper.eq("community_code", communityCode);
        return houseHoldMapper.selectMaps(wrapper).get(0);
    }

    /**
     * 查询男女人数 通过一组communityCode
     * @param communityCodes communityCode列表
     * @return 男女人数
     * @author Mr.Deng
     * @date 14:32 2018/11/21
     */
    public Map<String, Object> listSexByCommunityCodeList(List<String> communityCodes) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("SUM(CASE gender WHEN '0' THEN 1 else 0 END) boy" +
                ",SUM(CASE gender WHEN '1' THEN 1 else 0 END) girl");
        wrapper.in("community_code", communityCodes);
        return houseHoldMapper.selectMaps(wrapper).get(0);
    }

    /***
     * 获取外地人口，本地人口,其它人口
     * 传递参数为空返回所有鹰潭市的小区数据
     *
     * @param communityCode 小区code列表
     * @return java.util.Map<java.lang.String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               java.lang.Integer> map:{
     *     field:外地人口,
     *     local:本地人口,
     *     other:其他人口
     * }
     * @author liuwei
     * @date 2018/11/22 16:32
     */
    public Map<String, Integer> getFieldLocalPeople(String communityCode) {
        Map<String, Integer> map = Maps.newHashMapWithExpectedSize(2);
        Integer field, local, other;
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("count(*) as local");
        wrapper.eq("city", "鹰潭市");
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        local = Integer.parseInt(houseHoldMapper.selectMaps(wrapper).get(0).get("local").toString());

        wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("count(*) as field")
                .ne("city", "鹰潭市")
                .ne("city", "");
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        field = Integer.parseInt(houseHoldMapper.selectMaps(wrapper).get(0).get("field").toString());

        wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("count(*) as other")
                .eq("city", "");
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        other = Integer.parseInt(houseHoldMapper.selectMaps(wrapper).get(0).get("other").toString());
        map.put("field", field);
        map.put("local", local);
        map.put("other", other);
        return map;
    }

    /***
     * 统计住户总数，按小区code
     * @param communityCode 小区code
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/11/22 14:27
     */
    public Integer countByCommunityCode(String communityCode) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return houseHoldMapper.selectCount(wrapper);
    }

    /***
     * 统计住户总数，通过小区code列表
     * @param communityCodes 小区code列表
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/11/22 14:41
     */
    public Integer countByCommunityCodeList(List<String> communityCodes) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodes);
        return houseHoldMapper.selectCount(wrapper);
    }



    /***
     * 统计人员分布，精确到省， 通过小区code
     * @param communityCode 小区code
     * @return 统计人员分布，精确到省
     * @author shuyy
     * @date 2018/11/23 14:08
     * @company mitesofor
     */
    public List<Map<String, Object>> countPopulationDistributionByCommunityCode(String communityCode) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.groupBy("province");
        wrapper.setSqlSelect("province, count(*) num");
        return houseHoldMapper.selectMaps(wrapper);
    }

    /***
     * 统计人员分布，精确到市， 通过小区code列表
     * @param communityCodeList 小区code列表
     * @param province 省
     * @return java.util.List<java.util.Map                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               java.lang.String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               java.lang.Object>>
     * @author shuyy
     * @date 2018/11/23 14:08
     */
    public List<Map<String, Object>> countPopulationDistributionByCommunityCodeListAndProvince(List<String> communityCodeList, String province) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodeList);
        wrapper.eq("province", province);
        wrapper.groupBy("city");
        wrapper.setSqlSelect("city, count(*) num");
        return houseHoldMapper.selectMaps(wrapper);
    }

    /***
     * 统计人员分布，精确到市， 通过小区code
     * @param communityCode 小区code
     * @param province 省
     * @return List<Map                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Object>>
     * @author shuyy
     * @date 2018/11/23 14:08
     * @company mitesofor
     */
    public List<Map<String, Object>> countPopulationDistributionByCommunityCodeAndProvince(String communityCode, String province) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("province", province);
        wrapper.groupBy("city");
        wrapper.setSqlSelect("city, count(*) num");
        return houseHoldMapper.selectMaps(wrapper);
    }

    /**
     * 统计各个身份类型人数、通过小区code列表
     * @param communityCodeList 小区code列表
     * @return List<Map                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Object>>
     * @author shuyy
     * @date 2018/11/24 10:49
     */
    public List<Map<String, Object>> countIdentityTypeByCommunityCodeList(List<String> communityCodeList) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodeList);
        wrapper.groupBy("identity_type");
        wrapper.setSqlSelect("count(*) as num,identity_type");
        return houseHoldMapper.selectMaps(wrapper);
    }

    /**
     * 统计各个身份类型人数、通过小区code
     * @param communityCode 小区code
     * @return List<Map                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Object>>
     * @author shuyy
     * @date 2018/11/24 10:49
     */
    public List<Map<String, Object>> countIdentityTypeByCommunityCode(String communityCode) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.groupBy("identity_type");
        wrapper.setSqlSelect("identity_type, count(*) as num");
        return houseHoldMapper.selectMaps(wrapper);
    }



    /***
     * 统计年龄结构，通过小区code列表
     *
     * @param communityCodeList 小区code列表
     * @author shuyy
     * @return List<AgeConstruction>
     * @date 2018/11/22 16:32
     */
    public List<AgeConstruction> countAgeConstructionByCommuintyCodeList(List<String> communityCodeList) {
        List<AgeConstruction> ageConstructions = Lists.newArrayListWithCapacity(30);
        communityCodeList.forEach(item -> {
            List<HouseHold> houseHolds = this.listByCommunityCode(item);
            LocalDate now = LocalDate.now();
            LocalDate childTime = now.minusYears(10);
            LocalDate youngTime = now.minusYears(18);
            LocalDate youthTime = now.minusYears(45);
            LocalDate middleTime = now.minusYears(60);
            int childNum = 0;
            int youngNum = 0;
            int youthNum = 0;
            int middleNum = 0;
            int oldNum = 0;
            houseHolds = houseHolds.parallelStream().filter(a -> a.getBirthday().getYear() != 1900)
                    .collect(Collectors.toList());
            for (HouseHold houseHold : houseHolds) {
                LocalDate birthday = houseHold.getBirthday();
                if (birthday.isAfter(childTime)) {
                    childNum++;
                } else if (birthday.isAfter(youngTime)) {
                    youngNum++;
                } else if (birthday.isAfter(youthTime)) {
                    youthNum++;
                } else if (birthday.isAfter(middleTime)) {
                    middleNum++;
                } else {
                    oldNum++;
                }
            }
            AgeConstruction ageConstruction = new AgeConstruction(item, childNum, youngNum, youthNum, middleNum,
                    oldNum);
            ageConstruction.setGmtModified(LocalDateTime.now());
            ageConstruction.setGmtCreate(LocalDateTime.now());
            ageConstructions.add(ageConstruction);
        });
        return ageConstructions;
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
    public List<HouseHold> list() {
        return houseHoldMapper.selectList(null);
    }

    /***
     * 查询住户，通过社区编码
     *
     * @param communityCodeList 社区编码列表
     * @param param             其他参数
     * @return 住户列表
     * @author shuyy
     * @date 2018/11/20 8:50
     */
    public List<HouseHold> listFromDnakeByCommunityCodeList(List<String> communityCodeList, Map<String, Object> param) {
        List<HouseHold> result = Lists.newCopyOnWriteArrayList();
        communityCodeList.forEach(item -> {
            CountDownLatch pageCountDownLatch = new CountDownLatch(100);
            // 一个小区，最多的用户不会超过10000，所以这里不管小区用户有多少，都发送100个分页请求去查询。
            for (int index = 0; index < 100; index++) {
                int tmp = index;
                ThreadPoolUtil.execute(() -> {
                    try {
                        int pageSize = 100;
                        List<HouseHold> houseHoldList = listFromDnakeByCommunityCodePage(item, tmp, pageSize,
                                param);
                        result.addAll(houseHoldList);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    } finally {
                        pageCountDownLatch.countDown();
                    }
                });
            }
            try {
                pageCountDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    /***
     * 构建授权app设备对象
     * @param jsonObject json对象
     * @param houseHold 用户
     * @author shuyy
     * @date 2018/11/21 14:26
     */
    private void parseAppDeviceGroup(JSONObject jsonObject, HouseHold houseHold) {
        String appDeviceGroupIds = jsonObject.getString("deviceGroupIds");
        if (StringUtils.isNotBlank(appDeviceGroupIds)) {
            String[] devices = appDeviceGroupIds.split(",");
            ArrayList<AuthorizeAppHouseholdDeviceGroup> authorizeAppHouseholdDeviceGroups = Lists.newArrayListWithCapacity(devices.length);
            for (String device : devices) {
                AuthorizeAppHouseholdDeviceGroup authorizeAppHouseholdDeviceGroup = new AuthorizeAppHouseholdDeviceGroup(houseHold.getHouseholdId(),
                        Integer.parseInt(device));
                authorizeAppHouseholdDeviceGroup.setGmtCreate(LocalDateTime.now());
                authorizeAppHouseholdDeviceGroup.setGmtModified(LocalDateTime.now());
                authorizeAppHouseholdDeviceGroups.add(authorizeAppHouseholdDeviceGroup);
            }
            houseHold.setAuthorizeAppHouseholdDeviceGroups(authorizeAppHouseholdDeviceGroups);
        }
    }

    /***
     * 构建授权门禁设备对象
     * @param jsonObject json对象
     * @param houseHold 用户
     * @author shuyy
     * @date 2018/11/21 14:25
     */
    private void parseDoorDevice(JSONObject jsonObject, HouseHold houseHold) {
        String doorDeviceGroupIds = jsonObject.getString("doorDeviceGroupIds");
        if (StringUtils.isNotBlank(doorDeviceGroupIds)) {
            String[] devices = doorDeviceGroupIds.split(",");
            ArrayList<AuthorizeHouseholdDeviceGroup> authorizeHouseholdDeviceGroups = Lists.newArrayListWithCapacity(devices.length);
            for (String device : devices) {
                AuthorizeHouseholdDeviceGroup authorizeHouseholdDevice = new AuthorizeHouseholdDeviceGroup(houseHold.getHouseholdId(), Integer.parseInt(device));
                authorizeHouseholdDevice.setGmtCreate(LocalDateTime.now());
                authorizeHouseholdDevice.setGmtModified(LocalDateTime.now());
                authorizeHouseholdDeviceGroups.add(authorizeHouseholdDevice);
            }
            houseHold.setAuthorizeHouseholdDeviceGroups(authorizeHouseholdDeviceGroups);
        }
    }

    /***
     * 获取身份证号码，从dnake平台接口
     *
     * @param householdId 用户id
     * @return java.lang.String
     * @author shuyy
     * @date 2018/11/21 14:14
     * @company mitesofor
     */
    private String getCredentialNumFromDnake(Integer householdId) {
        return this.getCredentialNumFromDnake(householdId, 1);
    }

    /**
     * 获取身份证号码，从dnake平台接口
     *
     * @param householdId 用户id
     * @param retryNum    重试次数
     * @return java.lang.String
     * @author shuyy
     * @date 2018/11/21 14:13
     */
    private String getCredentialNumFromDnake(Integer householdId, int retryNum) {
        String url = "http://cmp.ishanghome.com/cmp/household/getStepOneInfo";
        NameValuePair[] data = {new NameValuePair("householdId", householdId.toString())};
        String result = httpLogin.post(url, data, httpLogin.getCookie());
        if (StringUtils.isBlank(result) || result.equals(CommonConstatn.ERROR)) {
            int retryNumMax = 10;
            if (retryNum > retryNumMax) {
                return StringUtils.EMPTY;
            }
            httpLogin.loginUser();
            retryNum++;
            return this.getCredentialNumFromDnake(householdId, retryNum);
        }
        JSONObject stepOneInfo = null;
        JSONObject jsonObject = JSON.parseObject(result);
        if (jsonObject != null) {
            stepOneInfo = jsonObject.getJSONObject("stepOneInfo");
        }
        if (stepOneInfo == null) {
            return StringUtils.EMPTY;
        } else {
            String credentialNum = stepOneInfo.getString("credentialNum");
            if (StringUtils.isBlank(credentialNum)) {
                return StringUtils.EMPTY;
            } else {
                return credentialNum;
            }
        }
    }

    /***
     * 从dnake平台获取身份证信息，更新
     *
     * @param list<HouseHold> 住户列表
     * @author shuyy
     * @date 2018/12/08 15:32
     */
    public List<HouseHold> getIdCardInfoFromDnake(List<HouseHold> list) {
        List<HouseHold> result = Lists.newArrayListWithCapacity(list.size());
        int tmp = 500;
        for (int i = 0; i < list.size(); i++) {
            if(i > tmp){
                tmp += 500;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(i);
            HouseHold item = list.get(i);
            // 查询身份证号
            String credentialNum = getCredentialNumFromDnake(item.getHouseholdId());
            if (credentialNum.equals(StringUtils.EMPTY)) {
                continue;
            }
            result.add(item);
            item.setCredentialNum(credentialNum);
            item.setIdentityType(HouseHold.NORMAL);
            // 通过身份证号，分析省、市、区县、出生日期、年龄
            IdCardInfo idCardInfo = idCardInfoExtractorUtil.idCardInfo(credentialNum);
            LocalDate birthday = idCardInfo.getBirthday();
            if (birthday != null) {
                item.setBirthday(birthday);
                String constellation = ConstellationUtil.calc(birthday);
                item.setConstellation(constellation);
            } else {
                item.setConstellation(StringUtils.EMPTY);
            }
            String city = idCardInfo.getCity();
            if (city != null) {
                item.setCity(city);
            }
            String province = idCardInfo.getProvince();
            if (province != null) {
                item.setProvince(province);
            }
            String region = idCardInfo.getRegion();
            if (region != null) {
                item.setRegion(region);
            }
            Integer gender = idCardInfo.getGender();
            if (gender != null) {
                item.setGender(gender);
            }

        }
        return result;
    }

    /***
     * 从Dnake接口查询住户
     *
     * @param communityCode 社区code
     * @param pageNum       分页num
     * @param pageSize      分页size
     * @param param         其他参数
     * @return 住户信息列表
     * @author shuyy
     * @date 2018/11/19 17:39
     */
    private List<HouseHold> listFromDnakeByCommunityCodePage(String communityCode, Integer pageNum, Integer pageSize,
                                                             Map<String, Object> param) {
        String url = "/v1/household/getHouseholdListMore";
//        String url = "/v1/household/getHouseholdList";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        map.put("communityCode", communityCode);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        if (param != null && !param.isEmpty()) {
            map.putAll(param);
        }
        String result = DnakeWebApiUtil.invoke(url, map);
        return parseJSON(communityCode, result);
    }

    private ArrayList<HouseHold> parseJSON(String communityCode, String result) {
        JSONObject jsonResult = JSON.parseObject(result);
        JSONArray householdList = jsonResult.getJSONArray("householdList");
        if (householdList == null || householdList.isEmpty()) {
            return Lists.newArrayListWithCapacity(0);
        }
        ArrayList<HouseHold> householdResultList = Lists.newArrayListWithCapacity(householdList.size());
        String communityName = clusterCommunityService.getByCommunityCode(communityCode).getCommunityName();
        for (int i = 0; i < householdList.size(); i++) {
            JSONObject jsonObject = householdList.getJSONObject(i);
            // 构建household对象residenceTime"residenceTime" -> "2023-08-15 00:00:00"
            HouseHold houseHold = householdList.getObject(i, HouseHold.class);
            houseHold.setGmtCreate(LocalDateTime.now());
            houseHold.setGmtModified(LocalDateTime.now());
            if (houseHold.getSipAccount() == null) {
                houseHold.setSipAccount(StringUtils.EMPTY);
            }
            if (houseHold.getSipPassword() == null) {
                houseHold.setSipPassword(StringUtils.EMPTY);
            }
            Integer health = 1;
            if (!health.equals(houseHold.getHouseholdStatus())) {
                continue;
            }
            houseHold.setCommunityCode(communityCode);
            houseHold.setCredentialNum(StringUtils.EMPTY);
            houseHold.setIdentityType(HouseHold.NORMAL);
            houseHold.setProvince(StringUtils.EMPTY);
            houseHold.setCity(StringUtils.EMPTY);
            houseHold.setRegion(StringUtils.EMPTY);
            houseHold.setBirthday(CommonConstatn.NULL_LOCAL_DATE);
            houseHold.setIdentityType((short) 99);
            houseHold.setConstellation(StringUtils.EMPTY);
            JSONArray houseList = jsonObject.getJSONArray("houseList");
            for (int index = 0; index < houseList.size(); index++) {
                JSONObject j = (JSONObject) houseList.get(index);
                Short householdType = j.getShort("householdType");
                String buildingName = (String) j.get("buildingName");
                String unitName = (String) j.get("unitName");
                String roomNum = (String) j.get("roomNum");
                String zoneName = (String) j.get("zoneName");
                // dnake接口返回数据有问题，这里进行转换
                String needConvertZoneName = "凯翔国际外滩";
                if (zoneName.equals(needConvertZoneName)) {
                    zoneName = "凯翔外滩国际";
                }
                Zone zone = zoneService.getByNameAndCommunityCode(zoneName, communityCode);
                if (zone == null) {
                    continue;
                }
                Building building = buildingService.getByNameAndZoneId(buildingName, zone.getZoneId());
                if (building == null) {
                    continue;
                }
                Unit unit = unitService.getByNameAndBuildingId(unitName, building.getBuildingId());
                if (unit == null) {
                    continue;
                }
                Room room = roomService.getByUnitIdAndRoomNum(roomNum, unit.getUnitId());
                if (room == null) {
                    continue;
                }
                HouseholdRoom householdRoom = new HouseholdRoom(communityCode, communityName, zone.getZoneId(),
                        zoneName, building.getBuildingId(), buildingName,
                        unit.getUnitId(), unitName, room.getRoomId(), roomNum, householdType, houseHold.getHouseholdId());
                householdRoom.setGmtCreate(LocalDateTime.now());
                householdRoom.setGmtModified(LocalDateTime.now());
                List<HouseholdRoom> householdRoomList = houseHold.getHouseholdRoomList();
                if (householdRoomList == null) {
                    householdRoomList = Lists.newArrayListWithCapacity(5);
                    houseHold.setHouseholdRoomList(householdRoomList);
                }
                householdRoomList.add(householdRoom);
            }
            parseAppDeviceGroup(jsonObject, houseHold);
            parseDoorDevice(jsonObject, houseHold);
            householdResultList.add(houseHold);
        }
        return householdResultList;
    }

    /***
     * 删除所有
     *
     * @author shuyy
     * @date 2018/11/19 17:45
     */
    @CacheClear(pre = "household")
    public void remove() {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("1=1; truncate household");
        houseHoldMapper.delete(wrapper);
    }

    /***
     * 查询househod， 通过住户id
     *
     * @param householdId 住户id
     * @return com.mit.community.entity.HouseHold
     * @author shuyy
     * @date 2018/11/23 14:51
     */
    @Cache(key = "household:householdId:{1}")
    HouseHold getByHouseholdId(Integer householdId) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        List<HouseHold> houseHolds = houseHoldMapper.selectList(wrapper);
        if (houseHolds.isEmpty()) {
            return null;
        } else {
            return houseHolds.get(0);
        }
    }

    /***
     * 查询有住户的所有房间id
     *
     * @return java.util.List<java.util.Map                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               java.lang.String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               java.lang.Object>>
     * @author shuyy
     * @date 2018/11/24 9:38
     */
    public List<Map<String, Object>> listActiveRoomId() {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.groupBy("room_id");
        wrapper.setSqlSelect("room_id");
        return houseHoldMapper.selectMaps(wrapper);
    }

    /***
     * 查询所有住户， 通过房间id
     *
     * @param roomId 房间号
     * @return java.util.List<com.mit.community.entity.HouseHold>
     * @author shuyy
     * @date 2018/11/24 9:42
     */
    public List<HouseHold> listByRoomId(Integer roomId) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("room_id", roomId);
        return houseHoldMapper.selectList(wrapper);
    }

    /**
     * 判断住户对象是否改变
     *
     * @param houseHoldA 住户A
     * @param houseHoldB 住户B
     * @return boolean
     * @author shuyy
     * @date 2018/12/12 10:38
     * @company mitesofor
     */
    public boolean isUpdate(HouseHold houseHoldA, HouseHold houseHoldB) {
        if (!houseHoldA.getMobile().equals(houseHoldB.getMobile())) {
            return true;
        }
        if (!houseHoldA.getHouseholdName().equals(houseHoldB.getHouseholdName())) {
            return true;
        }
        if (!houseHoldA.getHouseholdStatus().equals(houseHoldB.getHouseholdStatus())) {
            return true;
        }
        if (!houseHoldA.getAuthorizeStatus().equals(houseHoldB.getAuthorizeStatus())) {
            return true;
        }
        if (!houseHoldA.getGender().equals(houseHoldB.getGender())) {
            return true;
        }
        if (!houseHoldA.getResidenceTime().equals(houseHoldB.getResidenceTime())) {
            return true;
        }
        if (!houseHoldA.getSipAccount().equals(houseHoldB.getSipAccount())) {
            return true;
        }
        if (!houseHoldA.getSipPassword().equals(houseHoldB.getSipPassword())) {
            return true;
        }
        return false;
    }

    /**
     * 删除，通过住户id列表
     *
     * @param householdIdList 住户id列表
     * @author shuyy
     * @date 2018/12/18 18:40
     * @company mitesofor
     */
    @CacheClear(pre = "household")
    public void removeByhouseholdIdList(List<Integer> householdIdList) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.in("household_id", householdIdList);
        houseHoldMapper.delete(wrapper);

    }


}

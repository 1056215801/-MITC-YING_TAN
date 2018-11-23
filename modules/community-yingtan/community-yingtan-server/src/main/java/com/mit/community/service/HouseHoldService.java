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
import com.mit.community.common.HttpLogin;
import com.mit.community.common.ThreadPoolUtil;
import com.mit.community.constants.CommonConstatn;
import com.mit.community.entity.*;
import com.mit.community.mapper.HouseHoldMapper;
import com.mit.community.util.IdCardInfoExtractorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Slf4j
@Service
public class HouseHoldService extends ServiceImpl<HouseHoldMapper, HouseHold> {
    private final HouseHoldMapper houseHoldMapper;

    private final ClusterCommunityService clusterCommunityService;
    private final ZoneService zoneService;

    private final BuildingService buildingService;

    private final UnitService unitService;

    private final HttpLogin httpLogin;

    private final IdCardInfoExtractorUtil idCardInfoExtractorUtil;


    @Autowired
    public HouseHoldService(HouseHoldMapper houseHoldMapper, ClusterCommunityService clusterCommunityService, ZoneService zoneService, BuildingService buildingService, UnitService unitService, HttpLogin httpLogin, IdCardInfoExtractorUtil idCardInfoExtractorUtil) {
        this.houseHoldMapper = houseHoldMapper;
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
        this.buildingService = buildingService;
        this.unitService = unitService;
        this.httpLogin = httpLogin;
        this.idCardInfoExtractorUtil = idCardInfoExtractorUtil;
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

    /**
     * 通过小区code获取住户信息
     *
     * @param communityCode 小区code
     * @return 住户信息列表
     * @author Mr.Deng
     * @date 15:09 2018/11/21
     */
    public List<HouseHold> getByCommunityCode(String communityCode) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return houseHoldMapper.selectList(wrapper);
    }

    /***
     * 统计住户总数，按小区code
     * @param communityCode 小区code
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/11/22 14:27
     * @company mitesofor
     */
    public Integer countByCommunityCode(String communityCode) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return houseHoldMapper.selectCount(wrapper);
    }

    /**
     * 查询住户信息，通过小区code列表
     *
     * @param communityCodes 小区code列表
     * @return 住户信息列表
     * @author Mr.Deng
     * @date 15:11 2018/11/21
     */
    public List<HouseHold> listByCommunityCodeList(List<String> communityCodes) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodes);
        return houseHoldMapper.selectList(wrapper);
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


    /***
     * 统计住户总数，通过小区code列表
     * @param communityCodes 小区code列表
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/11/22 14:41
     * @company mitesofor
     */
    public Integer countByCommunityCodeList(List<String> communityCodes) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodes);
        return houseHoldMapper.selectCount(wrapper);
    }

    /**
     * 获取小区男女人数
     *
     * @return 男女人数
     * @author Mr.Deng
     * @date 16:40 2018/11/19
     */
    public Map<String, Object> getSexByCommunityCode(String communityCode) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("SUM(CASE gender WHEN '0' THEN 1 else 0 END) boy" +
                ",SUM(CASE gender WHEN '1' THEN 1 else 0 END) girl");
        wrapper.eq("community_code", communityCode);
        return houseHoldMapper.selectMaps(wrapper).get(0);
    }

    /**
     * 通过一组communityCode获取男女人数
     *
     * @param communityCodes communityCode列表
     * @return 男女人数
     * @author Mr.Deng
     * @date 14:32 2018/11/21
     */
    public Map<String, Object> listSexByCommunityCodes(List<String> communityCodes) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("SUM(CASE gender WHEN '0' THEN 1 else 0 END) boy" +
                ",SUM(CASE gender WHEN '1' THEN 1 else 0 END) girl");
        wrapper.in("community_code", communityCodes);
        return houseHoldMapper.selectMaps(wrapper).get(0);
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
        ArrayList<HouseHold> houseHolds = parseJSON(communityCode, result);
        CountDownLatch countDownLatch = new CountDownLatch(houseHolds.size());
        houseHolds.forEach(item -> {
            ThreadPoolUtil.submit(() -> {
                // 查询身份证号
                try {
                    String credentialNum = getCredentialNumFromDnake(item.getHouseholdId());
                    item.setCredentialNum(credentialNum);
                    // 通过身份证号，分析省、市、区县、出生日期、年龄
                    if (credentialNum.equals(StringUtils.EMPTY)) {
                        item.setProvince(StringUtils.EMPTY);
                        item.setCity(StringUtils.EMPTY);
                        item.setRegion(StringUtils.EMPTY);
                        item.setBirthday(LocalDate.of(1990, 1, 1));
                    } else {
                        IdCardInfo idCardInfo = idCardInfoExtractorUtil.idCardInfo(credentialNum);
                        LocalDate birthday = idCardInfo.getBirthday();
                        item.setBirthday(birthday == null ? LocalDate.of(1990, 1, 1) : birthday);
                        String city = idCardInfo.getCity();
                        item.setCity(city == null ? StringUtils.EMPTY : city);
                        String province = idCardInfo.getProvince();
                        item.setProvince(province == null ? StringUtils.EMPTY : province);
                        String region = idCardInfo.getRegion();
                        item.setRegion(region == null ? StringUtils.EMPTY : region);
                        Integer gender = idCardInfo.getGender();
                        if (gender != null) {
                            item.setGender(gender);
                        }
                    }
                } catch (IOException e) {
                    item.setCredentialNum(StringUtils.EMPTY);
                    log.error("获取身份证号码错误", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("countDownLatch.await() 报错了", e);
        }
        return houseHolds;
    }

    private ArrayList<HouseHold> parseJSON(String communityCode, String result) {
        JSONObject jsonResult = JSON.parseObject(result);
        JSONArray householdList = (JSONArray) jsonResult.get("householdList");
        String communityName = StringUtils.EMPTY;
        // 如果有数据，才查询小区名， 没数据就不用查
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
            // dnake接口返回数据有问题，这里进行转换
            String needConvertZoneName = "凯翔国际外滩";
            if (zoneName.equals(needConvertZoneName)) {
                zoneName = "凯翔外滩国际";
            }
            Zone zone = zoneService.getByNameAndCommunityCode(zoneName, communityCode);
            if (zone == null) {
                householdList.remove(i--);
                continue;
            }
            Integer zoneId = zone.getZoneId();
            houseHold.setZoneId(zoneId);
            houseHold.setZoneName(zoneName);
            String buildingName = jsonObject.getString("buildingName");
            Building building = buildingService.getByNameAndZoneId(buildingName, zoneId);
            if (building == null) {
                householdList.remove(i--);
                continue;
            }
            Integer buildingId = building.getBuildingId();
            houseHold.setBuildingId(buildingId);
            String unitName = jsonObject.getString("unitName");
            Unit unit = unitService.getByNameAndBuildingId(unitName, buildingId);
            if (unit == null) {
                householdList.remove(i--);
                continue;
            }
            Integer unitId = unit.getUnitId();
            houseHold.setUnitId(unitId);
            houseHold.setGmtCreate(LocalDateTime.now());
            houseHold.setGmtModified(LocalDateTime.now());
            if (houseHold.getSipAccount() == null) {
                houseHold.setSipAccount(StringUtils.EMPTY);
            }
            if (houseHold.getSipPassword() == null) {
                houseHold.setSipPassword(StringUtils.EMPTY);
            }
            if (houseHold.getHouseholdType() == null) {
                houseHold.setHouseholdType(1);
            }
            parseDoorDevice(jsonObject, houseHold);
            parseAppDevice(jsonObject, houseHold);
            householdResultList.add(houseHold);
        }
        return householdResultList;
    }

    /***
     * 构建授权app设备对象
     * @param jsonObject json对象
     * @param houseHold 用户
     * @author shuyy
     * @date 2018/11/21 14:26
     * @company mitesofor
     */
    private void parseAppDevice(JSONObject jsonObject, HouseHold houseHold) {
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
    }

    /***
     * 构建授权用户设备对象
     * @param jsonObject json对象
     * @param houseHold 用户
     * @author shuyy
     * @date 2018/11/21 14:25
     * @company mitesofor
     */
    private void parseDoorDevice(JSONObject jsonObject, HouseHold houseHold) {
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

    /***
     * 获取身份证号码，从dnake平台接口
     * @param householdId 用户id
     * @return java.lang.String
     * @author shuyy
     * @date 2018/11/21 14:14
     * @company mitesofor
     */
    public String getCredentialNumFromDnake(Integer householdId) throws IOException {
        return this.getCredentialNumFromDnake(householdId, 1);
    }

    /***
     * 获取身份证号码，从dnake平台接口
     * @param householdId 用户id
     * @param retryNum 重试次数
     * @return java.lang.String
     * @author shuyy
     * @date 2018/11/21 14:13
     * @company mitesofor
     */
    private String getCredentialNumFromDnake(Integer householdId, int retryNum) throws IOException {
        String url = "http://cmp.ishanghome.com/cmp/household/getStepOneInfo";
        NameValuePair[] data = {new NameValuePair("householdId", householdId.toString())};
        String result = httpLogin.post(url, data, httpLogin.getCookie());
        if (result == null || result.equals(CommonConstatn.ERROR)) {
            int retryNumMax = 10;
            if (retryNum > retryNumMax) {
                return StringUtils.EMPTY;
            }
            httpLogin.login();
            retryNum++;
            return this.getCredentialNumFromDnake(householdId, retryNum);
        }
        JSONObject stepOneInfo = null;
        JSONObject jsonObject = JSON.parseObject(result);
        if(jsonObject != null){
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
     * 统计年龄结构，通过小区code列表
     * @param communityCodeList 小区code列表
     * @return java.util.Map<java.lang.String               ,               java.lang.Integer>
     * @author shuyy
     * @date 2018/11/22 16:32
     * @company mitesofor
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
            houseHolds = houseHolds.parallelStream().filter(a -> a.getBirthday().getYear() != 1990).collect(Collectors.toList());
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
            AgeConstruction ageConstruction = new AgeConstruction(item, childNum, youngNum,
                    youthNum, middleNum, oldNum);
            ageConstruction.setGmtModified(LocalDateTime.now());
            ageConstruction.setGmtCreate(LocalDateTime.now());
            ageConstructions.add(ageConstruction);
        });
        return ageConstructions;
    }
}

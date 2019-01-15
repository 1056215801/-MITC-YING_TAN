package com.mit.community.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dnake.common.DnakeWebApiUtil;
import com.dnake.constant.DnakeConstants;
import com.dnake.constant.DnakeWebConstants;
import com.google.common.collect.Maps;
import com.mit.common.util.DateUtils;
import com.mit.community.entity.AccessControl;
import com.mit.community.entity.ActivePeople;
import com.mit.community.entity.Device;
import com.mit.community.entity.HouseHold;
import com.mit.community.module.pass.mapper.AccessControlMapper;
import com.mit.community.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 门禁记录业务层
 *
 * @author Mr.Deng
 * @date 2018/11/15 11:55
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class AccessControlService extends ServiceImpl<AccessControlMapper, AccessControl> {
    private final AccessControlMapper accessControlMapper;

    private final ClusterCommunityService clusterCommunityService;

    private final ZoneService zoneService;

    private final DeviceService deviceService;

    private final ActivePeopleService activePeopleService;

    private final HouseHoldService houseHoldService;

    @Autowired
    public AccessControlService(AccessControlMapper accessControlMapper,
                                ClusterCommunityService clusterCommunityService, ZoneService zoneService,
                                DeviceService deviceService, ActivePeopleService activePeopleService,
                                HouseHoldService houseHoldService) {
        this.accessControlMapper = accessControlMapper;
        this.clusterCommunityService = clusterCommunityService;
        this.zoneService = zoneService;
        this.deviceService = deviceService;
        this.activePeopleService = activePeopleService;
        this.houseHoldService = houseHoldService;
    }

    /**
     * 添加门禁记录
     *
     * @param accessControl 门禁记录信息
     * @author Mr.Deng
     * @date 11:57 2018/11/15
     */
    public void save(AccessControl accessControl) {
        accessControlMapper.insert(accessControl);
    }

    /**
     * 从dnake接口，分页查询门禁列表。通过小区编码
     *
     * @param communityCode 小区编码
     * @param pageSize      分页大小
     * @param pageNum       当前页
     * @param param         其他参数
     * @author shuyy
     * @date 2018/11/16 16:43
     */
    private List<AccessControl> listFromDnakeByCommunityCodePage(String communityCode, Integer pageNum,
                                                                 Integer pageSize, Map<String, Object> param) {
        String url = "/v1/device/getAccessControlList";
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(10);
        map.put("communityCode", communityCode);
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        map.put("pageNum", pageNum);
        map.put("accountId", DnakeWebConstants.ACCOUNT_ID);
        if (param != null && !param.isEmpty()) {
            map.putAll(param);
        }
        String result = DnakeWebApiUtil.invoke(url, map);
        JSONObject jsonResult = JSON.parseObject(result);
        JSONArray accessControlList = (JSONArray) jsonResult.get("accessControlList");
        return JSONObject.parseArray(accessControlList.toJSONString(),
                AccessControl.class);
    }

    /**
     * 获取最新的门禁记录
     *
     * @return com.mit.community.entity.AccessControl
     * @author shuyy
     * @date 2018/11/16 17:03
     */
    private AccessControl getNewestAccessControlByCommunityCode(String communityCode) {
        RowBounds rowBounds = new RowBounds(0, 1);
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.orderBy("access_time", false);
        wrapper.eq("community_code", communityCode);
        List<AccessControl> list = this.accessControlMapper.selectPage(rowBounds, wrapper);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询门禁记录信息，通过小区code
     *
     * @param communityCode 小区code
     * @return 门禁记录列表
     * @author Mr.Deng
     * @date 15:29 2018/11/21
     */
    public List<AccessControl> listByCommunityCode(String communityCode) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        return accessControlMapper.selectList(wrapper);
    }

    /**
     * 统计通行总数，按小区code
     *
     * @param communityCode 小区code
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/11/22 14:29
     */
    public Integer countByCommunityCode(String communityCode) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = dateTimeFormatter.format(LocalDateTime.now());
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.like("access_time", dateStr, SqlLike.RIGHT);
        return accessControlMapper.selectCount(wrapper);
    }

    /**
     * 分页查询门禁记录，通过小区code
     *
     * @param communityCodeList 小区code列表
     * @param pageNum           当前页
     * @param pageSize          分页总数
     * @return java.util.List<com.mit.community.entity.AccessControl>
     * @author shuyy
     * @date 2018/11/23 14:41
     */
    public List<AccessControl> listByCommunityCodeListPage(List<String> communityCodeList, Integer pageNum, Integer pageSize) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodeList);
        Page<AccessControl> page = new Page<>(pageNum, pageSize);
        return accessControlMapper.selectPage(page, wrapper);
    }

    /**
     * 统计门禁总数，通过小区code列表
     *
     * @param communityCodes 小区code列表
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/11/22 14:44
     */
    public Integer countByCommunityCodes(List<String> communityCodes) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = dateTimeFormatter.format(LocalDateTime.now());
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodes);
        wrapper.like("access_time", dateStr, SqlLike.RIGHT);
        return accessControlMapper.selectCount(wrapper);
    }

    /**
     * 统计最近一个月的活跃人数，通过设备名列表
     *
     * @param deviceNameList 设备名列表
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/11/22 11:22
     */
    public Long countRecentMonthActivePeopleByDeviceNameList(List<String> deviceNameList) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.in("device_name", deviceNameList);
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        wrapper.lt("access_time", LocalDateTime.now()).gt("access_time", lastMonth);
        wrapper.setSqlSelect("count(distinct household_id) num");
        return (Long) accessControlMapper.selectMaps(wrapper).get(0).get("num");
    }

    /**
     * 查询当前时间到凌晨2点的通行记录数，
     *
     * @param deviceNameList 设备name列表
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/11/22 14:04
     */
    private Integer countUntilTwoNumByDeviceNameList(List<String> deviceNameList) {
        if (deviceNameList.isEmpty()) {
            return 0;
        }
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.in("device_name", deviceNameList);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime standardTime = now.withHour(2);
        int two = 2;
        if (now.getHour() < two) {
            standardTime = standardTime.minusDays(1);
        }
        wrapper.lt("access_time", now).gt("access_time", standardTime);
        return accessControlMapper.selectCount(wrapper);
    }

    /**
     * 统计驻留人数，通过小区code
     *
     * @param communityCode 小区code
     * @return long
     * @author shuyy
     * @date 2018/11/22 14:23
     */
    public long countRemainPeopleByCommunityCode(String communityCode) {
        // 查出凌晨2点后进的数量、出的数量，然后：活跃人数 - （出-进）
        List<Device> outDevices = deviceService.listInOrOutByCommunityCode(communityCode, "出");
        List<String> outDeviceNameList = outDevices.parallelStream().map(Device::getDeviceName).collect(Collectors.toList());
        Integer outNum = this.countUntilTwoNumByDeviceNameList(outDeviceNameList);

        List<Device> inDevices = deviceService.listInOrOutByCommunityCode(communityCode, "进");
        List<String> inDeviceNameList = inDevices.parallelStream().map(Device::getDeviceName).collect(Collectors.toList());
        Integer inNum = this.countUntilTwoNumByDeviceNameList(inDeviceNameList);

        ActivePeople activePeople = activePeopleService.getByCommunityCode(communityCode);
        int i = outNum - inNum;
        if (activePeople == null) {
            return 1000 - i;
        } else {
            return activePeople.getActivePeopleNum() - i;
        }
    }

    /**
     * 统计驻留人数，通过小区code列表
     *
     * @param communityCodes 小区code列表
     * @return long
     * @author Mr.Deng
     * @date 18:43 2018/11/26
     */
    public long countRemainPeopleByCommunityCodes(List<String> communityCodes) {
        long s = 0L;
        for (String communityCode : communityCodes) {
            s = s + countRemainPeopleByCommunityCode(communityCode);
        }
        return s;
    }

    /***
     * 统计门禁记录开门方式，通过小区code
     * @param communityCodeList 小区code
     * @return 统计记录
     * @author shuyy
     * @date 2018/11/24 8:51
     * @company mitesofor
     */
    public List<Map<String, Object>> countInterActiveTypeByCommunityCodeList(List<String> communityCodeList) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.in("community_code", communityCodeList);
        wrapper.groupBy("interactive_type");
        wrapper.setSqlSelect("interactive_type, COUNT(*) num");
        return accessControlMapper.selectMaps(wrapper);
    }

    /***
     * 统计门禁记录开门方式，通过小区code
     * @param communityCode 小区code
     * @return 统计记录
     * @author shuyy
     * @date 2018/11/24 8:51
     * @company mitesofor
     */
    public List<Map<String, Object>> countInterActiveTypeByCommunityCode(String communityCode) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.groupBy("interactive_type");
        wrapper.setSqlSelect("interactive_type, COUNT(*) num");
        return accessControlMapper.selectMaps(wrapper);
    }

    /***
     * 统计最近半年通行记录数，通过住户id
     * @param householdId  住户id
     * @return java.lang.Integer
     * @author shuyy
     * @date 2018/11/24 10:02
     * @company mitesofor
     */
    public Integer countHalfYearNumByHouseholdId(Integer householdId) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime halfYear = now.minusMonths(6);
        wrapper.ge("access_time", halfYear).le("access_time", now);
        return accessControlMapper.selectCount(wrapper);
    }

    /***
     * 查询本小区通行人数
     * 不传参数返回所有
     *
     * @param communityCode 小区编号 传空参数返回所有
     * @return java.lang.Integer
     * @author lw
     * @date 2018/11/23 14:04
     */
    public Integer getPassNumber(String communityCode) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("count(DISTINCT household_id) as i");
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        Map<String, Object> map = accessControlMapper.selectMaps(wrapper).get(0);

        return Integer.parseInt(map.get("i").toString());
    }

    /***
     * 查询本小区通行人次
     * 不传参数返回所有
     *
     * @param communityCode 小区编号 传空参数返回所有
     * @return java.lang.Integer
     * @author lw
     * @date 2018/11/23 14:04
     */
    public Integer getPassPersonTime(String communityCode) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("count(*) as i");
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        Map<String, Object> map = accessControlMapper.selectMaps(wrapper).get(0);

        return Integer.parseInt(map.get("i").toString());
    }

    /***
     * 获取当前访问记录
     * @param deviceName 设备名
     * @return java.util.Map
     * @author shuyy
     * @date 2018/11/13 16:50
     */
    public AccessControl getCurrentAccess(String deviceName, String communityCode) {
        String result = StringUtils.EMPTY;
        try {
            result = this.listAccessControl(deviceName, communityCode, 20);
        } catch (Exception e) {
            // 报错了就取我们数据库的最新的一条记录
            List<AccessControl> list = this.listAccessControl(null, 1, deviceName);
            if (!list.isEmpty()) {
                return list.get(0);
            }
        }
        JSONObject jsonResult = JSON.parseObject(result);
        JSONArray accessControlList = (JSONArray) jsonResult.get("accessControlList");
        for (Object item : accessControlList) {
            JSONObject accessControl = (JSONObject) item;
            if (deviceName.equals(accessControl.get("deviceName"))) {
                LocalDateTime accessTime = DateUtils.parseStringToDateTime(accessControl.get("accessTime").toString(),
                        null);
                AccessControl o = accessControl.toJavaObject(AccessControl.class);
                String householdMobile = o.getHouseholdMobile();
                HouseHold household = houseHoldService.getByHouseholdByCellphoneAndCommunityCode(householdMobile, communityCode);
                if (household == null) {
                    o.setInteractiveType((short) 99);
                }else {
                    o.setIdentityType(household.getIdentityType());
                }
                String accessImgUrl = o.getAccessImgUrl();
                // 判定这个图片是否已经存到服务器。重试10次
                for (int i = 0; i < 10; i++) {
                    int status = HttpUtil.getStatus(accessImgUrl);
                    if (status == 200) {
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return o;
            }
        }
        return new AccessControl();
    }

    private String listAccessControl(String deviceName, String communityCode, Integer pageSize) {
        DnakeConstants.choose(DnakeConstants.MODEL_PRODUCT);
        String url = "/v1/device/getAccessControlList";
        HashMap<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(4);
        map.put("deviceName", deviceName);
        map.put("pageSize", pageSize);
        map.put("pageNum", "1");
        map.put("communityCode", communityCode);
        String invoke = DnakeWebApiUtil.invoke(url, map);
        System.out.println(invoke);
        return invoke;
    }

    public List<AccessControl> listAccessControl(String communityCode, Integer pageSize, String deviceNUm) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        if (StringUtils.isNotBlank(deviceNUm)) {
            wrapper.eq("device_num", deviceNUm);
        }
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        wrapper.orderBy("access_time", false);
        Page<AccessControl> page = new Page<>();
        page.setCurrent(1);
        page.setSize(pageSize);
        return accessControlMapper.selectPage(page, wrapper);
    }
}

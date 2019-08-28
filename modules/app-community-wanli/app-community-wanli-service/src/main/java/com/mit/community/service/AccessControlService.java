package com.mit.community.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mit.community.entity.AccessControl;
import com.mit.community.entity.Device;
import com.mit.community.entity.HouseHold;
import com.mit.community.mapper.AccessControlMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.text.SimpleDateFormat;

import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 门禁记录业务处理层
 * @author Mr.Deng
 * @date 2018/12/8 10:39
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class AccessControlService extends ServiceImpl<AccessControlMapper, AccessControl> {

    @Autowired
    private AccessControlMapper accessControlMapper;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 查询门禁记录，通过住户id
     * @param communityCode 小区code
     * @param householdId   住户id
     * @return 门禁记录列表
     * @author Mr.Deng
     * @date 10:43 2018/12/8
     */
    public List<AccessControl> listByHouseHoldId(String communityCode, Integer householdId) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("household_id", householdId);
        return accessControlMapper.selectList(wrapper);
    }

    /**
     * 统计门禁总数，通过住户id和小区code
     * @param communityCode 小区code
     * @param householdId   住户id
     * @return 门禁总数
     * @author Mr.Deng
     * @date 11:47 2018/12/24
     */
    public Integer countByHouseHoldIdAndCommunityCode(String communityCode, Integer householdId) {
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("household_id", householdId);
        return accessControlMapper.selectCount(wrapper);
    }

    /**
     * 统计门禁总数
     * @param cellphone     手机号
     * @param communityCode 小区code
     * @return 门禁总数
     * @author Mr.Deng
     * @date 11:49 2018/12/24
     */
    public Integer countByCellphoneAndCommunityCode(String cellphone, String communityCode) {
        HouseHold houseHold = houseHoldService.getByCellphoneAndCommunityCode(cellphone, communityCode);
        Integer accessControlNum = 0;
        if (houseHold != null) {
            accessControlNum = this.countByHouseHoldIdAndCommunityCode(communityCode, houseHold.getHouseholdId());
        }
        return accessControlNum;
    }

    /**
     * 查询门禁记录
     * @param householdId 住户id
     * @param deviceNum   设备编号列表
     * @param pageNum     页数
     * @param pageSize    一页显示数量，为零查全部
     * @return 门禁记录
     * @author Mr.Deng
     * @date 10:35 2018/12/21
     */
    public Page<AccessControl> listByHouseHoldIdAndDeviceNum(Integer householdId, List<String> deviceNum,
                                                             Integer pageNum, Integer pageSize) {
        List<Map<String, Object>> maps = null;
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        Page<AccessControl> page = new Page(pageNum, pageSize);
        wrapper.eq("household_id", householdId);
        wrapper.in("device_num", deviceNum);
        wrapper.orderBy("access_time", false);
        List<AccessControl> accessControls = accessControlMapper.selectPage(page, wrapper);
        page.setRecords(accessControls);
        return page;
    }

    /**
     * 查询门禁记录 通过电话号码
     * @param household_mobile
     * @param deviceNum
     * @param pageNum
     * @param pageSize
     * @return
     * @author xq
     * @date 10:35 2019/8/12
     */
    public Page<AccessControl> listByCellphoneAndDeviceNum(String household_mobile, List<String> deviceNum,
                                                             Integer pageNum, Integer pageSize) {
        List<Map<String, Object>> maps = null;
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        Page<AccessControl> page = new Page(pageNum, pageSize);
        wrapper.eq("household_mobile", household_mobile);
        wrapper.in("device_num", deviceNum);
        wrapper.orderBy("access_time", false);
        List<AccessControl> accessControls = accessControlMapper.selectPage(page, wrapper);
        page.setRecords(accessControls);
        return page;
    }

    /**
     * 查询门禁记录，通过社区code和手机号码
     * @param communityCode 小区code
     * @param cellphone     手机号码
     * @param deviceType    设备类型（M：单元门口机；W：围墙机；）
     * @param pageNum       页数
     * @param pageSize      一页显示数量，为零查全部
     * @return 门禁记录信息
     * @author Mr.Deng
     * @date 16:04 2018/12/12
     */
    public Page<AccessControl> listToHouseholdAccessControl(String communityCode, String cellphone, String deviceType,
                                                            Integer pageNum, Integer pageSize) {
        Page<AccessControl> page = new Page(pageNum, pageSize);
        //查询到相应小区和类型的设备信息
        List<Device> devices = deviceService.getByDeviceTypeAndCommunityCode(communityCode, deviceType);
        if (!devices.isEmpty()) {
            //获取到相应设备类型的设备编号
            List<String> deviceNums = devices.stream().map(Device::getDeviceNum).collect(Collectors.toList());
            //查询小区用户信息
            HouseHold houseHold = houseHoldService.getByCellphoneAndCommunityCode(cellphone, communityCode);
            if (houseHold != null) {
                //先是查询相应设备的设备编号-》查询住户id-》通过设备编号和住户id，查询门禁记录信息，进行分页查询
                //page = listByHouseHoldIdAndDeviceNum(houseHold.getHouseholdId(), deviceNums, pageNum, pageSize);
                page = listByCellphoneAndDeviceNum(cellphone, deviceNums, pageNum, pageSize);
            }
        }
        return page;
    }

    public Page<AccessControl> getAccessControlPage (String communityCode, String cardNum, String name, String zoneId, String buildingId, String unitId, Integer interactiveType, String deicveNum, String timeStart, String timeEnd, int pageNum, int pageSize) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Page<AccessControl> page = new Page<>(pageNum, pageSize);
        EntityWrapper<AccessControl> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        if (StringUtils.isNotBlank(cardNum)) {
            wrapper.eq("card_num", cardNum);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.eq("household_name", name);
        }
        if (StringUtils.isNotBlank(zoneId)) {
            wrapper.eq("zone_id", zoneId);
        }
        if (StringUtils.isNotBlank(buildingId)) {
            wrapper.eq("building_id", buildingId);
        }
        if (StringUtils.isNotBlank(unitId)) {
            wrapper.eq("unit_id", unitId);
        }
        if (interactiveType != null) {
            wrapper.eq("interactive_type", interactiveType);
        }
        if (StringUtils.isNotBlank(deicveNum)) {
            wrapper.eq("device_num", deicveNum);
        }
        if (StringUtils.isNotBlank(timeStart)) {
            wrapper.ge("access_time", timeStart);
        }
        if (StringUtils.isNotBlank(timeEnd)) {
            wrapper.le("access_time", timeEnd);
        }
        wrapper.orderBy("access_time", false);
        List<AccessControl> list = accessControlMapper.selectPage(page, wrapper);
        page.setRecords(list);
        return page;
    }


}

package com.mit.community.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.common.util.DateUtils;
import com.mit.community.constants.Constants;
import com.mit.community.entity.*;
import com.mit.community.mapper.ApplyKeyMapper;
import com.mit.community.util.ConstellationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 申请钥匙业务层
 *
 * @author Mr.Deng
 * @date 2018/12/3 14:45
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Service
public class ApplyKeyService {

    @Autowired
    private ApplyKeyMapper applyKeyMapper;
    @Autowired
    private ApplyKeyImgService applyKeyImgService;
    @Autowired
    private UserTrackService userTrackService;
    @Autowired
    private DnakeAppApiService dnakeAppApiService;
    @Autowired
    private UserService userService;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private IdCardInfoExtractorUtil idCardInfoExtractorUtil;
    @Autowired
    private AuthorizeAppHouseholdDeviceGroupService authorizeAppHouseholdDeviceGroupService;
    @Autowired
    private HouseholdRoomService householdRoomService;

    /**
     * 添加申请钥匙数据
     *
     * @param applyKey 申请钥匙数据
     * @return 添加数据条数
     * @author Mr.Deng
     * @date 11:07 2018/11/30
     */
    public Integer save(ApplyKey applyKey) {
        applyKey.setGmtCreate(LocalDateTime.now());
        applyKey.setGmtModified(LocalDateTime.now());
        return applyKeyMapper.insert(applyKey);
    }

    /**
     * 修改数据
     *
     * @param applyKey 申请钥匙数据
     * @return 修改数据
     * @author Mr.Deng
     * @date 15:28 2018/12/3
     */
    public Integer update(ApplyKey applyKey) {
        applyKey.setGmtModified(LocalDateTime.now());
        return applyKeyMapper.updateById(applyKey);
    }

    /**
     * 查询申请钥匙数据，通过申请钥匙id
     *
     * @param applyKeyId 申请钥匙id
     * @return 申请钥匙信息
     * @author Mr.Deng
     * @date 15:30 2018/12/3
     */
    public ApplyKey selectById(Integer applyKeyId) {
        return applyKeyMapper.selectById(applyKeyId);
    }

    /**
     * 查询申请钥匙列表. 通过申请状态
     *
     * @param zoneId           分区id
     * @param buildingId       楼栋id
     * @param unitId           单元id
     * @param roomId           房间id
     * @param contactPerson    联系人
     * @param contactCellphone 联系电话
     * @param status           状态
     * @return java.util.List<com.mit.community.entity.ApplyKey>
     * @author shuyy
     * @date 2018/12/14 16:03
     * @company mitesofor
     */
    public Page<ApplyKey> listByPage(Integer createUserId, String communityCode, Integer zoneId, Integer buildingId, Integer unitId,
                                     Integer roomId, String contactPerson, String contactCellphone, Integer status,
                                     LocalDateTime gmtCreateStart, LocalDateTime gmtCreateEnd, Integer pageNum, Integer pageSize) {
        EntityWrapper<ApplyKey> wrapper = new EntityWrapper<>();
        if (createUserId != null) {
            wrapper.eq("creator_user_id", createUserId);
        }
        if (zoneId != null) {
            wrapper.eq("zone_id", zoneId);
        }
        if (buildingId != null) {
            wrapper.eq("building_id", buildingId);
        }
        if (unitId != null) {
            wrapper.eq("unit_id", unitId);
        }
        if (roomId != null) {
            wrapper.eq("room_id", roomId);
        }
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("community_code", communityCode);
        }
        if (StringUtils.isNotBlank(contactPerson)) {
            wrapper.eq("contact_person", contactPerson);
        }
        if (StringUtils.isNotBlank(contactCellphone)) {
            wrapper.eq("contact_cellphone", contactCellphone);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (gmtCreateStart != null) {
            wrapper.ge("gmt_create", gmtCreateStart);
        }
        if (gmtCreateEnd != null) {
            wrapper.le("gmt_create", gmtCreateEnd);
        }
        wrapper.orderBy("gmt_create", false);
        if (pageNum != null && pageSize != null) {
            Page<ApplyKey> page = new Page<>(pageNum, pageSize);
            List<ApplyKey> applyKeys = applyKeyMapper.selectPage(page, wrapper);
            page.setRecords(applyKeys);
            return page;
        }
        return null;
    }



    /*
    public List<ApplyKey> listByCellphone(String contactPerson) {
        EntityWrapper<ApplyKey> wrapper = new EntityWrapper<>();
        wrapper.eq("contact_person", contactPerson);
        return applyKeyMapper.selectList(wrapper);
    }
*/

    /**
     * 申请钥匙
     *
     * @param communityCode    小区code
     * @param communityName    小区名称
     * @param zoneId           分区id
     * @param zoneName         分区名称
     * @param buildingId       楼栋id
     * @param buildingName     楼栋名称
     * @param unitId           单元id
     * @param unitName         单元名称
     * @param roomId           房间id
     * @param roomNum          房间编号
     * @param contactPerson    申请人
     * @param contactCellphone 申请人电话
     * @param content          描述
     * @param creatorUserId    创建人id
     * @author Mr.Deng
     * @date 14:50 2018/12/3
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertApplyKey(String communityCode, String communityName, Integer zoneId, String zoneName,
                               Integer buildingId, String buildingName, Integer unitId, String unitName, Integer roomId,
                               String roomNum, String contactPerson, String contactCellphone, String content,
                               Integer creatorUserId, String idCard, Short householdType, List<String> images) {
        ApplyKey applyKey = new ApplyKey(communityCode, communityName, zoneId, zoneName, buildingId, buildingName,
                unitId, unitName, roomId, roomNum, contactPerson, contactCellphone, 1, content, creatorUserId,
                StringUtils.EMPTY, Constants.NULL_LOCAL_DATE_TIME, idCard, householdType);
        this.save(applyKey);
        for (String image : images) {
            ApplyKeyImg applyKeyImg = new ApplyKeyImg(applyKey.getId(), image);
            applyKeyImgService.save(applyKeyImg);
        }
    }

    /**
     * 审批申请钥匙
     *
     * @param applyKeyId        申请记录id
     * @param checkPerson       审批人
     * @param residenceTime     过期时间
     * @param deviceGroupIdList 设备组id列表
     * @author shuyy
     * @date 2018/12/19 9:53
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    public String approval(Integer applyKeyId, String checkPerson,
                           LocalDate residenceTime, List<String> deviceGroupIdList) {
        // 判断用户表里有没有数据，有则说明数据还没同步，提示已有钥匙，5分钟后重新登录，直接返回
        ApplyKey applyKey = this.selectById(applyKeyId);
        Integer creatorUserId = applyKey.getCreatorUserId();
        User user = userService.getById(creatorUserId);
        String cellphone = user.getCellphone();
        List<HouseHold> houseHoldList = houseHoldService.getByCellphone(cellphone);
        if(!houseHoldList.isEmpty()){
            return "已经有住户信息";
        }
//        if (houseHoldList.isEmpty()) {
//            return "数据还没同步，已有钥匙,5分钟后重新查看";
//        }
        // 更新申请钥匙记录
        applyKey.setCheckTime(LocalDateTime.now());
        applyKey.setStatus(2);
        applyKey.setCheckPerson(checkPerson);
        this.update(applyKey);
        // dnake平台增加住户，
        String communityCode = applyKey.getCommunityCode();
        Integer zoneId = applyKey.getZoneId();
        Integer buildingId = applyKey.getBuildingId();
        Integer unitId = applyKey.getUnitId();
        Integer roomId = applyKey.getRoomId();
        String contactPerson = applyKey.getContactPerson();
        List<Map<String, Object>> houseList = Lists.newArrayListWithCapacity(10);
        Map<String, Object> h = Maps.newHashMapWithExpectedSize(4);
        h.put("zoneId", zoneId);
        h.put("buildingId", buildingId);
        h.put("unitId", unitId);
        h.put("roomId", roomId);
        h.put("householdType", applyKey.getHouseholdType());
        houseList.add(h);
        // 解析身份证信息
        String idCard = applyKey.getIdCard();
        String residenceTimeStr = DateUtils.format(residenceTime, null);
        IdCardInfo idCardInfo = idCardInfoExtractorUtil.idCardInfo(idCard);
        JSONObject jsonObject = dnakeAppApiService.saveHousehold(communityCode, cellphone, idCardInfo.getGender(),
                contactPerson, residenceTimeStr, houseList);
        if (jsonObject.get("errorCode") != null && !jsonObject.get("errorCode").equals(0)) {
            throw new RuntimeException(jsonObject.get("msg").toString());
        }
        // 添加授权设备组
        Integer householdId = (Integer) jsonObject.get("householdId");
        dnakeAppApiService.authorizeHousehold(communityCode, householdId, residenceTimeStr, deviceGroupIdList);
        // 本地数据库保存住户
        String constellation = ConstellationUtil.calc(idCardInfo.getBirthday());
        HouseHold houseHold = new HouseHold(applyKey.getCommunityCode(), constellation, householdId, contactPerson,
                1, 2,
                idCardInfo.getGender(), residenceTime,
                user.getCellphone(), StringUtils.EMPTY,
                StringUtils.EMPTY, idCard, idCardInfo.getProvince(),
                idCardInfo.getCity(), idCardInfo.getRegion(), idCardInfo.getBirthday(),
                (short) 99, null, null, null);
        houseHoldService.save(houseHold);
        // 本地数据库保存关联设备组
        List<AuthorizeAppHouseholdDeviceGroup> authorizeAppHouseholdDeviceGroups = Lists.newArrayListWithCapacity(deviceGroupIdList.size());
        deviceGroupIdList.forEach(item -> {
            AuthorizeAppHouseholdDeviceGroup authorizeAppHouseholdDeviceGroup = new AuthorizeAppHouseholdDeviceGroup(householdId, Integer.parseInt(item));
            authorizeAppHouseholdDeviceGroup.setGmtCreate(LocalDateTime.now());
            authorizeAppHouseholdDeviceGroup.setGmtModified(LocalDateTime.now());
            authorizeAppHouseholdDeviceGroups.add(authorizeAppHouseholdDeviceGroup);
        });
        authorizeAppHouseholdDeviceGroupService.insertBatch(authorizeAppHouseholdDeviceGroups);
        // 关联房屋
        HouseholdRoom householdRoom = new HouseholdRoom(applyKey.getCommunityCode(),
                applyKey.getCommunityName(),
                applyKey.getZoneId(), applyKey.getZoneName(),
                applyKey.getBuildingId(), applyKey.getBuildingName(),
                applyKey.getUnitId(), applyKey.getUnitName(),
                applyKey.getRoomId(), applyKey.getRoomNum(),
                applyKey.getHouseholdType(), householdId, null);
        householdRoomService.save(householdRoom);
        return "success";
    }

}

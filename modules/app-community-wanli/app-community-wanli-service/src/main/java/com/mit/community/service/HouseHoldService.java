package com.mit.community.service;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mit.community.constants.Constants;
import com.mit.community.entity.*;
import com.mit.community.entity.entity.DeviceGroup;
import com.mit.community.entity.entity.PersonBaseInfo;


import com.mit.community.mapper.HouseHoldMapper;
import com.mit.community.mapper.mapper.PersonLabelsMapper;
import com.mit.community.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import com.mit.community.mapper.*;
import com.mit.community.population.service.PersonBaseInfoService;
import com.mit.community.population.service.PersonLabelsService;

import com.mit.community.util.AuthorizeStatusUtil;
import com.mit.community.util.ConstellationUtil;
import com.mit.community.util.DateUtils;
import com.mit.community.util.Result;
import com.mit.community.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 住户
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@Service
public class HouseHoldService extends ServiceImpl<HouseHoldMapper,HouseHold> {


    @Autowired
    private HouseHoldMapper houseHoldMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ClusterCommunityService clusterCommunityService;
    @Autowired
    private UserHouseholdMapper userHouseholdMapper;
    @Autowired
    private HouseholdRoomService householdRoomService;
    @Autowired
    private DnakeAppApiService dnakeAppApiService;
    @Autowired
    private IdCardInfoExtractorUtil idCardInfoExtractorUtil;
    @Autowired
    private AuthorizeAppHouseholdDeviceGroupService authorizeAppHouseholdDeviceGroupService;
    @Autowired
    private AuthorizeHouseholdDeviceGroupService authorizeHouseholdDeviceGroupService;
    @Autowired
    private AuthorizeAppHouseholdDeviceGroupMapper authorizeAppHouseholdDeviceGroupMapper;
    @Autowired
    private AuthorizeHouseholdDeviceGroupMapper authorizeHouseholdDeviceGroupMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PersonBaseInfoService personBaseInfoService;
    @Autowired
    private DeviceGroupService deviceGroupService;
    @Autowired
    private PersonLabelsService personLabelsService;
    @Autowired
    private DeviceDeviceGroupService deviceDeviceGroupService;
    @Autowired
    private HouseHoldPhotoService houseHoldPhotoService;
    @Autowired
    private AccessCardService accessCardService;
    @Autowired
    private PersonLabelsMapper personLabelsMapper;

    public Page<HouseHoldPhotoInfo> getHouseHoldPhotoInfo(String communityCode, String mobile, String name, Integer isUpload, Integer pageNum, Integer pageSize) {
        Page<HouseHoldPhotoInfo> page = new Page<>(pageNum, pageSize);
        EntityWrapper<HouseHoldPhotoInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("b.community_code", communityCode);
        if (StringUtils.isNotBlank(mobile)) {
            wrapper.eq("b.mobile", mobile);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.eq("b.household_name", name);
        }
        if (isUpload != null) {
            wrapper.eq("a.is_upload", isUpload);
        }
        List<HouseHoldPhotoInfo> list = personLabelsMapper.getHouseHoldPhotoInfo(page, wrapper);//方法还没实现
        if (!list.isEmpty()) {
            for (HouseHoldPhotoInfo houseHoldPhotoInfo : list) {
                if (Integer.parseInt(houseHoldPhotoInfo.getDiffiTime()) <= 10) {
                    houseHoldPhotoInfo.setIsOnline(1);
                } else {
                    houseHoldPhotoInfo.setIsOnline(2);
                }
            }
        }
        page.setRecords(list);
        return page;
    }

    public static String getAuthString(int n) {
        StringBuffer auth = new StringBuffer("");
        String result = Integer.toBinaryString(n);
        int a = Integer.parseInt(result);
        int b = a/100;//百位
        int c = (a%100)/10;//十位
        int d = d=a%10;//个位
        if (b == 1) {
            auth.append("人脸，");
        }
        if (c == 1) {
            auth.append("app，");
        }
        if (d == 1) {
            auth.append("卡，");
        }
        return auth.toString();

    }

    public StepThreeInfo getInfoThree (Integer houseHoldId, String communityCode) {
        StepThreeInfo stepThreeInfo = new StepThreeInfo();
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", houseHoldId);
        List<HouseHold> houseHolds = houseHoldMapper.selectList(wrapper);
        HouseHold houseHold = houseHolds.get(0);
        if (houseHold.getAuthorizeStatus() == 0) {
            houseHold.setLabels("0");
        } else {
            String authString = getAuthString(houseHold.getAuthorizeStatus());
            houseHold.setLabels(authString);
        }

        stepThreeInfo.setHouseHold(houseHold);

        List<AccessCard> cardList = personLabelsMapper.selectAccessCardByHouseHoldId(houseHoldId);
        stepThreeInfo.setCardList(cardList);

        List<HouseHoldPhoto> photoList = personLabelsMapper.selectHouseHoldPhotoByHouseHoldId(houseHoldId);
        stepThreeInfo.setPhotoList(photoList);

        List<AuthorizeGroup> authorizeGroupList = new ArrayList<>();
        List<AuthorizeAppHouseholdDeviceGroup> authList = authorizeAppHouseholdDeviceGroupService.listByHouseholdId(houseHoldId);
        AuthorizeGroup authorizeGroup = null;
        if (!authList.isEmpty()) {
            for (AuthorizeAppHouseholdDeviceGroup authorizeHouseholdDeviceGroup : authList) {
                authorizeGroup = new AuthorizeGroup();
                com.mit.community.entity.entity.DeviceGroup deviceGroup = deviceGroupService.getById(authorizeHouseholdDeviceGroup.getDeviceGroupId());
                authorizeGroup.setDeviceGroupId(deviceGroup.getDeviceGroupId());
                authorizeGroup.setDeviceGroupName(deviceGroup.getDeviceGroupName());
                authorizeGroup.setGroupType(deviceGroup.getGroupType());
                authorizeGroup.setIsSelect(1);
                authorizeGroupList.add(authorizeGroup);
            }
        }

        List<Integer> deviceGroupsId = new ArrayList<>();
        if (!authList.isEmpty()) {
            deviceGroupsId = authList.parallelStream().map(AuthorizeAppHouseholdDeviceGroup::getDeviceGroupId).collect(Collectors.toList());
        }
        List<DeviceGroup> deviceGroups = deviceGroupService.getByCommunityCodeAndIds(communityCode, deviceGroupsId);
        if (!deviceGroups.isEmpty()) {
            for (DeviceGroup deviceGroup : deviceGroups){
                authorizeGroup = new AuthorizeGroup();
                authorizeGroup.setDeviceGroupId(deviceGroup.getDeviceGroupId());
                authorizeGroup.setDeviceGroupName(deviceGroup.getDeviceGroupName());
                authorizeGroup.setGroupType(deviceGroup.getGroupType());
                authorizeGroup.setIsSelect(2);
                authorizeGroupList.add(authorizeGroup);
            }
        }
        stepThreeInfo.setAuthList(authorizeGroupList);


        return stepThreeInfo;
    }
    /**
     * 查询住户，通过住户列表
     *
     * @param householdIdList 住户列表
     * @return java.util.List<com.mit.community.entity.HouseHold>
     * @author shuyy
     * @date 2018/11/30 11:15
     * @company mitesofor
     */
    public List<HouseHold> listByHouseholdIdList(List<Integer> householdIdList) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.in("household_id", householdIdList);
        return houseHoldMapper.selectList(wrapper);
    }

    /**
     * 查询住户列表，通过用户id
     *
     * @param userId 用户id
     * @return com.mit.community.entity.HouseHold
     * @author shuyy
     * @date 2018/12/7 10:54
     * @company mitesofor
     */
    public HouseHold getByUserId(Integer userId) {
        EntityWrapper<UserHousehold> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserHousehold> userHouseholds = userHouseholdMapper.selectList(wrapper);
        if (userHouseholds.isEmpty()) {
            return null;
        } else {
            List<Integer> householdIds = userHouseholds.parallelStream().map(UserHousehold::getHouseholdId).collect(Collectors.toList());
            return this.listByHouseholdIdList(householdIds).get(0);
        }
    }

    /**
     * 查询住户列表，通过手机号
     * @param cellphone
     * @return java.util.List<com.mit.community.entity.HouseHold>
     * @throws
     * @author shuyy
     * @date 2018/12/10 15:35
     * @company mitesofor
     */
    /**
     * 防止查询空值信息，暂时注掉（胡山林）
     *
     * @param cellphone
     * @return
     */
    //@Cache(key = "household:cellphone:{1}")
    public List<HouseHold> getByCellphone(String cellphone) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("mobile", cellphone);
        wrapper.eq("household_status", 1);//只查询状态“正常”的数据
        List<HouseHold> houseHolds = houseHoldMapper.selectList(wrapper);
        return houseHolds;
    }

    /**
     * 查询住户信息，通过手机号和小区code
     *
     * @param cellphone     手机号
     * @param communityCode 小区code
     * @return 住户信息
     * @author Mr.Deng
     * @date 14:15 2018/12/12
     */
    public HouseHold getByCellphoneAndCommunityCode(String cellphone, String communityCode) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("mobile", cellphone);
        wrapper.eq("community_code", communityCode);
        wrapper.eq("household_status", 1);//只查询状态“正常”的数据
        List<HouseHold> houseHolds = houseHoldMapper.selectList(wrapper);
        if (houseHolds.isEmpty()) {
            return null;
        }
        return houseHolds.get(0);
    }

    /**
     * 查询住户信息，通过手机号和小区code
     *
     * @param cellphone     手机号
     *
     * @return 住户信息
     * @author Mr.Deng
     * @date 14:15 2018/12/12
     */
    public HouseHold getByCellphoneAndCommunityCodes(String cellphone, String[] communityCodes) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("mobile", cellphone);
        wrapper.in("community_code", communityCodes);
        wrapper.eq("household_status", 1);//只查询状态“正常”的数据
        List<HouseHold> houseHolds = houseHoldMapper.selectList(wrapper);
        if (houseHolds.isEmpty()) {
            return null;
        }
        return houseHolds.get(0);
    }

    /**
     * 查找住户信息，通过住户id
     *
     * @param householdId 住户id
     * @return 住户信息
     * @author Mr.Deng
     * @date 16:06 2018/12/7
     */
    public HouseHold getByHouseholdId(Integer householdId) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("household_id", householdId);
        wrapper.eq("household_status", 1);
        List<HouseHold> houseHolds = houseHoldMapper.selectList(wrapper);
        if (houseHolds.isEmpty()) {
            return null;
        }
        return houseHolds.get(0);
    }

    /**
     * 更新数据
     *
     * @param houseHold 住户信息
     * @author Mr.Deng
     * @date 14:11 2018/12/13
     */
    @CacheClear(key = "household:cellphone:{1.mobile}")
    public void update(HouseHold houseHold) {
        houseHoldMapper.updateById(houseHold);
    }

    /**
     * 保存住户
     *
     * @param houseHold 住户对象
     * @author shuyy
     * @date 2019-01-24 10:51
     * @company mitesofor
     */
    @CacheClear(key = "household:cellphone:{1.mobile}")
    public void save(HouseHold houseHold) {
        houseHold.setGmtModified(LocalDateTime.now());
        houseHold.setGmtCreate(LocalDateTime.now());
        houseHoldMapper.insert(houseHold);
    }

    /**
     * 根据手机号和社区code查询本地钥匙信息
     *
     * @return
     */
    public HouseHold getHouseholdByPhoneAndCode(String cellphone, String communityCode) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("community_code", communityCode);
        wrapper.eq("mobile", cellphone);
        List<HouseHold> houseHolds = houseHoldMapper.selectList(wrapper);
        if (houseHolds.isEmpty()) {
            return null;
        }
        return houseHolds.get(0);
    }

    public HouseHold getHouseholdByPhoneAndName(String cellphone, String householdName) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        wrapper.eq("household_name", householdName);
        wrapper.eq("mobile", cellphone);
        List<HouseHold> houseHolds = houseHoldMapper.selectList(wrapper);
        if (houseHolds.isEmpty()) {
            return null;
        }
        return houseHolds.get(0);
    }

    /**
     * 分页查询住户信息数据
     *
     * @param request
     * @param zoneId
     * @param communityCode
     * @param buildingId
     * @param unitId
     * @param roomId
     * @param contactPerson
     * @param contactCellphone
     * @param status
     * @param search_validEndFlag
     * @param select_autyType
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<HouseHold> listHouseholdByPage(HttpServletRequest request,
                                               Integer zoneId,
                                               String communityCode,
                                               Integer buildingId,
                                               Integer unitId,
                                               Integer roomId,
                                               String contactPerson,
                                               String contactCellphone,
                                               Integer houseType,
                                               Integer status,
                                               Integer search_validEndFlag,//有效期标识：1-即将到期，2-已过期
                                               Integer select_autyType,//授权类型（二进制相加而来，查询时后台需要拆分）
                                               Integer pageNum, Integer pageSize) {
        EntityWrapper<HouseHold> wrapper = new EntityWrapper<>();
        if (zoneId != null) {
            wrapper.eq("b.zone_id", zoneId);
        }
        if (buildingId != null) {
            wrapper.eq("b.building_id", buildingId);
        }
        if (unitId != null) {
            wrapper.eq("b.unit_id", unitId);
        }
        if (roomId != null) {
            wrapper.eq("b.room_id", roomId);
        }
        if (StringUtils.isNotBlank(communityCode)) {
            wrapper.eq("a.community_code", communityCode);
        }
        if (StringUtils.isNotBlank(contactPerson)) {
            wrapper.like("a.household_name", contactPerson);
        }
        if (StringUtils.isNotBlank(contactCellphone)) {
            wrapper.like("a.mobile", contactCellphone);
        }
        //户主关系
        if (houseType != null) {
            wrapper.eq("a.housetype", houseType);
        }
        //住户状态
        if (status != null) {
            if (status == 2) {//停用
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                wrapper.lt("a.validity_time", LocalDate.parse(sdf.format(new Date()), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } else {
                wrapper.eq("a.household_status", status);
            }
        } else {//默认不查询注销数据
            wrapper.in("a.household_status", new Integer[]{1, 2});
        }
        //有效期限查询字段处理
        if (search_validEndFlag != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (search_validEndFlag == 1) {//期限时间小于30天，即将过期
                    //查询residenceTime(LocalDate)字段
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, 1);
                    Date startDate = cal.getTime();
                    cal.add(Calendar.DATE, 30);
                    Date endDate = cal.getTime();
                    wrapper.ge("a.validity_time", LocalDate.parse(sdf.format(startDate), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    wrapper.le("a.validity_time", LocalDate.parse(sdf.format(endDate), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
                if (search_validEndFlag == 2) {//已过期
                    wrapper.lt("a.validity_time", LocalDate.parse(sdf.format(new Date()), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //授权类型字段处理
        if (select_autyType != null) {
            wrapper.eq("a.authorize_status", Biannary2Decimal(select_autyType));
        }
        wrapper.orderBy("a.gmt_create", false);
        List<HouseHold> houseHolds = new ArrayList<>();
        if (pageNum != null && pageSize != null) {
            Page<HouseHold> page = new Page<>(pageNum, pageSize);
//            houseHolds = houseHoldMapper.selectPage(page, wrapper);
            houseHolds = personLabelsMapper.selectHouseHoldPage(page, wrapper);
            for (HouseHold houseHold : houseHolds) {
                //读取用户表的基本信息
                User user = userService.getByCellphoneNoCache(houseHold.getMobile());
                if (user != null) {
                    houseHold.setGender(user.getGender().intValue());
                    houseHold.setHouseholdName(user.getName() == null ? houseHold.getHouseholdName() : user.getName());
                    houseHold.setCredentialNum(user.getIdCardNumber() == null ? houseHold.getCredentialNum() : user.getIdCardNumber());
                }
                //权限到期天数计算diffDay,前端需要用来做样式判断
                if (houseHold.getValidityTime() != null) {
                    houseHold.setDiffDay(DateUtils.getDateInter(new Date(), houseHold.getValidityTime()));
                } else {
                    LocalDate residenceTime = houseHold.getResidenceTime();
                    LocalDate currentTime = LocalDate.now();
                    houseHold.setDiffDay(DateUtils.getLocalDateInter(currentTime, residenceTime));
                }
                //查询房屋信息
                List<HouseholdRoom> rooms = householdRoomService.listByHouseholdId(houseHold.getHouseholdId());
                if (rooms.size() != 0) {
                    //与户主关系字段赋值
                    StringBuffer houseTypeInfo = new StringBuffer();
                    for (int a=0; a<rooms.size(); a++) {
                        HouseholdRoom room = rooms.get(a);//默认取最早注册的房屋信息
                        Integer householdType = Integer.valueOf(room.getHouseholdType());
                        switch (householdType) {
                            case 1:
                                houseHold.setHouseholdType("本人");
                                houseTypeInfo.append("本人 ");
                                break;
                            case 2:
                                houseTypeInfo.append("配偶 ");
                                break;
                            case 3:
                                houseTypeInfo.append("父母 ");
                                break;
                            case 4:
                                houseTypeInfo.append("子女 ");
                                break;
                            case 5:
                                houseTypeInfo.append("亲属 ");
                                break;
                            case 6:
                                houseTypeInfo.append("非亲属 ");
                                break;
                            case 7:
                                houseTypeInfo.append("租赁 ");
                                break;
                            case 8:
                                houseTypeInfo.append("其他 ");
                                break;
                            case 9:
                                houseTypeInfo.append("保姆 ");
                                break;
                            case 10:
                                houseTypeInfo.append("护理人员 ");
                                break;
                            default:
                                houseTypeInfo.append("");
                                break;
                        }
                    }
                    houseHold.setHouseholdType(houseTypeInfo.toString());
                    //房屋信息处理
                    StringBuffer roomInfo = new StringBuffer();
                    for (int i = 0; i < rooms.size(); i++) {
                        if (i == rooms.size() - 1) {
                            roomInfo.append(rooms.get(i).getZoneName() + "-" + rooms.get(i).getBuildingName() + "-" + rooms.get(i).getUnitName() + "-" + rooms.get(i).getRoomNum());
                        } else {
                            roomInfo.append(rooms.get(i).getZoneName() + "-" + rooms.get(i).getBuildingName() + "-" + rooms.get(i).getUnitName() + "-" + rooms.get(i).getRoomNum() + ",");
                        }
                    }
                    houseHold.setHousing(roomInfo.toString());
                }
            }
            page.setRecords(houseHolds);
            return page;
        }
        return null;
    }

    /**
     * 将二进制转换为10进制
     * @param bi ：待转换的二进制
     * @return
     */
    public  static  Integer Biannary2Decimal(int bi){
        String binStr = bi+"";
        Integer sum = 0;
        int len = binStr.length();
        for (int i=1;i<=len;i++){
            //第i位 的数字为：
            int dt = Integer.parseInt(binStr.substring(i-1,i));
            sum+=(int)Math.pow(2,len-i)*dt;
        }
        return  sum;
    }

    /**
     * 保存住户房屋信息
     *
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer SaveHouseholdInfoByStepOne(String communityCode, PostHouseHoldInfoOne postHouseHoldInfoOne, List<HouseRoomsVo> list) {
        Integer msg = null;
        //参数获取

        String householdName = postHouseHoldInfoOne.getHouseHoldName();//住户姓名
        String mobile = postHouseHoldInfoOne.getMobile();//手机号码
        String idCard = postHouseHoldInfoOne.getIdCard();//证件号码
        PersonBaseInfo personBaseInfo = personBaseInfoService.getPersonByMobile(mobile, communityCode);
        if (personBaseInfo == null) {
            personBaseInfo = new PersonBaseInfo();
            personBaseInfo.setName(householdName);
            personBaseInfo.setCellphone(mobile);
            if(StringUtils.isNotBlank(idCard)){
                personBaseInfo.setIdCardNum(idCard);
            }
            personBaseInfo.setCommunity_code(communityCode);
            personBaseInfo.setGmtCreate(LocalDateTime.now());
            personBaseInfo.setGmtModified(LocalDateTime.now());
            personBaseInfoService.save(personBaseInfo);
        }
        try {
            /**
             * 判断是新增还是修改
             */
            HouseHold existHouseHold = this.getHouseholdByPhoneAndName(mobile, householdName);
            if (existHouseHold == null) {//新增
                Integer householdId = personLabelsService.getMaxHouseHoldId();
                HouseHold houseHold = null;
                // 本地数据库保存住户信息
                Integer gender = postHouseHoldInfoOne.getGender();
                String residenceTimeStr = postHouseHoldInfoOne.getResidenceTime().replace("/","-");
                if (StringUtils.isNotBlank(idCard)) {
                    IdCardInfo idCardInfo = idCardInfoExtractorUtil.idCardInfo(idCard);
                    String constellation = ConstellationUtil.calc(idCardInfo.getBirthday());
                    houseHold = new HouseHold(communityCode, constellation, householdId, householdName,
                            1, 0,
                            gender, com.mit.common.util.DateUtils.parseStringToLocalDate(residenceTimeStr, "yyyy-MM-dd"),
                            mobile, StringUtils.EMPTY,
                            StringUtils.EMPTY, idCard, idCardInfo.getProvince(),
                            idCardInfo.getCity(), idCardInfo.getRegion(), idCardInfo.getBirthday(),
                            (short) 99, null, null, null, null, null, null, null,
                            Integer.valueOf(list.get(0).getHouseholdType()),null,null,postHouseHoldInfoOne.getMobileBelong(), null,0);
                } else {
                    houseHold = new HouseHold(communityCode, StringUtils.EMPTY, householdId, householdName,
                            1, 0,
                            gender, com.mit.common.util.DateUtils.parseStringToLocalDate(residenceTimeStr, "yyyy-MM-dd"),
                            mobile, StringUtils.EMPTY,
                            StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                            StringUtils.EMPTY, StringUtils.EMPTY, com.mit.common.util.DateUtils.parseStringToLocalDate("1900-01-01", "yyyy-MM-dd"),
                            (short) 99, null, null, null, null, null, null, null,
                            Integer.valueOf(list.get(0).getHouseholdType()),null,null,postHouseHoldInfoOne.getMobileBelong(),null,0);
                }
                houseHold.setGmtModified(LocalDateTime.now());
                houseHold.setGmtCreate(LocalDateTime.now());
                houseHoldMapper.insert(houseHold);
                // 本地关联房屋
                ClusterCommunity clusterCommunity = clusterCommunityService.getByCommunityCode(communityCode);
                for (HouseRoomsVo room : list) {
                    HouseholdRoom householdRoom = new HouseholdRoom(communityCode,
                            clusterCommunity.getCommunityName(),
                            Integer.valueOf(room.getZoneId()),
                            room.getZoneName(),
                            Integer.valueOf(room.getBuildingId()),
                            room.getBuildingName(),
                            Integer.valueOf(room.getUnitId()),
                            room.getUnitName(),
                            Integer.valueOf(room.getRoomId()),
                            room.getRoomNum(),
                            Short.valueOf(room.getHouseholdType()),
                            householdId,
                            null);
                    householdRoomService.save(householdRoom);
                }
                //更新本地用户住户信息id
                User user = userService.getByCellphone(mobile);
                if (user != null) {
                    userService.updateCellphoneByHouseholdId(mobile, householdId);
                }
                msg = householdId;
            } else {//修改
                //根据住户id修改住户信息
                HouseHold edidHousehold = null;
                Integer gender = postHouseHoldInfoOne.getGender();
                String residenceTimeStr = postHouseHoldInfoOne.getResidenceTime();
                if (StringUtils.isNotBlank(idCard)) {
                    IdCardInfo idCardInfo = idCardInfoExtractorUtil.idCardInfo(idCard);
                    String constellation = ConstellationUtil.calc(idCardInfo.getBirthday());
                    edidHousehold = new HouseHold(communityCode, constellation, existHouseHold.getHouseholdId(), householdName,
                            1, 0,
                            gender, com.mit.common.util.DateUtils.parseStringToLocalDate(residenceTimeStr, "yyyy-MM-dd"),
                            mobile, StringUtils.EMPTY,
                            StringUtils.EMPTY, idCard, idCardInfo.getProvince(),
                            idCardInfo.getCity(), idCardInfo.getRegion(), idCardInfo.getBirthday(),
                            (short) 99, null, null, null, null, null, null, null,
                            Integer.valueOf(list.get(0).getHouseholdType()),null,null,postHouseHoldInfoOne.getMobileBelong(), existHouseHold.getCallMobile(),0);
                } else {
                    edidHousehold = new HouseHold(communityCode, StringUtils.EMPTY, existHouseHold.getHouseholdId(), householdName,
                            1, 0,
                            gender, com.mit.common.util.DateUtils.parseStringToLocalDate(residenceTimeStr, "yyyy-MM-dd"),
                            mobile, StringUtils.EMPTY,
                            StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                            StringUtils.EMPTY, StringUtils.EMPTY, com.mit.common.util.DateUtils.parseStringToLocalDate("1900-01-01", "yyyy-MM-dd"),
                            (short) 99, null, null, null, null, null, null, null,
                            Integer.valueOf(list.get(0).getHouseholdType()),null,null,postHouseHoldInfoOne.getMobileBelong(), existHouseHold.getCallMobile(),0);
                }
                edidHousehold.setGmtModified(LocalDateTime.now());
                houseHoldMapper.updateHouseholdByHouseholdId(edidHousehold);
                //删除房屋信息
                householdRoomService.deleteByHouseholdId(existHouseHold.getHouseholdId());
                // 本地关联房屋
                ClusterCommunity clusterCommunity = clusterCommunityService.getByCommunityCode(communityCode);
                for (HouseRoomsVo room : list) {
                    HouseholdRoom householdRoom = new HouseholdRoom(communityCode,
                            clusterCommunity.getCommunityName(),
                            Integer.valueOf(room.getZoneId()),
                            room.getZoneName(),
                            Integer.valueOf(room.getBuildingId()),
                            room.getBuildingName(),
                            Integer.valueOf(room.getUnitId()),
                            room.getUnitName(),
                            Integer.valueOf(room.getRoomId()),
                            room.getRoomNum(),
                            Short.valueOf(room.getHouseholdType()),
                            existHouseHold.getHouseholdId(),
                            null);
                    householdRoomService.save(householdRoom);
                }
                //同步修改用户手机号码
                userMapper.updateMobileByHouseholdId(mobile, existHouseHold.getHouseholdId());
                msg = existHouseHold.getHouseholdId();
            }
        } catch (Exception e) {
            msg = -1;
            e.printStackTrace();
            throw new RuntimeException("-1");
        }
        return msg;
    }

    /**
     * 保存住户授权信息
     *
     * @param directCall
     * @param tellNum
     * @param editFlag        //是否编辑标识
     * @param householdId
     * @param appAuthFlag
     * @param faceAuthFlag
     * @param deviceGIds
     * @param validityEndDate
     * @param cardListArr
     * @param phone
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String SaveHouseholdInfoByStepThree(Integer editFlag, Integer householdId, Integer appAuthFlag, Integer faceAuthFlag, String deviceGIds, String validityEndDate, String cardListArr, String imageUrls, String phone) {
        String msg = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //更新本地住户授权类型字段+本地更新住户有效期权限时间
            Integer authStatus = 0;
            if (StringUtils.isNotBlank(cardListArr)) {
                authStatus = AuthorizeStatusUtil.GetAuthStatus(appAuthFlag, faceAuthFlag, 1);
            } else {
                authStatus = AuthorizeStatusUtil.GetAuthStatus(appAuthFlag, faceAuthFlag, null);
            }
            houseHoldMapper.updateValidityTime(simpleDateFormat.parse(validityEndDate), authStatus, householdId);
            if (StringUtils.isNotBlank(phone)) {
                HouseHold houseHold = new HouseHold();
                houseHold.setHouseholdId(householdId);
                houseHold.setCallMobile(phone);
                houseHoldMapper.updateHouseholdByHouseholdId(houseHold);
            }
            HouseHold existHouseHold = this.getByHouseholdId(householdId);
            if (existHouseHold.getAuthorizeStatus() == 0) {//新增
                // 本地数据库保存关联设备组
                String[] deviceGroupIds = deviceGIds.split(",");
                List<String> deviceGroupIdList = Arrays.asList(deviceGroupIds);
                List<AuthorizeAppHouseholdDeviceGroup> authorizeAppHouseholdDeviceGroups = Lists.newArrayListWithCapacity(deviceGroupIdList.size());
                deviceGroupIdList.forEach(item -> {
                    AuthorizeAppHouseholdDeviceGroup authorizeAppHouseholdDeviceGroup = new AuthorizeAppHouseholdDeviceGroup(householdId, Integer.parseInt(item));
                    List<AuthorizeAppHouseholdDeviceGroup> groups = authorizeAppHouseholdDeviceGroupMapper.getObjectByIds(householdId, Integer.parseInt(item));
                    if (groups.size() == 0) {
                        authorizeAppHouseholdDeviceGroup.setGmtCreate(LocalDateTime.now());
                        authorizeAppHouseholdDeviceGroup.setGmtModified(LocalDateTime.now());
                        authorizeAppHouseholdDeviceGroups.add(authorizeAppHouseholdDeviceGroup);
                    }
                });
                if (authorizeAppHouseholdDeviceGroups.size() != 0) {
                    authorizeAppHouseholdDeviceGroupService.insertBatch(authorizeAppHouseholdDeviceGroups);
                }
                //关联本地APP已经授权的设备组，即生成本地钥匙列表，同时注册默认账号
                if (appAuthFlag == 1) {
                    List<AuthorizeHouseholdDeviceGroup> groupsList = Lists.newArrayListWithCapacity(deviceGroupIdList.size());
                    deviceGroupIdList.forEach(item -> {
                        AuthorizeHouseholdDeviceGroup authorizeAppHouseholdDeviceGroup = new AuthorizeHouseholdDeviceGroup(householdId, Integer.parseInt(item));
                        List<AuthorizeHouseholdDeviceGroup> groups = authorizeHouseholdDeviceGroupMapper.getObjectByIds(householdId, Integer.parseInt(item));
                        if (groups.size() == 0) {
                            authorizeAppHouseholdDeviceGroup.setGmtCreate(LocalDateTime.now());
                            authorizeAppHouseholdDeviceGroup.setGmtModified(LocalDateTime.now());
                            groupsList.add(authorizeAppHouseholdDeviceGroup);
                        }
                    });
                    if (groupsList.size() != 0) {
                        authorizeHouseholdDeviceGroupService.insertBatch(groupsList);
                    }
                }
            } else {//修改
                authorizeAppHouseholdDeviceGroupService.deleteByHouseholdId(householdId);
                authorizeHouseholdDeviceGroupService.deleteByHouseholdId(householdId);
                // 本地数据库保存关联设备组
                String[] deviceGroupIds = deviceGIds.split(",");
                List<String> deviceGroupIdList = Arrays.asList(deviceGroupIds);
                List<AuthorizeAppHouseholdDeviceGroup> authorizeAppHouseholdDeviceGroups = Lists.newArrayListWithCapacity(deviceGroupIdList.size());
                deviceGroupIdList.forEach(item -> {
                    AuthorizeAppHouseholdDeviceGroup authorizeAppHouseholdDeviceGroup = new AuthorizeAppHouseholdDeviceGroup(householdId, Integer.parseInt(item));
                    List<AuthorizeAppHouseholdDeviceGroup> groups = authorizeAppHouseholdDeviceGroupMapper.getObjectByIds(householdId, Integer.parseInt(item));
                    if (groups.size() == 0) {
                        authorizeAppHouseholdDeviceGroup.setGmtCreate(LocalDateTime.now());
                        authorizeAppHouseholdDeviceGroup.setGmtModified(LocalDateTime.now());
                        authorizeAppHouseholdDeviceGroups.add(authorizeAppHouseholdDeviceGroup);
                    }
                });
                if (authorizeAppHouseholdDeviceGroups.size() != 0) {
                    authorizeAppHouseholdDeviceGroupService.insertBatch(authorizeAppHouseholdDeviceGroups);
                }
                //关联本地APP已经授权的设备组，即生成本地钥匙列表
                if (appAuthFlag == 1) {
                    List<AuthorizeHouseholdDeviceGroup> groupsList = Lists.newArrayListWithCapacity(deviceGroupIdList.size());
                    deviceGroupIdList.forEach(item -> {
                        AuthorizeHouseholdDeviceGroup authorizeAppHouseholdDeviceGroup = new AuthorizeHouseholdDeviceGroup(householdId, Integer.parseInt(item));
                        List<AuthorizeHouseholdDeviceGroup> groups = authorizeHouseholdDeviceGroupMapper.getObjectByIds(householdId, Integer.parseInt(item));
                        if (groups.size() == 0) {
                            authorizeAppHouseholdDeviceGroup.setGmtCreate(LocalDateTime.now());
                            authorizeAppHouseholdDeviceGroup.setGmtModified(LocalDateTime.now());
                            groupsList.add(authorizeAppHouseholdDeviceGroup);
                        }
                    });
                    if (groupsList.size() != 0) {
                        authorizeHouseholdDeviceGroupService.insertBatch(groupsList);
                    }
                }
            }
            if (appAuthFlag == 1) {
                //注册默认账号
                //第一步：判断是否注册

                User user = userService.getByCellphoneNoCache(existHouseHold.getMobile());
                //第二步：没有进行默认注册
                if (user == null) {
                    user = new User(existHouseHold.getMobile(), "123456", householdId, existHouseHold.getHouseholdName(), existHouseHold.getGender().shortValue(), StringUtils.EMPTY, Constants.USER_ICO_DEFULT,
                            Constants.NULL_LOCAL_DATE, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                            "普通业主", StringUtils.EMPTY, null, null, null
                            , null, null, null, null, null, null, null, null, null,
                            existHouseHold.getHouseholdName(), existHouseHold.getCredentialNum(), existHouseHold.getProvince(),
                            existHouseHold.getCity(), existHouseHold.getRegion(), existHouseHold.getConstellation());
                    userService.save(user);
                } else {//非空修改
                    user.setName(existHouseHold.getHouseholdName());
                    user.setIdCardNumber(existHouseHold.getCredentialNum());
                    user.setProvince(existHouseHold.getProvince());
                    user.setCity(existHouseHold.getCity());
                    user.setDistrict(existHouseHold.getRegion());
                    user.setConstellation(existHouseHold.getConstellation());
                    user.setGender(existHouseHold.getGender().shortValue());
                    //更新时间
                    user.setGmtModified(LocalDateTime.now());
                    userMapper.updateById(user);
                }
            }
            msg = "success";
            if (StringUtils.isNotBlank(cardListArr) || StringUtils.isNotBlank(imageUrls)) {
                if (StringUtils.isNotBlank(deviceGIds)){
                    String[] deviceGroupIds = deviceGIds.split(",");
                    for (String deviceGroupId : deviceGroupIds){
                        List<DeviceDeviceGroup> deviceDeviceGroupsList = deviceDeviceGroupService.getGroupsByDeviceGroupId(Integer.parseInt(deviceGroupId));
                        if (StringUtils.isNotBlank(cardListArr)) {
                            String[] cardsNum = cardListArr.split(",");
                            for (int a=0; a<cardsNum.length; a++) {
                                if (!deviceDeviceGroupsList.isEmpty()) {
                                    AccessCard AccessCard = null;
                                    for (int i=0; i<deviceDeviceGroupsList.size(); i++) {
                                        AccessCard accessCard = accessCardService.getByHouseHoldIdAndDeviceNumAndCardNum(householdId, deviceDeviceGroupsList.get(i).getDeviceNum(), cardsNum[a]);
                                        if (accessCard == null) {
                                            DeviceIsOnline deviceIsOnline = personLabelsService.getIsOnline(deviceDeviceGroupsList.get(i).getDeviceNum());
                                            if (Integer.parseInt(deviceIsOnline.getTimeDiffi()) <= 10) {//设备在线
                                                if (sendCardToDevice(deviceIsOnline.getIp(),cardsNum[a])) {//下发成功
                                                    accessCardService.save(cardsNum[a],householdId,deviceDeviceGroupsList.get(i).getDeviceNum(),Integer.parseInt(deviceGroupId),2);
                                                } else{//下发不成功
                                                    accessCardService.save(cardsNum[a],householdId,deviceDeviceGroupsList.get(i).getDeviceNum(),Integer.parseInt(deviceGroupId),1);
                                                }
                                            } else {
                                                accessCardService.save(cardsNum[a],householdId,deviceDeviceGroupsList.get(i).getDeviceNum(),Integer.parseInt(deviceGroupId),1);
                                            }
                                        }
                                    }
                                } else { //所选设备组没有绑定设备
                                    accessCardService.save(cardsNum[a],householdId,null,Integer.parseInt(deviceGroupId),1);
                                }
                            }
                        }

                        if (StringUtils.isNotBlank(imageUrls)) {
                            String[] imageUrl = imageUrls.split(",");
                            for (String photoUrlNet : imageUrl) {
                                HouseHoldPhoto houseHoldPhoto = houseHoldPhotoService.getByPhotoUrlNet(photoUrlNet);
                                if (!deviceDeviceGroupsList.isEmpty()) {
                                    for (int i=0; i<deviceDeviceGroupsList.size(); i++) {
                                        HouseHoldPhoto houseHoldPhotoExits = houseHoldPhotoService.getByHouseHoldIdAndDeviceNumAndPhotoUrl(householdId, deviceDeviceGroupsList.get(i).getDeviceNum(), photoUrlNet);
                                        if (houseHoldPhotoExits == null) {
                                            DeviceIsOnline deviceIsOnline = personLabelsService.getIsOnline(deviceDeviceGroupsList.get(i).getDeviceNum());
                                            if (Integer.parseInt(deviceIsOnline.getTimeDiffi()) <= 10) {//设备在线
                                                if (sendFeaToDevice(deviceIsOnline.getIp(),houseHoldPhoto.getFeaUrl(), householdId)) {//下发成功
                                                    houseHoldPhotoService.save(householdId, houseHoldPhoto.getPhotoUrlNet(), houseHoldPhoto.getPhotoUrl(), houseHoldPhoto.getFeaUrl(),Integer.parseInt(deviceGroupId), 2, deviceDeviceGroupsList.get(i).getDeviceNum());
                                                } else{//下发不成功
                                                    houseHoldPhotoService.save(householdId, houseHoldPhoto.getPhotoUrlNet(), houseHoldPhoto.getPhotoUrl(), houseHoldPhoto.getFeaUrl(),Integer.parseInt(deviceGroupId), 1, deviceDeviceGroupsList.get(i).getDeviceNum());
                                                }
                                            } else {//设备不在线
                                                houseHoldPhotoService.save(householdId, houseHoldPhoto.getPhotoUrlNet(), houseHoldPhoto.getPhotoUrl(), houseHoldPhoto.getFeaUrl(),Integer.parseInt(deviceGroupId), 1, deviceDeviceGroupsList.get(i).getDeviceNum());
                                            }
                                        }
                                    }
                                } else {//权限组没有绑定设备
                                    houseHoldPhotoService.save(householdId, houseHoldPhoto.getPhotoUrlNet(), houseHoldPhoto.getPhotoUrl(), houseHoldPhoto.getFeaUrl(),Integer.parseInt(deviceGroupId), 1, null);
                                }
                            }
                        }
                    }
                } else { //没有选择权限组，直接保存就行
                    if (StringUtils.isNotBlank(cardListArr)) {//保存门禁卡
                        String[] cardsNum = cardListArr.split(",");
                        for (int a=0; a<cardsNum.length; a++) {
                            AccessCard accessCard = accessCardService.getByHouseHoldIdAndDeviceNumAndCardNum(householdId, null,cardsNum[a]);
                            if (accessCard == null) {
                                accessCardService.save(cardsNum[a],householdId,null,null,1);
                            }
                        }
                    }

                    /*if (StringUtils.isNotBlank(imageUrls)) {
                        String[] images = imageUrls.split(",");
                        for (int i=0; i< images.length; i++) {
                            houseHoldPhotoService.updateUploadByPhotoUrlNet(householdId, images[i]);
                        }
                    }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage().toString());
        }
        return msg;
    }

    public String saveHouseHoldPhoto(MultipartFile image, Integer houseHoldId) {
        String message = null;
        HouseHoldPhoto houseHoldPhoto = null;
        try {
            houseHoldPhoto = new HouseHoldPhoto();
            String uuid = UUID.randomUUID().toString();
            String fileHz =  uuid + ".jpg";
            String basePath = "f:\\face";
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            byte[] b = image.getBytes();
            String photoUrl = basePath + "\\" +fileHz;//本地保存地址
            File aa = new File(photoUrl);
            FileImageOutputStream fos = new FileImageOutputStream(aa);
            fos.write(b, 0, b.length);
            fos.close();

            String photoUrlNet = UploadUtil.uploadWithByte(b);//图片网络保存地址
            //String photoUrlNet = "aaaaaaaaaaa";
            boolean flag = faceAnalyse("f:", basePath, fileHz, basePath + "\\out" +fileHz, uuid + ".fea");
            if (flag == true) {
                houseHoldPhoto.setIsUpload(1);
                if (houseHoldId != null) {
                    houseHoldPhoto.setHouseHoldId(houseHoldId);
                }

                houseHoldPhoto.setPhotoUrlNet(photoUrlNet);
                houseHoldPhoto.setPhotoUrl(photoUrl);//照片本地保存路径
                houseHoldPhoto.setFeaUrl(basePath + "\\" + uuid + ".fea");
                houseHoldPhoto.setGmtCreate(LocalDateTime.now());
                houseHoldPhoto.setGmtModified(LocalDateTime.now());
                houseHoldPhotoService.save(houseHoldPhoto);
                message = photoUrlNet;
                return message;
            } else {
                message = "提取人脸特征值失败";
            }
        } catch (Exception e) {
            message = "提取人脸特征值失败";
        } finally {
            return message;
        }
    }

    public List<HouseHoldPhoto> saveImageAndAnalyse(MultipartFile[] images) {
        List<HouseHoldPhoto> list = new ArrayList<>();
        HouseHoldPhoto houseHoldPhoto = null;
        try{
            for (int i=0; i<images.length; i++) {
                houseHoldPhoto = new HouseHoldPhoto();
                String uuid = UUID.randomUUID().toString();
                String fileHz =  uuid + ".jpg";
                String basePath = "f:\\face";
                File file = new File(basePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                byte[] b = images[i].getBytes();
                String photoUrl = basePath + "\\" +fileHz;//本地保存地址
                File aa = new File(photoUrl);
                FileImageOutputStream fos = new FileImageOutputStream(aa);
                fos.write(b, 0, b.length);
                fos.close();

                String photoUrlNet = UploadUtil.uploadWithByte(b);//图片网络保存地址
                boolean flag = faceAnalyse("f:", basePath, fileHz, basePath + "\\out" +fileHz, uuid + ".fea");
                if (flag == true) {
                    houseHoldPhoto.setPhotoUrl(photoUrl);
                    houseHoldPhoto.setPhotoUrlNet(photoUrlNet);
                    houseHoldPhoto.setFeaUrl(basePath + "\\" + uuid + ".fea");
                    list.add(houseHoldPhoto);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean sendFeaToDevice(String ip, String feaPath, Integer houseHoldId){
        boolean flag = false;
        BASE64Encoder encoder = new BASE64Encoder();
        Map<String,String> params = new HashedMap();
        params.put("cmd","faceRegister");
        params.put("houseHoldId",String.valueOf(houseHoldId));
        try{
            File file = new File(feaPath);
            String feaBase64 = encoder.encode(Files.readAllBytes(file.toPath()));
            params.put("fea",feaBase64);
            String result = sendPost1("http://" + ip + ":28085",params);
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
            if (json.containsKey("message")) {
                flag = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    public static boolean sendCardToDevice(String ip, String cardNum){
        boolean flag = false;
        Map<String,String> params = new HashedMap();
        params.put("cmd","cardAdd");
        params.put("cardNum",cardNum);
        try{
            String result = sendPost1("http://" + ip + ":28085",params);
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(result);
            if (json.containsKey("message")) {
                flag = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    public static boolean faceAnalyse(String disk, String faceExePath, String inputPhotoName, String outputPhotoName, String feaFileName) {
        boolean flag = false;
        String cmdCommand = "cmd /c " + disk + " && cd " + faceExePath + " && face -i " + inputPhotoName + " -o " + outputPhotoName + " -f " + feaFileName +" -v model";
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while((line=bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line+"\n");
                System.out.println(line);
            }
            File file = new File(faceExePath + "\\" + feaFileName);
            if (file.exists()) {
                flag = true;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        }
    }




    /**
     * 注销住户信息
     * 清空用户信息缓存
     *
     * @param communityCode
     * @param ids
     * @return
     */
    @CacheClear(key = "user:cellphone{1}")
    @Transactional(rollbackFor = Exception.class)
    public String logOut(String communityCode, String ids) {
        String msg = "success";
        if (!StringUtils.isEmpty(ids)) {
            String[] id = ids.split(",");
            for (String s : id) {
                /**
                 * 调用狄耐克接口注销
                 */
                /*JSONObject message = dnakeAppApiService.logOut(Integer.valueOf(s), communityCode);
                if (message.get("errorCode") != null && !message.get("errorCode").equals(0)) {
                    msg = message.get("msg").toString();
                    throw new RuntimeException(message.get("msg").toString());
                }*/
                /**
                 * 本地注销
                 */
                //注销住户
                HouseHold houseHold = this.getByHouseholdId(Integer.valueOf(s));
                houseHold.setGmtModified(LocalDateTime.now());
                houseHold.setHouseholdStatus(0);//注销状态
                this.update(houseHold);
                //注销用户（重置用户的住户id）
                userMapper.updateByHouseholdId(Integer.valueOf(s));
            }
        }
        return msg;
    }

    /**
     * 停用住户
     *
     * @param communityCode
     * @param id
     * @return
     */
    public String Stop(String communityCode, Integer id) {
        String msg = "";
        if (id != null) {
            try {
                Calendar calendar = Calendar.getInstance();
                Date date = new Date();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                date = calendar.getTime();
                HouseHold houseHold = new HouseHold();
                houseHold.setGmtModified(LocalDateTime.now());
                houseHold.setHouseholdId(id);
                houseHold.setValidityTime(date);
                houseHold.setHouseholdStatus(2);//停用状态
                houseHoldMapper.updateHouseholdByHouseholdId(houseHold);
                msg = "success";
            } catch (Exception e) {
                msg = "fail";
            }
        }
        return msg;
    }

    /**
     * 获取狄耐克设备组列表
     *
     * @param communityCode
     * @return
     */
    public Result getDeviceGroupList(String communityCode) {
        List<DeviceGroupVo> list = new ArrayList<>();
        if (!StringUtils.isEmpty(communityCode)) {
            JSONObject message = dnakeAppApiService.getDeviceGroupList(communityCode);
            if (message.get("errorCode") != null && !message.get("errorCode").equals(0)) {
                throw new RuntimeException(message.get("msg").toString());
            }
            //获取需要的数据进行封装
            String data = message.toJSONString();
            JSONObject json = JSON.parseObject(data);
            String deviceGroupList = message.getString("deviceGroupList");
            JSONArray roomsArray = JSONArray.parseArray(deviceGroupList);
            for (int i = 0; i < roomsArray.size(); i++) {
                DeviceGroupVo deviceGroupVo = new DeviceGroupVo();
                //设备组类型
                String groupType = JSONObject.parseObject(roomsArray.get(i).toString()).getString("groupType");
                //设备组id
                String deviceGroupId = JSONObject.parseObject(roomsArray.get(i).toString()).getString("deviceGroupId");
                //设备组名称
                String deviceGroupName = JSONObject.parseObject(roomsArray.get(i).toString()).getString("deviceGroupName");
                deviceGroupVo.setGroupType(groupType);
                deviceGroupVo.setDeviceGroupId(deviceGroupId);
                deviceGroupVo.setDeviceGroupName(deviceGroupName);
                list.add(deviceGroupVo);
            }
        }
        Result result = new Result();
        result.setResultStatus(true);
        result.setObject(list);
        result.setMessage("success");
        return result;
    }

    /**
     * 平台修改手机号码
     *
     * @param mobile
     * @param householdId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String AlterMobile(String mobile, Integer householdId) {
        String msg = "";
        try {
            //修改住户信息手机号码
            String setSql = "mobile = " + " ' " + mobile + " ' ";
            EntityWrapper<HouseHold> ew = new EntityWrapper<>();
            ew.eq("mobile", householdId);
            houseHoldMapper.updateForSet(setSql, ew);
            //修改用户信息手机号码
            String setSql1 = "cellphone = " + " ' " + mobile + " ' ";
            EntityWrapper<User> ew1 = new EntityWrapper<>();
            ew1.eq("cellphone", householdId);
            userMapper.updateForSet(setSql1, ew1);
            msg = "success";
        } catch (Exception ex) {
            msg = "fail:" + ex.getMessage().toString();
        }
        return msg;
    }


    /**
     * 发送POST请求
     *
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost1(String url, Map<String,String> parameters) throws Exception{
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            parameters.get(name));
                }
                params = sb.toString();
            } else if(parameters.size() > 1){
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            parameters.get(name)).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            //params = URLEncoder.encode(params, "utf-8");
            System.out.println("发送的额参数="+params);
            // 创建URL对象
            //System.out.println("=====================发送上传参数请求请求");
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    public Page<HouseHold> getInfoList(String householdName,
                                       String mobile, Integer zoneId, Integer buildingId,
                                       Integer unitId, String roomNum, Short householdType,
                                       Integer householdStatus, Date validityTime,
                                       Integer authorizeStatus, Integer pageNum, Integer pageSize) {
        Page<HouseHold> page=new Page<>(pageNum,pageSize);
        EntityWrapper<HouseHold> wrapper=new EntityWrapper<>();
        if (StringUtils.isNotEmpty(householdName)){
            wrapper.eq("household_name",householdName);
        }
        if (StringUtils.isNotEmpty(mobile))
        {
            wrapper.eq("mobile",mobile);
        }
        if (validityTime!=null){
            wrapper.eq("validity_time",validityTime);
        }
        if (authorizeStatus != null) {
            wrapper.eq("authorize_status",authorizeStatus);
        }
        if (householdStatus != null) {
            wrapper.eq("household_status",householdStatus);
        }
       List<HouseHold> houseHoldList=houseHoldMapper.getInfoList(page,wrapper,zoneId,buildingId,unitId,roomNum,householdType);
        page.setRecords(houseHoldList);
        return page;
    }

}

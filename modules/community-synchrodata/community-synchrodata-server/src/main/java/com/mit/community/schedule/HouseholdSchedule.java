package com.mit.community.schedule;

import com.ace.cache.annotation.CacheClear;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.entity.AuthorizeAppHouseholdDeviceGroup;
import com.mit.community.entity.AuthorizeHouseholdDeviceGroup;
import com.mit.community.entity.HouseHold;
import com.mit.community.entity.HouseholdRoom;
import com.mit.community.mapper.AuthorizeHouseholdDeviceGroupMapper;
import com.mit.community.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 住户
 *
 * @author shuyy
 * @date 2018/11/19
 * @company mitesofor
 */
@Component
public class HouseholdSchedule {

    private final HouseHoldService houseHoldService;

    private final ClusterCommunityService clusterCommunityService;

    private final AuthorizeAppHouseholdDeviceGroupService authorizeAppHouseholdDeviceGroupService;

    private final AuthorizeHouseholdDeviceGroupService authorizeHouseholdDeviceGroupService;

    private final AccessControlService accessControlService;

    private final HouseholdRoomService householdRoomService;

    private final UserService userService;

    private final AuthorizeHouseholdDeviceGroupMapper authorizeHouseholdDeviceGroupMapper;

    @Autowired
    public HouseholdSchedule(HouseHoldService houseHoldService, ClusterCommunityService clusterCommunityService,
                             AuthorizeAppHouseholdDeviceGroupService authorizeAppHouseholdDeviceService,
                             AuthorizeHouseholdDeviceGroupService authorizeHouseholdDeviceService,
                             AccessControlService accessControlService, HouseholdRoomService householdRoomService, UserService userService,
                             AuthorizeHouseholdDeviceGroupMapper authorizeHouseholdDeviceGroupMapper) {
        this.houseHoldService = houseHoldService;
        this.clusterCommunityService = clusterCommunityService;
        this.authorizeAppHouseholdDeviceGroupService = authorizeAppHouseholdDeviceService;
        this.authorizeHouseholdDeviceGroupService = authorizeHouseholdDeviceService;
        this.accessControlService = accessControlService;
        this.householdRoomService = householdRoomService;
        this.userService = userService;
        this.authorizeHouseholdDeviceGroupMapper = authorizeHouseholdDeviceGroupMapper;
    }
    @Autowired
    private PersonBaseInfoService personBaseInfoService;

    /***
     * 删除然后导入
     * 同步住户数据的时候，先查出dnake接口返回的所有住户，然后和我们数据库住户进行对比，分析出，增加、修改、删除的数据，
     * 然后对这三类数据进行分别处理，对于修改的数据，如果修改了手机号，那么直接修改我们数据库用户表的手机号。
     * 对于增加的数据，直接增加。对于删除的数据，直接删除。
     * 授权设备组则直接删除再插入
     * @author shuyy
     * @date 2018/11/21 10:09
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    //@Scheduled(cron = "0 */10 * * * ?")
    @Scheduled(cron = "0 */2 * * * ?")
    @CacheClear(pre = "household")
    public void removeAndiImport() {
        try {
            List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
            communityCodeList.addAll(clusterCommunityService.listCommunityCodeListByCityName("南昌市"));
            List<HouseHold> houseHolds = houseHoldService.listFromDnakeByCommunityCodeList(communityCodeList, null);//
            if (houseHolds.isEmpty()) {
                return;
            }
            updateAuthAndHouseholdRoom(houseHolds);
            List<HouseHold> list = houseHoldService.list();
            // 对比出三种类型的数据
            Map<Integer, HouseHold> map = Maps.newHashMapWithExpectedSize(list.size());
            list.forEach(l -> map.put(l.getHouseholdId(), l));
            List<HouseHold> updateHousehold = Lists.newArrayListWithCapacity(houseHolds.size());
            List<HouseHold> addHousehold = Lists.newArrayListWithCapacity(houseHolds.size());
            List<HouseHold> removeHousehold = Lists.newArrayListWithCapacity(houseHolds.size());
            for (HouseHold h : houseHolds) {
                HouseHold houseHold = map.get(h.getHouseholdId());
                if (houseHold == null) {
                    addHousehold.add(h);
                } else {
                    boolean update = houseHoldService.isUpdate(h, houseHold);
                    if (update) {
                        h.setId(houseHold.getId());
                        h.setGmtModified(LocalDateTime.now());
                        updateHousehold.add(h);
                    }
                    map.remove(h.getHouseholdId());
                }
            }
            map.forEach((key, value) -> removeHousehold.add(value));
            // 删除
            if (!removeHousehold.isEmpty()) {
                List<Integer> deleteId = removeHousehold.parallelStream().map(HouseHold::getHouseholdId).collect(Collectors.toList());
                List<String> phone = removeHousehold.parallelStream().map(HouseHold::getMobile).collect(Collectors.toList());
                List<String> communityCodde = removeHousehold.parallelStream().map(HouseHold::getCommunityCode).collect(Collectors.toList());
                personBaseInfoService.removeByPhoneList(phone,communityCodde);
                userService.delete(phone);
                houseHoldService.removeByhouseholdIdList(deleteId);
            }
            // 更新
            if (!updateHousehold.isEmpty()) {
                userService.update(updateHousehold);
                houseHoldService.updateBatchById(updateHousehold, 1);
                /*updateHousehold.forEach(houseHold -> {
                    String mobile = houseHold.getMobile();
                    System.out.println("==============更新，householdId="+houseHold.getHouseholdId()+",性别="+houseHold.getGender());
                    userService.updateCellphoneByHouseholdId(mobile, houseHold.getHouseholdId());
                });*/
                /*for (int i=0; i<updateHousehold.size();i++) {
                    String mobile = updateHousehold.get(i).getMobile();
                    System.out.println("==============更新，householdId="+updateHousehold.get(i).getHouseholdId()+",性别="+updateHousehold.get(i).getGender());
                    userService.updateCellphoneByHouseholdId(mobile, updateHousehold.get(i).getHouseholdId());
                }*/

            }
            if (!addHousehold.isEmpty()) {
                // 增加
                personBaseInfoService.insert(addHousehold);
                userService.insert(addHousehold);
                houseHoldService.insertBatch(addHousehold);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新授权设备和关联房屋
     *
     * @param houseHolds
     * @return void
     * @throws
     * @author shuyy
     * @date 2018/12/12 10:45
     * @company mitesofor
     */
    @CacheClear(pre = "householdRoom")
    private void updateAuthAndHouseholdRoom(List<HouseHold> houseHolds) { ;
        //authorizeHouseholdDeviceGroupService.remove();
        authorizeAppHouseholdDeviceGroupService.remove();
        householdRoomService.remove();
        if (!houseHolds.isEmpty()) {
            houseHolds.forEach(item -> {
                List<AuthorizeAppHouseholdDeviceGroup> authorizeAppHouseholdDevices = item.getAuthorizeAppHouseholdDeviceGroups();
                // 对比出三种类型的数据
                item.getHouseholdId();
                List<AuthorizeAppHouseholdDeviceGroup> databaseAuthorizeAppHouseholdDeviceGroups = authorizeAppHouseholdDeviceGroupService.listByHouseholdId(item.getHouseholdId());
                Map<Integer, AuthorizeAppHouseholdDeviceGroup> map = Maps.newHashMapWithExpectedSize(authorizeAppHouseholdDevices.size());
                databaseAuthorizeAppHouseholdDeviceGroups.forEach(l -> map.put(l.getDeviceGroupId(), l));
                List<AuthorizeAppHouseholdDeviceGroup> addAppGroup = Lists.newArrayListWithCapacity(authorizeAppHouseholdDevices.size());
                List<AuthorizeAppHouseholdDeviceGroup> removeAppGroup = Lists.newArrayListWithCapacity(authorizeAppHouseholdDevices.size());
                for (AuthorizeAppHouseholdDeviceGroup h : authorizeAppHouseholdDevices) {
                    AuthorizeAppHouseholdDeviceGroup authorizeAppHouseholdDeviceGroup = map.get(h.getDeviceGroupId());
                    if (authorizeAppHouseholdDeviceGroup == null) {
                        addAppGroup.add(h);
                    } else {
                        map.remove(h.getHouseholdId());
                    }
                }
                map.forEach((key, value) -> removeAppGroup.add(value));
                // 删除
                if (!removeAppGroup.isEmpty()) {
                    List<Integer> deleteId = removeAppGroup.parallelStream().map(AuthorizeAppHouseholdDeviceGroup::getId).collect(Collectors.toList());
                    authorizeAppHouseholdDeviceGroupService.deleteBatchIds(deleteId);
                }
                if (!addAppGroup.isEmpty()) {
                    // 增加
                    authorizeAppHouseholdDeviceGroupService.insertBatch(addAppGroup);
                }

                if (authorizeAppHouseholdDevices != null && !authorizeAppHouseholdDevices.isEmpty()) {
                    authorizeAppHouseholdDeviceGroupService.insertBatch(authorizeAppHouseholdDevices);
                    /**
                     * 新增数据同步代码（胡山林）
                     */
                    /*List<AuthorizeHouseholdDeviceGroup> authorizeHouseholdDeviceGroups = item.getAuthorizeHouseholdDeviceGroups();
                    for (AuthorizeAppHouseholdDeviceGroup authHouse : authorizeAppHouseholdDevices) {
                        List<AuthorizeHouseholdDeviceGroup> existList = authorizeHouseholdDeviceGroupMapper.getObjectByIds(item.getHouseholdId(), authHouse.getDeviceGroupId());
                        if (existList.size() == 0) {
                            AuthorizeHouseholdDeviceGroup authorizeHouseholdDeviceGroup = new AuthorizeHouseholdDeviceGroup();
                            authorizeHouseholdDeviceGroup.setId(authHouse.getId());
                            authorizeHouseholdDeviceGroup.setHouseholdId(authHouse.getHouseholdId());
                            authorizeHouseholdDeviceGroup.setDeviceGroupId(authHouse.getDeviceGroupId());
                            authorizeHouseholdDeviceGroup.setGmtCreate(authHouse.getGmtCreate());
                            authHouse.setGmtModified(authHouse.getGmtModified());
                            authorizeHouseholdDeviceGroups.add(authorizeHouseholdDeviceGroup);
                        }
                    }
                    if (authorizeHouseholdDeviceGroups != null && !authorizeHouseholdDeviceGroups.isEmpty()) {
                        authorizeHouseholdDeviceGroupService.insertBatch(authorizeHouseholdDeviceGroups);
                    }*/
                }
                List<AuthorizeHouseholdDeviceGroup> authorizeHouseholdDeviceGroups = item.getAuthorizeHouseholdDeviceGroups();
                if (authorizeHouseholdDeviceGroups != null && !authorizeHouseholdDeviceGroups.isEmpty()) {
                    authorizeHouseholdDeviceGroupService.insertBatch(authorizeHouseholdDeviceGroups);
                }
                List<HouseholdRoom> householdRoomList = item.getHouseholdRoomList();
                if (householdRoomList != null && !householdRoomList.isEmpty()) {
                    householdRoomService.insertBatch(householdRoomList);
                }
            });
        }
        System.out.println("in");
    }

    /***
     * 更新身份证信息
     * @author shuyy
     * @date 2018/12/08 15:36
     * @company mitesofor
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateIdCard() {
        long start = System.currentTimeMillis();
        List<HouseHold> list = houseHoldService.list();
//    	list = list.subList(0, 1001);
        List<HouseHold> result = houseHoldService.getIdCardInfoFromDnake(list);
        if (!result.isEmpty()) {
            houseHoldService.updateBatchById(result);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /***
     * 身份类型：1、群众、2、境外人员、3、孤寡老人、4、信教人员、5、留守儿童、6、上访人员、99、其他
     * @author shuyy
     * @date 2018/11/24 10:36
     * @company mitesofor
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void parseIdentityType() {
        List<Map<String, Object>> maps = householdRoomService.listActiveRoomId();
        maps.forEach(item -> {
            Integer roomId = (Integer) item.get("room_id");
            List<HouseHold> houseHolds = houseHoldService.listByRoomId(roomId);
            // 只要这个房间有一个住户，没有录入身份证信息，就不分析，因为无法知道年龄
            int noBirthDayNum = houseHolds.parallelStream().filter(a -> a.getBirthday().getYear() < 1901)
                    .collect(Collectors.toList()).size();
            if (noBirthDayNum == 0) {
                int size = houseHolds.size();
                //房间住户只有1个
                if (size == 1) {
                    // 孤寡老人
                    HouseHold houseHold = houseHolds.get(0);
                    LocalDate birthday = houseHold.getBirthday();
                    Period period = Period.between(birthday, LocalDate.now());
                    int age = period.getYears();
                    int oldAge = 60;
                    if (age > oldAge) {
                        houseHold.setIdentityType(HouseHold.LONELY);
                        houseHoldService.updateById(houseHold);
                    }
                } else {
                    // 孤寡老人
                    // 出老人外，其他住户半年通行记录小于10次
                    List<HouseHold> oldHouseholdList = houseHolds.parallelStream().filter(houseHold -> {
                        LocalDate birthday = houseHold.getBirthday();
                        Period period = Period.between(birthday, LocalDate.now());
                        int age = period.getYears();
                        int oldAge = 60;
                        return age > oldAge;
                    }).collect(Collectors.toList());
                    if (oldHouseholdList.size() == 1) {
                        boolean isLonely = true;
                        for (HouseHold houseHold : houseHolds) {
                            if (!oldHouseholdList.contains(houseHold)) {
                                Integer householdId = houseHold.getHouseholdId();
                                Integer num = accessControlService.countHalfYearNumByHouseholdId(householdId);
                                if (num > 10) {
                                    isLonely = false;
                                    break;
                                }
                            }
                        }
                        if (isLonely) {
                            HouseHold houseHold = oldHouseholdList.get(0);
                            houseHold.setIdentityType(HouseHold.LONELY);
                            houseHoldService.updateById(houseHold);
                        }
                    }
                    // 留守儿童
                    List<HouseHold> youngList = houseHolds.parallelStream().filter(houseHold -> {
                        LocalDate birthday = houseHold.getBirthday();
                        Period period = Period.between(birthday, LocalDate.now());
                        int age = period.getYears();
                        int youngAge = 18;
                        return age < youngAge;
                    }).collect(Collectors.toList());
                    if (youngList.size() > 0) {
                        boolean isStayAtHome = true;
                        // 该房间其他住户，是否都是老人
                        for (HouseHold houseHold : houseHolds) {
                            if (!youngList.contains(houseHold)) {
                                LocalDate birthday = houseHold.getBirthday();
                                Period period = Period.between(birthday, LocalDate.now());
                                int age = period.getYears();
                                int oldAge = 60;
                                if (age < oldAge) {
                                    isStayAtHome = false;
                                    break;
                                }
                            }
                        }
                        // 该房间其他非老人住户，通行记录八年小于10次
                        if (isStayAtHome) {
                            for (HouseHold houseHold : houseHolds) {
                                if (!youngList.contains(houseHold) && !oldHouseholdList.contains(houseHold)) {
                                    Integer householdId = houseHold.getHouseholdId();
                                    Integer integer = accessControlService.countHalfYearNumByHouseholdId(householdId);
                                    if (integer > 10) {
                                        isStayAtHome = false;
                                        break;
                                    }
                                }
                            }
                        }
                        if (isStayAtHome) {
                            youngList.forEach(c -> {
                                c.setIdentityType(HouseHold.STAY_AT_HOME);
                                c.setIdentityType(HouseHold.LONELY);
                                houseHoldService.updateById(c);
                            });
                        }
                    }
                }
            }
        });
        // 分析身份类型
    }
}

package com.mit.community.schedule;

import com.mit.community.entity.AuthorizeAppHouseholdDevice;
import com.mit.community.entity.AuthorizeHouseholdDevice;
import com.mit.community.entity.HouseHold;
import com.mit.community.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    private final AuthorizeAppHouseholdDeviceService authorizeAppHouseholdDeviceService;

    private final AuthorizeHouseholdDeviceService authorizeHouseholdDeviceService;

    private final AccessControlService accessControlService;

    @Autowired
    public HouseholdSchedule(HouseHoldService houseHoldService, ClusterCommunityService clusterCommunityService,
                             AuthorizeAppHouseholdDeviceService authorizeAppHouseholdDeviceService,
                             AuthorizeHouseholdDeviceService authorizeHouseholdDeviceService,
                             AccessControlService accessControlService) {
        this.houseHoldService = houseHoldService;
        this.clusterCommunityService = clusterCommunityService;
        this.authorizeAppHouseholdDeviceService = authorizeAppHouseholdDeviceService;
        this.authorizeHouseholdDeviceService = authorizeHouseholdDeviceService;
        this.accessControlService = accessControlService;
    }

    /***
     * 删除然后导入
     * @author shuyy
     * @date 2018/11/21 10:09
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 40 1 * * ?")
    public void removeAndiImport() {
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        // 先删除本地数据库，再插入
        houseHoldService.remove();
        authorizeHouseholdDeviceService.remove();
        authorizeAppHouseholdDeviceService.remove();
        List<HouseHold> houseHolds = houseHoldService.listFromDnakeByCommunityCodeList(communityCodeList, null);
        if (!houseHolds.isEmpty()) {
            houseHoldService.insertBatch(houseHolds);
            houseHolds.forEach(item -> {
                List<AuthorizeAppHouseholdDevice> authorizeAppHouseholdDevices = item.getAuthorizeAppHouseholdDevices();
                if (authorizeAppHouseholdDevices != null && !authorizeAppHouseholdDevices.isEmpty()) {
                    authorizeAppHouseholdDeviceService.insertBatch(authorizeAppHouseholdDevices);
                }
                List<AuthorizeHouseholdDevice> authorizeHouseholdDevices = item.getAuthorizeHouseholdDevices();
                if (authorizeHouseholdDevices != null && !authorizeHouseholdDevices.isEmpty()) {
                    authorizeHouseholdDeviceService.insertBatch(authorizeHouseholdDevices);
                }
            });

        }
    }

    /***
     * 身份类型：1、群众、2、境外人员、3、孤寡老人、4、信教人员、5、留守儿童、6、上访人员、99、其他
     * @author shuyy
     * @date 2018/11/24 10:36
     * @company mitesofor
     */
    @Scheduled(cron = "0 40 3 * * ?")
    public void parseIdentityType() {
        List<Map<String, Object>> maps = houseHoldService.listActiveRoomId();
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

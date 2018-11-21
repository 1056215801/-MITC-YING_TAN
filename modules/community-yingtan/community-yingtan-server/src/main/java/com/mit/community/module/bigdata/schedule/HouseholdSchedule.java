package com.mit.community.module.bigdata.schedule;

import com.mit.community.entity.AuthorizeAppHouseholdDevice;
import com.mit.community.entity.AuthorizeHouseholdDevice;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.HouseHold;
import com.mit.community.service.AuthorizeAppHouseholdDeviceService;
import com.mit.community.service.AuthorizeHouseholdDeviceService;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.HouseHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    public HouseholdSchedule(HouseHoldService houseHoldService, ClusterCommunityService clusterCommunityService, AuthorizeAppHouseholdDeviceService authorizeAppHouseholdDeviceService, AuthorizeHouseholdDeviceService authorizeHouseholdDeviceService) {
        this.houseHoldService = houseHoldService;
        this.clusterCommunityService = clusterCommunityService;
        this.authorizeAppHouseholdDeviceService = authorizeAppHouseholdDeviceService;
        this.authorizeHouseholdDeviceService = authorizeHouseholdDeviceService;
    }

    /***
     * 删除然后导入
     * @author shuyy
     * @date 2018/11/21 10:09
     * @company mitesofor
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "*/5 * * * * ?")
    public void removeAndiImport() {
        List<String> clusterCommunityNameList = new ArrayList<>(4);
        clusterCommunityNameList.add("凯翔外滩小区");
        clusterCommunityNameList.add("心家泊小区");
        clusterCommunityNameList.add("南苑小区");
        clusterCommunityNameList.add("鹰王环东花苑小区");
        // 下面的是测试的
        clusterCommunityNameList.add("珉轩工业园");
        List<ClusterCommunity> clusterCommunitiesList = clusterCommunityService.listByNames(clusterCommunityNameList);
        List<String> communityCodeList = clusterCommunitiesList.stream().map(ClusterCommunity::getCommunityCode).collect(Collectors.toList());
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

}

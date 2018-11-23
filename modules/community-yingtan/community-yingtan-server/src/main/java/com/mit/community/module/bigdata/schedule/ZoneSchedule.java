package com.mit.community.module.bigdata.schedule;

import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Zone;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分区定时
 *
 * @author shuyy
 * @date 2018/11/20
 * @company mitesofor
 */
//@Component
public class ZoneSchedule {

    private final ZoneService zoneService;

    private final ClusterCommunityService clusterCommunityService;

    @Autowired
    public ZoneSchedule(ZoneService zoneService, ClusterCommunityService clusterCommunityService) {
        this.zoneService = zoneService;
        this.clusterCommunityService = clusterCommunityService;
    }

    /***
     * 定时同步分区
     *  Transactional：添加事务，防止这里删除了分区，还没来得及插入，另外一个事务就已经读分区数据，导致读出空数据
     * @author shuyy
     * @date 2018/11/20 11:28
     * @company mitesofor
    */
    @Scheduled(cron = "* * */5 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport(){
        List<String> clusterCommunityNameList = new ArrayList<>(4);
        clusterCommunityNameList.add("凯翔外滩小区");
        clusterCommunityNameList.add("心家泊小区");
        clusterCommunityNameList.add("南苑小区");
        clusterCommunityNameList.add("鹰王环东花苑小区");
        // 下面的是测试的
        clusterCommunityNameList.add("珉轩工业园");
        List<ClusterCommunity> clusterCommunitiesList = clusterCommunityService.listByNames(clusterCommunityNameList);
        List<String> list = clusterCommunitiesList.stream().map(ClusterCommunity::getCommunityCode).collect(Collectors.toList());
        // 先删除，再添加
        zoneService.remove();
        List<Zone> zones = zoneService.listFromDnakeByCommunityCodeList(list);
        zoneService.insertBatch(zones);
    }
}

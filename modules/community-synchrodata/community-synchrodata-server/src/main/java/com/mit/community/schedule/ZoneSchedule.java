package com.mit.community.schedule;

import com.ace.cache.annotation.CacheClear;
import com.mit.community.entity.Zone;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分区定时
 *
 * @author shuyy
 * @date 2018/11/20
 * @company mitesofor
 */
@Component
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
    @CacheClear(pre="zone")
    //@Scheduled(cron = "0 0 23 * * ?")
    @Scheduled(cron = "0 */10 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport(){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        communityCodeList.addAll(clusterCommunityService.listCommunityCodeListByCityName("南昌市"));
        List<Zone> zones = zoneService.listFromDnakeByCommunityCodeList(communityCodeList);
        if(!zones.isEmpty()) {
            // 先删除，再添加
            zoneService.remove();
            zoneService.insertBatch(zones);
        }
    }
}

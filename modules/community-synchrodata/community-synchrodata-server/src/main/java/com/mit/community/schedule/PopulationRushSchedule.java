package com.mit.community.schedule;

import com.mit.community.entity.PopulationRush;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.PopulationRushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 人流高峰
 *
 * @author shuyy
 * @date 2018/11/23
 * @company mitesofor
 */
@Component
public class PopulationRushSchedule {

    private final PopulationRushService populationRushService;

    private final ClusterCommunityService clusterCommunityService;

    @Autowired
    public PopulationRushSchedule(PopulationRushService populationRushService, ClusterCommunityService clusterCommunityService) {
        this.populationRushService = populationRushService;
        this.clusterCommunityService = clusterCommunityService;
    }

    @Scheduled(cron = "0 0 0 */7 * ?")
    @Transactional(rollbackFor = Exception.class)
    public void update() {
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        communityCodeList.addAll(clusterCommunityService.listCommunityCodeListByCityName("南昌市"));
        communityCodeList.forEach(item -> {
            PopulationRush populationRush = populationRushService.countByCommunityCode(item);
            populationRushService.updateByCommunityCode(populationRush);
        });
    }
}

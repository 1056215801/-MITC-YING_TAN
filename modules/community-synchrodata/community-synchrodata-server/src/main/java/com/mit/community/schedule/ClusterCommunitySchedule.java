package com.mit.community.schedule;

import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Zone;
import com.mit.community.service.ClusterCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 小区定时器
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@Component
public class ClusterCommunitySchedule {

    private final ClusterCommunityService clusterCommunityService;

    @Autowired
    public ClusterCommunitySchedule(ClusterCommunityService clusterCommunityService) {
        this.clusterCommunityService = clusterCommunityService;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport(){
        clusterCommunityService.remove();
        List<ClusterCommunity> clusterCommunities = clusterCommunityService.listFromDnake();
        clusterCommunities.forEach(item -> {
            clusterCommunityService.save(item);
        });
    }
}

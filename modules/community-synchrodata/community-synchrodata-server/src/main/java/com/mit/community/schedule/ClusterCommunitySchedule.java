package com.mit.community.schedule;

import com.ace.cache.annotation.CacheClear;
import com.mit.community.entity.ClusterCommunity;
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

    /**
     * 用先删除后添加的方式同步数据
     *
     * @author Mr.Deng
     * @date 10:45 2019/3/7
     */
    //@Scheduled(cron = "0 0 0 1 * ?")
    @Scheduled(cron = "0 */10 * * * ?")
    @CacheClear(pre = "community")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport() {
        List<ClusterCommunity> clusterCommunities = clusterCommunityService.listFromDnake();
        if (clusterCommunities.isEmpty()) {
            return;
        }
        clusterCommunityService.remove();
        clusterCommunities.forEach(item -> {
            clusterCommunityService.save(item);
        });
    }
}

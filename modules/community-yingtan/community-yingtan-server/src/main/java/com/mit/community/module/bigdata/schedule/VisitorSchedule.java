package com.mit.community.module.bigdata.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mit.community.entity.Visitor;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.VisitorService;

/**
 * 访客定时同步
 *
 * @author shuyy
 * @date 2018/11/21
 * @company mitesofor
 */
//@Component
public class VisitorSchedule {

    private final VisitorService visitorService;

    private final ClusterCommunityService clusterCommunityService;

    @Autowired
    public VisitorSchedule(VisitorService visitorService, ClusterCommunityService clusterCommunityService) {
        this.visitorService = visitorService;
        this.clusterCommunityService = clusterCommunityService;
    }

    @Scheduled(cron = "0 0 0 */1 * ?")
    @Transactional(rollbackFor = Exception.class)
    public void removeAndImport(){
        List<String> communityCodeList = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        // 删除所有访客，再插入
        visitorService.remove();
        List<Visitor> visitors = visitorService.listFromDnakeByCommunityCodeList(communityCodeList);
        if(!visitors.isEmpty()) {
        	visitorService.insertBatch(visitors);
        }
    }
}

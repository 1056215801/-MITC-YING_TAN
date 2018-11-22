package com.mit.community.module.bigdata.schedule;

import com.mit.community.entity.ClusterCommunity;
import com.mit.community.entity.Visitor;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Scheduled(cron = "*/5 * * * * ?")
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
        // 删除所有访客，再插入
        visitorService.remove();
        List<Visitor> visitors = visitorService.listFromDnakeByCommunityCodeList(list);
        visitorService.insertBatch(visitors);
    }
}

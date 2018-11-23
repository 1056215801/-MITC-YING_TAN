package com.mit.community.module.bigdata.schedule;

import com.mit.community.entity.AccessControl;
import com.mit.community.entity.ClusterCommunity;
import com.mit.community.service.AccessControlService;
import com.mit.community.service.ClusterCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 门禁 定时器
 *
 * @author shuyy
 * @date 2018/11/16
 * @company mitesofor
 */
//@Component
public class AccessControlSchedule {
    
    private final AccessControlService accessControlService;
    
    private final ClusterCommunityService clusterCommunityService;

    @Autowired
    public AccessControlSchedule(AccessControlService accessControlService, ClusterCommunityService clusterCommunityService) {
        this.accessControlService = accessControlService;
        this.clusterCommunityService = clusterCommunityService;
    }

    /***
     * 增量导入。只导数据库中最新的记录（门禁时间最新）之后的记录
     * @author shuyy
     * @date 2018/11/16 16:55
     * @company mitesofor
    */
    @Scheduled(cron = "*/10 * * * * ?")
    public void importIncrement (){
        List<String> clusterCommunityNameList = new ArrayList<>(4);
        clusterCommunityNameList.add("凯翔外滩小区");
        clusterCommunityNameList.add("南苑小区");
        clusterCommunityNameList.add("鹰王环东花苑小区");
        // 下面的是测试的
        clusterCommunityNameList.add("珉轩工业园");
        List<ClusterCommunity> clusterCommunitiesList = clusterCommunityService.listByNames(clusterCommunityNameList);
        List<AccessControl> allAccessControlsList = accessControlService.listIncrementByCommunityCode(clusterCommunitiesList);
        if(!allAccessControlsList.isEmpty()){
            /* 测试模式，测试数据是否完成，所以把这里注释掉
            allAccessControlsList.forEach(item -> accessControlService.save(item));*/
            accessControlService.insertBatch(allAccessControlsList);
            /* 批量插入会导致一条记录数据出问题，所有的数据都插入失败，所以这里注释掉
            // 批量插入
            accessControlService.insertBatch(allAccessControlsList);*/
        }
    }




}

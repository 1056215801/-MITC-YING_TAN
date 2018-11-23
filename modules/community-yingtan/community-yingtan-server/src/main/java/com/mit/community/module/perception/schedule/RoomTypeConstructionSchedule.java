package com.mit.community.module.perception.schedule;

import com.mit.community.entity.RoomTypeConstruction;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.HouseHoldService;
import com.mit.community.service.RoomTypeConstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LW
 * @creatTime 2018-11-23 11:17
 * @company mitesofor
 */
public class RoomTypeConstructionSchedule {

    private final RoomTypeConstructionService roomTypeConstructionService;
    private final HouseHoldService houseHoldService;
    private final ClusterCommunityService clusterCommunityService;

    @Autowired
    public RoomTypeConstructionSchedule(RoomTypeConstructionService roomTypeConstructionService,HouseHoldService houseHoldService,ClusterCommunityService clusterCommunityService){
        this.clusterCommunityService=clusterCommunityService;
        this.houseHoldService=houseHoldService;
        this.roomTypeConstructionService=roomTypeConstructionService;
    }
    /**
     * 删除然后导入
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "*/10 * * * * ?")
    public void removeAndiImport(){
        List<String> communityCodes;
        //删除所有
        roomTypeConstructionService.remove();
//        查找所有的鹰潭的小区id
        communityCodes=clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        for (String communityCode:communityCodes){
            RoomTypeConstruction roomTypeConstruction=new RoomTypeConstruction();
//            查找外来人口


        }
    }
}

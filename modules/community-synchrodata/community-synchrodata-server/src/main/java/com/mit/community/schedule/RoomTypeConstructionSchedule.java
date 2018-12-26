package com.mit.community.schedule;

import com.mit.community.entity.RoomTypeConstruction;
import com.mit.community.service.ClusterCommunityService;
import com.mit.community.service.RoomTypeConstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 房间类型定时统计
 * @author LW
 * @creatTime 2018-11-23 11:17
 * @company mitesofor
 */
//@Component
public class RoomTypeConstructionSchedule {

    private final RoomTypeConstructionService roomTypeConstructionService;
    private final ClusterCommunityService clusterCommunityService;

    @Autowired
    public RoomTypeConstructionSchedule(RoomTypeConstructionService roomTypeConstructionService,
                                        ClusterCommunityService clusterCommunityService) {
        this.clusterCommunityService = clusterCommunityService;
        this.roomTypeConstructionService = roomTypeConstructionService;
    }

    /**
     * 删除然后导入
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 30 3 * * ?")
    public void removeAndiImport() {
        List<String> communityCodes;
        //删除所有
        roomTypeConstructionService.remove();
        //查找所有的鹰潭的小区id
        communityCodes = clusterCommunityService.listCommunityCodeListByCityName("鹰潭市");
        if (!communityCodes.isEmpty()) {
            for (String communityCode : communityCodes) {
                //遍历出人口类型结构数据再保存
                RoomTypeConstruction roomTypeConstruction = roomTypeConstructionService.countRoomTypeConstructionByCommunityCode(communityCode);
                roomTypeConstructionService.save(roomTypeConstruction);
            }
        }
    }
}

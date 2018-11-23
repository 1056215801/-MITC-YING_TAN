package com.mit.community.module.perception.schedule;

import com.mit.community.service.HouseHoldService;
import com.mit.community.service.RoomTypeConstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LW
 * @creatTime 2018-11-23 11:17
 * @company mitesofor
 */
public class RoomTypeConstructionSchedule {

    private final RoomTypeConstructionService roomTypeConstructionService;
    private final HouseHoldService houseHoldService;
//    private final R houseHoldService;

    @Autowired
    public RoomTypeConstructionSchedule(RoomTypeConstructionService roomTypeConstructionService,HouseHoldService houseHoldService){
        this.houseHoldService=houseHoldService;
        this.roomTypeConstructionService=roomTypeConstructionService;
    }
    /**
     * 删除然后导入
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "*/10 * * * * ?")
    public void removeAndiImport(){
        //删除所有
        roomTypeConstructionService.remove();

    }
}

package com.mit.community.schedule;

import com.mit.community.entity.Warning;
import com.mit.community.service.SysUserService;
import com.mit.community.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 警报定时同步数据
 *
 * @author Mr.Deng
 * @date 2018/11/27 14:39
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Component
public class WarningSchedule {

    private final WarningService warningService;

    private final SysUserService sysUserService;

    @Autowired
    public WarningSchedule(WarningService warningService, SysUserService sysUserService) {
        this.warningService = warningService;
        this.sysUserService = sysUserService;
    }

    @Scheduled(cron = "0 */15 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void saveWaring() {
        warningService.remove();
        List<Warning> warningList = warningService.listFromDnake();
        if(!warningList.isEmpty()){
            warningService.insertBatch(warningList);
        }
    }

}

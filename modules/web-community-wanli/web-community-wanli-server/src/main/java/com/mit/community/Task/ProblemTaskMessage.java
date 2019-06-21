package com.mit.community.Task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProblemTaskMessage {


    @Scheduled(cron = "0/1 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void task(){
        
    }
}

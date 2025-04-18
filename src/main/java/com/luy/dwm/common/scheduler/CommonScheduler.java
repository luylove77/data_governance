package com.luy.dwm.common.scheduler;

import com.luy.dwm.model.service.DmTableSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class CommonScheduler {

    @Autowired
    DmTableSyncService dmTableSyncService;

    //秒 分 时 日 月 周, 6个* 每秒执行一次
    //cron = "0/5 * * * * *" 每5秒执行一次
    //cron = "0 30 3 * * *" 夜里3:30执行一次
//    @Scheduled(cron = "* * * * * *")
//    public void test001(){
//        System.out.println(" 定时任务 秒级= " +new Date());
//
//    }

    // 每分钟调用一次，0秒触发
    @Scheduled(cron = "0 * * * * *")
    public void execSyncDataInfo() throws Exception {
        log.info("定时任务execSyncDataInfo 开始执行 = "+new Date());
        dmTableSyncService.syncDataInfoForScheduler();
        log.info("定时任务execSyncDataInfo 执行结束 = "+new Date());
    }
}

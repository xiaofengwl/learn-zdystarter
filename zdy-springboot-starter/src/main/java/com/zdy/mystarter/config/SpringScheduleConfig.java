package com.zdy.mystarter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by Jason on 2020/1/7.
 * 定时任务处理，需要一个前提,@EnableScheduling 在启动服务的时候需要开启
 */
@Order(5)
@Configuration
public class SpringScheduleConfig {

    private static int index=0;
    /**
     * 定时任务-1
     * 每一分钟执行一次
     */
    @Scheduled(cron="0 0/1 * * * ?")
    public void execute1(){
        index++;
        System.out.println("SpringScheduleConfig...execute..."+index+"...."+System.currentTimeMillis());
    }
}

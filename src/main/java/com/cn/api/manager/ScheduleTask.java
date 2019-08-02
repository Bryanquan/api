package com.cn.api.manager;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Scheduled(cron = "0/5 * * * * ? ") // 间隔5秒执行
    public static void printHello(){
        System.out.println(Thread.currentThread().getName()+" "+"work1: 每5秒执行一次");
    }

}

package com.edu.guohosp.task.scheduled;

import com.edu.guohosp.rabbit.constant.MqConst;
import com.edu.guohosp.rabbit.service.RabbitService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/9 0009 22:07
 * @description:
 * @project: server-Java
 * @version: 1.0
 */
@Component
@EnableScheduling
@Slf4j
public class ScheduledTask {
    @Resource
    private RabbitService rabbitService;

    /**
     * 每天8点执行方法，提醒就医
     * 正式使用时间：每天8点一次 0 0 8 * * ?
     * 测试使用时间：5秒一次 0/5 * * * * ?
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void taskPatient() {
        log.info(new DateTime().toString("yyyy-MM-dd HH:mm:ss") + "执行任务");
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_8, "");
    }
}

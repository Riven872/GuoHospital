package com.edu.guohosp.receiver;

import com.edu.guohosp.model.hosp.Schedule;
import com.edu.guohosp.rabbit.constant.MqConst;
import com.edu.guohosp.rabbit.service.RabbitService;
import com.edu.guohosp.service.ScheduleService;
import com.edu.guohosp.vo.msm.MsmVo;
import com.edu.guohosp.vo.order.OrderMqVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/8 0008 22:02
 * @description:
 * @project: server-Java
 * @version: 1.0
 */
@Component
public class HospitalReceiver {
    @Resource
    private ScheduleService scheduleService;

    @Resource
    private RabbitService rabbitService;

    /**
     * 更新排班数据监听器
     *
     * @param msmVo
     * @param message
     * @param channel
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_ORDER, durable = "true"),
            exchange = @Exchange(MqConst.EXCHANGE_DIRECT_ORDER),
            key = {MqConst.ROUTING_ORDER}
    ))
    public void receiver(OrderMqVo orderMqVo, Message message, Channel channel) {
        // 获取要更新的实体
        Schedule schedule = scheduleService.getScheduleById(orderMqVo.getScheduleId());
        // 更新可预约数
        schedule.setReservedNumber(orderMqVo.getReservedNumber());
        // 更新剩余预约数
        schedule.setAvailableNumber(orderMqVo.getAvailableNumber());
        // 进行更新
        scheduleService.updateByMq(schedule);

        // 发送短信
        MsmVo msmVo = orderMqVo.getMsmVo();
        rabbitService.sendMessage(MqConst.QUEUE_MSM_ITEM, MqConst.ROUTING_MSM_ITEM, msmVo);
    }
}

package com.edu.guohosp.rabbit.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/8 0008 1:55
 * @description:
 * @project: server-Java
 * @version: 1.0
 */
@Service
public class RabbitService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param exchange   交换机
     * @param routingKey 路由key
     * @param message    消息
     * @return
     */
    public boolean sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        return true;
    }
}

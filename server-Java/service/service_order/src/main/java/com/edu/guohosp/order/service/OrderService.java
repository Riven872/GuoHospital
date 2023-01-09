package com.edu.guohosp.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.guohosp.model.order.OrderInfo;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/7 0007 20:09
 * @description:
 * @project: server-Java
 * @version: 1.0
 */
public interface OrderService extends IService<OrderInfo> {
    //根据科室和就诊人创建挂号订单并返回订单id
    Long saveOrders(String scheduleId, Long patientId);

    //监听定时任务发送来的信息，然后发送就诊通知
    void patientTips();
}

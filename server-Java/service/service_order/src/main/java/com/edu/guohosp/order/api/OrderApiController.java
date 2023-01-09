package com.edu.guohosp.order.api;

import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.order.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/7 0007 20:09
 * @description:
 * @project: server-Java
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderApiController {
    @Resource
    private OrderService orderService;

    /**
     * 根据科室和就诊人创建挂号订单并返回订单id
     *
     * @param scheduleId
     * @param patientId
     * @return
     */
    @PostMapping("/auth/submitOrder/{scheduleId}/{patientId}")
    public Result saveOrders(@PathVariable String scheduleId, @PathVariable Long patientId) {
        Long orderId = orderService.saveOrders(scheduleId, patientId);
        return Result.ok(orderId);
    }
}

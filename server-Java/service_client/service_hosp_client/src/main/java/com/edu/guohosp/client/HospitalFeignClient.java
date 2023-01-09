package com.edu.guohosp.client;

import com.edu.guohosp.vo.hosp.ScheduleOrderVo;
import com.edu.guohosp.vo.order.SignInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/7 0007 21:02
 * @description:
 * @project: server-Java
 * @version: 1.0
 */
@FeignClient("service-hosp")
@Component
public interface HospitalFeignClient {
    /**
     * 根据排班id获取预约下单数据（内部远程调用接口）
     *
     * @param scheduleId
     * @return
     */
    @GetMapping("/api/hosp/hospital/inner/getScheduleOrderVo/{scheduleId}")
    ScheduleOrderVo getScheduleOrderVo(@PathVariable("scheduleId") String scheduleId);

    /**
     * 根据医院编码获取医院签名信息（内部远程调用接口）
     * @param hoscode
     * @return
     */
    @GetMapping("/api/hosp/hospital/inner/getSignInfoVo/{hoscode}")
    SignInfoVo getSignInfoVo(@PathVariable("hoscode") String hoscode);
}

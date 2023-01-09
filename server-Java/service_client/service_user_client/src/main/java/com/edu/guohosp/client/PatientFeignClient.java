package com.edu.guohosp.client;

import com.edu.guohosp.model.user.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/7 0007 20:27
 * @description:
 * @project: server-Java
 * @version: 1.0
 */
@Component
@FeignClient("service-user")
public interface PatientFeignClient {
    //根据就诊人id获取其信息（内部远程调用接口）
    @GetMapping("/api/user/patient/inner/get/{id}")
    Patient getPatientOrder(@PathVariable("id") Long id);
}

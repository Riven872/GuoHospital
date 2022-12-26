package com.edu.guohosp.service;

import com.edu.guohosp.model.hosp.Hospital;

import java.util.Map;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/23 0023 18:27
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
public interface HospitalService {
    void save(Map<String, Object> paramMap);

    Hospital getHospitalByHosCode(String code);
}
package com.edu.guohosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.guohosp.model.user.Patient;

import java.util.List;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/4 0004 22:35
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
public interface PatientService extends IService<Patient> {
    List<Patient> findAllByUserId(Long userId);

    Patient getPatientById(Long id);
}

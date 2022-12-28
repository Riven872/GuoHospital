package com.edu.guohosp.service;

import com.edu.guohosp.model.hosp.Hospital;
import com.edu.guohosp.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
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

    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);

    Map getHospById(String id);

    String getHospName(String hoscode);

    List<Hospital> findByHosName(String hosname);

    Map<String, Object> item(String hoscode);
}

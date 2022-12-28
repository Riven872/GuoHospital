package com.edu.guohosp.repository;

import com.edu.guohosp.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/23 0023 18:20
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {
    //判断是否存在数据
    Hospital getHospitalByHoscode(String hoscode);

    //根据医院名称模糊查询医院
    List<Hospital> findHospitalByHosnameLike(String hosname);
}

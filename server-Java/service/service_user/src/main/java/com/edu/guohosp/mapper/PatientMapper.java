package com.edu.guohosp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.guohosp.model.user.Patient;
import org.apache.ibatis.annotations.Mapper;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/4 0004 22:36
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
}

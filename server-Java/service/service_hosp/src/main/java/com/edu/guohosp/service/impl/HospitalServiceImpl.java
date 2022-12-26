package com.edu.guohosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.edu.guohosp.model.hosp.Hospital;
import com.edu.guohosp.repository.HospitalRepository;
import com.edu.guohosp.service.HospitalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/23 0023 18:28
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    @Resource
    private HospitalRepository hospitalRepository;

    /**
     * 保存到医院上传过来的信息
     * @param paramMap
     */
    @Override
    public void save(Map<String, Object> paramMap) {
        //把参数map集合转换为Hospital对象
        String jsonString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(jsonString, Hospital.class);

        //判断该数据是否已经存在
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);
        //存在进行修改
        if (hospitalExist != null) {
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
        }
        //不存在则新增
        else{
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
        }
        hospital.setUpdateTime(new Date());
        hospital.setIsDeleted(0);
        hospitalRepository.save(hospital);
    }

    /**
     * 根据医院编码查询医院信息
     * @param code
     * @return
     */
    @Override
    public Hospital getHospitalByHosCode(String code) {
        Hospital hospitalByHoscode = hospitalRepository.getHospitalByHoscode(code);
        return hospitalByHoscode;
    }
}

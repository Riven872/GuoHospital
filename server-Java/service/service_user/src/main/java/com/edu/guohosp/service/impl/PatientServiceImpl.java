package com.edu.guohosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.guohosp.client.DictFeignClient;
import com.edu.guohosp.enums.DictEnum;
import com.edu.guohosp.mapper.PatientMapper;
import com.edu.guohosp.model.user.Patient;
import com.edu.guohosp.service.PatientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {
    @Resource
    private DictFeignClient dictFeignClient;

    /**
     * 根据当前登录用户的id查询其所有的就诊人列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Patient> findAllByUserId(Long userId) {
        //region 根据用户id查询出相关联的所有就诊人信息列表
        List<Patient> patients = this.list(new LambdaQueryWrapper<Patient>().eq(Patient::getUserId, userId));
        //endregion

        //region 通过远程调用，得到编码对应具体内容，查询数据字典表的内容
        patients.forEach(e -> this.packPatient(e));
        //endregion
        return null;
    }

    // 将其他参数进行封装（将类型由数字变成具体的名称）
    private void packPatient(Patient patient) {
        //region 通过远程调用，获取值对应的name
        // 根据证件类型编码和具体的值，获取证件类型对应的名称
        String certificatesTypeString = this.dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), patient.getCertificatesType());
        // 根据证件类型编码和具体的值，获取联系人证件类型对应的名称
        String contactsCertificatesTypeString = this.dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), patient.getContactsCertificatesType());
        // 根据省份具体的值，获取省份对应的名称
        String provinceString = this.dictFeignClient.getName(patient.getProvinceCode());
        // 根据城市...
        String cityString = this.dictFeignClient.getName(patient.getCityCode());
        // 根据区县...
        String districtString = this.dictFeignClient.getName(patient.getDistrictCode());
        //endregion

        //region 将查询到的name封装到要返回的实体中
        patient.getParam().put("certificatesTypeString", certificatesTypeString);
        patient.getParam().put("contactsCertificatesTypeString", contactsCertificatesTypeString);
        patient.getParam().put("provinceString", provinceString);
        patient.getParam().put("cityString", cityString);
        patient.getParam().put("districtString", districtString);
        patient.getParam().put("fullAddress", provinceString + cityString + districtString + patient.getAddress());
        //endregion
    }

    /**
     * 根据就诊人id获取就诊人的信息
     *
     * @param id
     * @return
     */
    @Override
    public Patient getPatientById(Long id) {
        //region 查询基本就诊人的信息
        Patient patient = this.getById(id);
        //endregion

        //region 封装就诊人的信息
        this.packPatient(patient);
        //endregion

        return patient;
    }
}

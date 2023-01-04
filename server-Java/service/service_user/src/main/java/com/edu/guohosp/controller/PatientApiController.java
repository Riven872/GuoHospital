package com.edu.guohosp.controller;

import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.common.utils.AuthContextHolder;
import com.edu.guohosp.model.user.Patient;
import com.edu.guohosp.service.PatientService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/4 0004 22:34
 * @description: 就诊人接口
 * @project: server-Java
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController {
    @Resource
    private PatientService patientService;

    /**
     * 根据当前登录用户的id查询其所有的就诊人列表
     *
     * @param request
     * @return
     */
    @GetMapping("/auth/findAll")
    public Result findAll(HttpServletRequest request) {
        List<Patient> list = patientService.findAllByUserId(AuthContextHolder.getUserId(request));
        return Result.ok(list);
    }

    /**
     * 将当前用户添加为就诊人
     *
     * @param patient
     * @param request
     * @return
     */
    @PostMapping("/auth/save")
    public Result savePatient(@RequestBody Patient patient, HttpServletRequest request) {
        patient.setUserId(AuthContextHolder.getUserId(request));
        patientService.save(patient);
        return Result.ok();
    }

    /**
     * 根据就诊人id获取就诊人的信息
     * @param id
     * @return
     */
    @GetMapping("/auth/get/{id}")
    public Result getPatient(@PathVariable Long id){
        Patient patient = patientService.getPatientById(id);
        return Result.ok(patient);
    }

    /**
     * 修改页面提交的就诊人的信息
     *
     * @param patient
     * @return
     */
    @PostMapping("/auth/update")
    public Result updatePatient(@RequestBody Patient patient) {
        patientService.updateById(patient);
        return Result.ok();
    }

    /**
     * 删除就诊人
     * @param id
     * @return
     */
    @DeleteMapping("/auth/remove/{id}")
    public Result removePatient(@PathVariable Long id){
        patientService.removeById(id);
        return Result.ok();
    }
}

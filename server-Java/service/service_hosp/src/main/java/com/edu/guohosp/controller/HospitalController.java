package com.edu.guohosp.controller;

import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.model.hosp.Hospital;
import com.edu.guohosp.service.HospitalService;
import com.edu.guohosp.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/26 0026 12:11
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/hosp/hospital")

public class HospitalController {
    @Resource
    private HospitalService hospitalService;

    /**
     * 根据条件分页查询医院列表
     *
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    @GetMapping("/list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page, @PathVariable Integer limit, HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> pageModel = hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 更新医院上线状态
     *
     * @param id
     * @param status
     * @return
     */
    @GetMapping("/updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id, @PathVariable Integer status) {
        hospitalService.updateStatus(id, status);
        return Result.ok();
    }

    /**
     * 查询医院详情
     *
     * @param id
     * @return
     */
    @GetMapping("/showHospDetail/{id}")
    public Result showHospitalDetail(@PathVariable String id) {
        Map<String, Object> map = hospitalService.getHospById(id);
        return Result.ok(map);
    }
}

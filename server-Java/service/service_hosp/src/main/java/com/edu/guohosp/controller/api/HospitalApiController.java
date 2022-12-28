package com.edu.guohosp.controller.api;

import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.model.hosp.Hospital;
import com.edu.guohosp.service.DepartmentService;
import com.edu.guohosp.service.HospitalService;
import com.edu.guohosp.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/28 0028 16:03
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/hosp/hospital")
public class HospitalApiController {
    @Resource
    private HospitalService hospitalService;

    @Resource
    private DepartmentService departmentService;

    /**
     * 查询医院列表
     *
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    @GetMapping("/findHospList/{page}/{limit}")
    public Result findHospList(@PathVariable Integer page, @PathVariable Integer limit, HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> hospitals = hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        return Result.ok(hospitals);
    }

    /**
     * 根据医院名称模糊查询医院
     *
     * @param hosname
     * @return
     */
    @GetMapping("/findByHosname/{hosname}")
    public Result findByHosName(@PathVariable String hosname) {
        List<Hospital> list = hospitalService.findByHosName(hosname);
        return Result.ok(list);
    }

    /**
     * 根据医院编号获取医院的科室列表
     *
     * @return
     */
    @GetMapping("/department/{hoscode}")
    public Result index(@PathVariable String hoscode) {
        return Result.ok(departmentService.findDeptTree(hoscode));
    }

    /**
     * 根据医院编号获取医院的预约挂号详情
     *
     * @return
     */
    @GetMapping("/findHospDetail/{hoscode}")
    public Result item(@PathVariable String hoscode) {
        Map<String, Object> map = hospitalService.item(hoscode);
        return Result.ok(map);
    }
}

package com.edu.guohosp.controller;

import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.service.DepartmentService;
import com.edu.guohosp.vo.hosp.DepartmentVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/27 0027 13:29
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/hosp/department")

public class departmentController {
    @Resource
    private DepartmentService departmentService;

    /**
     * 根据医院编号查询科室
     *
     * @param hoscode
     * @return
     */
    @GetMapping("/getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }
}

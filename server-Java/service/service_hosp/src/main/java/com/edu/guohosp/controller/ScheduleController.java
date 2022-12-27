package com.edu.guohosp.controller;

import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.model.hosp.Schedule;
import com.edu.guohosp.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/27 0027 15:11
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/hosp/schedule")

public class ScheduleController {
    @Resource
    private ScheduleService scheduleService;

    /**
     * 根据医院编号和科室编号查询排班规则
     *
     * @param page
     * @param limit
     * @param hoscode
     * @param depcode
     * @return
     */
    @GetMapping("/getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable Long page, @PathVariable Long limit, @PathVariable String hoscode, @PathVariable String depcode) {
        Map<String, Object> map = scheduleService.getRuleSchedule(page, limit, hoscode, depcode);
        return Result.ok(map);
    }

    /**
     * 根据医院编号、科室编号和工作日期，查询排班详细信息
     *
     * @param hoscode
     * @param depcode
     * @param workDate
     * @return
     */
    @GetMapping("/getScheduleDetail/{hoscode}/{depcode}/{workDate}")
    public Result getScheduleDetail(@PathVariable String hoscode, @PathVariable String depcode, @PathVariable String workDate) {
        List<Schedule> list = scheduleService.getScheduleDetail(hoscode, depcode, workDate);
        return Result.ok(list);
    }
}

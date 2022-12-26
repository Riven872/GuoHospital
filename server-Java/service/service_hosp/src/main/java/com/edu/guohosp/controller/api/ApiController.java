package com.edu.guohosp.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.guohosp.common.helper.HttpRequestHelper;
import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.model.hosp.Department;
import com.edu.guohosp.model.hosp.Hospital;
import com.edu.guohosp.model.hosp.HospitalSet;
import com.edu.guohosp.model.hosp.Schedule;
import com.edu.guohosp.service.DepartmentService;
import com.edu.guohosp.service.HospitalService;
import com.edu.guohosp.service.HospitalSetService;
import com.edu.guohosp.service.ScheduleService;
import com.edu.guohosp.vo.hosp.DepartmentQueryVo;
import com.edu.guohosp.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/23 0023 18:30
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/hosp")
public class ApiController {
    @Resource
    private HospitalService hospitalService;

    @Resource
    private HospitalSetService hospitalSetService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private ScheduleService scheduleService;

    /**
     * 获取医院上传过来的信息并保存到Mongo数据库
     *
     * @param request
     * @return
     */
    @PostMapping("/saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        //获取医院平台传递过来的医院信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        //使用工具类，将String[]转换成Object，方便后续处理
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);
        //得到医院上传的签名密钥（已经被MD5加密过）
        String sign = paramMap.get("sign").toString();
        //得到医院上传的医院编号
        String code = paramMap.get("hoscode").toString();
        //查询编号对应的密钥是否匹配（需要将查出来的密钥MD5加密）
        HospitalSet hospitalSet = hospitalSetService.getOne(new LambdaQueryWrapper<HospitalSet>().eq(!StringUtils.isEmpty(code), HospitalSet::getHoscode, code));
        //if (!MD5.encrypt(hospitalSet.getSignKey()).equals(sign)) {
        //    throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        //}

        //region 传输过程中，"+"转换成了" "，因此需要转换回来
        String logoData = paramMap.get("logoData").toString();
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);
        //endregion

        //得到医院信息后，进行保存
        hospitalService.save(paramMap);
        return Result.ok();
    }

    /**
     * 通过医院编号查询医院信息
     *
     * @param request
     * @return
     */
    @PostMapping("/hospital/show")
    public Result getHospital(HttpServletRequest request) {
        //获取医院平台传递过来的医院信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        //使用工具类，将String[]转换成Object，方便后续处理
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        //这里需要判断签名，一致才能调用接口，此处为了调试暂时忽略

        //根据医院编号查询信息
        String code = paramMap.get("hoscode").toString();
        Hospital hospitalByHosCode = hospitalService.getHospitalByHosCode(code);
        return Result.ok(hospitalByHosCode);
    }

    /**
     * 保存医院上传过来的科室信息
     *
     * @param request
     * @return
     */
    @PostMapping("/saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        //获取医院平台传递过来的医院信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        //使用工具类，将String[]转换成Object，方便后续处理
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        departmentService.save(paramMap);
        return Result.ok();
    }

    /**
     * 分页查询科室信息
     *
     * @param request
     * @return
     */
    @PostMapping("/department/list")
    public Result findDepartment(HttpServletRequest request) {
        //获取医院平台传递过来的医院信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        //使用工具类，将String[]转换成Object，方便后续处理
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        String code = paramMap.get("hoscode").toString();//医院编号
        String page = paramMap.get("page").toString();//页码
        String limit = paramMap.get("limit").toString();//条数
        page = StringUtils.isEmpty(page) ? "1" : page;//页码默认值为1
        limit = StringUtils.isEmpty(limit) ? "1" : limit;//条数默认值为1

        DepartmentQueryVo queryVo = new DepartmentQueryVo();
        queryVo.setHoscode(code);
        Page<Department> pageDepartment = departmentService.findPageDepartment(Integer.parseInt(page), Integer.parseInt(limit), queryVo);
        return Result.ok(pageDepartment);
    }

    /**
     * 根据医院上传的医院编号和科室编号删除指定科室
     *
     * @param dopcode
     * @param hoscode
     * @return
     */
    @PostMapping("/department/remove")
    public Result removeDepartment(String hoscode, String dopcode) {
        departmentService.remove(hoscode, dopcode);
        return Result.ok();
    }

    /**
     * 保存医院上传过来的排班信息
     * @param request
     * @return
     */
    @PostMapping("/saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        //获取医院平台传递过来的医院信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        //使用工具类，将String[]转换成Object，方便后续处理
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        //校验签名

        //保存信息
        scheduleService.save(paramMap);

        return Result.ok();
    }

    /**
     * 分页查询排班信息
     * @return
     */
    @PostMapping("/schedule/list")
    public Result findSchedule(HttpServletRequest request){
        //获取医院平台传递过来的医院信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        //使用工具类，将String[]转换成Object，方便后续处理
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        //校验签名

        String code = paramMap.get("hoscode").toString();//医院编号
        String page = paramMap.get("page").toString();//页码
        String limit = paramMap.get("limit").toString();//条数
        page = StringUtils.isEmpty(page) ? "1" : page;//页码默认值为1
        limit = StringUtils.isEmpty(limit) ? "1" : limit;//条数默认值为1

        ScheduleQueryVo queryVo = new ScheduleQueryVo();
        queryVo.setHoscode(code);
        Page<Schedule> schedules = scheduleService.findPageSchedule(Integer.parseInt(page), Integer.parseInt(limit), queryVo);
        return Result.ok(schedules);
    }

    /**
     * 根据医院上传的医院编号和排班编号删除指定排班
     * @param request
     * @return
     */
    @PostMapping("/schedule/remove")
    public Result remove(HttpServletRequest request){
        //获取医院平台传递过来的医院信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        //使用工具类，将String[]转换成Object，方便后续处理
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        //校验签名

        String hoscode = paramMap.get("hoscode").toString();
        String hosScheduleId = paramMap.get("hosScheduleId").toString();

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }
}

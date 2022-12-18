package com.edu.guohosp.controller;

import com.edu.guohosp.model.hosp.HospitalSet;
import com.edu.guohosp.service.HospitalSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("医院设置管理信息")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    @Resource
    private HospitalSetService hospitalSetService;

    /**
     * 查询医院设置表中的所有信息
     * @return
     */
    @ApiOperation("查询医院设置表中的所有信息")
    @GetMapping("/findAll")
    public List<HospitalSet> findAllHospitalSet(){
        List<HospitalSet> list = hospitalSetService.list();
        return list;
    }

    /**
     * 逻辑删除医院的设置信息
     * @param id
     * @return
     */
    @ApiOperation("逻辑删除医院的设置信息")
    @DeleteMapping("{id}")
    public Boolean removeHospSet(@PathVariable("id") Long id){
        boolean flag = hospitalSetService.removeById(id);
        return flag;
    }
}

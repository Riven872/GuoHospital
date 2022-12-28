package com.edu.guohosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.edu.guohosp.client.DictFeignClient;
import com.edu.guohosp.model.hosp.Hospital;
import com.edu.guohosp.repository.HospitalRepository;
import com.edu.guohosp.service.HospitalService;
import com.edu.guohosp.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Resource
    private DictFeignClient dictFeignClient;

    /**
     * 保存到医院上传过来的信息
     *
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
        else {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
        }
        hospital.setUpdateTime(new Date());
        hospital.setIsDeleted(0);
        hospitalRepository.save(hospital);
    }

    /**
     * 根据医院编码查询医院信息
     *
     * @param code
     * @return
     */
    @Override
    public Hospital getHospitalByHosCode(String code) {
        Hospital hospitalByHoscode = hospitalRepository.getHospitalByHoscode(code);
        return hospitalByHoscode;
    }

    /**
     * 根据条件分页查询医院列表
     *
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        //创建分页对象
        Pageable pageable = PageRequest.of(page - 1, limit);
        //创建条件匹配器
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        //hospitalQueryVo转换成Hospital对象
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo, hospital);
        Example<Hospital> example = Example.of(hospital, matcher);
        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);
        List<Hospital> list = pages.getContent();
        list.stream().forEach(e -> this.setHospitalType(e));
        return pages;
    }

    //设置医院级别
    private Hospital setHospitalType(Hospital hospital) {
        //根据code和value查询医院等级名称
        String hostypeString = this.dictFeignClient.getName("Hostype", hospital.getHostype());
        //查询省市区
        String provinceString = this.dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = this.dictFeignClient.getName(hospital.getCityCode());
        String districtString = this.dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("hostypeString", hostypeString);
        hospital.getParam().put("fullAddress", provinceString + cityString + districtString);
        return hospital;
    }

    /**
     * 更新医院上线状态
     *
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(String id, Integer status) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    /**
     * 查询医院详情
     * @param id
     * @return
     */
    @Override
    public Map getHospById(String id) {
        Map<String, Object> map = new HashMap<>();
        //设置医院等级和地址
        Hospital hospital = this.setHospitalType(hospitalRepository.findById(id).get());
        //将医院信息和预约规则分开
        map.put("hospital", hospital);
        map.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回预约规则
        hospital.setBookingRule(null);
        return map;
    }

    /**
     * 根据医院编号查询医院名称
     * @param hoscode
     * @return
     */
    @Override
    public String getHospName(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        if (hospital !=null) {
            return hospital.getHosname();
        }
        return null;
    }

    @Override
    public List<Hospital> findByHosName(String hosname) {
        return hospitalRepository.findHospitalByHosnameLike(hosname);
    }

    /**
     * 根据医院编号获取医院的预约挂号详情
     * @param hoscode
     * @return
     */
    @Override
    public Map<String, Object> item(String hoscode) {
        Map<String, Object> map = new HashMap<>();
        //设置医院等级和地址
        Hospital hospital = this.setHospitalType(this.getHospitalByHosCode(hoscode));
        //将医院信息和预约规则分开
        map.put("hospital", hospital);
        map.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回预约规则
        hospital.setBookingRule(null);
        return map;
    }
}

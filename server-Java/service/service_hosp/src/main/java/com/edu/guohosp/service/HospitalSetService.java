package com.edu.guohosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.guohosp.model.hosp.HospitalSet;
import com.edu.guohosp.vo.order.SignInfoVo;

public interface HospitalSetService extends IService<HospitalSet> {
    //根据医院编码获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);
}

package com.edu.guohosp.msm.service.impl;

import com.edu.guohosp.msm.service.MsmService;
import com.edu.guohosp.vo.msm.MsmVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/29 0029 18:56
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@Service
public class MsmServiceImpl implements MsmService {
    /**
     * mq使用发送短信
     *
     * @param msmVo
     * @return
     */
    @Override
    public boolean send(MsmVo msmVo) {
        if (!StringUtils.isEmpty(msmVo.getPhone())) {
            //调用发送短信的方法
            return true;
        }
        return false;
    }
}

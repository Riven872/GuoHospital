package com.edu.guohosp.msm.service;

import com.edu.guohosp.vo.msm.MsmVo;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/29 0029 18:55
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
public interface MsmService{
    //mq使用发送短信
    boolean send(MsmVo msmVo);
}

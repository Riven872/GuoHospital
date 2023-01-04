package com.edu.guohosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.guohosp.model.user.UserInfo;
import com.edu.guohosp.vo.user.LoginVo;
import com.edu.guohosp.vo.user.UserAuthVo;

import java.util.Map;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/29 0029 11:12
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
public interface UserInfoService extends IService<UserInfo> {
    //用户手机号登录
    Map<String, Object> loginUser(LoginVo loginVo);

    void userAuth(Long userId, UserAuthVo userAuthVo);
}

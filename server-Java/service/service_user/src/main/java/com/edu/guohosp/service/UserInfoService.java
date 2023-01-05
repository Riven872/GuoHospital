package com.edu.guohosp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.guohosp.model.user.UserInfo;
import com.edu.guohosp.vo.user.LoginVo;
import com.edu.guohosp.vo.user.UserAuthVo;
import com.edu.guohosp.vo.user.UserInfoQueryVo;

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

    //分页、带条件查询用户列表
    Page<UserInfo> selectPage(Page<UserInfo> infoPage, UserInfoQueryVo userInfoQueryVo);

    //用户状态修改（锁定、解锁）
    void lock(Long userId, Integer status);

    //点击用户查看用户的详情信息
    Map<String, Object> show(Long userId);

    //审批修改用户状态
    void approval(Long userId, Integer authStatus);
}

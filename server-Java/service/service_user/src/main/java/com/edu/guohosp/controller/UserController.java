package com.edu.guohosp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.model.user.UserInfo;
import com.edu.guohosp.service.UserInfoService;
import com.edu.guohosp.vo.user.UserInfoQueryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/5 0005 21:53
 * @description: 用户管理接口
 * @project: server-Java
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Resource
    private UserInfoService userInfoService;

    /**
     * 分页、带条件查询用户列表
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, UserInfoQueryVo userInfoQueryVo) {
        Page<UserInfo> infoPage = new Page<>(page, limit);
        Page<UserInfo> pageModel = userInfoService.selectPage(infoPage, userInfoQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * 用户状态修改（锁定、解锁）
     *
     * @param userId
     * @param status
     * @return
     */
    @GetMapping("/lock/{userId}/{status}")
    public Result lock(@PathVariable Long userId, @PathVariable Integer status) {
        userInfoService.lock(userId, status);
        return Result.ok();
    }

    /**
     * 点击用户查看用户的详情信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/show/{userId}")
    public Result show(@PathVariable Long userId) {
        Map<String, Object> map = userInfoService.show(userId);
        return Result.ok(map);
    }

    /**
     * 审批修改用户状态
     *
     * @param userId
     * @param authStatus
     * @return
     */
    @GetMapping("/approval/{userId}/{authStatus}")
    public Result approval(@PathVariable Long userId, @PathVariable Integer authStatus) {
        userInfoService.approval(userId, authStatus);
        return Result.ok();
    }
}

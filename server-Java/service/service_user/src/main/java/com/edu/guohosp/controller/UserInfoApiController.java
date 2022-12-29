package com.edu.guohosp.controller;

import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.service.UserInfoService;
import com.edu.guohosp.vo.user.LoginVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/29 0029 11:11
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {
    @Resource
    private UserInfoService userInfoService;

    /**
     * 用户手机号登录
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        Map<String, Object> map = userInfoService.loginUser(loginVo);
        return Result.ok(map);
    }
}

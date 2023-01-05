package com.edu.guohosp.controller.api;

import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.common.utils.AuthContextHolder;
import com.edu.guohosp.model.user.UserInfo;
import com.edu.guohosp.service.UserInfoService;
import com.edu.guohosp.vo.user.LoginVo;
import com.edu.guohosp.vo.user.UserAuthVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/29 0029 11:11
 * @description: 用户登录、认证接口
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

    /**
     * 根据传递过来的用户信息进行用户的认证
     * @param userAuthVo
     * @param request
     * @return
     */
    @PostMapping("/auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        userInfoService.userAuth(AuthContextHolder.getUserId(request), userAuthVo);
        return Result.ok();
    }

    /**
     * 根据用户id查询用户信息
     * @param request
     * @return
     */
    @GetMapping("/auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        UserInfo userInfo = userInfoService.getById(AuthContextHolder.getUserId(request));
        return Result.ok(userInfo);
    }
}

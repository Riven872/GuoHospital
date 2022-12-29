package com.edu.guohosp.msm.controller;

import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.msm.service.MsmService;
import com.edu.guohosp.msm.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/29 0029 18:56
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/msm")
public class MsmApiController {
    @Resource
    private MsmService msmService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send/{phone}")
    public Result sendCode(@PathVariable String phone) {
        //region 先尝试从Redis中获取验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }
        //endregion

        //region 将验证码放到Redis中
        code = RandomUtil.getSixBitRandom();
        log.info("验证码为:" + code);
        redisTemplate.opsForValue().set(phone, code, 2, TimeUnit.MINUTES);

        //发送短信的已经做过了，就不整合了

        return Result.ok();
        //endregion
    }
}

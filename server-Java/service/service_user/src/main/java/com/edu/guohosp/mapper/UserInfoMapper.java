package com.edu.guohosp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.guohosp.model.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/29 0029 11:14
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}

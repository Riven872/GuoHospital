package com.edu.guohosp.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.guohosp.model.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/7 0007 20:10
 * @description:
 * @project: server-Java
 * @version: 1.0
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderInfo> {
}

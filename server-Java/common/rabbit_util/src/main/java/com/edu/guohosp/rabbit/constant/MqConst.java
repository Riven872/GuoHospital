package com.edu.guohosp.rabbit.constant;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/8 0008 21:41
 * @description:
 * @project: server-Java
 * @version: 1.0
 */
public class MqConst {
    //region 预约下单
    public static final String EXCHANGE_DIRECT_ORDER = "exchange.direct.order";

    public static final String ROUTING_ORDER = "order";

    public static final String QUEUE_ORDER = "queue.order";
    //endregion

    //region 发送短信
    public static final String EXCHANGE_DIRECT_MSM = "exchange.direct.msm";

    public static final String ROUTING_MSM_ITEM = "msm.item";

    public static final String QUEUE_MSM_ITEM = "queue.msm.item";
    //endregion

    //region 定时任务
    public static final String EXCHANGE_DIRECT_TASK = "exchange.direct.task";

    public static final String ROUTING_TASK_8 = "task.8";

    public static final String QUEUE_TASK_8 = "queue.task.8";
    //endregion
}

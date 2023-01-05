package com.edu.guohosp.msm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/29 0029 18:53
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//该模块不需要Sql数据库，因此不用自动加载数据源
@EnableDiscoveryClient
@Slf4j
@ComponentScan(basePackages = "com.edu")
public class ServiceMsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMsmApplication.class, args);
        log.info("service-msm启动成功");
    }
}

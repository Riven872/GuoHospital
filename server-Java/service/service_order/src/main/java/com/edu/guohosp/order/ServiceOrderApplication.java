package com.edu.guohosp.order;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/7 0007 19:59
 * @description:
 * @project: server-Java
 * @version: 1.0
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.edu")
@ComponentScan(basePackages = "com.edu")
@MapperScan(basePackages = "com.edu.guohosp.order.mapper")
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
        log.info("service-order启动成功");
    }
}

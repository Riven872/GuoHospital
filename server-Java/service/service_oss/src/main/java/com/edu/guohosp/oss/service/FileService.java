package com.edu.guohosp.oss.service;

import org.springframework.web.multipart.MultipartFile;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/3 0003 22:35
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
public interface FileService {
    //上传文件到阿里云oss，并返回存储路径url
    String upload(MultipartFile file);
}

package com.edu.guohosp.oss.controller;

import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.oss.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2023/1/3 0003 22:33
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {
    @Resource
    private FileService fileService;

    /**
     * 上传文件到阿里云oss，并返回存储路径url
     * @param file
     * @return
     */
    @PostMapping("/fileUpload")
    public Result fileUpload(MultipartFile file){
        String fileUrl = fileService.upload(file);
        return Result.ok(fileUrl);
    }
}

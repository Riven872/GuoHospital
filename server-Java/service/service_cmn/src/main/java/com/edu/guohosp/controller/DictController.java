package com.edu.guohosp.controller;

import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.model.cmn.Dict;
import com.edu.guohosp.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api("数据字典模块")
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {
    @Resource
    private DictService dictService;
    /**
     * 根据父id查询子数据列表
     * @param parentId
     * @return
     */
    @ApiOperation("根据父id查询子数据列表")
    @GetMapping("/findChildData/{id}")
    public Result findByParentId(@PathVariable("id") Long parentId){
        List<Dict> list = dictService.findChildData(parentId);
        return Result.ok(list);
    }

    /**
     * 导出数据字典
     * @param response 用于设置下载信息
     * @return
     */
    @ApiOperation("导出数据字典")
    @GetMapping("/exportData")
    public void exportDict(HttpServletResponse response){
        dictService.exportDictData(response);
    }

    /**
     * 导入数据字典
     * @param file 读取用户上传的文件
     * @return
     */
    @PostMapping("importData")
    @ApiOperation("导入数据字典")
    public Result importDict(MultipartFile file){
        dictService.importData(file);
        return Result.ok();
    }
}

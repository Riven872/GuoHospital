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
    @PostMapping("/importData")
    @ApiOperation("导入数据字典")
    public Result importDict(MultipartFile file){
        dictService.importData(file);
        return Result.ok();
    }

    /**
     * 根据code和value查询对应的name
     * @param dictCode
     * @param value
     * @return
     */
    @GetMapping("/getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode, @PathVariable String value){
        String dictName = dictService.getDictName(dictCode, value);
        return dictName;
    }

    /**
     * 根据value查询对应的name
     * @param value
     * @return
     */
    @GetMapping("/getName/{value}")
    public String getName(@PathVariable String value){
        String dictName = dictService.getDictName("", value);
        return dictName;
    }

    /**
     * 根据code获取下级节点
     * @param dictCode
     * @return
     */
    @GetMapping("/findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode){
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }
}

package com.edu.guohosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.guohosp.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    //根据父id查询子数据列表
    List<Dict> findChildData(Long parentId);

    //导出数据字典
    void exportDictData(HttpServletResponse response);

    //导入数据字典
    void importData(MultipartFile file);

    //查询对应的name字段
    String getDictName(String dictCode, String value);

    //获得下级节点
    List<Dict> findByDictCode(String dictCode);
}

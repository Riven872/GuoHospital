package com.edu.guohosp.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.edu.guohosp.model.cmn.Dict;
import com.edu.guohosp.service.DictService;
import com.edu.guohosp.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/22 0022 19:38
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@Component
public class DictListener extends AnalysisEventListener<DictEeVo> {
    @Resource
    private DictService dictService;

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        //将导入的数据插入到数据库中
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);
        dictService.save(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

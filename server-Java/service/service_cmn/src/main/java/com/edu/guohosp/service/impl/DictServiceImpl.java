package com.edu.guohosp.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.guohosp.listener.DictListener;
import com.edu.guohosp.mapper.DictMapper;
import com.edu.guohosp.model.cmn.Dict;
import com.edu.guohosp.service.DictService;
import com.edu.guohosp.vo.cmn.DictEeVo;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Resource
    private DictListener dictListener;

    /**
     * 根据父id查询子数据列表
     *
     * @param parentId
     * @return
     */
    @Override
    @Cacheable(value = "dict", keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long parentId) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(parentId != null, Dict::getParentId, parentId);
        List<Dict> list = this.list(wrapper);
        list.forEach(e -> e.setHasChildren(this.hasChild((e.getId()))));
        return list;
    }

    //判断id下是否有子节点
    private boolean hasChild(Long parentId) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(parentId != null, Dict::getParentId, parentId);
        int count = this.count(wrapper);
        return count > 0;
    }

    /**
     * 导出数据字典
     *
     * @param response
     */
    @Override
    @SneakyThrows
    public void exportDictData(HttpServletResponse response) {
        //region 设置下载信息
        //设置响应的内容类型
        response.setContentType("application/vnd.ms-excel");
        //设置编码格式
        response.setCharacterEncoding("utf-8");
        //使用URLEncoder防止中文乱码
        String fileName = URLEncoder.encode("数据字典", "UTF-8");
        //设置返回头
        response.setHeader("Content-disposition", "attachment:filename=" + fileName + ".xlsx");
        //endregion

        //查询所有信息
        List<Dict> list = this.list();
        List<DictEeVo> dictEeVos = JSON.parseArray(JSON.toJSONString(list), DictEeVo.class);
        EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict").doWrite(dictEeVos);
    }

    /**
     * 导入数据字典
     * @param file
     */
    @Override
    @SneakyThrows
    @CacheEvict(value = "dict", allEntries = true)
    public void importData(MultipartFile file) {
        EasyExcel.read(file.getInputStream(), DictEeVo.class, dictListener).sheet().doRead();
    }
}

package com.edu.guohosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.guohosp.common.result.Result;
import com.edu.guohosp.common.utils.MD5;
import com.edu.guohosp.model.hosp.HospitalSet;
import com.edu.guohosp.service.HospitalSetService;
import com.edu.guohosp.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Api("医院设置管理信息")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    @Resource
    private HospitalSetService hospitalSetService;

    /**
     * 查询医院设置表中的所有信息
     *
     * @return
     */
    @ApiOperation("查询医院设置表中的所有信息")
    @GetMapping("/findAll")
    public Result findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    /**
     * 逻辑删除医院的设置信息
     *
     * @param id
     * @return
     */
    @ApiOperation("逻辑删除医院的设置信息")
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable("id") Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 分页、条件查询医院设置
     *
     * @param current
     * @param limit
     * @param queryVo
     * @return
     */
    @ApiOperation("分页、条件查询医院设置")
    @PostMapping("/findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable Long current, @PathVariable Long limit, @RequestBody(required = false) HospitalQueryVo queryVo) {
        Page<HospitalSet> page = new Page<>(current, limit);

        LambdaQueryWrapper<HospitalSet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(!StringUtils.isEmpty(queryVo.getHosname()), HospitalSet::getHosname, queryVo.getHosname())
                .like(!StringUtils.isEmpty(queryVo.getHoscode()), HospitalSet::getHoscode, queryVo.getHoscode());

        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, queryWrapper);

        return Result.ok(hospitalSetPage);
    }

    /**
     * 新增医院设置
     *
     * @param hospitalSet
     * @return
     */
    @ApiOperation("新增医院设置")
    @PostMapping("/saveHospitalSet")
    public Result saveHospSet(@RequestBody HospitalSet hospitalSet) {
        hospitalSet.setStatus(1);//状态为可用
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + new Random().nextInt(1000) + ""));//签名密钥

        boolean isSave = hospitalSetService.save(hospitalSet);
        if (isSave) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 根据id查询对应的医院设置
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询对应的医院设置")
    @GetMapping("/getHospSet/{id}")
    public Result getHospSetById(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);

        if (hospitalSet != null) {
            return Result.ok(hospitalSet);
        } else {
            return Result.fail("该医院设置不存在！");
        }
    }

    /**
     * 修改医院设置的信息
     *
     * @param hospitalSet
     * @return
     */
    @ApiOperation("修改医院设置的信息")
    @PostMapping("/updateHospitalSet")
    public Result updateHospSetById(@RequestBody HospitalSet hospitalSet) {
        boolean isUpdate = hospitalSetService.updateById(hospitalSet);
        if (isUpdate) {
            return Result.ok("修改成功");
        } else {
            return Result.fail("修改失败");
        }
    }

    /**
     * 批量删除医院设置
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量删除医院设置")
    @DeleteMapping("/batchRemoveHospSet")
    public Result batchRemoveHospSet(@RequestBody List<Long> ids) {
        boolean isRemove = hospitalSetService.removeByIds(ids);

        if (isRemove) {
            return Result.ok("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 修改医院的可用状态
     *
     * @param id
     * @param status
     * @return
     */
    @ApiOperation("修改医院的可用状态")
    @PutMapping("/lockHospitalSet/{id}/{status}")
    public Result lockHospSet(@PathVariable Long id, @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        if (hospitalSet != null) {
            hospitalSet.setStatus(status);
            hospitalSetService.updateById(hospitalSet);
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    /**
     * 向管理系统发送密钥
     *
     * @param id
     * @return
     */
    @ApiOperation("向管理系统发送密钥")
    @GetMapping("/sendSignKey/{id}")
    public Result sendSignKey(@PathVariable Long id) {
        HospitalSet set = hospitalSetService.getById(id);
        String hosCode = set.getHoscode();//获取医院编码
        String signKey = set.getSignKey();//获取医院密钥

        //todo 发送短信
        return Result.fail("功能未完成");
    }
}

package com.edu.guohosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.guohosp.common.exception.YyghException;
import com.edu.guohosp.common.helper.JwtHelper;
import com.edu.guohosp.common.result.ResultCodeEnum;
import com.edu.guohosp.enums.AuthStatusEnum;
import com.edu.guohosp.mapper.UserInfoMapper;
import com.edu.guohosp.model.user.Patient;
import com.edu.guohosp.model.user.UserInfo;
import com.edu.guohosp.service.PatientService;
import com.edu.guohosp.service.UserInfoService;
import com.edu.guohosp.vo.user.LoginVo;
import com.edu.guohosp.vo.user.UserAuthVo;
import com.edu.guohosp.vo.user.UserInfoQueryVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Copyright © 2022 https://github.com/Riven872 All rights reserved.
 *
 * @author: Riven
 * @date: 2022/12/29 0029 11:12
 * @description：
 * @project: server-Java
 * @version: 1.0
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private PatientService patientService;

    /**
     * 用户手机号登录
     *
     * @return
     */
    @Override
    public Map<String, Object> loginUser(LoginVo loginVo) {
        //region 手机号或验证码是否为空
        String phone = loginVo.getPhone();
        String code = loginVo.getCode();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //endregion

        //region 判断手机验证码是否与输入的验证码一致
        String redisCode = redisTemplate.opsForValue().get(phone);
        if (!code.equals(redisCode)) {
            throw new YyghException(ResultCodeEnum.CODE_ERROR);
        }
        //endregion

        //region 用户不存在则新增
        UserInfo userInfo = this.getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getPhone, phone));
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setName("");
            userInfo.setPhone(phone);
            userInfo.setStatus(1);
            this.save(userInfo);
        }
        //endregion

        //region 判断该账户是否已被禁用
        if (userInfo.getStatus() == 0) {
            throw new YyghException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }
        //endregion

        //region 返回用户信息
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name", name);
        //jwt生成token
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token", token);
        //endregion

        return map;
    }

    /**
     * 根据用户id和封装的用户信息进行用户信息的认证
     *
     * @param userId
     * @param userAuthVo
     */
    @Override
    public void userAuth(Long userId, UserAuthVo userAuthVo) {
        //region 根据用户id查询用户信息
        UserInfo userInfo = this.getById(userId);
        //endregion

        //region 设置认证信息
        userInfo.setName(userAuthVo.getName());//认证人姓名
        userInfo.setCertificatesType(userAuthVo.getCertificatesType());//证件类型
        userInfo.setCertificatesNo(userAuthVo.getCertificatesNo());//证件编号
        userInfo.setCertificatesUrl(userAuthVo.getCertificatesUrl());//证件路径
        userInfo.setAuthStatus(AuthStatusEnum.AUTH_RUN.getStatus());//认证状态
        //endregion

        //region 更新认证信息
        this.updateById(userInfo);
        //endregion
    }

    /**
     * 分页、带条件查询用户列表
     *
     * @param infoPage
     * @param userInfoQueryVo
     * @return
     */
    @Override
    public Page<UserInfo> selectPage(Page<UserInfo> infoPage, UserInfoQueryVo userInfoQueryVo) {
        //region 拼接查询条件
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(userInfoQueryVo.getKeyword() != null, UserInfo::getName, userInfoQueryVo.getKeyword());//用户名称
        queryWrapper.eq(userInfoQueryVo.getStatus() != null, UserInfo::getStatus, userInfoQueryVo.getStatus());//用户状态
        queryWrapper.eq(userInfoQueryVo.getAuthStatus() != null, UserInfo::getAuthStatus, userInfoQueryVo.getAuthStatus());//认证状态
        queryWrapper.ge(userInfoQueryVo.getCreateTimeBegin() != null, UserInfo::getCreateTime, userInfoQueryVo.getCreateTimeBegin());//开始时间
        queryWrapper.le(userInfoQueryVo.getCreateTimeEnd() != null, UserInfo::getCreateTime, userInfoQueryVo.getCreateTimeEnd());//结束时间
        //endregion

        //region 根据条件分页查询
        Page<UserInfo> page = this.page(infoPage, queryWrapper);
        //endregion

        page.getRecords().forEach(e -> this.packageUserInfo(e));

        return page;
    }

    //将编号变成对应的值的名称
    private void packageUserInfo(UserInfo userInfo) {
        userInfo.getParam().put("authStatusString", AuthStatusEnum.getStatusNameByStatus(userInfo.getAuthStatus()));//处理认证状态编码
        userInfo.getParam().put("statusString", userInfo.getStatus() == 0 ? "锁定" : "正常");//处理用户状态
    }

    /**
     * 用户状态修改（锁定、解锁）
     *
     * @param userId
     * @param status
     */
    @Override
    public void lock(Long userId, Integer status) {
        if (!(status == 1 || status == 0)) {
            return;
        }
        UserInfo userInfo = this.getById(userId);
        if (userInfo != null) {
            userInfo.setStatus(status);
        }
        this.updateById(userInfo);
    }

    /**
     * 点击用户查看用户的详情信息
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> show(Long userId) {
        Map<String, Object> map = new HashMap<>();

        //region 根据userId查询用户信息并将编号改成对应的名称
        UserInfo userInfo = this.getById(userId);
        this.packageUserInfo(userInfo);
        //endregion

        //region 根据userId查询与其相关的就诊人信息
        List<Patient> patientList = patientService.findAllByUserId(userId);
        //endregion

        map.put("userInfo", userInfo);
        map.put("patientList", patientList);
        return map;
    }

    /**
     * 审批修改用户状态
     *
     * @param userId
     * @param authStatus
     */
    @Override
    public void approval(Long userId, Integer authStatus) {
        if (!(authStatus == 2 || authStatus == -1)) {
            return;
        }

        UserInfo userInfo = this.getById(userId);
        if (userInfo == null) {
            return;
        }
        userInfo.setAuthStatus(authStatus);
        this.updateById(userInfo);
    }
}

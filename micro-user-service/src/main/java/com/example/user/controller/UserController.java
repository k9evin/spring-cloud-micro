package com.example.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.BaseResponse;
import com.example.ResultCode;
import com.example.ResultUtils;
import com.example.user.domain.User;
import com.example.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * The type User controller.
 */
@RestController
@Slf4j
@RequestMapping(value = "/user")
@ApiOperation("用户管理")
public class UserController {

    @Resource
    private UserService userService;


    @GetMapping("/{id}")
    @ApiOperation("获取单用户信息")
    @ResponseBody
    @Cacheable(cacheNames = "user", key = "#id", unless = "#result == null")
    public BaseResponse<User> getUser(@PathVariable Long id) {
        if (id >= 0) {
            // 获取用户信息
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                return ResultUtils.error(ResultCode.PARAMS_ERROR, null, "用户不存在");
            }
            // 返回脱敏用户信息
            return ResultUtils.success(user);
        }
        return ResultUtils.error(ResultCode.SYSTEM_ERROR, null, "信息获取失败");
    }


    @GetMapping("/test")
    @ApiOperation("测试普通用户权限")
    public String test() {
        return "Permission: user";
    }
}
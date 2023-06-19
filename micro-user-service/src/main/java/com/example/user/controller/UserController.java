package com.example.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.BaseResponse;
import com.example.ResultCode;
import com.example.ResultUtils;
import com.example.user.domain.User;
import com.example.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type User controller.
 */
@RestController
@Slf4j
@RequestMapping("/user")
@ApiOperation("用户管理")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param user 用户
     * @return 新用户id
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public BaseResponse<Long> userRegister(@RequestBody User user) {
        if (user == null) {
            return ResultUtils.failure(ResultCode.NULL_PARAMS_ERROR, null, "请输入用户名和密码");
        }
        String username = user.getUsername();
        String password = user.getPassword();
        Long role = user.getRole();
        if (StringUtils.isAnyBlank(username, password)) {
            return ResultUtils.failure(ResultCode.NULL_PARAMS_ERROR, null, "用户名或密码不能为空");
        }
        long result = userService.userRegister(username, password, role);
        if (result == -1) {
            return ResultUtils.failure(ResultCode.PARAMS_ERROR, null, "注册错误，请检查后重试");
        }
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param user 用户
     * @return the base response
     */
    @GetMapping("/login")
    @ApiOperation("用户登录")
    @ResponseBody
    public BaseResponse<HashMap<String, Object>> userLogin(@RequestBody User user) {
        if (user == null) {
            return ResultUtils.failure(ResultCode.NULL_PARAMS_ERROR, null, "请输入用户名和密码");
        }
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            return ResultUtils.failure(ResultCode.NULL_PARAMS_ERROR, null, "用户名或密码不能为空");
        }
        HashMap<String, Object> hashMap = userService.userLogin(username, password);
        if (hashMap != null) {
            return ResultUtils.success(hashMap);
        }
        return ResultUtils.failure(ResultCode.PARAMS_ERROR, null, "请检查用户名或密码！");
    }

    /**
     * 获取所有用户脱敏信息
     * 管理员权限
     *
     * @return 所有用户
     */
    @GetMapping("/getAllUsers")
    // @LoginRequired
    @ApiOperation("获取所有用户信息")
    public BaseResponse<List<User>> getAllUsers() {
        List<User> users = userService.list();
        List<User> safeUsers = users.stream().map(user -> userService.safeUser(user)).collect(Collectors.toList());
        return ResultUtils.success(safeUsers);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取单用户信息")
    @ResponseBody
    public BaseResponse<User> getUser(@PathVariable Long id) {
        if (id >= 0) {
            // 获取用户信息
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            User user = userService.getOne(queryWrapper);
            if (user != null) {
                // 返回脱敏用户信息
                User safeUser = userService.safeUser(user);
                return ResultUtils.success(safeUser);
            }
        }
        return ResultUtils.failure(ResultCode.SYSTEM_ERROR, null, "信息获取失败");
    }


    @GetMapping("/admin/hello")
    @ApiOperation("测试管理员权限")
    public String hello() {
        return "Permission: admin";
    }

    @PostMapping("/welcome")
    public String welcome() {
        return "Welcome page";
    }
}
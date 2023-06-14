package com.example.user.controller;


import com.example.BaseResponse;
import com.example.ResultUtils;
import com.example.user.annotation.LoginRequired;
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
            throw new RuntimeException("用户信息不能为空");
        }
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new RuntimeException("用户名或密码不能为空");
        }
        long result = userService.userRegister(username, password);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param user 用户
     * @return the base response
     */
    @GetMapping("login")
    @ApiOperation("用户登录")
    @ResponseBody
    public BaseResponse<HashMap<String, Object>> userLogin(@RequestBody User user) {
        if (user == null) {
            throw new RuntimeException("用户信息不能为空");
        }
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new RuntimeException("用户名或密码不能为空");
        }
        HashMap<String, Object> hashMap = userService.userLogin(username, password);
        if (hashMap != null) {
            return ResultUtils.success(hashMap);
        }
        return ResultUtils.fail(null, "请检查用户名或密码！");
    }

    /**
     * 获取所有用户脱敏信息
     * 请求头中必须要带有token
     *
     * @return 所有用户
     */
    @GetMapping("/getAllUsers")
    @LoginRequired
    @ApiOperation("获取所有用户信息")
    public BaseResponse<List<User>> getAllUsers() {
        List<User> users = userService.list();
        List<User> safeUsers = users.stream().map(user -> userService.safeUser(user)).collect(Collectors.toList());
        return ResultUtils.success(safeUsers);
    }
}
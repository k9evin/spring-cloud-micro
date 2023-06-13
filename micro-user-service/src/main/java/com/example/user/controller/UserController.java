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

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

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
        return ResultUtils.success(hashMap);
    }

    @GetMapping("/hello")
    @LoginRequired
    @ApiOperation("测试")
    public String hello() {
        return "hello";
    }
}
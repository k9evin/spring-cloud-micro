package com.example.user.controller;


import com.example.BaseResponse;
import com.example.ResultUtils;
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

//    @GetMapping("/{id}")
//    @ApiOperation("根据id获取用户信息")
//    public BaseResponse<User> getUser(@PathVariable Long id) {
//        User user = userService.getUser(id);
//        log.info("根据id获取用户信息，用户名称为：{}", user.getUsername());
//        return ResultUtils.success(user);
//    }
//
//    @GetMapping("/search")
//    @ApiOperation("搜索多用户")
//    public BaseResponse<List<User>> searchUsersByIds(@RequestParam List<Long> ids) {
//        List<User> userList = userService.searchUsersByIds(ids);
//        log.info("根据ids获取用户信息，用户列表为：{}", userList);
//        return ResultUtils.success(userList);
//    }
//
//    @GetMapping("/search/{username}")
//    @ApiOperation("根据username搜索用户")
//    public BaseResponse<User> searchUserByUsername(@PathVariable String username) {
//        User user = userService.searchUserByUsername(username);
//        log.info("根据username获取用户信息，用户为：{}", user);
//        return ResultUtils.success(user);
//    }
//
//    @PostMapping("/update")
//    @ApiOperation("更新用户信息")
//    public BaseResponse<String> update(@RequestBody User user) {
//        userService.updateUser(user);
//        log.info("更新用户信息，用户名称为：{}", user.getUsername());
//        return ResultUtils.success("User's data has been updated successfully");
//    }
//
//    @PostMapping("/delete/{id}")
//    @ApiOperation("删除用户")
//    public BaseResponse<Boolean> delete(@PathVariable Long id) {
//        boolean flag = userService.deleteUser(id);
//        log.info("删除用户信息，用户id为：{}", id);
//        return ResultUtils.success(flag);
//    }
}
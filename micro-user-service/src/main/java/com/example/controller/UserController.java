package com.example.controller;


import com.example.BaseResponse;
import com.example.ResultUtils;
import com.example.domain.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/create")
    public BaseResponse<Boolean> createUser(@RequestBody User user) {
        Boolean flag = userService.createUser(user);
        log.info("创建用户，用户名称为：{}", user.getUsername());
        return ResultUtils.success(flag);
    }

    @GetMapping("/{id}")
    public BaseResponse<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        log.info("根据id获取用户信息，用户名称为：{}", user.getUsername());
        return ResultUtils.success(user);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsersByIds(@RequestParam List<Long> ids) {
        List<User> userList = userService.searchUsersByIds(ids);
        log.info("根据ids获取用户信息，用户列表为：{}", userList);
        return ResultUtils.success(userList);
    }

    @GetMapping("/search/{username}")
    public BaseResponse<User> searchUserByUsername(@PathVariable String username) {
        User user = userService.searchUserByUsername(username);
        log.info("根据username获取用户信息，用户为：{}", user);
        return ResultUtils.success(user);
    }

    @PostMapping("/update")
    public BaseResponse<String> update(@RequestBody User user) {
        userService.updateUser(user);
        log.info("更新用户信息，用户名称为：{}", user.getUsername());
        return ResultUtils.success("User's data has been updated successfully");
    }

    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> delete(@PathVariable Long id) {
        boolean flag = userService.deleteUser(id);
        log.info("删除用户信息，用户id为：{}", id);
        return ResultUtils.success(flag);
    }
}
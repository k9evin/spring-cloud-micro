package com.example.user.controller;

import com.example.BaseResponse;
import com.example.ResultCode;
import com.example.ResultUtils;
import com.example.user.domain.User;
import com.example.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@ApiOperation(value = "管理员管理")
@RequestMapping(value = "/admin")
public class AdminController {

    @Resource
    private UserService userService;

    @PostMapping("/deleteUser/{id}")
    @ApiOperation("删除用户")
    @CacheEvict(cacheNames = "user", key = "#id")
    public BaseResponse<Boolean> deleteUser(@PathVariable("id") Long id) {
        if (id == null) {
            return ResultUtils.error(ResultCode.NULL_PARAMS_ERROR, null, "id不能为空");
        }
        boolean result = userService.deleteUser(id);
        if (!result) {
            return ResultUtils.error(ResultCode.PARAMS_ERROR, null, "删除错误，请检查后重试");
        }
        return ResultUtils.success(true);
    }

    /**
     * 获取所有用户脱敏信息
     * 管理员权限
     *
     * @return 所有用户
     */
    @GetMapping("/getAllUsers")
    @CachePut(cacheNames = "users")
    @ApiOperation("获取所有用户信息")
    public BaseResponse<List<User>> getAllUsers() {
        AdminController.log.info("========== Retrieving all users from database ==========");
        List<User> users = userService.list();
        // List<User> safeUsers = users.stream().map(user -> userService.safeUser(user)).collect(Collectors.toList());
        return ResultUtils.success(users);
    }

    @GetMapping("/test")
    @ApiOperation("测试管理员权限")
    public String test() {
        return "Permission: admin";
    }
}

package com.example.user.controller;

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

@RestController
@Slf4j
@ApiOperation(value = "登录注册认证")
@RequestMapping(value = "/auth")
public class AuthController {

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
            return ResultUtils.error(ResultCode.NULL_PARAMS_ERROR, null, "请输入用户名和密码");
        }
        String username = user.getUsername();
        String password = user.getPassword();
        Long role = user.getRoleId();
        if (StringUtils.isAnyBlank(username, password, role.toString())) {
            return ResultUtils.error(ResultCode.NULL_PARAMS_ERROR, null, "用户名或密码不能为空");
        }
        long result = userService.userRegister(username, password, role);
        if (result == -1) {
            return ResultUtils.error(ResultCode.PARAMS_ERROR, null, "注册错误，请检查后重试");
        }
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param user 用户
     * @return the base response
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    @ResponseBody
    public BaseResponse<HashMap<String, Object>> userLogin(@RequestBody User user) {
        if (user == null) {
            return ResultUtils.error(ResultCode.NULL_PARAMS_ERROR, null, "请输入用户名和密码");
        }
        AuthController.log.info("========== 执行login ==========");
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            return ResultUtils.error(ResultCode.NULL_PARAMS_ERROR, null, "用户名或密码不能为空");
        }
        HashMap<String, Object> hashMap = userService.userLogin(username, password);
        if (hashMap.get("token") != null) {
            return ResultUtils.success(hashMap);
        }
        return ResultUtils.error(ResultCode.PARAMS_ERROR, null, "请检查用户名或密码！");
    }

    @PostMapping("/welcome")
    public String welcome() {
        return "登陆成功！";
    }
}

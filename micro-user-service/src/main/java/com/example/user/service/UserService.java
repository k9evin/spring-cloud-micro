package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.user.domain.User;

import java.util.HashMap;


/**
 * The interface User service.
 *
 * @author Administrator
 * @description 针对表 【user】的数据库操作Service
 * @createDate 2023 -06-12 10:53:38
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 用户密码
     * @param role
     * @return long 新用户id
     */
    long userRegister(String username, String password, Long role);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 用户密码
     * @return string token
     */
    HashMap<String, Object> userLogin(String username, String password);

    /**
     * 脱敏用户
     *
     * @param user 用户
     * @return user 脱敏用户
     */
    User safeUser(User user);

    // String getRoleById(Long id);
}

package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.domain.User;
import com.example.user.mapper.UserMapper;
import com.example.user.service.UserService;
import com.example.user.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * The type User service.
 *
 * @author Administrator
 * @description 针对表 【user】的数据库操作Service实现
 * @createDate 2023 -06-12 10:53:38
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final String SALT = "dinglicom";
    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String username, String password, Long role) {
        // 检验参数为空
        if (StringUtils.isAnyBlank(username, password)) {
            return -1;
        }
        // 检验密码长度
        if (password.length() < 6) {
            return -1;
        }
        // 检验用户重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // 密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPwd = bCryptPasswordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptedPwd);
        user.setRoleId(role);
        boolean saveResult = save(user);
        // 注册失败
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public HashMap<String, Object> userLogin(String username, String password) {
        HashMap<String, Object> result = new HashMap<>();
        // 检验参数为空
        if (StringUtils.isAnyBlank(username, password)) {
            result.put("code", -1);
            result.put("msg", "用户名或密码不能为空");
            return result;
        }
        // 检验用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            result.put("code", -1);
            result.put("msg", "用户不存在");
            return result;
        }
        // 检验密码是否正确
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean flag = bCryptPasswordEncoder.matches(password, user.getPassword());
        UserServiceImpl.log.info("flag:{}", flag);
        if (!flag) {
            result.put("code", -1);
            result.put("msg", "密码错误");
            return result;
        }
        // 生成token
        String token = TokenUtils.createToken(user);
        result.put("code", 0);
        result.put("msg", "登录成功");
        result.put("token", token);
        return result;
    }


    @Override
    public User safeUser(User user) {
        if (user == null) {
            return null;
        }
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setRoleId(user.getRoleId());
        return safeUser;
    }

    @Override
    public boolean deleteUser(Long id) {
        if (id == null) {
            return false;
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            return false;
        }
        return removeById(id);
    }
}

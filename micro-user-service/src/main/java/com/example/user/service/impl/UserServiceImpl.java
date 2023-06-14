package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.domain.User;
import com.example.user.mapper.UserMapper;
import com.example.user.service.UserService;
import com.example.user.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private static final String SALT = "dinglicom";
    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String username, String password) {
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
        String encryptedPwd = DigestUtils.md5DigestAsHex((UserServiceImpl.SALT + password).getBytes());
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptedPwd);
        boolean saveResult = save(user);
        // 注册失败
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public HashMap<String, Object> userLogin(String username, String password) {
        // 检验参数为空
        if (StringUtils.isAnyBlank(username, password)) {
            return null;
        }
        // 密码加密
        String encryptedPwd = DigestUtils.md5DigestAsHex((UserServiceImpl.SALT + password).getBytes());
        // 校验用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", encryptedPwd);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }
        // 返回token
        String token = TokenUtils.createToken(user);
        User safeUser = safeUser(user);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("User", safeUser);
        hashMap.put("Token", token);
        return hashMap;
    }

    @Override
    public User safeUser(User user) {
        if (user == null) {
            return null;
        }
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        return safeUser;
    }


}

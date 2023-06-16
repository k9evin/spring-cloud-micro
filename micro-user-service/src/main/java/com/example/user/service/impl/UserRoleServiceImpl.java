package com.example.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.domain.UserRole;
import com.example.user.mapper.UserRoleMapper;
import com.example.user.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【user_role_tbl】的数据库操作Service实现
 * @createDate 2023-06-16 11:21:23
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {

}





package com.example.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.user.domain.Role;
import com.example.user.mapper.RoleMapper;
import com.example.user.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【role_tbl】的数据库操作Service实现
 * @createDate 2023-06-16 11:07:46
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

}





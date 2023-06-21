package com.example.user.component;


import com.example.user.domain.Privilege;
import com.example.user.domain.Role;
import com.example.user.mapper.PrivilegeMapper;
import com.example.user.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;

@Component
@Slf4j
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Resource
    private PrivilegeMapper privilegeMapper;

    @Resource
    private RoleMapper roleMapper;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取url
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestUrl = filterInvocation.getRequestUrl();
        // 放行登录请求
        if ("/login.html".equals(requestUrl)) {
            return Collections.singletonList(() -> "permitAll");
        }
        // 获取拥有url的角色集合
        Privilege privilege = privilegeMapper.getRoleByUrl(requestUrl);
        Role role = roleMapper.selectById(privilege.getId());
        MySecurityMetadataSource.log.info("url:{}, role:{}", requestUrl, role.getRole());
        return Collections.singletonList(role::getRole);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return Collections.emptyList();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
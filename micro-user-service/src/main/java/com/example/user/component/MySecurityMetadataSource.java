package com.example.user.component;


import com.example.user.domain.Role;
import com.example.user.mapper.PrivilegeMapper;
import com.example.user.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

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
        // 解决url中有参数的情况 例如 /admin/deleteUser/1
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        requestUrl = antPathMatcher.match("/**/{userId:[0-9]+}", requestUrl) ? requestUrl.substring(0, requestUrl.lastIndexOf("/")) : requestUrl;
        // 获取拥有url的角色集合
        Long roleId = privilegeMapper.getRoleIdByUrl(requestUrl);
        // 如果访问的url没有限制权限
        if (roleId == null) {
            MySecurityMetadataSource.log.info("url:{}, role:{}", requestUrl, "ROLE_ANONYMOUS");
            return Collections.singletonList(() -> "ROLE_ANONYMOUS");
        }
        Role role = roleMapper.selectById(roleId);
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
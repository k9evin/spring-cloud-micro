package com.example.user.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
public class MyAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws InsufficientAuthenticationException {
        // 获取用户权限
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        for (ConfigAttribute configAttribute : configAttributes) {
            // 获取访问该url所需的权限
            String attribute = configAttribute.getAttribute();
            for (GrantedAuthority authority : grantedAuthorities) {
                // 判断用户权限是否匹配, 允许管理员访问所有url
                if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                    MyAccessDecisionManager.log.info("管理员权限 {}", attribute);
                    return;
                }
                if (attribute.equals(authority.getAuthority()) || "ROLE_ANONYMOUS".equals(attribute)) {
                    MyAccessDecisionManager.log.info("匹配成功 {}", attribute);
                    return;
                }
            }
        }
        // 抛出异常
        throw new AccessDeniedException("权限不足,无法访问");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
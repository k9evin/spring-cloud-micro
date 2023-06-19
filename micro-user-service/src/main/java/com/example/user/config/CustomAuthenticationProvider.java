package com.example.user.config;

import com.example.user.service.impl.UserDetailServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Custom authentication provider.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailServiceImpl userDetailsServiceImpl;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
        // 密码校验
        boolean flag = passwordEncoder.matches(password, userDetails.getPassword());
        // 校验通过
        if (flag) {
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        }
        throw new AuthenticationException("用户名或密码错误") {
            private static final long serialVersionUID = -3649642162690908928L;
        };
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

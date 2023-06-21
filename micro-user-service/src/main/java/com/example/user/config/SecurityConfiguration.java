package com.example.user.config;

import com.example.user.component.CustomAuthenticationProvider;
import com.example.user.component.MyAccessDecisionManager;
import com.example.user.component.MySecurityMetadataSource;
import com.example.user.handler.MyAccessDeniedHandler;
import com.example.user.handler.MyAuthenticationFailureHandler;
import com.example.user.handler.MyAuthenticationSuccessHandler;
import com.example.user.handler.MyLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

/**
 * The type Security configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Resource
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private MySecurityMetadataSource mySecurityMetadataSource;

    @Resource
    private MyAccessDecisionManager myAccessDecisionManager;

    // 配置角色继承
    // @Bean
    // RoleHierarchy roleHierarchy() {
    //     RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    //     final String hierarchy = "ROLE_admin > ROLE_user";
    //     roleHierarchy.setHierarchy(hierarchy);
    //     return roleHierarchy;
    // }


    /**
     * 加密方式
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources",
                        "/swagger-resources/configuration/security",
                        "/swagger-ui.html", "/doc.html");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .and()
                .formLogin().loginPage("/login.html").loginProcessingUrl("/auth/login").permitAll()
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler);

        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(mySecurityMetadataSource);
                        o.setAccessDecisionManager(myAccessDecisionManager);
                        return o;
                    }
                })
                .antMatchers("/auth/**").permitAll()  // 注册登录接口放行
                // .antMatchers("/admin/**").hasRole("ADMIN") // admin接口只允许admin访问
                // .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // user接口只允许user和admin访问
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        //
        // // 登录
        // http.formLogin().loginPage("/login.html").loginProcessingUrl("/auth/login").permitAll()
        //         .successHandler(myAuthenticationSuccessHandler)
        //         .failureHandler(myAuthenticationFailureHandler);
        // .successForwardUrl("/welcome");

        // 权限不足处理
        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);

        // 登出
        http.logout()
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .deleteCookies("JSESSIONID");
    }
}

package com.example.user.interceptor;

import com.example.user.annotation.LoginRequired;
import com.example.user.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Token");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent((LoginRequired.class))) {
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            if (loginRequired.required()) {
                if (token == null) {
                    throw new RuntimeException("无Token，请登录后重试！");
                }
                JwtInterceptor.log.info("验证token: {}", token);
                TokenUtils.verifyToken(token);
            }
        }
        return true;
    }
}

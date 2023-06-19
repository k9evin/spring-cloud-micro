package com.example.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * The type Token utils.
 */
@Slf4j
public class TokenUtils {

    private static final long EXPIRE_TIME = 60 * 60 * 1000;
    private static final String SECRET = "dinglicom";

    /**
     * 生成JWT token
     *
     * @param user 用户
     * @return token
     */
    public static String createToken(User user) {
        String token;
        Date expiredTime = new Date(System.currentTimeMillis() + TokenUtils.EXPIRE_TIME);
        token = JWT.create()
                .withIssuer("spring-cloud-microservice")
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(expiredTime)
                .sign(Algorithm.HMAC256(TokenUtils.SECRET));
        return token;
    }

    /**
     * 校验token是否有效
     *
     * @param token token值
     * @return 是否有效
     */
    public static boolean verifyToken(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TokenUtils.SECRET)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            throw new RuntimeException("Token expired, log in first");
        }
    }

    public static Long getUserId(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TokenUtils.SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        System.out.println(decodedJWT.getClaim("id").asLong());
        return decodedJWT.getClaim("id").asLong();
    }

    /**
     * 从cookies中获取token
     *
     * @param request the request
     * @return the token
     */
    public static String getToken(HttpServletRequest request) {
        return request.getHeader("token");
    }
}

package com.example.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.user.domain.User;

import java.util.Date;

/**
 * The type Token utils.
 */
public class TokenUtils {


    private static final long EXPIRE_TIME = 60 * 1000;
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
        } catch (Exception e) {
            throw new RuntimeException("Token已无效，请重试！");
        }
    }
}

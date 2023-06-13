package com.example.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.user.domain.User;

import java.util.Date;

public class TokenUtils {
    private static final long EXPIRE_TIME = 10 * 60 * 60 * 1000;
    private static final String SECRET = "dinglicom";

    public static String createToken(User user) {
        String token;
        Date expiredTime = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        token = JWT.create()
                .withIssuer("spring-cloud-microservice")
                .withClaim("username", user.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(expiredTime)
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    public static boolean verifyToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).withIssuer("spring-cloud-microservice").build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date(System.currentTimeMillis()));
    }
}

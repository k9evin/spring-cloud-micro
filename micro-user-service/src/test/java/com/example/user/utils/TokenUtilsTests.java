package com.example.user.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokenUtilsTests {

    @Test
    void contextLoads() {
        boolean flag = TokenUtils.verifyToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTY4NjY4MTAxNiwidXNlcm5hbWUiOiJhZG1pbiJ9.NYWwQd2RFMQ-HKS7_k_T2sThirORqWWQIs2WXPR7UVE");
        System.out.println(flag);
    }

}

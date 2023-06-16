package com.example.user.utils;

import org.junit.jupiter.api.Test;

class TokenUtilsTest {

    @Test
    void getUserId() {
        TokenUtils.getUserId("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzcHJpbmctY2xvdWQtbWljcm9zZXJ2aWNlIiwiaWQiOjEsImV4cCI6MTY4NjkwNzY2NiwiaWF0IjoxNjg2OTA0MDY2LCJ1c2VybmFtZSI6ImFkbWluIn0.9feZ0aGnQQyV2H7DKakUL7RddBuK5d3zp6uV1C-J7PE");
    }
}
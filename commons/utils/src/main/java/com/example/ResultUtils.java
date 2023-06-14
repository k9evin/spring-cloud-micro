package com.example;

public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "success");
    }

    public static <T> BaseResponse<T> fail(T data) {
        return new BaseResponse<>(400, data, "failed");
    }

    public static <T> BaseResponse<T> fail(T data, String description) {
        return new BaseResponse<>(400, data, "failed", description);
    }
}

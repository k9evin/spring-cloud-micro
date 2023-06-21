package com.example;

public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ResultCode.SUCCESS.getCode(), data, ResultCode.SUCCESS.getMessage());
    }

    public static <T> BaseResponse<T> success(T data, String description) {
        return new BaseResponse<>(ResultCode.SUCCESS.getCode(), data, description);
    }

    public static <T> BaseResponse<T> failure(ResultCode code, T data, String description) {
        return new BaseResponse<>(code.getCode(), data, description);
    }
}

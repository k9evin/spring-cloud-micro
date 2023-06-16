package com.example;

/**
 * 自定义状态码
 */
public enum ResultCode {
    /**
     * Success result code.
     */
    SUCCESS(0, "操作成功"),
    /**
     * Null params error result code.
     */
    NULL_PARAMS_ERROR(40001, "请求参数为空"),
    /**
     * Params error result code.
     */
    PARAMS_ERROR(40000, "请求参数错误"),
    /**
     * Not login result code.
     */
    NOT_LOGIN(40100, "未登录"),
    /**
     * No permission result code.
     */
    NO_PERMISSION(40101, "权限不足"),
    /**
     * System error result code.
     */
    SYSTEM_ERROR(50000, "系统内部错误");
    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}

package com.example;


import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 3977796148001553541L;
    private int code;
    private T data;
    private String description;

    BaseResponse(int code, T data, String description) {
        this.code = code;
        this.data = data;
        this.description = description;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }
}

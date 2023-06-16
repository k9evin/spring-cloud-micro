package com.example;


import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 3977796148001553541L;
    private ResultCode code;
    private T data;
    private String description;

    BaseResponse(ResultCode code, T data, String description) {
        this.code = code;
        this.data = data;
        this.description = description;
    }

    public BaseResponse(ResultCode code, T data) {
        this(code, data, "");
    }
}

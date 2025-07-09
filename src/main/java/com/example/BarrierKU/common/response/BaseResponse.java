package com.example.BarrierKU.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class BaseResponse<T> {
    @JsonProperty("isSuccess")
    private final boolean isSuccess;
    private final int code;
    private final String message;
    private final T result;

    public BaseResponse(ResponseCode responseCode, T result) {
        this.isSuccess = responseCode.isSuccess();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.result = result;
    }

    public BaseResponse(ResponseCode responseCode) {
        this.isSuccess = responseCode.isSuccess();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.result = null;
    }

    public static <T> BaseResponse<T> ok(T result) {
        return new BaseResponse<>(ResponseCode.SUCCESS, result);
    }
}

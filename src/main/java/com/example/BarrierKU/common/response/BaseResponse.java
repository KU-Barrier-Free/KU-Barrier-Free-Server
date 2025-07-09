package com.example.BarrierKU.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"success", "code", "message", "result"})
public class BaseResponse<T> {

    @Schema(description = "성공 여부", example = "true")
    private final boolean success;

    @Schema(description = "응답 코드", example = "200")
    private final int code;

    @Schema(description = "응답 메세지", example = "요청에 성공하였습니다.")
    private final String message;

    private final T result;

    public BaseResponse(ResponseCode responseCode, T result) {
        this.success = responseCode.isSuccess();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.result = result;
    }

    public BaseResponse(ResponseCode responseCode) {
        this.success = responseCode.isSuccess();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.result = null;
    }

    public static <T> BaseResponse<T> ok(T result) {
        return new BaseResponse<>(ResponseCode.SUCCESS, result);
    }
}

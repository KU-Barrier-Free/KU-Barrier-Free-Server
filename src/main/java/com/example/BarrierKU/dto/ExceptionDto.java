package com.example.BarrierKU.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.h2.api.ErrorCode;

@Getter
public class ExceptionDto {
    @NotNull
    private final Integer code;

    @NotNull
    private final String message;

    public ExceptionDto(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}

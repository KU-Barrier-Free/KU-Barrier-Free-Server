package com.example.BarrierKU.common.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum ResponseCode {

    // 1000 번대 : global 요청 성공/실패
    SUCCESS(true, 200, "요청에 성공하였습니다."),
    INVALID_PATH_VARIABLE_TYPE(false, 400, "요청 경로에 포함된 값의 타입이 올바르지 않습니다. 올바른 형식으로 요청해주세요."),

    // 2000 번대 : home

    // 3000 번대 : building
    BUILDING_NOT_FOUND(false, 404, "해당 건물을 찾을 수 없습니다."),

    // 4000 번대 : facility
    FACILITY_NOT_FOUND(false, 404, "해당 편의시설을 찾을 수 없습니다.");

    // 5000 번대 : place


    // 6000 번대 : path

    private boolean isSuccess;
    private int code;
    private String message;
}

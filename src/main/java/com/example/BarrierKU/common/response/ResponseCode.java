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

    // 테스트
    TEST_EXCEPTION(true, 100, "테스트용 예외입니다."),

    // global 요청 성공
    SUCCESS(true, 200, "요청에 성공하였습니다."),

    // 공통 에러
    INVALID_PATH_VARIABLE_TYPE(false, 400, "요청 경로에 포함된 값의 타입이 올바르지 않습니다. 올바른 형식으로 요청해주세요."),
    BAD_REQUEST(false, 400, "유효하지 않은 요청입니다."),
    API_NOT_FOUND(false, 404, "존재하지 않는 API입니다."),
    METHOD_NOT_ALLOWED(false, 405, "유효하지 않은 Http 메서드입니다."),
    INTERNAL_SERVER_ERROR(false, 500, "서버 내부 오류입니다."),


    // home

    // building
    BUILDING_NOT_FOUND(false, 404, "해당 건물을 찾을 수 없습니다."),

    // facility
    FACILITY_NOT_FOUND(false, 404, "해당 편의시설을 찾을 수 없습니다.");

    // place


    // path

    private boolean isSuccess;
    private int code;
    private String message;
}

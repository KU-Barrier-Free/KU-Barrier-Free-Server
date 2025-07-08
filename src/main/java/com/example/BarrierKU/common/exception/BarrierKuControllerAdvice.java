package com.example.BarrierKU.common.exception;

import com.example.BarrierKU.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.example.BarrierKU.common.response.ResponseCode.INVALID_PATH_VARIABLE_TYPE;

@Slf4j
@RestControllerAdvice
public class BarrierKuControllerAdvice {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<?>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        String errorMessage = String.format("요청 파라미터 '%s'는 '%s' 타입이어야 합니다. 현재 값: '%s'",
                e.getName(), e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "알 수 없음", e.getValue());
        log.warn("잘못된 파라미터 타입 오류: {}", errorMessage);
        return new ResponseEntity<>(new BaseResponse<>(INVALID_PATH_VARIABLE_TYPE), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BarrierKuException.class)
    public ResponseEntity<BaseResponse<?>> handleBarrierKuException(BarrierKuException e) {
        return new ResponseEntity<>(new BaseResponse<>(e.getResponseCode()), HttpStatus.BAD_REQUEST);

    }
}

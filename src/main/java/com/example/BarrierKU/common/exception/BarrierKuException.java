package com.example.BarrierKU.common.exception;

import com.example.BarrierKU.common.response.ResponseCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class BarrierKuException extends RuntimeException {

    private static final String APP_PACKAGE_PREFIX = "com.example.BarrierKU";
    private final ResponseCode responseCode;

    public BarrierKuException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;

        StackTraceElement[] stackTrace = getStackTrace();

        StringBuilder filtered = new StringBuilder();

        filtered.append(this.getClass().getSimpleName())
                .append(" - message : ")
                .append(responseCode.getMessage())
                .append("\n");

        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().startsWith(APP_PACKAGE_PREFIX)) {
                filtered.append("\tat ").append(element).append("\n");
            }
        }

        log.warn(filtered.toString());
    }
}

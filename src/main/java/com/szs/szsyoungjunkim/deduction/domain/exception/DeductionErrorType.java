package com.szs.szsyoungjunkim.deduction.domain.exception;

import com.szs.szsyoungjunkim.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum DeductionErrorType implements ErrorType {

    INVALID_RESPONSE_VALUE(HttpStatus.BAD_GATEWAY, "스크래핑 응답이 null 입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    DeductionErrorType(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return this.name();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

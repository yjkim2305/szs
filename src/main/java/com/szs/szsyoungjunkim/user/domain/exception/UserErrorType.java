package com.szs.szsyoungjunkim.user.domain.exception;

import com.szs.szsyoungjunkim.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum UserErrorType implements ErrorType {
    INVALID_USER(HttpStatus.OK, "회원가입에 적합하지 않는 회원입니다.")
    ;


    private final HttpStatus httpStatus;
    private final String message;

    UserErrorType(HttpStatus httpStatus, String message) {
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

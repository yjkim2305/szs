package com.szs.szsyoungjunkim.common.exception;

import org.springframework.http.HttpStatus;

public enum AccessTokenErrorType implements ErrorType {
    EXPIRE_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "accessToken이 만료되었습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "적합하지 않은 accessToken 입니다.")
    ;


    private final HttpStatus httpStatus;
    private final String message;

    AccessTokenErrorType(HttpStatus httpStatus, String message) {
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

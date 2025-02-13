package com.szs.szsyoungjunkim.refund.domain.exception;

import com.szs.szsyoungjunkim.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum RefundErrorType implements ErrorType {

    NOT_EXIST_USER_REFUND(HttpStatus.BAD_REQUEST, "스크래핑을 먼저 진행해주세요."),
    NOT_EXIST_BRACKET(HttpStatus.NOT_FOUND, "해당 세율 구간을 찾을 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    RefundErrorType(HttpStatus httpStatus, String message) {
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

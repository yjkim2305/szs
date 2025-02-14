package com.szs.szsyoungjunkim.deduction.api.exception;

import com.szs.szsyoungjunkim.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum OpenFeignErrorType implements ErrorType {
    API_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    API_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "요청한 서버 내부 오류가 발생했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    OpenFeignErrorType(HttpStatus httpStatus, String message) {
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

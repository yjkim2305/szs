package com.szs.szsyoungjunkim.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CoreException extends RuntimeException {
    private final ErrorType errorType;
    private final HttpStatus httpStatus;
    private final String message;

    public CoreException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.httpStatus = errorType.getHttpStatus();
        this.message = errorType.getMessage();
    }

    public CoreException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
        this.httpStatus = errorType.getHttpStatus();
        this.message = message;
    }
}

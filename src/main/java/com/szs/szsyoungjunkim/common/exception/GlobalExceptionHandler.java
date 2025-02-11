package com.szs.szsyoungjunkim.common.exception;

import com.szs.szsyoungjunkim.common.api.response.ApiRes;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ApiRes<?>> handleCoreException(CoreException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ApiRes.error(e.getHttpStatus().value(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiRes<?>> handleException(Exception e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiRes.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}

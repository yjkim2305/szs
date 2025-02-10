package com.szs.szsyoungjunkim.common.exception;

public record ErrorResponse(
        String code,
        String message
) {
}

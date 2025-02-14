package com.szs.szsyoungjunkim.common.config;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.deduction.api.exception.OpenFeignErrorType;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.util.Map;
import java.util.function.Supplier;

public class OpenFeignErrorDecoder implements ErrorDecoder {

    private static final Map<Integer, Supplier<Exception>> ERROR_MAP = Map.of(
            404, () -> new CoreException(OpenFeignErrorType.API_NOT_FOUND_EXCEPTION),
            500, () -> new CoreException(OpenFeignErrorType.API_INTERNAL_SERVER_ERROR)
    );

    @Override
    public Exception decode(String s, Response response) {
        int status = response.status();
        return ERROR_MAP.getOrDefault(status, () -> new RuntimeException("Unexpected Error: " + status)).get();
    }
}

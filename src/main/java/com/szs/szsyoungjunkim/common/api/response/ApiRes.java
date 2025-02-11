package com.szs.szsyoungjunkim.common.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiRes<T> {
    @Schema(description = "응답 코드", example = "200")
    private int status;

    @Schema(description = "응답 메시지", example = "success")
    private String message;

    private T data;


    public static <T> ApiRes<T> createSuccess(T data) {
        return new ApiRes<>(HttpStatus.OK.value(), "success", data);
    }

    public static ApiRes<?> createSuccessWithNoContent() {
        return new ApiRes<>(HttpStatus.OK.value(), "success", null);
    }

    public static ApiRes<?> error(int status, String message) {
        return new ApiRes<>(status, message, null);
    }

    private ApiRes(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
package com.szs.szsyoungjunkim.user.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserCreateReqest(

        @Schema(description = "사용자 아이디", example = "kw68") @NotBlank
        String userId,
        @Schema(description = "사용자 비밀번호", example = "123456") @NotBlank
        String password,
        @Schema(description = "사용자 이름", example = "관우") @NotBlank
        String name,
        @Schema(description = "사용자 주민등록번호", example = "681108-1582816") @NotBlank
        String regNo
) {
}

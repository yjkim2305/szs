package com.szs.szsyoungjunkim.user.api.response;

import com.szs.szsyoungjunkim.user.application.dto.UserLoginDto;

public record UserLoginResponse(
        String accessToken,
        String refreshToken
) {
    public static UserLoginResponse of(UserLoginDto userLoginDto) {
        return new UserLoginResponse(userLoginDto.accessToken(), userLoginDto.refreshToken());
    }
}

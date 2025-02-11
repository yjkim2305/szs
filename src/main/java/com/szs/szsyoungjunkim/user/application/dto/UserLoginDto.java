package com.szs.szsyoungjunkim.user.application.dto;

public record UserLoginDto(
        String accessToken,
        String refreshToken
) {
    public static UserLoginDto of(String accessToken, String refreshToken) {
        return new UserLoginDto(accessToken, refreshToken);
    }
}

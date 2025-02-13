package com.szs.szsyoungjunkim.refresh.application.dto;

public record RefreshReissueDto(
        String accessToken,
        String refreshToken
) {
    public static RefreshReissueDto of(String accessToken, String refreshToken) {
        return new RefreshReissueDto(accessToken, refreshToken);
    }
}
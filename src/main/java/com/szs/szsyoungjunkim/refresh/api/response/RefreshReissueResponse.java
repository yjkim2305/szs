package com.szs.szsyoungjunkim.refresh.api.response;

import com.szs.szsyoungjunkim.refresh.application.dto.RefreshReissueDto;

public record RefreshReissueResponse(
        String accessToken,
        String refreshToken
) {
    public static RefreshReissueResponse from(RefreshReissueDto refreshReissueDto) {
        return new RefreshReissueResponse(refreshReissueDto.accessToken(), refreshReissueDto.refreshToken());
    }
}

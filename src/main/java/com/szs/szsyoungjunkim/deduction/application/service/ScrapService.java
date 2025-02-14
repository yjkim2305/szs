package com.szs.szsyoungjunkim.deduction.application.service;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.deduction.api.exception.DeductionErrorType;
import com.szs.szsyoungjunkim.deduction.feign.ScrapClient;
import com.szs.szsyoungjunkim.deduction.feign.request.ScrapRequest;
import com.szs.szsyoungjunkim.deduction.feign.response.ClientApiRes;
import com.szs.szsyoungjunkim.deduction.feign.response.ScrapResponse;
import com.szs.szsyoungjunkim.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapClient scrapClient;

    public ScrapResponse getScrapData(User user) {
        ClientApiRes<ScrapResponse> scrapResponse = scrapClient.scrapData(ScrapRequest.of(user.getName(), user.getRegNo()));

        if (scrapResponse == null || scrapResponse.getData() == null) {
            throw new CoreException(DeductionErrorType.INVALID_RESPONSE_VALUE);
        }

        return scrapResponse.getData();
    }

}

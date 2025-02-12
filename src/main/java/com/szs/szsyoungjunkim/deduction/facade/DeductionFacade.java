package com.szs.szsyoungjunkim.deduction.facade;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.deduction.application.service.DeductionService;
import com.szs.szsyoungjunkim.deduction.domain.exception.DeductionErrorType;
import com.szs.szsyoungjunkim.deduction.feign.ScrapClient;
import com.szs.szsyoungjunkim.deduction.feign.request.ScrapRequest;
import com.szs.szsyoungjunkim.deduction.feign.response.ClientApiRes;
import com.szs.szsyoungjunkim.deduction.feign.response.ScrapResponse;
import com.szs.szsyoungjunkim.user.application.service.UserService;
import com.szs.szsyoungjunkim.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeductionFacade {

    private final UserService userService;
    private final DeductionService deductionService;
    private final ScrapClient scrapClient;

    public void userScrap(String userId) {
        User user = userService.findByUserId(userId);

        ClientApiRes<ScrapResponse> scrapResponse = scrapClient.scrapData(ScrapRequest.of(user.getName(), user.getRegNo()));

        if (scrapResponse == null || scrapResponse.getData() == null) {
            throw new CoreException(DeductionErrorType.INVALID_RESPONSE_VALUE);
        }

        deductionService.saveDeductions(scrapResponse.getData().deduction(), userId);
    }
}

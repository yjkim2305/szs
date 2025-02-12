package com.szs.szsyoungjunkim.deduction.facade;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.deduction.application.service.DeductionService;
import com.szs.szsyoungjunkim.deduction.domain.exception.DeductionErrorType;
import com.szs.szsyoungjunkim.deduction.feign.ScrapClient;
import com.szs.szsyoungjunkim.deduction.feign.request.ScrapRequest;
import com.szs.szsyoungjunkim.deduction.feign.response.ClientApiRes;
import com.szs.szsyoungjunkim.deduction.feign.response.ScrapResponse;
import com.szs.szsyoungjunkim.refund.application.service.RefundService;
import com.szs.szsyoungjunkim.refund.application.service.dto.RefundCreateCommand;
import com.szs.szsyoungjunkim.user.application.service.UserService;
import com.szs.szsyoungjunkim.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeductionFacade {

    private final UserService userService;
    private final DeductionService deductionService;
    private final ScrapClient scrapClient;
    private final RefundService refundService;

    @Transactional
    public void userScrap(String userId) {
        User user = userService.findByUserId(userId);

        ClientApiRes<ScrapResponse> scrapResponse = scrapClient.scrapData(ScrapRequest.of(user.getName(), user.getRegNo()));

        if (scrapResponse == null || scrapResponse.getData() == null) {
            throw new CoreException(DeductionErrorType.INVALID_RESPONSE_VALUE);
        }

        Integer taxYear = scrapResponse.getData().deduction().creditCardDeductionResponse().year();

        if (deductionService.existsByUserIdAndTaxYear(userId, taxYear) || refundService.existsByUserIdAndTaxYear(userId, taxYear)) {
            throw new CoreException(DeductionErrorType.EXIST_TAX_YEAR_DEDUCTION);
        }

        deductionService.saveDeductions(scrapResponse.getData().deduction(), userId);

        refundService.saveRefund(RefundCreateCommand.of(taxYear,
                scrapResponse.getData().totalIncomeTax(),
                scrapResponse.getData().deduction().taxCredit(),
                userId));
    }
}

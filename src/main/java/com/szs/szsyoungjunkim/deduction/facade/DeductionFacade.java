package com.szs.szsyoungjunkim.deduction.facade;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.deduction.application.service.DeductionService;
import com.szs.szsyoungjunkim.deduction.application.service.ScrapService;
import com.szs.szsyoungjunkim.deduction.api.exception.DeductionErrorType;
import com.szs.szsyoungjunkim.deduction.feign.response.ScrapResponse;
import com.szs.szsyoungjunkim.refund.application.service.RefundService;
import com.szs.szsyoungjunkim.refund.application.dto.RefundCreateCommand;
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
    private final ScrapService scrapService;
    private final RefundService refundService;

    @Transactional
    public void userScrap(String userId) {
        User user = userService.findByUserId(userId);

        ScrapResponse scrapResponse = scrapService.getScrapData(user);

        Integer taxYear = scrapResponse.deduction().creditCardDeductionResponse().year();

        if (deductionService.existsByUserIdAndTaxYear(userId, taxYear) || refundService.existsByUserIdAndTaxYear(userId, taxYear)) {
            throw new CoreException(DeductionErrorType.EXIST_TAX_YEAR_DEDUCTION);
        }

        deductionService.saveDeductions(scrapResponse.deduction(), userId);

        refundService.saveRefund(RefundCreateCommand.of(taxYear,
                scrapResponse.totalIncomeTax(),
                scrapResponse.deduction().taxCredit(),
                userId));
    }
}

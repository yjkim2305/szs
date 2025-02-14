package com.szs.szsyoungjunkim.refund.facade;

import com.szs.szsyoungjunkim.deduction.application.service.DeductionService;
import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.refund.application.service.FinalTaxService;
import com.szs.szsyoungjunkim.refund.application.service.RefundService;
import com.szs.szsyoungjunkim.refund.domain.Refund;
import com.szs.szsyoungjunkim.refund.facade.dto.FinalTaxDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RefundFacade {

    private final DeductionService deductionService;
    private final RefundService refundService;
    private final FinalTaxService finalTaxService;

    public FinalTaxDto getFinalTaxAmount(String userId) {
        List<Deduction> deductions = deductionService.findByUserId(userId);
        Refund refund = refundService.findByUserId(userId);
        return FinalTaxDto.of(finalTaxService.calculateFinalTax(deductions, refund));
    }
}

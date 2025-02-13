package com.szs.szsyoungjunkim.refund.facade;

import com.szs.szsyoungjunkim.deduction.application.service.DeductionService;
import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.refund.application.service.RefundService;
import com.szs.szsyoungjunkim.refund.domain.FinalTax;
import com.szs.szsyoungjunkim.refund.domain.Refund;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RefundFacade {

    private final DeductionService deductionService;
    private final RefundService refundService;

    public String getFinalTaxAmount(String userId) {
        List<Deduction> deduction = deductionService.findByUserId(userId);
        Refund refund = refundService.findByUserId(userId);
        return String.format("%,d", FinalTax.of(deduction, refund).finalTaxCalculate());
    }
}

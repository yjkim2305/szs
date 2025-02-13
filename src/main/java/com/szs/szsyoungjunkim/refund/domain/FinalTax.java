package com.szs.szsyoungjunkim.refund.domain;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.refund.domain.enums.TaxBracket;
import com.szs.szsyoungjunkim.refund.domain.exception.RefundErrorType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FinalTax {
    List<Deduction> deductions;
    Refund refund;

    @Builder
    private FinalTax(List<Deduction> deductions, Refund refund) {
        this.deductions = deductions;
        this.refund = refund;
    }

    public static FinalTax of(List<Deduction> deductions, Refund refund) {
        return FinalTax.builder()
                .deductions(deductions)
                .refund(refund)
                .build();
    }

    public long finalTaxCalculate() {
        //소득공제
        long taxDeduction = deductions.stream()
                .map(d -> BigDecimal.valueOf(d.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(0, RoundingMode.HALF_UP)
                .longValue();

        //산출세액
        long taxAmount = taxAmountCalculate(taxDeduction);

        //세액공제
        long totalTaxCredit = (refund.getTotalTaxCredit() != null) ? Math.round(refund.getTotalTaxCredit()) : 0L;

        return taxAmount - totalTaxCredit;
    }

    private long taxAmountCalculate(long taxDeduction) {
        //과세표준
        long taxBase = refund.getTotalIncome() - taxDeduction;

        TaxBracket taxBracket = null;
        for (TaxBracket bracket : TaxBracket.values()) {
            if (taxBase > bracket.getLowerBound() && taxBase <= bracket.getUpperBound()) {
                taxBracket = bracket;
                break;
            }
        }

        if (taxBracket == null) {
            throw new CoreException(RefundErrorType.NOT_EXIST_BRACKET);
        }

        //산출세액 = 과세표준 * 기본세율
        return taxBracket.calculateTax(taxBase)
                .setScale(0, RoundingMode.HALF_UP)
                .longValue();
    }


}

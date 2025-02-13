package com.szs.szsyoungjunkim.refund.domain.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum TaxBracket {
    BRACKET_1(0, 14000000, "0.06", 0),
    BRACKET_2(14000000, 50000000, "0.15", 840000),
    BRACKET_3(50000000, 88000000, "0.24", 6240000),
    BRACKET_4(88000000, 150000000, "0.35", 15360000),
    BRACKET_5(150000000, 300000000, "0.38", 37060000),
    BRACKET_6(300000000, 500000000, "0.40", 94060000),
    BRACKET_7(500000000, 1000000000, "0.42", 174060000),
    BRACKET_8(1000000000, Long.MAX_VALUE, "0.45", 384060000);

    private final long lowerBound;
    private final long upperBound;
    private final BigDecimal taxRate;
    private final long baseTax;

    TaxBracket(long lowerBound, long upperBound, String taxRate, long baseTax) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.taxRate = new BigDecimal(taxRate);
        this.baseTax = baseTax;
    }
    
    public BigDecimal calculateTax(Long taxBase) {
        return taxRate.multiply(BigDecimal.valueOf(taxBase - lowerBound)).add(BigDecimal.valueOf(baseTax));
    }
}

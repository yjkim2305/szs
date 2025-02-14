package com.szs.szsyoungjunkim.refund.facade.dto;

public record FinalTaxDto(
        long finalTax
) {
    public static FinalTaxDto of(long finalTax) {
        return new FinalTaxDto(finalTax);
    }

    public String getFormattedTax() {
        return String.format("%,d", finalTax);
    }
}

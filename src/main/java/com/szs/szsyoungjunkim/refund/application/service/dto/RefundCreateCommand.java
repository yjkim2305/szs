package com.szs.szsyoungjunkim.refund.application.service.dto;

public record RefundCreateCommand(
        Integer taxYear,
        Integer totalIncomeTax,
        String taxCredit,
        String userId
) {

    public static RefundCreateCommand of(Integer taxYear, Integer totalIncomeTax, String taxCredit, String userId) {
        return new RefundCreateCommand(taxYear, totalIncomeTax, taxCredit, userId);
    }
}

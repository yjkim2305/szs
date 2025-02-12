package com.szs.szsyoungjunkim.refund.application.service.dto;

public record RefundCreateCommand(
        Integer taxYear,
        Long totalIncomeTax,
        String taxCredit,
        String userId
) {

    public static RefundCreateCommand of(Integer taxYear, Long totalIncomeTax, String taxCredit, String userId) {
        return new RefundCreateCommand(taxYear, totalIncomeTax, taxCredit, userId);
    }
}

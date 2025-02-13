package com.szs.szsyoungjunkim.refund.application.repository;

import com.szs.szsyoungjunkim.refund.domain.Refund;

public interface RefundRepository {
    void saveRefund(Refund refund);
    Boolean existsByUserIdAndTaxYear(String userId, Integer taxYear);
    Refund findByUserId(String userId);
}

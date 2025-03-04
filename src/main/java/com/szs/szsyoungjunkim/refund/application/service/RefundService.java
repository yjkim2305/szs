package com.szs.szsyoungjunkim.refund.application.service;

import com.szs.szsyoungjunkim.refund.application.repository.RefundRepository;
import com.szs.szsyoungjunkim.refund.application.dto.RefundCreateCommand;
import com.szs.szsyoungjunkim.refund.domain.Refund;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundRepository refundRepository;

    public void saveRefund(RefundCreateCommand refundCreateCommand) {
        refundRepository.saveRefund(Refund.from(refundCreateCommand));
    }

    public Boolean existsByUserIdAndTaxYear(String userId, Integer taxYear) {
        return refundRepository.existsByUserIdAndTaxYear(userId, taxYear);
    }

    public Refund findByUserId(String userId) {
        return refundRepository.findByUserId(userId);
    }

}

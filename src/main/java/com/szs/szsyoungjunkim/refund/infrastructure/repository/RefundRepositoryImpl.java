package com.szs.szsyoungjunkim.refund.infrastructure.repository;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.refund.application.repository.RefundRepository;
import com.szs.szsyoungjunkim.refund.domain.Refund;
import com.szs.szsyoungjunkim.refund.domain.exception.RefundErrorType;
import com.szs.szsyoungjunkim.refund.infrastructure.entity.RefundEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefundRepositoryImpl implements RefundRepository {

    private final RefundJpaRepository refundJpaRepository;

    @Override
    public void saveRefund(Refund refund) {
        refundJpaRepository.save(RefundEntity.toEntity(refund));
    }

    @Override
    public Boolean existsByUserIdAndTaxYear(String userId, Integer taxYear) {
        return refundJpaRepository.existsByUserIdAndTaxYear(userId, taxYear);
    }

    @Override
    public Refund findByUserId(String userId) {
        return Refund.from(refundJpaRepository.findByUserId(userId).orElseThrow(
                () -> new CoreException(RefundErrorType.NOT_EXIST_USER_REFUND)));
    }
}

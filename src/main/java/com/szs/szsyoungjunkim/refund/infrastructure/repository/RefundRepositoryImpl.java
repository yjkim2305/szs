package com.szs.szsyoungjunkim.refund.infrastructure.repository;

import com.szs.szsyoungjunkim.refund.application.repository.RefundRepository;
import com.szs.szsyoungjunkim.refund.domain.Refund;
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
}

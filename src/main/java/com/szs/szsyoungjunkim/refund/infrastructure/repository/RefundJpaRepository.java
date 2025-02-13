package com.szs.szsyoungjunkim.refund.infrastructure.repository;

import com.szs.szsyoungjunkim.refund.infrastructure.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefundJpaRepository extends JpaRepository<RefundEntity, Long> {
    Boolean existsByUserIdAndTaxYear(String userId, Integer taxYear);
    Optional<RefundEntity> findByUserId(String userId);
}

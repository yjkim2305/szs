package com.szs.szsyoungjunkim.deduction.infrastructure.repository;

import com.szs.szsyoungjunkim.deduction.infrastructure.entity.DeductionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeductionJpaRepository extends JpaRepository<DeductionEntity, Long> {
    Boolean existsByUserIdAndTaxYear(String userId, Integer taxYear);
    List<DeductionEntity> findByUserId(String userId);
}

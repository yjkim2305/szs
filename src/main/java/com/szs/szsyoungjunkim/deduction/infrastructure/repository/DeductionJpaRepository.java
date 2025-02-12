package com.szs.szsyoungjunkim.deduction.infrastructure.repository;

import com.szs.szsyoungjunkim.deduction.infrastructure.entity.DeductionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeductionJpaRepository extends JpaRepository<DeductionEntity, Long> {

}

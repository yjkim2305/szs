package com.szs.szsyoungjunkim.deduction.infrastructure.repository;

import com.szs.szsyoungjunkim.deduction.application.repository.DeductionRepository;
import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.deduction.infrastructure.entity.DeductionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeductionRepositoryImpl implements DeductionRepository {

    private final DeductionJpaRepository deductionJpaRepository;

    @Override
    public void saveAll(List<Deduction> deductions) {
        deductionJpaRepository.saveAll(DeductionEntity.toEntityList(deductions));
    }
}

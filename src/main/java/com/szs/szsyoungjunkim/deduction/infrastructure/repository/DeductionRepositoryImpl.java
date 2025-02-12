package com.szs.szsyoungjunkim.deduction.infrastructure.repository;

import com.szs.szsyoungjunkim.deduction.application.repository.DeductionRepository;
import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.deduction.infrastructure.entity.DeductionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeductionRepositoryImpl implements DeductionRepository {

    private final DeductionJpaRepository deductionJpaRepository;

    @Override
    public void save(Deduction deduction) {
        deductionJpaRepository.save(DeductionEntity.toEntity(deduction));
    }
}

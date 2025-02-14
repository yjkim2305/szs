package com.szs.szsyoungjunkim.deduction.infrastructure.repository;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.deduction.application.repository.DeductionRepository;
import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.deduction.api.exception.DeductionErrorType;
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

    @Override
    public Boolean existsByUserIdAndTaxYear(String userId, Integer taxYear) {
        return deductionJpaRepository.existsByUserIdAndTaxYear(userId, taxYear);
    }

    @Override
    public List<Deduction> findByUserId(String userId) {
        List<DeductionEntity> deductions = deductionJpaRepository.findByUserId(userId);

        if (deductions.isEmpty()) {
            throw new CoreException(DeductionErrorType.NOT_EXIST_USER_DEDUCTION);
        }

        return Deduction.fromEntityList(deductions);
    }
}

package com.szs.szsyoungjunkim.deduction.application.service;

import com.szs.szsyoungjunkim.deduction.application.repository.DeductionRepository;
import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.deduction.feign.response.DeductionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeductionService {

    private final DeductionRepository deductionRepository;

    @Transactional
    public void saveDeductions(DeductionResponse deductionResponse, String userId) {
        deductionRepository.saveAll(Deduction.convertToDeductions(deductionResponse, userId));
    }

    public Boolean existsByUserIdAndTaxYear(String userId, Integer taxYear) {
        return deductionRepository.existsByUserIdAndTaxYear(userId, taxYear);
    }
}

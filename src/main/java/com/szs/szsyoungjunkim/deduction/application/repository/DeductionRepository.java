package com.szs.szsyoungjunkim.deduction.application.repository;

import com.szs.szsyoungjunkim.deduction.domain.Deduction;

import java.util.List;

public interface DeductionRepository {
    void saveAll(List<Deduction> deductions);
}

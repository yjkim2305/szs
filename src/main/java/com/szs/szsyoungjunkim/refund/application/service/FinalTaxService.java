package com.szs.szsyoungjunkim.refund.application.service;

import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.refund.domain.FinalTax;
import com.szs.szsyoungjunkim.refund.domain.Refund;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinalTaxService {
    public long calculateFinalTax(List<Deduction> deductions, Refund refund) {
        return FinalTax.of(deductions, refund).finalTaxCalculate();
    }
}

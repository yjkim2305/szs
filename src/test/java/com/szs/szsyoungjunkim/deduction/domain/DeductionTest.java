package com.szs.szsyoungjunkim.deduction.domain;

import com.szs.szsyoungjunkim.deduction.feign.response.CreditCardDeductionResponse;
import com.szs.szsyoungjunkim.deduction.feign.response.DeductionResponse;
import com.szs.szsyoungjunkim.deduction.feign.response.NationalPensionDeductionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DeductionTest {

    @Test
    @DisplayName("국민연금과 신용카드소득공제 변환 테스트")
    void convertToDeductions_test() {
        List<NationalPensionDeductionResponse> nationalPensionDeductionResponses = new ArrayList<>();
        NationalPensionDeductionResponse nationalPensionDeductionResponse = new NationalPensionDeductionResponse("2023-01", "300,000.25");

        nationalPensionDeductionResponses.add(nationalPensionDeductionResponse);

        List<Map<String, String>> monthDeductions = new ArrayList<>();
        Map<String, String> monthDeduction = new HashMap<>();
        monthDeduction.put("01", "100,000.10");
        monthDeductions.add(monthDeduction);

        CreditCardDeductionResponse creditCardDeductionResponse = new CreditCardDeductionResponse(2023, monthDeductions);
        DeductionResponse deductionResponse = new DeductionResponse(nationalPensionDeductionResponses, creditCardDeductionResponse, "300,000");

        List<Deduction> deductions = Deduction.convertToDeductions(deductionResponse, "testUser");

        assertNotNull(deductions);
        assertEquals(deductions.get(0).getAmount(), 300000.25);
        assertEquals(deductions.get(1).getAmount(), 100000.1);
        assertEquals(2, deductions.size());
    }

}
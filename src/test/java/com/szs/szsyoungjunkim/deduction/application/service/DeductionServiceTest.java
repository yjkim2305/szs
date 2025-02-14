package com.szs.szsyoungjunkim.deduction.application.service;

import com.szs.szsyoungjunkim.deduction.application.repository.DeductionRepository;
import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.deduction.domain.enums.DeductionType;
import com.szs.szsyoungjunkim.deduction.feign.response.CreditCardDeductionResponse;
import com.szs.szsyoungjunkim.deduction.feign.response.DeductionResponse;
import com.szs.szsyoungjunkim.deduction.feign.response.NationalPensionDeductionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeductionServiceTest {

    @Mock
    private DeductionRepository deductionRepository;

    @InjectMocks
    private DeductionService deductionService;

    @Test
    @DisplayName("소득공제 성공적으로 저장 테스트")
    void saveDeduction() {
        String userId = "testUser";
        List<NationalPensionDeductionResponse> nationalPensionDeductionResponses = new ArrayList<>();
        NationalPensionDeductionResponse nationalPensionDeductionResponse = new NationalPensionDeductionResponse("2023-01", "300,000.25");

        nationalPensionDeductionResponses.add(nationalPensionDeductionResponse);

        List<Map<String, String>> monthDeductions = new ArrayList<>();
        Map<String, String> monthDeduction = new HashMap<>();
        monthDeduction.put("01", "100,000.10");
        monthDeductions.add(monthDeduction);

        CreditCardDeductionResponse creditCardDeductionResponse = new CreditCardDeductionResponse(2023, monthDeductions);
        DeductionResponse deductionResponse = new DeductionResponse(nationalPensionDeductionResponses, creditCardDeductionResponse, "300,000");

        List<Deduction> deductions = List.of(Deduction.defaultBuilder().build());

        mockStatic(Deduction.class);
        when(Deduction.convertToDeductions(deductionResponse, userId)).thenReturn(deductions);

        deductionService.saveDeductions(deductionResponse, userId);

        verify(deductionRepository, times(1)).saveAll(deductions);
    }

    @Test
    @DisplayName("소득공제가 해당 유저와 해당 년도의 데이터가 존재할 경우 true를 반환환다.")
    void existsByUserIdAndTaxYear_test() {
        String userId = "testUser";
        Integer taxYear = 2023;

        when(deductionRepository.existsByUserIdAndTaxYear(userId, taxYear)).thenReturn(true);

        boolean exists = deductionService.existsByUserIdAndTaxYear(userId, taxYear);

        assertThat(exists).isTrue();
        verify(deductionRepository, times(1)).existsByUserIdAndTaxYear(userId, taxYear);
    }

    @Test
    @DisplayName("소득공제가 해당 유저의 데이터가 존재할 경우 데이터를 반환한다.")
    void findByUserId_test() {
        String userId = "testUser";
        List<Deduction> deductions = Collections.singletonList(Deduction.of(userId, 2023, 1, 300000.25, DeductionType.NATIONAL_PENSION));
        when(deductionRepository.findByUserId(userId)).thenReturn(deductions);

        List<Deduction> result = deductionService.findByUserId(userId);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        verify(deductionRepository, times(1)).findByUserId(userId);
    }


}
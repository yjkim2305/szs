package com.szs.szsyoungjunkim.refund.facade;

import com.szs.szsyoungjunkim.deduction.application.service.DeductionService;
import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.refund.application.service.FinalTaxService;
import com.szs.szsyoungjunkim.refund.application.service.RefundService;
import com.szs.szsyoungjunkim.refund.domain.Refund;
import com.szs.szsyoungjunkim.refund.facade.dto.FinalTaxDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefundFacadeTest {

    @Mock
    private DeductionService deductionService;

    @Mock
    private RefundService refundService;

    @Mock
    private FinalTaxService finalTaxService;

    @InjectMocks
    private RefundFacade refundFacade;

    @Test
    @DisplayName("결정세액을 정상적으로 계산한다.")
    void getFinalTaxAmount_Success() {
        String userId = "testUser";

        Deduction deduction1 = mock(Deduction.class);
        Deduction deduction2 = mock(Deduction.class);
        List<Deduction> deductions = List.of(deduction1, deduction2);

        Refund refund = mock(Refund.class);

        long expectedFinalTax = 1000000L;
        when(deductionService.findByUserId(userId)).thenReturn(deductions);
        when(refundService.findByUserId(userId)).thenReturn(refund);
        when(finalTaxService.calculateFinalTax(deductions, refund)).thenReturn(expectedFinalTax);

        FinalTaxDto result = refundFacade.getFinalTaxAmount(userId);

        assertThat(result).isNotNull();
        assertThat(result.finalTax()).isEqualTo(expectedFinalTax);
        verify(deductionService, times(1)).findByUserId(userId);
        verify(refundService, times(1)).findByUserId(userId);
        verify(finalTaxService, times(1)).calculateFinalTax(deductions, refund);
    }

}
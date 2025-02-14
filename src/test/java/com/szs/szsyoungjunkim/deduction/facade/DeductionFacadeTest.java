package com.szs.szsyoungjunkim.deduction.facade;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.deduction.api.exception.DeductionErrorType;
import com.szs.szsyoungjunkim.deduction.application.service.DeductionService;
import com.szs.szsyoungjunkim.deduction.application.service.ScrapService;
import com.szs.szsyoungjunkim.deduction.feign.response.CreditCardDeductionResponse;
import com.szs.szsyoungjunkim.deduction.feign.response.DeductionResponse;
import com.szs.szsyoungjunkim.deduction.feign.response.NationalPensionDeductionResponse;
import com.szs.szsyoungjunkim.deduction.feign.response.ScrapResponse;
import com.szs.szsyoungjunkim.refund.application.service.RefundService;
import com.szs.szsyoungjunkim.refund.application.dto.RefundCreateCommand;
import com.szs.szsyoungjunkim.user.application.service.UserService;
import com.szs.szsyoungjunkim.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeductionFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private DeductionService deductionService;

    @Mock
    private ScrapService scrapService;

    @Mock
    private RefundService refundService;

    @InjectMocks
    private DeductionFacade deductionFacade;

    @Test
    @DisplayName("사용자의 스크래핑이 정상 작동하여 데이터가 적재되어야 한다.")
    void userScrap_Success() {
        String userId = "test";

        User user = User.defaultBuilder()
                .userId(userId)
                .password("password")
                .name("동탁")
                .regNo("921108-1582816")
                .build();

        Integer taxYear = 2023;

        List<NationalPensionDeductionResponse> nationalPensionDeductionResponses = new ArrayList<>();
        NationalPensionDeductionResponse nationalPensionDeductionResponse = new NationalPensionDeductionResponse("2023-01", "300,000.25");

        nationalPensionDeductionResponses.add(nationalPensionDeductionResponse);

        List<Map<String, String>> monthDeductions = new ArrayList<>();
        Map<String, String> monthDeduction = new HashMap<>();
        monthDeduction.put("01", "100,000.10");
        monthDeductions.add(monthDeduction);

        CreditCardDeductionResponse creditCardDeductionResponse = new CreditCardDeductionResponse(2023, monthDeductions);
        DeductionResponse deductionResponse = new DeductionResponse(nationalPensionDeductionResponses, creditCardDeductionResponse, "300,000");

        ScrapResponse scrapResponse = new ScrapResponse("동탁", 20000000L, deductionResponse);

        when(userService.findByUserId(userId)).thenReturn(user);
        when(scrapService.getScrapData(user)).thenReturn(scrapResponse);
        when(deductionService.existsByUserIdAndTaxYear(userId, taxYear)).thenReturn(Boolean.FALSE);
        when(refundService.existsByUserIdAndTaxYear(userId, taxYear)).thenReturn(Boolean.FALSE);

        deductionFacade.userScrap(userId);

        verify(userService, times(1)).findByUserId(userId);
        verify(scrapService, times(1)).getScrapData(user);
        verify(deductionService, times(1)).saveDeductions(deductionResponse, userId);
        verify(refundService, times(1)).saveRefund(any(RefundCreateCommand.class));
    }

    @Test
    @DisplayName("사용자의 스크래핑 데이터가 존재할 경우 중복 적재되지 않도록 예외 발생")
    void userScrap_Fail() {
        String userId = "test";

        User user = User.defaultBuilder()
                .userId(userId)
                .password("password")
                .name("동탁")
                .regNo("921108-1582816")
                .build();

        Integer taxYear = 2023;

        List<NationalPensionDeductionResponse> nationalPensionDeductionResponses = new ArrayList<>();
        NationalPensionDeductionResponse nationalPensionDeductionResponse = new NationalPensionDeductionResponse("2023-01", "300,000.25");

        nationalPensionDeductionResponses.add(nationalPensionDeductionResponse);

        List<Map<String, String>> monthDeductions = new ArrayList<>();
        Map<String, String> monthDeduction = new HashMap<>();
        monthDeduction.put("01", "100,000.10");
        monthDeductions.add(monthDeduction);

        CreditCardDeductionResponse creditCardDeductionResponse = new CreditCardDeductionResponse(2023, monthDeductions);
        DeductionResponse deductionResponse = new DeductionResponse(nationalPensionDeductionResponses, creditCardDeductionResponse, "300,000");

        ScrapResponse scrapResponse = new ScrapResponse("동탁", 20000000L, deductionResponse);

        when(userService.findByUserId(userId)).thenReturn(user);
        when(scrapService.getScrapData(user)).thenReturn(scrapResponse);
        when(deductionService.existsByUserIdAndTaxYear(userId, taxYear)).thenReturn(Boolean.TRUE);

        CoreException exception = assertThrows(CoreException.class, () -> {
            deductionFacade.userScrap(userId);
        });

        assertEquals(DeductionErrorType.EXIST_TAX_YEAR_DEDUCTION.getMessage(), exception.getMessage());
        assertEquals(DeductionErrorType.EXIST_TAX_YEAR_DEDUCTION.name(), exception.getErrorType().getErrorCode());

        verify(userService, times(1)).findByUserId(userId);
        verify(scrapService, times(1)).getScrapData(user);
        verify(deductionService, times(1)).existsByUserIdAndTaxYear(userId, taxYear);
        verify(refundService, never()).saveRefund(any());
    }

}
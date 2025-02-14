package com.szs.szsyoungjunkim.deduction.application.service;

import com.szs.szsyoungjunkim.deduction.feign.ScrapClient;
import com.szs.szsyoungjunkim.deduction.feign.request.ScrapRequest;
import com.szs.szsyoungjunkim.deduction.feign.response.*;
import com.szs.szsyoungjunkim.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScrapServiceTest {

    @Mock
    private ScrapClient scrapClient;

    @InjectMocks
    private ScrapService scrapService;

    @Test
    void scrap_Success() {
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
        ClientApiRes<ScrapResponse> scrapApiRes = new ClientApiRes<>();
        setField(scrapApiRes, "status", "SUCCESS"); // 정상 응답
        setField(scrapApiRes, "data", scrapResponse);


        when(scrapClient.scrapData(ScrapRequest.of("동탁", "921108-1582816"))).thenReturn(scrapApiRes);

        ScrapResponse result = scrapService.getScrapData(
                User.defaultBuilder()
                .name("동탁")
                .regNo("921108-1582816")
                .build());

        assertThat(result).isNotNull();
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed for field: " + fieldName, e);
        }
    }
}
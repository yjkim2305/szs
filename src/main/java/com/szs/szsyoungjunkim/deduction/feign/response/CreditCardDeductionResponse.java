package com.szs.szsyoungjunkim.deduction.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record CreditCardDeductionResponse(
        @JsonProperty("year")
        int year,

        @JsonProperty("month")
        List<Map<String, String>> monthDeductions
) {
}

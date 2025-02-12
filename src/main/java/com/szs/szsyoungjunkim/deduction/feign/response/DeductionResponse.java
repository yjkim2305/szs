package com.szs.szsyoungjunkim.deduction.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DeductionResponse(
        @JsonProperty("국민연금")
        List<NationalPensionDeductionResponse> nationalPension,

        @JsonProperty("신용카드소득공제")
        CreditCardDeductionResponse creditCardDeductionResponse,

        @JsonProperty("세액공제")
        String taxCredit
) {
}

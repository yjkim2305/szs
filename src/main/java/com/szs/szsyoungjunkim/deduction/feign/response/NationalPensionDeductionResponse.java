package com.szs.szsyoungjunkim.deduction.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NationalPensionDeductionResponse(
        @JsonProperty("월")
        String month,
        @JsonProperty("공제액")
        String amount
) {
}

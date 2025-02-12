package com.szs.szsyoungjunkim.deduction.feign.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScrapResponse(
    @JsonProperty("이름")
    String name,
    @JsonProperty("종합소득금액")
    Integer totalIncomeTax,
    @JsonProperty("소득공제")
    DeductionResponse deduction
) {
}

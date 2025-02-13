package com.szs.szsyoungjunkim.refund.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefundResponse(
        @JsonProperty("결정세액")
        String finalTax
) {
}

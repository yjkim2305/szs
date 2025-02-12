package com.szs.szsyoungjunkim.deduction.domain.enums;

public enum DeductionType {
    CREDIT_CARD,
    NATIONAL_PENSION,
    TAX_CREDIT;

    @Override
    public String toString() {
        return name();
    }
}

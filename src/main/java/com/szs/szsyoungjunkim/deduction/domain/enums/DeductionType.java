package com.szs.szsyoungjunkim.deduction.domain.enums;

public enum DeductionType {
    CREDIT_CARD,                //신용카드소득공제
    NATIONAL_PENSION,           //국민연금
    TAX_CREDIT                  //
    ;

    @Override
    public String toString() {
        return name();
    }
}

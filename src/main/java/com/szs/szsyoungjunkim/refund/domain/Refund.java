package com.szs.szsyoungjunkim.refund.domain;

import com.szs.szsyoungjunkim.refund.application.service.dto.RefundCreateCommand;
import com.szs.szsyoungjunkim.refund.infrastructure.entity.RefundEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Refund {
    private Long id;
    private String userId;
    private Integer taxYear;
    private Integer totalIncome;         //종합 소득 금액
    private Double totalTaxCredit;      //세액 공제
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder(builderMethodName = "defaultBuilder")
    private Refund(String userId, Integer taxYear, Integer totalIncome, Double totalTaxCredit) {
        this.userId = userId;
        this.taxYear = taxYear;
        this.totalIncome = totalIncome;
        this.totalTaxCredit = totalTaxCredit;
    }

    @Builder(builderMethodName = "entityBuilder")
    private Refund(Long id, String userId, Integer taxYear, Integer totalIncome, Double totalTaxCredit, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.userId = userId;
        this.taxYear = taxYear;
        this.totalIncome = totalIncome;
        this.totalTaxCredit = totalTaxCredit;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public static Refund from(RefundCreateCommand refundCreateCommand) {
        return Refund.defaultBuilder()
                .userId(refundCreateCommand.userId())
                .taxYear(refundCreateCommand.taxYear())
                .totalIncome(refundCreateCommand.totalIncomeTax())
                .totalTaxCredit(Double.valueOf(refundCreateCommand.taxCredit().replace(",", "")))
                .build();
    }

    public static Refund from(RefundEntity entity) {
        return Refund.entityBuilder()
                .id(entity.getId())
                .taxYear(entity.getTaxYear())
                .totalIncome(entity.getTotalIncome())
                .totalTaxCredit(entity.getTotalTaxCredit())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }

}

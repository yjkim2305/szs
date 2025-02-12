package com.szs.szsyoungjunkim.refund.infrastructure.entity;

import com.szs.szsyoungjunkim.common.domain.entity.BaseTimeEntity;
import com.szs.szsyoungjunkim.refund.domain.Refund;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "refund")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    private Integer taxYear;
    private Integer totalIncome;            //종합 소득 금액
    private Double totalTaxCredit;          //세액 공제

    @Builder
    private RefundEntity(Long id, String userId, Integer taxYear, Integer totalIncome, Double totalTaxCredit) {
        this.userId = userId;
        this.taxYear = taxYear;
        this.totalIncome = totalIncome;
        this.totalTaxCredit = totalTaxCredit;
    }

    public static RefundEntity toEntity(Refund refund) {
        return RefundEntity.builder()
                .id(refund.getId())
                .userId(refund.getUserId())
                .taxYear(refund.getTaxYear())
                .totalIncome(refund.getTotalIncome())
                .totalTaxCredit(refund.getTotalTaxCredit())
                .build();
    }
}

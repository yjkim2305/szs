package com.szs.szsyoungjunkim.deduction.infrastructure.entity;

import com.szs.szsyoungjunkim.common.domain.entity.BaseTimeEntity;
import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.deduction.domain.enums.DeductionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "deduction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeductionEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    private Integer year;

    private Integer month;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private DeductionType type;

    @Builder
    private DeductionEntity(String userId, Integer year, Integer month, Long amount, DeductionType type) {
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.type = type;
    }

    public static DeductionEntity toEntity(Deduction deduction) {
        return DeductionEntity.builder()
                .userId(deduction.getUserId())
                .year(deduction.getYear())
                .month(deduction.getMonth())
                .amount(deduction.getAmount())
                .type(deduction.getType())
                .build();
    }

}

package com.szs.szsyoungjunkim.deduction.infrastructure.entity;

import com.szs.szsyoungjunkim.common.domain.entity.BaseTimeEntity;
import com.szs.szsyoungjunkim.deduction.domain.Deduction;
import com.szs.szsyoungjunkim.deduction.domain.enums.DeductionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "deduction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeductionEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    private Integer taxYear;

    private Integer taxMonth;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private DeductionType type;

    @Builder
    private DeductionEntity(Long id, String userId, Integer taxYear, Integer taxMonth, Double amount, DeductionType type) {
        this.userId = userId;
        this.taxYear = taxYear;
        this.taxMonth = taxMonth;
        this.amount = amount;
        this.type = type;
    }

    public static DeductionEntity toEntity(Deduction deduction) {
        return DeductionEntity.builder()
                .id(deduction.getId())
                .userId(deduction.getUserId())
                .taxYear(deduction.getTaxYear())
                .taxMonth(deduction.getTaxMonth())
                .amount(deduction.getAmount())
                .type(deduction.getType())
                .build();
    }

    public static List<DeductionEntity> toEntityList(List<Deduction> deductions) {
        return deductions.stream()
                .map(DeductionEntity::toEntity)
                .toList();
    }

}

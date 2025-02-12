package com.szs.szsyoungjunkim.deduction.domain;

import com.szs.szsyoungjunkim.deduction.domain.enums.DeductionType;
import com.szs.szsyoungjunkim.deduction.infrastructure.entity.DeductionEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Deduction {
    private Long id;
    private String userId;
    private Integer year;
    private Integer month;
    private Long amount;
    private DeductionType type;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    @Builder(builderMethodName = "entityBuilder")
    private Deduction(Long id, String userId, Integer year, Integer month, Long amount, DeductionType type, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.type = type;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public static Deduction from(DeductionEntity deductionEntity) {
        return Deduction.entityBuilder()
                .id(deductionEntity.getId())
                .userId(deductionEntity.getUserId())
                .year(deductionEntity.getYear())
                .month(deductionEntity.getMonth())
                .amount(deductionEntity.getAmount())
                .type(deductionEntity.getType())
                .createdDate(deductionEntity.getCreatedDate())
                .updatedDate(deductionEntity.getUpdatedDate())
                .build();
    }

}

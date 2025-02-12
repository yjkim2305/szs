package com.szs.szsyoungjunkim.deduction.domain;

import com.szs.szsyoungjunkim.deduction.domain.enums.DeductionType;
import com.szs.szsyoungjunkim.deduction.feign.response.DeductionResponse;
import com.szs.szsyoungjunkim.deduction.feign.response.NationalPensionDeductionResponse;
import com.szs.szsyoungjunkim.deduction.infrastructure.entity.DeductionEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Deduction {
    private Long id;
    private String userId;
    private Integer year;
    private Integer month;
    private Double amount;
    private DeductionType type;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder(builderMethodName = "defaultBuilder")
    private Deduction(String userId, Integer year, Integer month, Double amount, DeductionType type) {
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.type = type;
    }

    @Builder(builderMethodName = "entityBuilder")
    private Deduction(Long id, String userId, Integer year, Integer month, Double amount, DeductionType type, LocalDateTime createdDate, LocalDateTime updatedDate) {
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

    public static List<Deduction> convertToDeductions(DeductionResponse deductionResponse, String userId) {
        //국민연금 처리
        List<Deduction> deductions = new ArrayList<>();

        deductions.addAll(Deduction.fromNationalPenstionList(deductionResponse.nationalPension(), userId));
        deductions.addAll(Deduction.fromNationalPenstionList(deductionResponse.nationalPension(), userId));
        return deductions;
    }

    public static List<Deduction> fromNationalPenstionList(List<NationalPensionDeductionResponse> nationalPensionDeductions, String userId) {
        return nationalPensionDeductions.stream()
                .map(deduction -> Deduction.of(deduction, userId))
                .toList();
    }

    public static Deduction of(NationalPensionDeductionResponse nationalPensionDeduction, String userId) {
        return Deduction.defaultBuilder()
                .userId(userId)
                .year(Integer.valueOf(nationalPensionDeduction.month().split("-")[0]))
                .month(Integer.valueOf(nationalPensionDeduction.month().split("-")[1]))
                .build();
    }

}

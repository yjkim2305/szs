package com.szs.szsyoungjunkim.refresh.infrastructure.entity;

import com.szs.szsyoungjunkim.common.domain.entity.BaseTimeEntity;
import com.szs.szsyoungjunkim.refresh.domain.Refresh;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "refresh")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String refreshToken;
    private LocalDateTime expiration;

    @Builder
    private RefreshEntity(Long id, String userId, String refreshToken, LocalDateTime expiration) {
        this.id = id;
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }

    public static RefreshEntity toEntity(Refresh refresh) {
        return RefreshEntity.builder()
                .id(refresh.getId())
                .userId(refresh.getUserId())
                .refreshToken(refresh.getRefreshToken())
                .expiration(refresh.getExpiration())
                .build();
    }
}

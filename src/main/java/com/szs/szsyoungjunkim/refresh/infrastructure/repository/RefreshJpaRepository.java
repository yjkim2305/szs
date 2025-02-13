package com.szs.szsyoungjunkim.refresh.infrastructure.repository;

import com.szs.szsyoungjunkim.refresh.infrastructure.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshJpaRepository extends JpaRepository<RefreshEntity, Long> {
    Boolean existsByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}

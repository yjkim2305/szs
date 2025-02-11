package com.szs.szsyoungjunkim.user.infrastructure.repository;

import com.szs.szsyoungjunkim.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByUserId(String userId);
    Optional<UserEntity> findByUserId(String userId);
}

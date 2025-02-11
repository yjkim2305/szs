package com.szs.szsyoungjunkim.user.infrastructure.repository;

import com.szs.szsyoungjunkim.user.application.repository.UserRepository;
import com.szs.szsyoungjunkim.user.domain.User;
import com.szs.szsyoungjunkim.user.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void signUpUser(User user) {
        userJpaRepository.save(UserEntity.toEntity(user));
    }

    @Override
    public Boolean existsByUserId(String username) {
        return userJpaRepository.existsByUserId(username);
    }
}

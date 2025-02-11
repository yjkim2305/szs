package com.szs.szsyoungjunkim.user.application.repository;

import com.szs.szsyoungjunkim.user.domain.User;

public interface UserRepository {
    void signUpUser(User user);
    Boolean existsByUserId(String username);
}

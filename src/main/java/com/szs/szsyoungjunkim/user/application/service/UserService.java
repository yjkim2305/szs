package com.szs.szsyoungjunkim.user.application.service;

import com.szs.szsyoungjunkim.user.application.dto.UserCreateCommand;
import com.szs.szsyoungjunkim.user.application.repository.UserRepository;
import com.szs.szsyoungjunkim.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    void signUpUser(UserCreateCommand userCreateCommand) {
        User user =  User.createWithEncodedPassword(userCreateCommand, bCryptPasswordEncoder);

        user.validateUsers(userCreateCommand.name(), userCreateCommand.regNo());

        userRepository.signUpUser(user);
    }
}

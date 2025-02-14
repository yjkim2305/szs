package com.szs.szsyoungjunkim.user.application.service;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.user.application.dto.UserCreateCommand;
import com.szs.szsyoungjunkim.user.application.repository.UserRepository;
import com.szs.szsyoungjunkim.user.domain.User;
import com.szs.szsyoungjunkim.user.api.exception.UserErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUpUser(UserCreateCommand userCreateCommand) {
        Boolean isExist = userRepository.existsByUserId(userCreateCommand.userId());

        if (isExist) {
            throw new CoreException(UserErrorType.EXIST_USER);
        }

        User user =  User.createWithEncodedPassword(userCreateCommand, bCryptPasswordEncoder);

        user.validateUsers(userCreateCommand.name(), userCreateCommand.regNo());

        userRepository.signUpUser(user);
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}

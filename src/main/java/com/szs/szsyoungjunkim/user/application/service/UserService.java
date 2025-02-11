package com.szs.szsyoungjunkim.user.application.service;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.user.application.dto.UserCreateCommand;
import com.szs.szsyoungjunkim.user.application.dto.UserLoginCommand;
import com.szs.szsyoungjunkim.user.application.dto.UserLoginDto;
import com.szs.szsyoungjunkim.user.application.repository.UserRepository;
import com.szs.szsyoungjunkim.user.domain.User;
import com.szs.szsyoungjunkim.user.domain.exception.UserErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public void signUpUser(UserCreateCommand userCreateCommand) {
        Boolean isExist = userRepository.existsByUserId(userCreateCommand.userId());

        if (isExist) {
            throw new CoreException(UserErrorType.EXIST_USER);
        }

        User user =  User.createWithEncodedPassword(userCreateCommand, bCryptPasswordEncoder);

        user.validateUsers(userCreateCommand.name(), userCreateCommand.regNo());

        userRepository.signUpUser(user);
    }

    public UserLoginDto login(UserLoginCommand userLoginCommand) {
        //사용자 존재 여부 확인
        User user = userRepository.findByUserId(userLoginCommand.userId());
        //비밀번호 검증
        user.validatePassword(userLoginCommand.password(), bCryptPasswordEncoder);
        //jwt 토큰 생성
        String accessToken = jwtUtil.createAccessJwt("access", user.getUserId());
        String refreshToken = jwtUtil.createRefreshJwt("refresh", user.getUserId());

        return UserLoginDto.of(accessToken, refreshToken);

    }
}

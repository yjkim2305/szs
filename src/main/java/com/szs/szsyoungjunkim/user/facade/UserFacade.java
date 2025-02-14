package com.szs.szsyoungjunkim.user.facade;

import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.user.application.dto.UserLoginCommand;
import com.szs.szsyoungjunkim.user.application.dto.UserLoginDto;
import com.szs.szsyoungjunkim.user.application.service.AuthService;
import com.szs.szsyoungjunkim.user.application.service.UserService;
import com.szs.szsyoungjunkim.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public UserLoginDto login(UserLoginCommand userLoginCommand) {
        //사용자 존재 여부 확인
        User user = userService.findByUserId(userLoginCommand.userId());
        //비밀번호 검증
        user.validatePassword(userLoginCommand.password(), bCryptPasswordEncoder);

        return authService.generateTokens(user);
    }

}

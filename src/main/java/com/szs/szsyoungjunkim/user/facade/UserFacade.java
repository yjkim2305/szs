package com.szs.szsyoungjunkim.user.facade;

import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.refresh.application.service.RefreshService;
import com.szs.szsyoungjunkim.user.application.dto.UserLoginCommand;
import com.szs.szsyoungjunkim.user.application.dto.UserLoginDto;
import com.szs.szsyoungjunkim.user.application.service.UserService;
import com.szs.szsyoungjunkim.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final RefreshService refreshService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${spring.jwt.refresh.expiration}")
    private Long refreshExpiration;

    public UserLoginDto login(UserLoginCommand userLoginCommand) {
        //사용자 존재 여부 확인
        User user = userService.findByUserId(userLoginCommand.userId());
        //비밀번호 검증
        user.validatePassword(userLoginCommand.password(), bCryptPasswordEncoder);
        //jwt 토큰 생성
        String accessToken = jwtUtil.createAccessJwt("access", user.getUserId());
        String refreshToken = jwtUtil.createRefreshJwt("refresh", user.getUserId());

        //refresh token 저장
        refreshService.addRefreshToken(user.getUserId(), refreshToken, refreshExpiration);

        return UserLoginDto.of(accessToken, refreshToken);
    }

}

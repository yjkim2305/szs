package com.szs.szsyoungjunkim.user.application.service;

import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.refresh.application.service.RefreshService;
import com.szs.szsyoungjunkim.user.application.dto.UserLoginDto;
import com.szs.szsyoungjunkim.user.domain.User;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RefreshService refreshService;
    private final JwtUtil jwtUtil;

    @Value("${spring.jwt.refresh.expiration}")
    private Long refreshExpiration;

    public UserLoginDto generateTokens(User user) {
        //jwt 토큰 생성
        String accessToken = jwtUtil.createAccessJwt("access", user.getUserId());
        String refreshToken = jwtUtil.createRefreshJwt("refresh", user.getUserId());

        //refresh token 저장
        refreshService.addRefreshToken(user.getUserId(), refreshToken, refreshExpiration);
        return UserLoginDto.of(accessToken, refreshToken);
    }

    public Cookie createRefreshCookie(String refreshToken) {
        return jwtUtil.createCookie("refresh", refreshToken);
    }

}

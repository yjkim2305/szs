package com.szs.szsyoungjunkim.refresh.application.service;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.refresh.application.dto.RefreshReissueDto;
import com.szs.szsyoungjunkim.refresh.application.repository.RefreshRepository;
import com.szs.szsyoungjunkim.refresh.domain.Refresh;
import com.szs.szsyoungjunkim.refresh.domain.exception.RefreshErrorType;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshService {
    @Value("${spring.jwt.refresh.expiration}")
    private Long refreshExpiration;

    private final RefreshRepository refreshRepository;
    private final JwtUtil jwtUtil;

    public void addRefreshToken(String userId, String refreshToken, Long expiredMs) {
        refreshRepository.save(Refresh.of(userId, refreshToken, expiredMs));
    }

    @Transactional
    public RefreshReissueDto reissueToken(HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            throw new CoreException(RefreshErrorType.NOT_EXIST_AUTHORIZATION);
        }

        //토큰 만료 여부 검사
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new CoreException(RefreshErrorType.EXPIRE_REFRESH_TOKEN);
        }

        String category = jwtUtil.getCategory(refreshToken);

        //access 토큰인지 검사
        if (!category.equals("refresh")) {
            throw new CoreException(RefreshErrorType.INVALID_REFRESH_TOKEN);
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefreshToken(refreshToken);
        if (!isExist) {
            throw new CoreException(RefreshErrorType.INVALID_REFRESH_TOKEN);
        }

        String userId = jwtUtil.getUserId(refreshToken);

        //make new JWT
        String newAccess = jwtUtil.createAccessJwt("access", userId);
        String newRefresh = jwtUtil.createRefreshJwt("refresh", userId);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefreshToken(refreshToken);
        refreshRepository.save(Refresh.of(userId, newRefresh, refreshExpiration));

        return RefreshReissueDto.of(newAccess, newRefresh);
    }

    public Cookie createRefreshCookie(String refreshToken) {
        return jwtUtil.createCookie("refresh", refreshToken);
    }
}

package com.szs.szsyoungjunkim.refresh.application.service;

import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.refresh.application.repository.RefreshRepository;
import com.szs.szsyoungjunkim.refresh.domain.Refresh;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private final RefreshRepository refreshRepository;
    private final JwtUtil jwtUtil;

    public void addRefreshToken(String userId, String refreshToken, Long expiredMs) {
        refreshRepository.save(Refresh.of(userId, refreshToken, expiredMs));
    }
}

package com.szs.szsyoungjunkim.refresh.application.service;

import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.refresh.application.dto.RefreshReissueDto;
import com.szs.szsyoungjunkim.refresh.application.repository.RefreshRepository;
import com.szs.szsyoungjunkim.refresh.domain.Refresh;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshServiceTest {
    @Mock
    private RefreshRepository refreshRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private RefreshService refreshService;

    private final String userId = "testUser";
    private final String refreshToken = "mockRefreshToken";
    private final String newAccessToken = "newAccessToken";
    private final String newRefreshToken = "newRefreshToken";
    private final Long refreshExpiration = 604800L;  // 7일

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(refreshService, "refreshExpiration", refreshExpiration);
    }

    @Test
    @DisplayName("refresh 토큰이 정상적으로 저장된다.")
    void addRefreshToken_Success() {
        Refresh mockRefresh = mock(Refresh.class);

        try (var mockedStatic = mockStatic(Refresh.class)) {
            when(Refresh.of(userId, refreshToken, refreshExpiration)).thenReturn(mockRefresh);
            refreshService.addRefreshToken(userId, refreshToken, refreshExpiration);
            verify(refreshRepository, times(1)).save(mockRefresh);
        }
    }


    @Test
    @DisplayName("refresh 토큰으로 access 토큰이 정상적으로 발급된다.")
    void reissueToken_Success() {
        // Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie refreshCookie = new Cookie("refresh", refreshToken);
        when(request.getCookies()).thenReturn(new Cookie[]{refreshCookie});
        when(jwtUtil.isExpired(refreshToken)).thenReturn(false);
        when(jwtUtil.getCategory(refreshToken)).thenReturn("refresh");
        when(refreshRepository.existsByRefreshToken(refreshToken)).thenReturn(true);
        when(jwtUtil.getUserId(refreshToken)).thenReturn(userId);
        when(jwtUtil.createAccessJwt("access", userId)).thenReturn(newAccessToken);
        when(jwtUtil.createRefreshJwt("refresh", userId)).thenReturn(newRefreshToken);

        try (var mockedStatic = mockStatic(Refresh.class)) {
            when(Refresh.of(userId, newRefreshToken, refreshExpiration)).thenReturn(mock(Refresh.class));

            RefreshReissueDto result = refreshService.reissueToken(request);

            assertThat(result).isNotNull();
            assertThat(result.accessToken()).isEqualTo(newAccessToken);
            assertThat(result.refreshToken()).isEqualTo(newRefreshToken);

            verify(refreshRepository, times(1)).deleteByRefreshToken(refreshToken);
            verify(refreshRepository, times(1)).save(any(Refresh.class));
        }
    }

}
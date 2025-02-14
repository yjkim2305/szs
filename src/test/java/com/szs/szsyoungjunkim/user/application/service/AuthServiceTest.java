package com.szs.szsyoungjunkim.user.application.service;

import com.szs.szsyoungjunkim.common.util.JwtUtil;
import com.szs.szsyoungjunkim.refresh.application.service.RefreshService;
import com.szs.szsyoungjunkim.user.domain.User;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private RefreshService refreshService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private final Long refreshExpiration = 86400000L;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(authService, "refreshExpiration", refreshExpiration);
    }

    @Test
    void generateTokens_Success() {
        User user = User.defaultBuilder()
                .userId("testUser")
                .password("password")
                .name("name")
                .regNo("123456-1234567")
                .build();

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        when(jwtUtil.createAccessJwt("access", user.getUserId())).thenReturn(accessToken);
        when(jwtUtil.createRefreshJwt("refresh", user.getUserId())).thenReturn(refreshToken);

        authService.generateTokens(user);

        verify(refreshService, times(1)).addRefreshToken(user.getUserId(), refreshToken, refreshExpiration);
        verify(jwtUtil, times(1)).createAccessJwt("access", user.getUserId());
        verify(jwtUtil, times(1)).createRefreshJwt("refresh", user.getUserId());
    }

    @Test
    void createRefreshCookie_Success() {
        String refreshToken = "refreshToken";
        Cookie expectedCookie = new Cookie("refresh", refreshToken);
        when(jwtUtil.createCookie("refresh", refreshToken)).thenReturn(expectedCookie);

        Cookie result = authService.createRefreshCookie(refreshToken);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("refresh");
        assertThat(result.getValue()).isEqualTo(refreshToken);
        verify(jwtUtil, times(1)).createCookie("refresh", refreshToken);
    }



}
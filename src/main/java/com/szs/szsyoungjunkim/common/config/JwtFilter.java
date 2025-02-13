package com.szs.szsyoungjunkim.common.config;

import com.szs.szsyoungjunkim.common.exception.AccessTokenErrorType;
import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.common.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorization.split(" ")[1];

        //토큰 만료 여부 검사
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            throw new CoreException(AccessTokenErrorType.EXPIRE_ACCESS_TOKEN);
        }

        String category = jwtUtil.getCategory(accessToken);

        //access 토큰인지 검사
        if (!category.equals("access")) {
            throw new CoreException(AccessTokenErrorType.INVALID_ACCESS_TOKEN);
        }

        String userId = jwtUtil.getUserId(accessToken);

        Authentication authToken = new UsernamePasswordAuthenticationToken(userId, null, Collections.singletonList(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

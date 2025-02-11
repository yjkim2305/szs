package com.szs.szsyoungjunkim.user.api.request;

public record UserLoginRequest(
        String userId,
        String password
) {
}

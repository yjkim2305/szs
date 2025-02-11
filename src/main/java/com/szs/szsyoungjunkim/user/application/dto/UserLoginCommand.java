package com.szs.szsyoungjunkim.user.application.dto;

public record UserLoginCommand(
        String userId,
        String password
) {
}

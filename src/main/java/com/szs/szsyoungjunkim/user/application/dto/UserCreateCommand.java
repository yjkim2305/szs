package com.szs.szsyoungjunkim.user.application.dto;

public record UserCreateCommand(
        String userId,
        String password,
        String name,
        String regNo
) {
}

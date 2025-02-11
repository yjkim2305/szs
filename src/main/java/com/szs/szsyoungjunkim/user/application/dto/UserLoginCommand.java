package com.szs.szsyoungjunkim.user.application.dto;

import com.szs.szsyoungjunkim.user.api.request.UserLoginRequest;

public record UserLoginCommand(
        String userId,
        String password
) {
    public static UserLoginCommand from(UserLoginRequest rq) {
        return new UserLoginCommand(rq.userId(), rq.password());
    }
}

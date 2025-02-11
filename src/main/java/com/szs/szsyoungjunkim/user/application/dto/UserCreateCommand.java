package com.szs.szsyoungjunkim.user.application.dto;

import com.szs.szsyoungjunkim.user.api.request.UserCreateReqest;

public record UserCreateCommand(
        String userId,
        String password,
        String name,
        String regNo
) {

    public static UserCreateCommand from(UserCreateReqest rq) {
        return new UserCreateCommand(rq.userId(), rq.password(), rq.name(), rq.regNo());
    }
}

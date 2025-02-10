package com.szs.szsyoungjunkim.user.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private Long id;
    private String userId;
    private String password;
    private String name;
    private String regNo;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

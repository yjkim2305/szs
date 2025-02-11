package com.szs.szsyoungjunkim.user.domain;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.user.application.dto.UserCreateCommand;
import com.szs.szsyoungjunkim.user.domain.exception.UserErrorType;
import com.szs.szsyoungjunkim.user.infrastructure.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private Long id;
    private String userId;
    private String password;
    private String name;
    private String regNo;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private static final Map<String, String> permitedUsers = Map.of(
            "동탁", "921108-1582816",
            "관우", "681108-1582816",
            "손권", "890601-2455116",
            "유비", "790411-1656116",
            "조조", "810326-2715702"
    );

    @Builder(builderMethodName = "defaultBuilder")
    private User(String userId, String password, String name, String regNo) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }

    @Builder(builderMethodName = "entityBuilder")
    private User(Long id, String userId, String password, String name, String regNo, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void validateUsers(String name, String regNo) {
        String permittedRegNo = permitedUsers.get(name);

        if (permittedRegNo == null || !permittedRegNo.equals(regNo)) {
            throw new CoreException(UserErrorType.INVALID_USER);
        }
    }

    public void validatePassword(String password, BCryptPasswordEncoder bCryptPasswordEncoder) {
        boolean matches = bCryptPasswordEncoder.matches(password, this.password);
        if (!matches) {
            throw new CoreException(UserErrorType.NOT_EXIST_USER_PASSWORD);
        }
    }

    public static User createWithEncodedPassword(UserCreateCommand userCreateCommand, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return defaultBuilder()
                .userId(userCreateCommand.userId())
                .password(bCryptPasswordEncoder.encode(userCreateCommand.password()))
                .name(userCreateCommand.name())
                .regNo(userCreateCommand.regNo())
                .build();
    }

    public static User from(UserEntity userEntity) {
        return entityBuilder()
                .id(userEntity.getId())
                .userId(userEntity.getUserId())
                .password(userEntity.getPassword())
                .name(userEntity.getName())
                .regNo(userEntity.getRegNo())
                .createdDate(userEntity.getCreatedDate())
                .updatedDate(userEntity.getUpdatedDate())
                .build();
    }


}

package com.szs.szsyoungjunkim.user.domain;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.user.application.dto.UserCreateCommand;
import com.szs.szsyoungjunkim.user.domain.exception.UserErrorType;
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

    @Builder
    private User(String userId, String password, String name, String regNo) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }

    public void validateUsers(String name, String regNo) {
        String permittedRegNo = permitedUsers.get(name);

        if (permittedRegNo == null || !permittedRegNo.equals(regNo)) {
            throw new CoreException(UserErrorType.INVALID_USER);
        }
    }

    public static User createWithEncodedPassword(UserCreateCommand userCreateCommand, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .userId(userCreateCommand.userId())
                .password(bCryptPasswordEncoder.encode(userCreateCommand.password()))
                .name(userCreateCommand.name())
                .regNo(userCreateCommand.regNo())
                .build();
    }


}

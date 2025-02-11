package com.szs.szsyoungjunkim.user.domain;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.user.application.dto.UserCreateCommand;
import com.szs.szsyoungjunkim.user.domain.exception.UserErrorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    @DisplayName("허용된 사용자는 예외 없이 validateUsers를 통과해야 한다.")
    void validateUsers_Success() {
        //given
        User user = User.entityBuilder()
                .userId("dongtak123")
                .password("password")
                .name("동탁")
                .regNo("921108-1582816")
                .build();

        //when & then
        assertDoesNotThrow(() -> user.validateUsers(user.getName(), user.getRegNo()));
    }

    @Test
    @DisplayName("허용되지 않는 사용자는 예외가 발생해야 한다.")
    void validateUsers_Fail() {
        //given
        User user = User.entityBuilder()
                .userId("dongtak123")
                .password("password")
                .name("동탁")
                .regNo("921108-1582817")
                .build();

        //when
        CoreException exception = assertThrows(CoreException.class, () -> {
            user.validateUsers(user.getName(), user.getRegNo());
        });

        //then
        assertEquals(UserErrorType.INVALID_USER.getMessage(), exception.getMessage());
        assertEquals(UserErrorType.INVALID_USER.name(), exception.getErrorType().getErrorCode());
    }

    @Test
    @DisplayName("비밀번호가 암호화된 상태로 User 객체가 정상적으로 생성되어야 한다")
    void createWithEncodedPassword() {
        // given
        UserCreateCommand command = new UserCreateCommand("user123", "securePassword", "유비", "790411-1656116");

        // when
        User user = User.createWithEncodedPassword(command, passwordEncoder);

        // then
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo("user123");
        assertThat(user.getName()).isEqualTo("유비");
        assertThat(user.getRegNo()).isEqualTo("790411-1656116");

        assertThat(passwordEncoder.matches("securePassword", user.getPassword())).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 올바르면 검증을 통과한다.")
    void validatePassword_Success() {
        //given
        UserCreateCommand command = new UserCreateCommand("user123", "securePassword", "유비", "790411-1656116");
        User user = User.createWithEncodedPassword(command, passwordEncoder);

        //when & then
        assertDoesNotThrow(() -> user.validatePassword("securePassword", passwordEncoder));
    }

    @Test
    @DisplayName("비밀번호가 올바르지 않으면 검증에서 예외가 발생한다.")
    void validatePassword_Fail() {
        //given
        User user = User.entityBuilder()
                .userId("dongtak123")
                .password("password")
                .name("동탁")
                .regNo("921108-1582817")
                .build();

        //when
        CoreException exception = assertThrows(CoreException.class, () -> {
            user.validatePassword(user.getPassword(), passwordEncoder);
        });

        //then
        assertEquals(UserErrorType.NOT_EXIST_USER_PASSWORD.getMessage(), exception.getMessage());
        assertEquals(UserErrorType.NOT_EXIST_USER_PASSWORD.name(), exception.getErrorType().getErrorCode());
    }
}
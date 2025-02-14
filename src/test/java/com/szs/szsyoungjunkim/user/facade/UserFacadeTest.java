package com.szs.szsyoungjunkim.user.facade;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.user.api.exception.UserErrorType;
import com.szs.szsyoungjunkim.user.application.dto.UserLoginCommand;
import com.szs.szsyoungjunkim.user.application.dto.UserLoginDto;
import com.szs.szsyoungjunkim.user.application.service.AuthService;
import com.szs.szsyoungjunkim.user.application.service.UserService;
import com.szs.szsyoungjunkim.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserFacade userFacade;

    @Test
    @DisplayName("로그인 성공 테스트")
    void login_Success() {
        UserLoginDto userLoginDto = new UserLoginDto("accessToken", "refreshToken");
        UserLoginCommand userLoginCommand = new UserLoginCommand("test", "password");
        User user = User.entityBuilder()
                .userId("test")
                .password("password")
                .name("동탁")
                .regNo("921108-1582816")
                .build();

        when(userService.findByUserId(userLoginCommand.userId())).thenReturn(user);
        when(bCryptPasswordEncoder.matches(userLoginCommand.password(), user.getPassword())).thenReturn(true);
        when(authService.generateTokens(user)).thenReturn(userLoginDto);

        UserLoginDto result = userFacade.login(userLoginCommand);

        assertEquals(userLoginDto.accessToken(), result.accessToken());
        assertEquals(userLoginDto.refreshToken(), result.refreshToken());
    }

    @Test
    @DisplayName("비밀번호가 다를 경우 로그인에 실패")
    void login_Fail() {
        UserLoginDto userLoginDto = new UserLoginDto("accessToken", "refreshToken");
        UserLoginCommand userLoginCommand = new UserLoginCommand("test", "password123");
        User user = User.entityBuilder()
                .userId("test")
                .password("password")
                .name("동탁")
                .regNo("921108-1582816")
                .build();

        when(userService.findByUserId(userLoginCommand.userId())).thenReturn(user);
        when(bCryptPasswordEncoder.matches(userLoginCommand.password(), user.getPassword())).thenReturn(false);

        CoreException exception = assertThrows(CoreException.class, () -> {
            userFacade.login(userLoginCommand);
        });

        assertEquals(UserErrorType.NOT_EXIST_USER_PASSWORD.getMessage(), exception.getMessage());
        assertEquals(UserErrorType.NOT_EXIST_USER_PASSWORD.name(), exception.getErrorType().getErrorCode());
    }

}
package com.szs.szsyoungjunkim.user.application.service;

import com.szs.szsyoungjunkim.common.exception.CoreException;
import com.szs.szsyoungjunkim.user.application.dto.UserCreateCommand;
import com.szs.szsyoungjunkim.user.application.repository.UserRepository;
import com.szs.szsyoungjunkim.user.domain.User;
import com.szs.szsyoungjunkim.user.api.exception.UserErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원가입에 적합한 회원으로 회원가입했을 경우 성공해야 한다.")
    void signUpUser_Success() {
        UserCreateCommand userCreateCommand = new UserCreateCommand("testUser", "password", "조조", "810326-2715702");
        User user = User.createWithEncodedPassword(userCreateCommand, bCryptPasswordEncoder);

        when(userRepository.existsByUserId(userCreateCommand.userId())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(userCreateCommand.password())).thenReturn("encodedPassword");

        userService.signUpUser(userCreateCommand);

        verify(userRepository, times(1)).signUpUser(any(User.class));
    }

    @Test
    @DisplayName("이미 존재하는 사용자를 회원가입 할 경우 예외 발생해야 한다.")
    void signUpUser_Fail() {
        UserCreateCommand userCreateCommand = new UserCreateCommand("testUser", "password", "조조", "810326-2715702");
        User user = User.createWithEncodedPassword(userCreateCommand, bCryptPasswordEncoder);

        when(userRepository.existsByUserId(userCreateCommand.userId())).thenReturn(true);

        CoreException exception = assertThrows(CoreException.class, () -> {
            userService.signUpUser(userCreateCommand);
        });

        assertEquals(UserErrorType.EXIST_USER.getMessage(), exception.getMessage());
        assertEquals(UserErrorType.EXIST_USER.name(), exception.getErrorType().getErrorCode());
    }

    @Test
    @DisplayName("존재하는 사용자에 대해 조회할 수 있다.")
    void findByUserId_Success() {
        User user = User.defaultBuilder()
                .userId("testUser")
                .password("password")
                .name("name")
                .regNo("123456-1234567")
                .build();

        when(userRepository.findByUserId("testUser")).thenReturn(user);

        User foundUser = userService.findByUserId("testUser");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserId()).isEqualTo("testUser");
    }

}
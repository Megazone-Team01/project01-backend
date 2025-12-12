package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    void signup() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
                .email("Mingky@naver.com")
                .password("Mingky123!@")
                .passwordConfirm("Mingky123!@")
                .name("박밍키")
                .phone("01012345678")
                .type(1)
                .build();

        // When, Then
        GetUserResponse response = userService.signup(request);

        assertNotNull(response);
        assertEquals("Mingky@naver.com", response.getEmail());
        assertEquals("박밍키", response.getName());


        // DB에 저장 여부 확인
        assertTrue(userRepository.findByEmail(response.getEmail()).isPresent());

    }

    @Test
    void signup_NotEqualPassword() {

        // Given : 비밀번호와 2차비밀번호가 다른 경우
        CreateUserRequest request = CreateUserRequest.builder()
                .email("Mingky@naver.com")
                .password("Mingky123!@")
                .passwordConfirm("Mingky1")
                .name("박밍키")
                .phone("01012345678")
                .type(1)
                .build();

        // When, Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                                            () -> userService.signup(request));

        assertEquals("비밀번호가 일치하지 않습니다.", exception.getMessage());
    }

    @Test
    void signup_NotFoundEmail() {

        // Given : 기존 DB에 동일한 이메일이 존재하는 경우
        CreateUserRequest basicRequest = CreateUserRequest.builder()
                .email("Mindun12@naver.com")
                .password("Mingky123!@")
                .passwordConfirm("Mingky123!@")
                .name("박밍키")
                .phone("01012345678")
                .type(1)
                .build();

        userService.signup(basicRequest);

        CreateUserRequest request = CreateUserRequest.builder()
                .email("Mindun12@naver.com")
                .password("Mingky123!@")
                .passwordConfirm("Mingky123!@")
                .name("박밍키")
                .phone("01012345678")
                .type(1)
                .build();

        // When, Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.signup(request));

        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
    }

    @Test
    void login() {

        // Given : 회원 가입 후 로그인하기
        CreateUserRequest signupRequest = CreateUserRequest.builder()
                .email("Mindun123@naver.com")
                .password("Mingky123!@")
                .passwordConfirm("Mingky123!@")
                .name("박밍키")
                .phone("01012345678")
                .type(1)
                .build();

        userService.signup(signupRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .email("Mindun123@naver.com")
                .password("Mingky123!@")
                .build();

        userService.login(loginRequest);

        // When & Then
        assertEquals(signupRequest.getEmail(), loginRequest.getEmail());
        assertEquals(signupRequest.getPassword(), loginRequest.getPassword());

    }
}
package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetLoginResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = false)
    public GetUserResponse signup(CreateUserRequest request) {

        // 2차 비밀번호 체크
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.");
        }

        // 이메일 중복 체크
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String password = passwordEncoder.encode(request.getPassword());

        // 추후 강사는 관리자 승인 요청 후 처리되도록 하기 위해 우선 주석
        // UserRole role = roleRepository.findById(request.getRoleId())
        //                .orElseThrow(() -> new RuntimeException("Role not found"));

        // 현재는 User만 회원가입 할 수 있도록
        UserRole defaultRole = userRoleRepository.findByName("USER")
                .orElseThrow(() -> new CustomException("Default role not found"));

        // User 생성 후 저장
        User user = User.builder()
                .email(request.getEmail())
                .password(password)
                .name(request.getName())
                .phone(request.getPhone())
                .role(defaultRole)
                .addressCode(request.getAddressCode())
                .type(request.getType())
                .build();

        User savedUser = userRepository.save(user);

        return new GetUserResponse(savedUser.getId(),
                                    savedUser.getEmail(),
                                    savedUser.getName());
    }

    @Override
    public GetLoginResponse login(LoginRequest request) {
        // 이메일이 존재하는지 확인
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("존재하지 않는 이메일입니다."));

        // 사용자게 입력받은 비밀번호와 이메일에 해당하는 복호화된 비밀번호를 비교
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.");
        }

        String password = passwordEncoder.encode(user.getPassword());
        passwordEncoder.matches(request.getPassword(), password);

        return new GetLoginResponse(user.getId(),
                                    user.getEmail(),
                                    user.getName());
    }
}

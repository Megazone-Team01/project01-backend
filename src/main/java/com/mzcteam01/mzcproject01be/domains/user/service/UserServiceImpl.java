package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.common.enums.ChannelType;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.UserErrorCode;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetLoginResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetMyResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRoleRepository;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import com.mzcteam01.mzcproject01be.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional(readOnly = false)
    public GetUserResponse signup(CreateUserRequest request) {

        // 2차 비밀번호 체크
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new CustomException(UserErrorCode.PASSWORD_NOT_MATCH.getMessage());
        }

        // 이메일 중복 체크
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(UserErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
        }

        // 비밀번호 암호화
        String password = passwordEncoder.encode(request.getPassword());


        // 현재는 User로만 회원가입 할 수 있도록
        // 만약 강사인 경우 추후 관리자 승인 요청 및 승인하는 화면에서 처리될 예정
        UserRole defaultRole = userRoleRepository.findByName("USER")
                .orElseThrow(() -> new CustomException(UserErrorCode.DEFAULT_ROLE_NOT_FOUND.getMessage()));

        // 프론트에서 받아온 채널역할의 문자열을 코드값으로 변경
        int channelType = ChannelType.fromName(request.getType()).getCode();

        // User 생성 후 저장
        User user = User.builder()
                .email(request.getEmail())
                .password(password)
                .name(request.getName())
                .phone(request.getPhone())
                .role(defaultRole)
                .addressCode(request.getAddressCode())
                .type(channelType)
                .build();

        User savedUser = userRepository.save(user);

        return new GetUserResponse(savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName());
    }


    @Override
    @Transactional(readOnly = false)
    public GetLoginResponse login(LoginRequest request) {
        // 이메일이 존재하는지 확인
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(UserErrorCode.EMAIL_NOT_FOUND.getMessage()));

        // 사용자게 입력받은 비밀번호와 이메일에 해당하는 복호화된 비밀번호를 비교
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(UserErrorCode.PASSWORD_NOT_MATCH.getMessage());
        }

        // JWT claims 생성
        Map<String, Object> claims = Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "name", user.getName(),
                "role", user.getRole().getName()
        );

        // AccessToken, RefreshToken 생성
        String accessToken = jwtUtil.generateToken(claims, 2); // 10분
        String refreshToken = jwtUtil.generateToken(claims, 180 * 24 * 60); // 180일

        // DB에 RefreshToken 저장
        user.updateRefreshToken(refreshToken, LocalDateTime.now().plusDays(180));
//        log.info("Saving refreshToken for user {}: {}", user.getId(), refreshToken);
//        log.info("accessToken for user {}: {}", user.getId(), accessToken);
        userRepository.save(user);

        return GetLoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().getName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 마이페이지 조회
    @Override
    public GetMyResponse getMyInfo(int id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));

        return GetMyResponse.of(user);
    }

    // 마이페이지 수정
//    @Override
//    @Transactional(readOnly = false)
//    public GetMyResponse getMyInfo(int id){
//
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));
//
//        return GetMyResponse.of(user);
//    }

    // 회원 탈퇴
//    @Override
//    @Transactional(readOnly = false)
//    public void deleteMyInfo(int id){
//
//        userRepository.findById(id)
//                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));
//
//        userRepository.deleteById(id);
//    }

}

package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.common.enums.ChannelType;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.RoomErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.UserErrorCode;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.CreateUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.GetUserRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.LoginRequest;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.AdminGetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetLoginResponse;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.GetUserResponse;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import com.mzcteam01.mzcproject01be.domains.user.repository.QUserOrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final QUserOrganizationRepository qUserOrganizationRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

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
    public GetLoginResponse login(LoginRequest request) {
        // 이메일이 존재하는지 확인
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(UserErrorCode.EMAIL_NOT_FOUND.getMessage()));

        // 사용자게 입력받은 비밀번호와 이메일에 해당하는 복호화된 비밀번호를 비교
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(UserErrorCode.PASSWORD_NOT_MATCH.getMessage());
        }

        String password = passwordEncoder.encode(user.getPassword());
        passwordEncoder.matches(request.getPassword(), password);

        return new GetLoginResponse(user.getId(),
                user.getEmail(),
                user.getName());
    }

    @Override
    @Transactional
    public List<AdminGetUserResponse> list(GetUserRequest request) {
        List<User> result = userRepository.findAll();
        System.out.println( request.getType() );
        if( request.getUserRole() != null ){
            result = result.stream().filter( user ->
                user.getRole() == request.getUserRole()
            ).toList();
        }
        if( request.getType() != null ){
            result = result.stream().filter( user ->
                user.getType().equals(request.getType())
            ).toList();
        }
        return result.stream().map(AdminGetUserResponse::of).toList();
    }

    @Override
    @Transactional
    public void delete(int id, int deletedBy) {
        User user = userRepository.findById( id ).orElseThrow( () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()) );
        user.delete();
    }
}

package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.common.enums.ChannelType;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.UserErrorCode;
import com.mzcteam01.mzcproject01be.common.utils.RelatedEntityChecker;
import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import com.mzcteam01.mzcproject01be.domains.file.repository.FileRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OnlineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.service.LectureFacade;
import com.mzcteam01.mzcproject01be.common.exception.*;
import com.mzcteam01.mzcproject01be.domains.user.dto.request.*;
import com.mzcteam01.mzcproject01be.domains.user.dto.response.*;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserOrganization;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import com.mzcteam01.mzcproject01be.domains.user.repository.*;
import com.mzcteam01.mzcproject01be.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final QUserOrganizationRepository qUserOrganizationRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final LectureFacade lectureFacade;
    private final UserOrganizationRepository userOrganizationRepository;
    private final JwtUtil jwtUtil;
    private final UserLectureRepository userLectureRepository;
    private final OnlineLectureRepository onlineLectureRepository;
    private final OfflineLectureRepository offlineLectureRepository;
    private final RelatedEntityChecker relatedEntityChecker;
    private final FileRepository fileRepository;

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


        // 학생, 강사 중 선택하여 회원가입 가능
        // 추후 강사는 오프라인 조직에 가입하고자하는 경우 대표선생님의 승인이 필요
        // 다만, 온라인 조직은 승인 대기 없이 바로 조직에 가입.
        UserRole role = userRoleRepository.findByName(request.getRole())
                .orElseThrow(() -> new CustomException(UserErrorCode.ROLE_NOT_FOUND.getMessage()));

        // 프론트에서 받아온 채널역할의 문자열을 코드값으로 변경
        int channelType = ChannelType.fromName(request.getType()).getCode();

        // User 생성 후 저장
        User user = User.builder()
                .email(request.getEmail())
                .password(password)
                .name(request.getName())
                .phone(request.getPhone())
                .role(role)
                .address(request.getAddress())
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
        String accessToken = jwtUtil.generateToken(claims, 10); // 10분
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

    @Override
    @Transactional
    public List<AdminGetUserResponse> list(GetUserRequest request) {
        List<User> result = userRepository.findAll();
        if( request.getUserRole() != null ){
            result = result.stream().filter( user ->
                    user.getRole().getName().equals( request.getUserRole() )
            ).toList();
        }
        if( request.getType() != null ){
            result = result.stream().filter( user ->
                user.getType().equals(request.getType())
            ).toList();
        }
        if( request.getSortBy() != null ){
            if( request.getSortBy().equals("RECENT") ){
                result = result.stream().filter( user ->
                        user.getCreatedAt().isAfter( LocalDateTime.now().minusDays(1) )
                ).toList();
            }
        }
        if( request.getSearchString() != null ){
            result = result.stream().filter( user ->
                    user.getName().contains( request.getSearchString() )
            ).toList();
        }
        return result.stream().map(AdminGetUserResponse::of).toList();
    }

    @Override
    @Transactional
    public void delete(int id, int deletedBy) {
        User user = userRepository.findById( id ).orElseThrow( () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()) );
        if( !relatedEntityChecker.relatedUserCheck( id ) ) throw new CustomException( CommonErrorCode.RELATED_ENTITY_EXISTED.getMessage());
        user.delete();
    }

    @Override
    @Transactional
    public AdminGetUserDetailResponse getUserDetailById(int id) {
        // User 정보 조회
        User user = userRepository.findById(id).orElseThrow(
                () -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage())
        );
        // Lecture 정보 조회
        List<Lecture> lectures = null;
        if (user.getRole().getName().equals("TEACHER")) lectures = lectureFacade.getAllTeachingLecture(id);
        else if (user.getRole().getName().equals("STUDENT")) lectures = lectureFacade.getAllLearningLecture(id);
        else lectures = new ArrayList<>();
        // Organization 정보 조회
        List<UserOrganization> organizations = userOrganizationRepository.findAllByUserId(id);
        // Response 객체 생성
        return AdminGetUserDetailResponse.of(user, lectures, organizations);
    }

    // 마이페이지 조회
    @Override
    public GetProfileResponse getProfileInfo(int id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));
        // Lecture 정보 조회
        List<Lecture> lectures = null;
        if (user.getRole().getName().equals("TEACHER")) lectures = lectureFacade.getAllTeachingLecture(id);
        else if (user.getRole().getName().equals("STUDENT")) lectures = lectureFacade.getAllLearningLecture(id);
        else lectures = new ArrayList<>();
        // Organization 정보 조회
        List<UserOrganization> organizations = userOrganizationRepository.findAllByUserId(id);

        // Response 객체 생성
        return GetProfileResponse.of(user, lectures, organizations);
    }

    // 마이페이지 수정
    @Override
    @Transactional(readOnly = false)
    public GetProfileUpdateResponse updateProfileInfo(int id, UpdateUserRequest request){

        // 회원 유형 변경 시 체크
        List<Integer> lectureIds = userLectureRepository.findLectureIdsByUserId(id);
        int newType = "ONLINE".equalsIgnoreCase(request.getType()) ? 1 :
                "OFFLINE".equalsIgnoreCase(request.getType()) ? 2 : 0;
        log.info(String.valueOf(newType));
        // 2. 변경 로직
        if (newType == 1) { // 온라인으로 변경
            // lectureIds 중 오프라인 강의가 있는지 확인
            boolean hasOfflineLecture = lectureIds.stream()
                    .anyMatch(offlineLectureRepository::existsById);
            if (hasOfflineLecture) {
                throw new IllegalArgumentException("오프라인 강의를 수강 중이므로 온라인 회원으로 변경할 수 없습니다.");
            }
        } else if (newType == 2) { // 오프라인으로 변경
            boolean hasOnlineLecture = lectureIds.stream()
                    .anyMatch(onlineLectureRepository::existsById);
            if (hasOnlineLecture) {
                throw new IllegalArgumentException("온라인 강의를 수강 중이므로 오프라인 회원으로 변경할 수 없습니다.");
            }
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));

        // 파일 처리
        File file = null;
        if (request.getFileId() != null) {
            file = fileRepository.findById(request.getFileId())
                    .orElseThrow(() -> new CustomException("파일이 존재하지 않습니다."));
        }

        user.updateProfile(
                request.getName(),
                request.getPhone().replaceAll("-", ""),
                request.getAddress(),
                request.getAddressDetail(),
                newType,
                file
        );

        return new GetProfileUpdateResponse(
                user.getName(),
                user.getPhone().replaceAll("-", ""),
                user.getAddress(),
                user.getAddressDetail(),
                user.getType(),
                user.getFile() != null ? user.getFile().getId() : null,
                user.getFile() != null ? user.getFile().getUrl() : ""
        );
    }

    // 회원 탈퇴
    @Override
    @Transactional(readOnly = false)
    public void deleteUserInfo(int id){

        userRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));

        if( !relatedEntityChecker.relatedUserCheck( id ) )
            throw new CustomException( CommonErrorCode.RELATED_ENTITY_EXISTED.getMessage());

        userRepository.deleteById(id);
    }


    @Override
    public List<GetApproveOrganizationResponse> approveOrganization(int id) {
        // ownerId가 소유자인 조직 중 status=0(가입요청 상태)인 모든 UserOrganization 조회
        List<UserOrganization> requests = qUserOrganizationRepository.findActiveByUserAndOwner(id);

        if (requests.isEmpty()) {
            throw new CustomException(UserErrorCode.USER_ORGANIZATION_NOT_FOUND.getMessage());
        }

        // UserOrganization 객체 전체를 DTO로 변환
        List<GetApproveOrganizationResponse> responseList = requests.stream()
                .map(GetApproveOrganizationResponse::of)
                .toList();

        return responseList;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateStatusUserOrganization(UpdateStatusUserOrganizationRequest request) {
        int userOrganizationId = request.getUserOrganizationId();
        int status = request.getStatus();

        UserOrganization userOrganization = userOrganizationRepository.findById(userOrganizationId)
                                                .orElseThrow(() -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()));

        userOrganization.update(status);

    }
}

package com.mzcteam01.mzcproject01be.domains.lecture.service;

import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import com.mzcteam01.mzcproject01be.common.base.category.repository.CategoryRepository;
import com.mzcteam01.mzcproject01be.common.base.category.service.CategoryService;
import com.mzcteam01.mzcproject01be.common.base.day.entity.Day;
import com.mzcteam01.mzcproject01be.common.base.day.repository.DayRepository;
import com.mzcteam01.mzcproject01be.common.exception.*;
import com.mzcteam01.mzcproject01be.common.utils.CategoryConverter;
import com.mzcteam01.mzcproject01be.domains.file.entity.File;
import com.mzcteam01.mzcproject01be.domains.file.repository.FileRepository;
import com.mzcteam01.mzcproject01be.domains.file.service.FileService;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.request.OfflineLectureUploadRequest;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineDetailResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineListResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl.QOfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces.OfflineLectureService;
import com.mzcteam01.mzcproject01be.domains.organization.entity.Organization;
import com.mzcteam01.mzcproject01be.domains.organization.repository.OrganizationRepository;
import com.mzcteam01.mzcproject01be.domains.room.entity.Room;
import com.mzcteam01.mzcproject01be.domains.room.repository.RoomRepository;
import com.mzcteam01.mzcproject01be.domains.user.entity.User;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserLectureRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OfflineLectureServiceImpl implements OfflineLectureService {

    private final QOfflineLectureRepository qOfflineLectureRepository;
    private final CategoryConverter categoryConverter;
    private final OfflineLectureRepository offlineLectureRepository;
    private final UserRepository userRepository;
    private final UserLectureRepository userLectureRepository;
    private final OrganizationRepository organizationRepository;
    private final RoomRepository roomRepository;
    private final CategoryService categoryService;
    private final DayRepository dayRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final CategoryRepository categoryRepository;



    @Override
    public LectureOfflineDetailResponse findLecture(int id) {
        OfflineLecture offlineLecture = qOfflineLectureRepository.findById(id)
                .orElseThrow(() -> new CustomException(LectureErrorCode.OFFLINE_NOT_FOUND.getMessage()));

        return LectureOfflineDetailResponse.of(offlineLecture, categoryConverter.fullCodeToLayer(offlineLecture.getCategory()));
    }


    @Override
    public List<GetLectureResponse> getTop9Lectures(Integer searchType) {

        PageRequest pageRequest = PageRequest.of(0, 9);
        return qOfflineLectureRepository.findOfflineLectures(searchType, pageRequest,null)
                .stream()
                .map(GetLectureResponse::of)
                .toList();
    }

    @Override
    public LectureOfflineListResponse getAllLectures(Integer searchType, int page, String keyword) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        Page<OfflineLecture> offline = qOfflineLectureRepository.findOfflineLectures(searchType, pageRequest, keyword);
        return LectureOfflineListResponse.of(offline);
    }


    @Override
    @Transactional  // ✅ readOnly = false
    public void createOfflineLecture(OfflineLectureUploadRequest request, int userId) {
        User teacher = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND.getMessage()));

        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new CustomException(OrganizationErrorCode.ORGANIZATION_NOT_FOUND.getMessage()));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new CustomException(RoomErrorCode.ROOM_NOT_FOUND.getMessage()));

        log.info("roomId: {}, teacher: {}, organization: {}",
                room.getId(), teacher.getId(), organization.getId());

        // 카테고리 전체 경로
        String categoryFullPath = request.getCategory();
        log.info("category: {}", categoryFullPath);

        // 파일 업로드
        Map<String, Object> fileResult = fileService.uploadFiles(request.getThumbnail(), userId);
        int fileId = (int) fileResult.get("fileId");
        File fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new CustomException(FileErrorCode.FILE_NOT_FOUND.getMessage()));

        // ✅ 시간 포맷 변환: "14:30" → "1430"
        String startTime = request.getStartTime().replace(":", "");
        String endTime = request.getEndTime().replace(":", "");

        log.info("startTime: {}, endTime: {}", startTime, endTime);

        // 요일별로 강의 생성
        String[] days = request.getDay().split(",");

        for (String dayName : days) {
            Day dayValue = dayRepository.findByName(dayName.trim())
                    .orElseThrow(() -> new CustomException(DayErrorCode.DAY_NOT_FOUND.getMessage()));

            OfflineLecture lecture = OfflineLecture.builder()
                    .name(request.getName())
                    .organization(organization)
                    .teacher(teacher)
                    .category(categoryFullPath)
                    .description(request.getDescription())
                    .maxNum(request.getMaxNum())
                    .room(room)
                    .price(request.getPrice())
                    .startAt(request.getStartAt().atStartOfDay())
                    .thumbnailFile(fileEntity)
                    .endAt(request.getEndAt().atStartOfDay())
                    .startTimeAt(startTime)   // "1430"
                    .endTimeAt(endTime)       // "1600"
                    .day(dayValue.getValue())
                    .createdBy(userId)
                    .build();

            offlineLectureRepository.save(lecture);
            log.info("강의 저장 완료: {}, 요일: {}", request.getName(), dayValue.getName());
        }

        log.info("오프라인 강의 생성 완료: {} ({}개 요일), 카테고리: {}",
                request.getName(), days.length, categoryFullPath);
    }
}

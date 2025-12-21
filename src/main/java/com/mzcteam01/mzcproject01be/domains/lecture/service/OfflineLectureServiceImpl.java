package com.mzcteam01.mzcproject01be.domains.lecture.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.LectureErrorCode;
import com.mzcteam01.mzcproject01be.common.utils.CategoryConverter;
import com.mzcteam01.mzcproject01be.common.exception.UserErrorCode;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.GetLectureResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineDetailResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.LectureOfflineListResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.dto.response.UserStatusResponse;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.OfflineLecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.queryDsl.QOfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.service.interfaces.OfflineLectureService;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserLectureRepository;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OfflineLectureServiceImpl implements OfflineLectureService {

    private final QOfflineLectureRepository qOfflineLectureRepository;
    private final CategoryConverter categoryConverter;
    private final OfflineLectureRepository offlineLectureRepository;
    private final UserRepository userRepository;
    private final UserLectureRepository userLectureRepository;

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



}

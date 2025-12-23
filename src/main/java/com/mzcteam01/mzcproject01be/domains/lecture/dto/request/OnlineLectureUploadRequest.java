package com.mzcteam01.mzcproject01be.domains.lecture.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OnlineLectureUploadRequest {

    // ===== 파일 =====
    @NotNull(message = "영상은 필수입니다.")
    private MultipartFile video;

    private MultipartFile thumbnail;

    // ===== 기본 정보 =====
    @NotBlank(message = "강의 제목은 필수입니다.")
    private String name;

    @NotBlank(message = "강의 설명은 필수입니다.")
    private String description;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    @NotNull(message = "가격은 필수입니다.")
    @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
    private Integer price;

    @NotNull(message = "시작일은 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;

    @NotNull(message = "종료일은 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endAt;

    @NotNull(message = "소속 기관은 필수입니다.")
    private Integer organizationId;

    private int isOnline = 1;
}

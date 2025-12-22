package com.mzcteam01.mzcproject01be.domains.lecture.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OfflineLectureUploadRequest {
    // ===== 파일 =====
    private MultipartFile thumbnail; // 선택

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate startAt;

    @NotNull(message = "종료일은 필수입니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate endAt;

    @NotNull(message = "소속 기관은 필수입니다.")
    private int organizationId;

    // ===== 오프라인 전용 =====
    @NotBlank(message = "요일은 필수입니다.")
    private String day;

    @NotBlank(message = "시작 시간은 필수입니다.")
    private String startTime;

    @NotBlank(message = "종료 시간은 필수입니다.")
    private String endTime;

    @NotNull(message = "최대 인원은 필수입니다.")
    @Positive(message = "최대 인원은 1명 이상이어야 합니다.")
    private int maxNum;

    @NotNull(message = "강의실은 필수입니다.")
    private Integer roomId;

    @NotNull
    private int status;

    private int isOnline = 0;
}

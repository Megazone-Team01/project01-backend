package com.mzcteam01.mzcproject01be.domains.lecture.exception;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.exception.LectureErrorCode;
import com.mzcteam01.mzcproject01be.domains.lecture.controller.OfflineLectureController;
import com.mzcteam01.mzcproject01be.domains.lecture.controller.OnlineLectureController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice(assignableTypes = {
        OfflineLectureController.class,
        OnlineLectureController.class
})
public class LectureExceptionHandler {

    @ExceptionHandler(LectureException.class)  // LectureException 처리
    public ResponseEntity<?> handleLectureException(LectureException e) {
        log.info("🔥 LectureExceptionHandler 동작! 🔥");

        LectureErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(Map.of(
                        "message", errorCode.getMessage()
                ));
    }
}

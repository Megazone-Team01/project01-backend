package com.mzcteam01.mzcproject01be.domains.notification.controller;

import com.mzcteam01.mzcproject01be.domains.notification.repository.EmitterRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag( name = "알람 SSE" )
@Slf4j
@RequestMapping("/api/v1/sse")
public class NotificationController {
    private final EmitterRepository emitterRepository;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@RequestParam Integer userId) {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);

        emitterRepository.save(userId, emitter);
        log.info("SSE 연결 성공: {}", userId);

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("Connected successfully"));
        } catch (IOException e) {
            log.error("초기 메시지 전송 실패", e);
        }

        emitter.onCompletion(() -> {
            emitterRepository.delete(userId);
            log.info("SSE 연결 종료: {}", userId);
        });

        emitter.onTimeout(() -> {
            emitterRepository.delete(userId);
            log.info("SSE 연결 타임아웃: {}", userId);
        });

        emitter.onError((ex) -> {
            emitterRepository.delete(userId);
            log.error("SSE 연결 에러: {}", userId, ex);
        });

        return emitter;
    }

    @PostMapping("/send/{userId}")
    public ResponseEntity<String> sendToUser(
            @PathVariable Integer userId,
            @RequestBody Map<String, Object> data) {

        SseEmitter emitter = emitterRepository.get(userId);

        if (emitter == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            emitter.send(SseEmitter.event()
                    .name("message")
                    .data(data));
            return ResponseEntity.ok("메시지 전송 성공");
        } catch (IOException e) {
            emitterRepository.delete(userId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("메시지 전송 실패");
        }
    }
}

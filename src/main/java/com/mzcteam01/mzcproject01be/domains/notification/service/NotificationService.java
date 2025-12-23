package com.mzcteam01.mzcproject01be.domains.notification.service;

import com.mzcteam01.mzcproject01be.domains.notification.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final EmitterRepository emitterRepository;

    public void sendNotification(Integer userId, String message) {
        SseEmitter emitter = emitterRepository.get(userId);

        if (emitter != null) {
            try {
                Map<String, Object> notification = Map.of(
                        "message", message,
                        "timestamp", LocalDateTime.now()
                );

                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(notification));

            } catch (IOException e) {
                emitterRepository.delete(userId);
                log.error("알림 전송 실패: {}", userId, e);
            }
        }
    }

    public void broadcastNotification(String message) {
        Map<Integer, SseEmitter> emitters = emitterRepository.getAll();
        List<Integer> deadEmitters = new ArrayList<>();

        emitters.forEach((userId, emitter) -> {
            try {
                Map<String, Object> notification = Map.of(
                        "message", message,
                        "timestamp", LocalDateTime.now()
                );

                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(notification));
            } catch (IOException e) {
                deadEmitters.add(userId);
                log.error("브로드캐스트 실패: {}", userId, e);
            }
        });

        deadEmitters.forEach(emitterRepository::delete);
    }
}

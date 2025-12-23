package com.mzcteam01.mzcproject01be.domains.notification.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmitterRepository {
    private final Map<Integer, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void save(Integer userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
    }

    public void delete(Integer userId) {
        emitters.remove(userId);
    }

    public SseEmitter get(Integer userId) {
        return emitters.get(userId);
    }

    public Map<Integer, SseEmitter> getAll() {
        return emitters;
    }

    public boolean exists(Integer userId) {
        return emitters.containsKey(userId);
    }
}

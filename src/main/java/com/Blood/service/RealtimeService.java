package com.Blood.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RealtimeService {

    private static final Logger logger = LoggerFactory.getLogger(RealtimeService.class);
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(24 * 60 * 60 * 1000L); // 24 Hours Keep-Alive
        emitters.add(emitter);

        emitter.onCompletion(() -> {
            logger.info("SSE Emitter completed and removed.");
            emitters.remove(emitter);
        });
        
        emitter.onTimeout(() -> {
            logger.info("SSE Emitter timed out and removed.");
            emitters.remove(emitter);
        });

        emitter.onError((e) -> {
            logger.info("SSE Emitter error and removed: {}", e.getMessage());
            emitters.remove(emitter);
        });

        try {
            emitter.send(SseEmitter.event().name("connected").data("Successfully connected to Realtime Blood Network."));
            logger.info("New SSE client subscribed. Total active emitters: {}", emitters.size());
        } catch (IOException e) {
            emitters.remove(emitter);
            logger.error("Failed to send initial connection event: {}", e.getMessage());
        }

        return emitter;
    }

    public void broadcast(String eventName, Object data) {
        logger.info("Broadcasting realtime event: {} with data: {}", eventName, data);
        List<SseEmitter> deadEmitters = new java.util.ArrayList<>();
        
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(data));
            } catch (Exception e) {
                logger.warn("Failed to send event to emitter. Marking for removal.");
                deadEmitters.add(emitter);
            }
        }
        
        if (!deadEmitters.isEmpty()) {
            emitters.removeAll(deadEmitters);
            logger.info("Removed {} dead emitters. Remaining: {}", deadEmitters.size(), emitters.size());
        }
    }
}

package com.wanted.preonboarding.notification.application;

import com.wanted.preonboarding.notification.emitter.SseEmitters;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class SendMessage {
    private final SseEmitters sseEmitters;
    public SendMessage(SseEmitters sseEmitters) {
        this.sseEmitters = sseEmitters;
    }

    public void add(String performanceId, SseEmitter emitter) {
        sseEmitters.add(performanceId, emitter);
    }

    public void sendEvent(String performanceId, String message) {
        sseEmitters.sendEvent(performanceId, message);
    }
}

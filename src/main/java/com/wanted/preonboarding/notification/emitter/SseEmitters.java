package com.wanted.preonboarding.notification.emitter;

import com.wanted.preonboarding.ticket.application.dto.response.ReservationCancelResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SseEmitters {
    private final Map<UUID, EmitterContainer> emitters = new ConcurrentHashMap<>();

    public EmitterContainer add(UUID emitterId, String performanceId) {
        UUID finalEmitterId =  emitterId == null ? UUID.randomUUID() : emitterId;
        SseEmitter sseEmitter = new SseEmitter();
        EmitterContainer emitterContainer = EmitterContainer.builder().id(finalEmitterId).performanceId(performanceId).emitter(sseEmitter).build();
        this.emitters.put(finalEmitterId, emitterContainer);
        log.info("새 emitter 추가: {}- {}", finalEmitterId, emitterContainer);
        log.info("emitter가 {}개", emitters.size());
        sseEmitter.onCompletion(() -> {
            log.info("onCompletion callback");
            this.emitters.remove(finalEmitterId);
        });
        sseEmitter.onTimeout(() -> {
            log.info("onTimeout callback");
            sseEmitter.complete();
        });

        return emitterContainer;
    }

    public void sendEvent(UUID to, String message) {
        EmitterContainer container = this.emitters.get(to);
        if (container == null) {
            return;
        }
        try {
            container.getEmitter().send(SseEmitter.event().name("message").data(message));
        } catch (IOException e) {
            container.getEmitter().completeWithError(e);
        }

    }

    public void sendCancelledEvent(ReservationCancelResponse cancelInfo) {
        String performanceId = cancelInfo.getPerformanceId().toString();
        List<EmitterContainer> emitterContainerList = this.emitters.values().stream().filter(entry -> entry.getPerformanceId().equals(performanceId)).toList();
        if (emitterContainerList.isEmpty()) return;
        emitterContainerList.forEach(emitterContainer ->
                {
                    try {
                        emitterContainer.getEmitter().send(SseEmitter.event().name("cancel").data(cancelInfo));
                    } catch (IOException e) {
                        emitterContainer.getEmitter().completeWithError(e);
                    }
                }
        );
    }

    public EmitterContainer getEmitterById(UUID emitterId, String performanceId) {
        if (emitterId == null || this.emitters.get(emitterId) == null) return this.add(emitterId, performanceId);
        return this.emitters.get(emitterId);

    }
}

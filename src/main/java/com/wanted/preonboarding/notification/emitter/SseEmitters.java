package com.wanted.preonboarding.notification.emitter;

import com.wanted.preonboarding.ticket.application.dto.response.ReservationCancelResponse;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
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

    public EmitterContainer add(List<Notification> notifications) {
        // 타임아웃 10분
        SseEmitter sseEmitter = new SseEmitter(1000L * 600L);
        UUID emitterId = UUID.randomUUID();
        EmitterContainer container = EmitterContainer.of(notifications,sseEmitter,emitterId);
        this.emitters.put(emitterId,container);
        log.info("새 emitter 추가: {}- {}", emitterId,container);
        log.info("emitter가 {}개", emitters.size());
        sseEmitter.onCompletion(() -> {
            log.info("onCompletion callback");
            this.emitters.remove(emitterId);
        });
        sseEmitter.onTimeout(() -> {
            log.info("onTimeout callback");
            sseEmitter.complete();
        });

        return container;
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
        List<EmitterContainer> emitterContainerList = this.emitters.values().stream().filter(entry -> entry.getPerformanceIds().contains(performanceId)).toList();
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

    public EmitterContainer getEmitter(String name, String phoneNumber){
        for (EmitterContainer container:this.emitters.values()){
             if (container.getPhoneNumber().equals(phoneNumber)&&container.getUserName().equals(name)){return container;}
        }
        return null;
    }

}

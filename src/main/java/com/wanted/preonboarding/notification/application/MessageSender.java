package com.wanted.preonboarding.notification.application;

import com.wanted.preonboarding.notification.emitter.EmitterContainer;
import com.wanted.preonboarding.notification.emitter.SseEmitters;
import com.wanted.preonboarding.ticket.application.dto.response.ReservationCancelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageSender {
    private final SseEmitters sseEmitters;

    public void add(UUID emitterId, String performanceId) {
        sseEmitters.add(emitterId, performanceId);
    }

    public EmitterContainer getEmitter(UUID emitterId, String performanceId){
        return sseEmitters.getEmitterById(emitterId,performanceId);
    }

    public void sendEvent(UUID emitterId, String message) {
        sseEmitters.sendEvent(emitterId, message);
    }
    public void sendCancelledEvent(ReservationCancelResponse response){sseEmitters.sendCancelledEvent(response);}
}

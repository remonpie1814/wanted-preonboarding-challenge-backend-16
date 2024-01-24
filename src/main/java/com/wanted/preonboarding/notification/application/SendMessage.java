package com.wanted.preonboarding.notification.application;

import com.wanted.preonboarding.notification.emitter.SseEmitters;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@RequiredArgsConstructor
public class SendMessage {
    private final SseEmitters sseEmitters;

    public void add(String performanceId, SseEmitter emitter) {
        sseEmitters.add(performanceId, emitter);
    }

    public void sendEvent(String performanceId, String message) {
        sseEmitters.sendEvent(performanceId, message);
    }
    public void sendCancelledEvent(ReserveInfo reservation){sseEmitters.sendCancelledEvent(reservation);}
}

package com.wanted.preonboarding.notification.emitter;

import com.wanted.preonboarding.ticket.domain.entity.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class EmitterContainer {
    private UUID id;
    private SseEmitter emitter;
    private List<String> performanceIds;
    private String userName;
    private String phoneNumber;

    public static EmitterContainer of(List<Notification> notifications,SseEmitter sseEmitter,UUID emitterId){
        List<String> performanceIds = notifications.stream().map(notification -> notification.getPerformance().getId().toString()).toList();
        return EmitterContainer
                .builder()
                .id(emitterId)
                .emitter(sseEmitter)
                .userName(notifications.get(0).getName())
                .performanceIds(performanceIds)
                .phoneNumber(notifications.get(0).getPhoneNumber())
                .build();
    }

    @Override
    public String toString() {
        return "EmitterContainer{" +
                "id=" + id +
                ", emitter=" + emitter +
                ", performanceId='" + performanceIds + '\'' +
                '}';
    }
}

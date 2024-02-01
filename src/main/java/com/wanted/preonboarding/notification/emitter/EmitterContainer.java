package com.wanted.preonboarding.notification.emitter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class EmitterContainer {
    private UUID id;
    private SseEmitter emitter;
    private String performanceId;
    // private String userName;


    @Override
    public String toString() {
        return "EmitterContainer{" +
                "id=" + id +
                ", emitter=" + emitter +
                ", performanceId='" + performanceId + '\'' +
                '}';
    }
}

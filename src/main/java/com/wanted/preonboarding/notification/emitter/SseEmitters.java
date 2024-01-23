package com.wanted.preonboarding.notification.emitter;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SseEmitters {
    private final Map<String, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter add(String name, SseEmitter emitter) {
        this.emitters.computeIfAbsent(name, k -> new ArrayList<SseEmitter>());
        this.emitters.get(name).add(emitter);
        log.info("new emitter added: {}- {}", name, emitter);
        log.info("emitter list size: {}", emitters.get(name).size());
        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            this.emitters.get(name).remove(emitter);
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitter.complete();
        });

        return emitter;
    }

    public void sendEvent(String to, String message) {
        List<SseEmitter> emitterList = this.emitters.get(to);
        if(emitterList==null || emitterList.size()<1){
            return;
        }
        emitterList.forEach(sseEmitter ->
                {
                    try {
                        sseEmitter.send(SseEmitter.event().name("message").data(message));
                    } catch (IOException e) {
                        sseEmitter.completeWithError(e);
                    }
                }
        );

    }

    public void sendCancelledEvent(ReserveInfo reserveInfo){
        String pid = reserveInfo.getPerformanceId().toString();
        List<SseEmitter> emitterList = this.emitters.get(pid);
        emitterList.forEach(sseEmitter ->
                {
                    try {
                        sseEmitter.send(SseEmitter.event().name("cancel").data(reserveInfo));
                    } catch (IOException e) {
                        sseEmitter.completeWithError(e);
                    }
                }
        );
    }
}

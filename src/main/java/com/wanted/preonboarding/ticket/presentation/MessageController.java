package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.notification.application.MessageSender;
import com.wanted.preonboarding.notification.emitter.EmitterContainer;
import com.wanted.preonboarding.ticket.application.dto.response.ReservationCancelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageSender messageSender;

    // performanceId와 함께 연결
    // emitterId가 비어있을 경우 새 emitter를 생성. 비어있지 않을 경우 기존 것에 연결.
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@RequestParam(required = false) UUID emitterId, @RequestParam String performanceId) {
        EmitterContainer container = messageSender.getEmitter(emitterId,performanceId);
        try {
            container.getEmitter().send(SseEmitter.event()
                    .name("connect")
                    .data(container.getId()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(container.getEmitter());
    }

    // 메시지 이벤트 트리거
    @PostMapping(value="/trigger")
    public ResponseEntity<String> trigger(@RequestBody ReservationCancelResponse response){
        messageSender.sendCancelledEvent(response);
        return ResponseEntity.ok().body("done");
    }

    // 브라우저에서 확인하기 위한 경로
    @GetMapping("/testSSE")
    public ResponseEntity<String> testSSE() {
        String htmlContent = "<html><body><script>" +
                "const sse = new EventSource(\"http://localhost:8016/connect?performanceId=8520baeb-b987-11ee-a439-0242ac120002\");" +
                "sse.addEventListener('cancel', e => {  \n" +
                "    const { data: receivedMsg } = e;  \n" +
                "    alert(receivedMsg);  \n" +
                "});" +
                "</script></body></html>";
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlContent);
    }


}

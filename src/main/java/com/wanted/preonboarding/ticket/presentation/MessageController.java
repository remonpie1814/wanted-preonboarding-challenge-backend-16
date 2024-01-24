package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.notification.application.SendMessage;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final SendMessage sendMessage;

    // performanceId와 함께 연결
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@RequestParam String performanceId) {
        SseEmitter emitter = new SseEmitter();
        sendMessage.add(performanceId,emitter);
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(emitter);
    }

    // 메시지 이벤트 트리거
    @PostMapping(value="/trigger")
    public ResponseEntity<String> trigger(@RequestBody ReserveInfo reserveInfo){
        sendMessage.sendCancelledEvent(reserveInfo);
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

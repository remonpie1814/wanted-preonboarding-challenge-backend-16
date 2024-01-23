package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.notification.application.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
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
    @GetMapping(value="/trigger")
    public ResponseEntity<String> trigger(@RequestParam String performanceId){
        sendMessage.sendEvent(performanceId,"이 공연이 취소되었어요");
        return ResponseEntity.ok().body("done");
    }

    // 브라우저에서 확인하기 위한 경로
    @GetMapping("/testSSE")
    public ResponseEntity<String> exampleMethod() {
        String htmlContent = "<html><body><script>" +
                "const sse = new EventSource(\"http://localhost:8016/connect?performanceId=1\");" +
                "sse.addEventListener('message', e => {  \n" +
                "    const { data: receivedMsg } = e;  \n" +
                "    alert(receivedMsg);  \n" +
                "});" +
                "</script></body></html>";
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlContent);
    }


}

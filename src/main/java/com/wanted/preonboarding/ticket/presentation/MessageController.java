package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.notification.application.MessageSender;
import com.wanted.preonboarding.notification.emitter.EmitterContainer;
import com.wanted.preonboarding.ticket.application.dto.request.NotificationSubscribeRequest;
import com.wanted.preonboarding.ticket.application.dto.response.ReservationCancelResponse;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageSender messageSender;

    @PostMapping(value = "/subscribe")
    public ResponseEntity<ResponseHandler<Notification>> subscribe(@RequestBody NotificationSubscribeRequest request) {
        return ResponseEntity.ok()
                .body(ResponseHandler.<Notification>builder().data(messageSender.subscribe(request)).build());
    }

    @DeleteMapping(value = "/subscribe")
    public ResponseEntity<ResponseHandler<String>> unsubscribe(@RequestBody NotificationSubscribeRequest request) {
        messageSender.unsubscribe(request);
        return ResponseEntity.ok()
                .body(ResponseHandler.<String>builder().data("unsubscribed!").build());
    }


    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@RequestParam String name, @RequestParam String phoneNumber) {
        EmitterContainer container = messageSender.getEmitter(name, phoneNumber);
        try {
            container.getEmitter().send(SseEmitter.event()
                    .name("connect")
                    .data("connected!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(container.getEmitter());
    }

    // 메시지 이벤트 트리거
    @PostMapping(value = "/trigger")
    public ResponseEntity<String> trigger(@RequestBody ReservationCancelResponse response) {
        messageSender.sendCancelledEvent(response);
        return ResponseEntity.ok().body("done");
    }

    // 브라우저에서 확인하기 위한 경로
    @GetMapping("/testSSE")
    public ResponseEntity<String> testSSE() {
        String htmlContent = "<html><body><script>" +
                "const sse = new EventSource(\"http://localhost:8016/connect?name=yeji&phoneNumber=010-1111-2222\");" +
                "sse.addEventListener('cancel', e => {  \n" +
                "    const { data: receivedMsg } = e;  \n" +
                "    alert(receivedMsg);  \n" +
                "});" +
                "</script></body></html>";
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlContent);
    }


}

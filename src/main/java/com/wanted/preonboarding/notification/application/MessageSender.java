package com.wanted.preonboarding.notification.application;

import com.wanted.preonboarding.core.exception.NotFoundException;
import com.wanted.preonboarding.notification.emitter.EmitterContainer;
import com.wanted.preonboarding.notification.emitter.SseEmitters;
import com.wanted.preonboarding.ticket.application.dto.request.NotificationSubscribeRequest;
import com.wanted.preonboarding.ticket.application.dto.response.ReservationCancelResponse;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.infrastructure.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageSender {
    private final SseEmitters sseEmitters;
    private final NotificationRepository notificationRepository;

    public EmitterContainer getEmitter(String name, String phoneNumber) {
        EmitterContainer container = sseEmitters.getEmitter(name, phoneNumber);
        if(container==null){
            container = sseEmitters.add(getNotificationListByNameAndPhone(name, phoneNumber));
        }
        return container;
    }

    public void sendEvent(UUID emitterId, String message) {
        sseEmitters.sendEvent(emitterId, message);
    }

    public void sendCancelledEvent(ReservationCancelResponse response) {
        sseEmitters.sendCancelledEvent(response);
    }


    public List<Notification> getNotificationListByNameAndPhone(String name, String phoneNumber) {
        return notificationRepository.findByNameAndPhoneNumber(name, phoneNumber);
    }

    public Notification subscribe(NotificationSubscribeRequest request) {
        EmitterContainer container = sseEmitters.getEmitter(request.getName(), request.getPhoneNumber());
        if (container!=null){
            List<String> performanceIds = new ArrayList<String>(container.getPerformanceIds());
            performanceIds.add(request.getPerformanceId());
            container.setPerformanceIds(performanceIds);
        }
        return notificationRepository.save(request.getNotification());
    }

    public void unsubscribe(NotificationSubscribeRequest request) {
        Notification notification = notificationRepository.findOneByNameAndPhoneNumberAndPerformanceId(request.getName(), request.getPhoneNumber(), UUID.fromString(request.getPerformanceId()))
                .orElseThrow(() -> new NotFoundException("구독 내용"));
        notificationRepository.delete(notification);
    }
}

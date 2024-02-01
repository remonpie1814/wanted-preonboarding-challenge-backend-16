package com.wanted.preonboarding.ticket.application.dto.request;

import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationSubscribeRequest {
    private String name;
    private String phoneNumber;
    private String performanceId;

    public Notification getNotification(){
        return Notification
                .builder()
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .performance(Performance.builder().id(UUID.fromString(this.performanceId)).build())
                .build();
    }
}

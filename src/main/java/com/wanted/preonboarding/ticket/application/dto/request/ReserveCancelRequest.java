package com.wanted.preonboarding.ticket.application.dto.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReserveCancelRequest {
    private String performanceId;
    private int round;
    private char line;
    private int seat;
}

package com.wanted.preonboarding.ticket.application.dto.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReserveResponse {
    private int round;
    private String performanceName;
    private char line;
    private int seat;
    private String reservationName;
    private String phoneNumber;
}

package com.wanted.preonboarding.ticket.application.dto.request;

import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReserveRequest {
    private String name;
    private String phoneNumber;
    private int amount;
    private UUID performanceId;
    private int round;
    private char line;
    private int Seat;

}

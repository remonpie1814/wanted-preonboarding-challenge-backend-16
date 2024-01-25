package com.wanted.preonboarding.ticket.application.dto.request;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.User;
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
    private String performanceId;
    private int round;
    private char line;
    private int seat;

    public Reservation getReservation(){
        return Reservation
                .builder()
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .performanceId(UUID.fromString(this.performanceId))
                .round(this.round)
                .line(this.line)
                .seat(this.seat)
                .gate(1)
                .build();

    }

    public User getUser(){
        return User
                .builder()
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .amount(this.amount)
                .build();
    }

    public PerformanceSeatInfo getSeatInfo(){
        return PerformanceSeatInfo
                .builder()
                .round(this.round)
                .line(this.line)
                .seat(this.seat)
                .performanceId(UUID.fromString(this.performanceId))
                .build();
    }

}

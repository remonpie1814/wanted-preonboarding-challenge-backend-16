package com.wanted.preonboarding.ticket.application.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.User;
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

    public static ReserveResponse of(Reservation reservation, Performance performance){
        return ReserveResponse
                .builder()
                .round(reservation.getRound())
                .performanceName(performance.getName())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .reservationName(reservation.getName())
                .phoneNumber(reservation.getPhoneNumber())
                .build();

    }

}

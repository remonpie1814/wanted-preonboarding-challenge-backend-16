package com.wanted.preonboarding.ticket.application.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationFindResponse {
    private String performanceName;
    private UUID performanceId;
    private int round;
    private char line;
    private int seat;
    private String reservationName;
    private String reservationPhoneNumber;

    public static ReservationFindResponse of(Reservation reservation, String performanceName) {
        return ReservationFindResponse
                .builder()
                .performanceId(reservation.getPerformanceId())
                .performanceName(performanceName)
                .round(reservation.getRound())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .reservationName(reservation.getName())
                .reservationPhoneNumber(reservation.getPhoneNumber())
                .build();

    }
}

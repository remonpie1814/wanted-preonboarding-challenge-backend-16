package com.wanted.preonboarding.ticket.application.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReserveCancelResponse {
    private UUID performanceId;
    private String performanceName;
    private Date startDate;
    private int round;
    private char line;
    private int seat;

    public static ReserveCancelResponse of(Reservation reservation, Performance performance) {
        return ReserveCancelResponse
                .builder()
                .performanceId(reservation.getPerformanceId())
                .performanceName(performance.getName())
                .startDate(performance.getStart_date())
                .round(reservation.getRound())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .build();
    }
}

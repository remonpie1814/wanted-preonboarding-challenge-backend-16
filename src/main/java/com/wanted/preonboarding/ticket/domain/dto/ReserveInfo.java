package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId;
    private String performanceName;
    private String reservationName;
    private String reservationPhoneNumber;
    private String reservationStatus; // 예약; 취소;
    private long amount;
    private int round;
    private char line;
    private int seat;


    public static ReserveInfo of(Reservation entity, String performanceName) {
        return ReserveInfo.builder()
                .performanceId(entity.getPerformanceId())
                .performanceName(performanceName)
                .reservationName(entity.getName())
                .reservationPhoneNumber(entity.getPhoneNumber())
                .round(entity.getRound())
                .line(entity.getLine())
                .seat(entity.getSeat())
                .build();
    }

    public static ReserveInfo of(ReserveInfo entity, String performanceName) {
        return ReserveInfo.builder()
                .performanceId(entity.getPerformanceId())
                .performanceName(performanceName)
                .reservationName(entity.getReservationName())
                .reservationPhoneNumber(entity.getReservationPhoneNumber())
                .amount(entity.getAmount())
                .round(entity.getRound())
                .line(entity.getLine())
                .seat(entity.getSeat())
                .build();
    }

}

package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.notification.application.SendMessage;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.application.dto.request.ReserveCancelRequest;
import com.wanted.preonboarding.ticket.application.dto.request.ReserveRequest;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveCancelResponse;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveResponse;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;
    private final SendMessage sendMessage;

    // 예약
    @PostMapping("/")
    public ResponseEntity<ResponseHandler<ReserveResponse>> reserve(@RequestBody ReserveRequest request) {
        System.out.println("reserve...");

        return ResponseEntity
                .ok()
                .body(ResponseHandler.<ReserveResponse>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(ticketSeller.reserve(request))
                        .build()
                );


    }

    // 조회
    @PostMapping("/inquiry")
    public ResponseEntity<ResponseHandler<List<ReserveInfo>>> reservation(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String phoneNumber = request.get("phoneNumber");
        // note: 리스트를 돌며 공연 ID로 Name을 가져오는 쿼리를 보내는데 이럴 바에야 그냥 JOIN연산을 하는 게 낫지 않을까?
        List<ReserveInfo> reservations = ticketSeller.getReservationByNameAndPhone(name, phoneNumber)
                .stream()
                .map(e -> ReserveInfo.of(e, ticketSeller.getPerformanceName(e.getPerformanceId())))
                .toList();

        return ResponseEntity
                .ok()
                .body(ResponseHandler.<List<ReserveInfo>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(reservations)
                        .build()
                );
    }

    // 예약 취소
    @PostMapping("/cancel")
    public ResponseEntity<ResponseHandler<String>> cancel(@RequestBody ReserveCancelRequest request) {
        System.out.println("cancel...");

        ReserveCancelResponse response =  ticketSeller.cancelReservation(request);

        sendMessage.sendCancelledEvent(response);

        return ResponseEntity
                .ok()
                .body(ResponseHandler.<String>builder()
                        .statusCode(HttpStatus.OK)
                        .data("canceled")
                        .build()
                );
    }

}

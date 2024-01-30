package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.core.exception.NotEnoughAmountException;
import com.wanted.preonboarding.core.exception.NotFoundException;
import com.wanted.preonboarding.notification.application.MessageSender;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.application.dto.request.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.application.dto.request.ReservationFindRequest;
import com.wanted.preonboarding.ticket.application.dto.request.ReserveRequest;
import com.wanted.preonboarding.ticket.application.dto.response.ReservationFindResponse;
import com.wanted.preonboarding.ticket.application.dto.response.ReservationCancelResponse;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveResponse;
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
    private final MessageSender messageSender;

    // 예약
    @PostMapping("/")
    public ResponseEntity<ResponseHandler<ReserveResponse>> reserve(@RequestBody ReserveRequest request) throws NotEnoughAmountException, NotFoundException {
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

    // 예약 조회
    @PostMapping("/find")
    public ResponseEntity<ResponseHandler<List<ReservationFindResponse>>> reservation(@RequestBody ReservationFindRequest request) {
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<List<ReservationFindResponse>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(
                                ticketSeller.getReservationByNameAndPhone(request.getName(),request.getPhoneNumber())
                                .stream()
                                .map(ReservationFindResponse::of)
                                .toList()
                        )
                        .build()
                );
    }

    // 예약 취소
    @PostMapping("/cancel")
    public ResponseEntity<ResponseHandler<ReservationCancelResponse>> cancel(@RequestBody ReservationCancelRequest request) throws NotFoundException {
        System.out.println("cancel...");
        ReservationCancelResponse response =  ticketSeller.cancelReservation(request);
        messageSender.sendCancelledEvent(response);

        return ResponseEntity
                .ok()
                .body(ResponseHandler.<ReservationCancelResponse>builder()
                        .statusCode(HttpStatus.OK)
                        .data(response)
                        .build()
                );
    }

}
